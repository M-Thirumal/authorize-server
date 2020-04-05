package in.thirumal.security.otp;

/**
 * @author Thirumal
 * @version 1.0
 */
public interface OtpGenerator {
	/**
	 * Generates a random token.
	 */
	public String generateToken(int length);
	
}
