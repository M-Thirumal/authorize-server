package in.thirumal.security;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.thirumal.exception.AuthorizeException;
import in.thirumal.exception.ErrorFactory;
import in.thirumal.persistence.dao.LoginIdentifierDao;
import in.thirumal.persistence.dao.PasswordDao;
import in.thirumal.persistence.model.LoginIdentifier;
import in.thirumal.persistence.model.Password;
import in.thirumal.persistence.model.shared.Identifier;

/**
 * @author Thirumal
 * @since 14/04/2017
 * @version 1.0
 * User id can be either E-mail or Mobile number
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private LoginIdentifierDao loginIdentifierDao;
	private PasswordDao passwordDao;
	//@Autowired
    //private HttpServletRequest request;
	//
	
	@Autowired
	public UserDetailsServiceImpl (LoginIdentifierDao loginIdentifierDao, PasswordDao passwordDao) {
		this.loginIdentifierDao = loginIdentifierDao;
		this.passwordDao = passwordDao;
	}
	/**
	 *  Password verification will be done by spring itself
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		LoginIdentifier loginUser = loginIdentifierDao.getV1(Identifier.builder().text(username.toLowerCase()).build(), LoginIdentifierDao.BY_IDENTIFIER)
				.orElseThrow(()->new AuthorizeException(ErrorFactory.UNAUTHORIZED, "User id is not found"));	 //Always lower case
		Password password = passwordDao.getV1(Identifier.builder().pk(loginUser.getLoginId()).build(), PasswordDao.BY_LOGIN_ID).orElseThrow(() ->
				new AuthorizeException(ErrorFactory.UNAUTHORIZED, "Password is not found"));
		boolean credentialsNonExpired = credentialsNonExpired(password);
		boolean accountNonLocked = true;//accountNonLocked(loginUser);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		actuator(loginUser, grantedAuthorities);
		return new User(loginUser.getIdentifier(), password.getSecret(), true, true, credentialsNonExpired, accountNonLocked, grantedAuthorities);
	}
	/*
	@SuppressWarnings("unused")
	private String getClientIP() {
	    String xfHeader = request.getHeader("X-Forwarded-For");
	    if (xfHeader == null){
	        return request.getRemoteAddr();
	    }
	    return xfHeader.split(",")[0];
	}*/
	
//	private boolean accountNonExpired() {
//		// TODO Check user it account is non expired
//		return true;
//	}
//	private boolean accountNonLocked(LoginUser loginUser) {
//		if (!loginUser.isUserLock() && (loginUser.getFailureCount() == null || loginUser.getFailureCount().compareTo(Integer.valueOf(6)) < 1)) {
//			return true;
//		}
//		logger.debug("The requested user account is locked: {}", loginUser);
//		return false;
//	}
	private boolean credentialsNonExpired(Password password) {
		Period period = Period.between(password.getStartTime().toLocalDate(), LocalDate.now());
		if (period.getYears() == 0 && period.getMonths() < 3) {
			return true;
		}
		logger.debug("The user is not changed the password since " + period.getYears() + " YEARS " + period.getMonths() 
			+ " MONTHS " + period.getDays() + " DAYS.");
		return false;
	}
	
	/**
	 * @param loginUser
	 * @param grantedAuthorities
	 */
	private void actuator(LoginIdentifier loginUser, Set<GrantedAuthority> grantedAuthorities) {
		//ACTUATOR
		if (loginUser.getIdentifier().equalsIgnoreCase("m.thirumal@hotmail.com")) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ACTUATOR"));
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_SYSADMIN"));
		}
	}	
	
}
