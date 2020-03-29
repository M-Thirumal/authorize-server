package in.thirumal.service.rest;

import java.util.List;

import in.thirumal.persistence.model.shared.Identifier;
import in.thirumal.service.GenericPartyService;
import in.thirumal.service.resource.UserResource;

/**
 * @author Thirumal
 *
 */
public class UserService implements GenericPartyService<UserResource, Identifier, Boolean> {

	@Override
	public UserResource create(UserResource resource, Identifier identifier, Boolean isVerified) {
		// TODO Auto-generated method stub
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
