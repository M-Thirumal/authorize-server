/**
 * 
 */
package in.thirumal.service.resource;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Thirumal
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter
@Builder@ToString
public class UserResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2360529880458726676L;
	
	private Long partyId;
	

}
