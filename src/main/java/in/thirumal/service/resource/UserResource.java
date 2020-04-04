/**
 * 
 */
package in.thirumal.service.resource;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

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
	private UUID partyUuid;
	private String firstName;
	private String lastName;
	private Integer genderCd;
	private String genderLocale;
	private OffsetDateTime birthDate;
	private Integer loginIdentifierCd;
	private String loginIdentifierLocale;
	private String loginIdentifier;
	private String secret;

}
