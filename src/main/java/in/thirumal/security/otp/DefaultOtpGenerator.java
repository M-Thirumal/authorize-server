/**
 * 
 */
package in.thirumal.security.otp;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

/**
 * @author Thirumal
 * @version 1.0
 */
@Component
public class DefaultOtpGenerator implements OtpGenerator {
	
	private static final char[] CHARS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9'};	
	/* (non-Javadoc)
	 * @see com.enkindle.security.otp.OtpGenerator#generateToken()
	 */
	@Override
	public String generateToken(int length) {
		Random rand = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(CHARS[rand.nextInt(CHARS.length)]);
		}
		return sb.toString();
	}

}
