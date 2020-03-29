package in.thirumal.service.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.thirumal.persistence.GenericDao;
import in.thirumal.persistence.model.LoginIdentifier;
import in.thirumal.persistence.model.Party;
import in.thirumal.persistence.model.shared.Identifier;
import in.thirumal.service.GenericPartyService;
import in.thirumal.service.resource.UserResource;

/**
 * @author Thirumal
 *
 */
@Service
public class UserService implements GenericPartyService<UserResource, Identifier, Boolean> {

	@Autowired
	private GenericDao<Party, Identifier, String> partyDao;
	private GenericDao<LoginIdentifier, Identifier, String> loginIdentifierDao;
	
	@Override
	public UserResource create(UserResource userResource, Identifier identifier, Boolean isVerified) {
		
		return null;
	}

	@Override
	public UserResource update(UserResource resource, Identifier identifier, Boolean isVerified) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResource get(Identifier identifier, Boolean isVerified) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResource getForList(Identifier identifier, Boolean isVerified) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResource forList(UserResource t, Identifier identifier, Boolean isVerified) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserResource> list(Identifier identifier, Boolean isVerified) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Identifier identifier, Boolean isVerified) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserResource activate(Identifier identifier, Boolean isVerified) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResource inActivate(Identifier identifier, Boolean isVerified) {
		// TODO Auto-generated method stub
		return null;
	}

}
