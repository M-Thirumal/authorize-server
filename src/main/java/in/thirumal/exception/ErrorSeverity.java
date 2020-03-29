package in.thirumal.exception;
/**
 * 
 * @author திருமால்
 *
 */
public enum ErrorSeverity {
	
	FATAL,
	NONFATAL;
	
	public static ErrorSeverity getErrorSeverity(String severity){
		if(severity.equals("FATAL")) {
			return FATAL;
		}
		if(severity.equals("NONFATAL")) {
			return NONFATAL;
		}
		return NONFATAL;
	}
	
}
