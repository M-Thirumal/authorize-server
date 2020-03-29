package in.thirumal.persistence.model;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * Generated using erm-postgresql-spring-boot project
 * @see <a href="https://github.com/M-Thirumal/erm-postgresql-spring-boot">erm-postgresql-spring-boot</a>
 * @author erm-postgresql-spring-boot
 * @since 2020-03-29
 * @version 1.0
 */
@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
@ToString@Builder
@EqualsAndHashCode
public class Password implements java.io.Serializable  {

	private static final long serialVersionUID = 1L;

	//Declaring fields
	private Long passwordId;
	private Long loginId;
	private String secret;
	private OffsetDateTime startTime;
	private OffsetDateTime endTime;

	//TODO hashcode & equals method
}
