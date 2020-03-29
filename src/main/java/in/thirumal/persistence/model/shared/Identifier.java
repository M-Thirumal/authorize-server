/**
 * 
 */
package in.thirumal.persistence.model.shared;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Thirumal
 * @since 03/07/2017
 * @version 1.0
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter
@ToString @Builder
public class Identifier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2442778896121136685L;
	private Long pk;
	private Long fk;
	private String text;
	private Long localeCd;
	private UUID uuid;
	private Identifier parentIdentifier; //locale Identifier
	private Pageable pageable;
	
	public Identifier(Long pk, Long localeCd) {
		super();
		this.pk = pk;
		this.localeCd = localeCd;
	}
	
}
