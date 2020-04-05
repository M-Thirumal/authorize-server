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
public class LoginQuestion implements java.io.Serializable  {

	private static final long serialVersionUID = 1L;

	//Declaring fields
	private Long loginQuestionId;
	private Long loginId;
	private long genericCd;
	private String genericLocale;
	private String answer;
	private OffsetDateTime startTime;
	private OffsetDateTime endTime;
	@EqualsAndHashCode.Exclude
	private OffsetDateTime rowCreationTime;
	@EqualsAndHashCode.Exclude
	private OffsetDateTime rowUpdateTime;
	private String rowUpdateInfo;

	//TODO hashcode & equals method
}
