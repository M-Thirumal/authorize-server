/**
 * 
 */
package in.thirumal.exception;

import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;

/**
 * @author thirumal
 *
 */
public class InvalidToken extends InvalidTokenException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8238118091856637453L;

	public InvalidToken(String msg, Throwable t) {
		super(msg, t);
	}

	public InvalidToken(String msg) {
		super(msg);
	}

	@Override
	public int getHttpErrorCode() {
		return 401;
	}

	public String getOAuth2ErrorCode() {
		return "Session Expired";
	}

}
