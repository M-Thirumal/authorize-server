package in.thirumal.exception;

import java.security.Timestamp;

import lombok.ToString;

/**
 * @author திருமால்
 * @since 15/04/2017
 * @version 1.0
 */
@ToString
public class AuthorizeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private ErrorDefinition errorDefinition;
	private Timestamp timestamp;
	
	public AuthorizeException(String message) {
		super(message);
	}
	
	public AuthorizeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AuthorizeException(ErrorDefinition definition) {
		this.errorDefinition = definition;
	}
	
	public AuthorizeException(ErrorDefinition definition, String message) {
		super(message);
		this.errorDefinition = definition;
		this.errorDefinition.setMessage(message);		
	}

	/**
	 * @return the errorDefinition
	 */
	public ErrorDefinition getErrorDefinition() {
		return errorDefinition;
	}

	/**
	 * @param errorDefinition the errorDefinition to set
	 */
	public void setErrorDefinition(ErrorDefinition errorDefinition) {
		this.errorDefinition = errorDefinition;
	}

	/**
	 * @return the timestamp
	 */
	public Timestamp getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
