package in.thirumal.persistence.model.shared;

import java.time.LocalDate;
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
 * @since 2020-04-04
 * @version 1.0
 */
@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
@ToString@Builder
@EqualsAndHashCode
public class GenericCd implements java.io.Serializable  {

	private static final long serialVersionUID = 1L;

	//Declaring fields
	private Long genericCd;
	private String genericLocale;
	private Integer localeCd;
	private String code;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer sequence;
	@EqualsAndHashCode.Exclude
	private OffsetDateTime rowCreationTime;
	@EqualsAndHashCode.Exclude
	private String rowCreatedBy;
	@EqualsAndHashCode.Exclude
	private String rowUpdatedBy;
	@EqualsAndHashCode.Exclude
	private OffsetDateTime rowUpdateTime;
	private String rowUpdateInfo;
	private Integer parentGenericCd;
	private String parentGenericLocale;

	//TODO hashcode & equals method
}
