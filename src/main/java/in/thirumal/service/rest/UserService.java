package in.thirumal.service.rest;

import java.time.OffsetDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.thirumal.exception.AuthorizeException;
import in.thirumal.exception.ErrorFactory;
import in.thirumal.persistence.GenericDao;
import in.thirumal.persistence.dao.LoginIdentifierDao;
import in.thirumal.persistence.dao.PasswordDao;
import in.thirumal.persistence.model.Login;
import in.thirumal.persistence.model.LoginIdentifier;
import in.thirumal.persistence.model.Party;
import in.thirumal.persistence.model.PartyName;
import in.thirumal.persistence.model.Password;
import in.thirumal.persistence.model.shared.Identifier;
import in.thirumal.service.GenericPartyService;
import in.thirumal.service.resource.UserResource;

/**
 * @author Thirumal
 *
 */
@Service
public class UserService implements GenericPartyService<UserResource, Identifier> {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private GenericDao<Party, Identifier, String> partyDao;
	@Autowired
	private GenericDao<PartyName, Identifier, String> partyNameDao;
	@Autowired
	private GenericDao<Login, Identifier, String> loginDao;
	@Autowired
	private GenericDao<Password, Identifier, String> passwordDao;
	@Autowired
	private GenericDao<LoginIdentifier, Identifier, String> loginIdentifierDao;
	
	@Transactional
	@Override
	public UserResource create(UserResource userResource, Identifier identifier) {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		validateUserId(userResource);
		//
		Party party = partyDao.create(Party.builder().birthDate(userResource.getBirthDate() == null ? OffsetDateTime.now() :
			userResource.getBirthDate()).build(), identifier);
		//
		if (userResource.getFirstName() != null || userResource.getLastName() != null) {
			partyNameDao.create(PartyName.builder().partyId(party.getPartyId()).firstName(userResource.getFirstName())
				.restOfName(userResource.getLastName()).preferred(true).genericCd(PartyName.DEFAULT_NAME_TYPE_CD)
				.startTime(party.getBirthDate()).build(), identifier);
		}
		//
		Login login = loginDao.create(Login.builder().partyId(party.getPartyId()).build(), identifier);
		//
		LoginIdentifier loginIdentifier = loginIdentifierDao.create(LoginIdentifier.builder()
				.loginId(login.getLoginId()).genericCd(userResource.getLoginIdentifierCd()).identifier(userResource.getLoginIdentifier())
				.startTime(OffsetDateTime.now()).build(), identifier);
		//
		passwordDao.create(Password.builder().loginId(login.getLoginId()).secret(userResource.getSecret())
				.startTime(OffsetDateTime.now()).build(), identifier);
		
		return fillResource(party, loginIdentifier);
	}

	private UserResource fillResource(Party party, LoginIdentifier loginIdentifier) {
		return UserResource.builder().partyId(party.getPartyId()).partyUuid(party.getPartyUuid()).birthDate(party.getBirthDate())
				.loginIdentifierCd(loginIdentifier.getGenericCd()).loginIdentifier(loginIdentifier.getIdentifier()).build();
	}

	private void validateUserId(UserResource userResource) {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (loginIdentifierDao.getV1(Identifier.builder().localeCd(3).text(userResource.getLoginIdentifier()).build(), 
				LoginIdentifierDao.BY_IDENTIFIER).isPresent()) {
			throw new AuthorizeException(ErrorFactory.BAD_REQUEST, "The requested id is already claimed");
		}		
	}
	
	@Transactional
	public boolean changePassword(UserResource userResource, Identifier identifier) {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		LoginIdentifier loginIdentifier = loginIdentifierDao.getV1(Identifier.builder().localeCd(3).text(userResource.getLoginIdentifier()).build(), 
				LoginIdentifierDao.BY_IDENTIFIER).orElseThrow(()->new AuthorizeException(ErrorFactory.BAD_REQUEST, 
						"The requested user id is available"));
		List<Password> passwords = passwordDao.list(Identifier.builder().pk(loginIdentifier.getLoginId()).build(), PasswordDao.BY_LAST_3);
		passwords.forEach(System.out::println);
		if (passwords.stream().anyMatch(p -> passwordEncoder.matches(userResource.getSecret(), p.getSecret()))) {
			throw new AuthorizeException(ErrorFactory.RESOURCE_FAILED_VALIDATION, "Password must not match with last 3 password");
		}
		//
		Password password = passwordDao.getV1(Identifier.builder().pk(loginIdentifier.getLoginId()).build(), PasswordDao.BY_LOGIN_ID)
				.orElseThrow(()->new AuthorizeException(ErrorFactory.BAD_REQUEST, 
						"The requested user not set the password"));
		password.setEndTime(OffsetDateTime.now());
		passwordDao.update(password, identifier);
		passwordDao.create(Password.builder().loginId(loginIdentifier.getLoginId()).startTime(OffsetDateTime.now())
				.secret(userResource.getSecret()).build(), identifier);
		return Boolean.TRUE;
	}

	@Override
	public UserResource update(UserResource resource, Identifier identifier) {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return null;
	}

	@Override
	public UserResource get(Identifier identifier) {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return null;
	}

	@Override
	public UserResource getForList(Identifier identifier) {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return null;
	}

	@Override
	public UserResource forList(UserResource t, Identifier identifier) {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return null;
	}

	@Override
	public List<UserResource> list(Identifier identifier) {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return null;
	}

	@Override
	public boolean delete(Identifier identifier) {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return false;
	}

	@Override
	public UserResource activate(Identifier identifier) {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return null;
	}

	@Override
	public UserResource inActivate(Identifier identifier) {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		return null;
	}

}
