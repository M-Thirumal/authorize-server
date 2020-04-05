package in.thirumal.service.rest;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.thirumal.exception.AuthorizeException;
import in.thirumal.exception.ErrorFactory;
import in.thirumal.persistence.GenericDao;
import in.thirumal.persistence.dao.LoginIdentifierDao;
import in.thirumal.persistence.model.Login;
import in.thirumal.persistence.model.LoginIdentifier;
import in.thirumal.persistence.model.Party;
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

	@Autowired
	private GenericDao<Party, Identifier, String> partyDao;
	@Autowired
	private GenericDao<Login, Identifier, String> loginDao;
	@Autowired
	private GenericDao<Password, Identifier, String> passwordDao;
	@Autowired
	private GenericDao<LoginIdentifier, Identifier, String> loginIdentifierDao;
	
	@Transactional
	@Override
	public UserResource create(UserResource userResource, Identifier identifier) {
		validateUserId(userResource);
		Party party = partyDao.create(Party.builder().birthDate(OffsetDateTime.now()).build(), identifier);
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
		if (loginIdentifierDao.getV1(Identifier.builder().localeCd(3).text(userResource.getLoginIdentifier()).build(), 
				LoginIdentifierDao.BY_IDENTIFIER).isPresent()) {
			throw new AuthorizeException(ErrorFactory.BAD_REQUEST, "The requested id is already claimed");
		}
		
	}

	@Override
	public UserResource update(UserResource resource, Identifier identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResource get(Identifier identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResource getForList(Identifier identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResource forList(UserResource t, Identifier identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserResource> list(Identifier identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Identifier identifier) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserResource activate(Identifier identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResource inActivate(Identifier identifier) {
		// TODO Auto-generated method stub
		return null;
	}

}
