package in.thirumal.persistence.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import in.thirumal.exception.AuthorizeException;
import in.thirumal.exception.ErrorFactory;
import in.thirumal.persistence.GenericDao;
import in.thirumal.persistence.model.PartyType;
import in.thirumal.persistence.model.shared.Identifier;

/**
 * Generated using erm-postgresql-spring-boot project
 * @see <a href="https://github.com/M-Thirumal/erm-postgresql-spring-boot">erm-postgresql-spring-boot</a>
 * @author erm-postgresql-spring-boot
 * @since 2020-03-29
 * @version 1.0
 */
@Repository
public class PartyTypeDao implements GenericDao <PartyType, Identifier, String>  {

	private static final String CREATE    = "PartyType.create";
	private static final String GET_BY    = "PartyType.getBy";
	private static final String LIST_BY   = "PartyType.listBy";
	private static final String DELETE_BY = "PartyType.deleteBy";

	private final JdbcTemplate jdbcTemplate;
	private Environment environment;

	@Autowired
	public PartyTypeDao(JdbcTemplate jdbcTemplate, Environment environment) {
		this.jdbcTemplate = jdbcTemplate;
		this.environment = environment;
	}

	@Override
	public PartyType create(PartyType partytype, Identifier identifier) { 
		return createV1(partytype, identifier).orElse(null);
	}

	@Override
	public Optional<PartyType> createV1(PartyType partytype, Identifier identifier) { 
		return getV1(new Identifier(insert(partytype, identifier), identifier.getLocaleCd()));
	}

	@Override
	public Long insert(PartyType partytype, Identifier identifier) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(con ->
			setPreparedStatement(partytype, con.prepareStatement(environment.getProperty(CREATE),
					new String[] { "party_type_id" })), holder);
		return holder.getKey().longValue();
	}

	public PreparedStatement setPreparedStatement(PartyType partytype, PreparedStatement ps) throws SQLException {
		if(partytype.getPartyId() == null) {
			ps.setObject(1, null);
		} else { 
			ps.setLong(1, partytype.getPartyId());
		}
		if(partytype.getGenericCd() == null) {
			ps.setObject(2, null);
		} else { 
			ps.setInt(2, partytype.getGenericCd());
		}
		if(partytype.getStartTime() == null) {
			ps.setObject(3, null);
		} else { 
			ps.setObject(3, partytype.getStartTime());
		}
		if(partytype.getEndTime() == null) {
			ps.setObject(4, null);
		} else { 
			ps.setObject(4, partytype.getEndTime());
		}
		if(partytype.getRowCreationTime() == null) {
			ps.setObject(5, null);
		} else { 
			ps.setObject(5, partytype.getRowCreationTime());
		}
		if(partytype.getRowUpdateTime() == null) {
			ps.setObject(6, null);
		} else { 
			ps.setObject(6, partytype.getRowUpdateTime());
		}
		if(partytype.getRowUpdateInfo() == null) {
			ps.setObject(7, null);
		} else { 
			ps.setString(7, partytype.getRowUpdateInfo());
		}
		return ps;
	}

	/*Bulk/Batch Insert*/
	public void insertBatch(List<PartyType> partytypeList) {
		jdbcTemplate.batchUpdate(environment.getProperty(CREATE), new BatchPreparedStatementSetter() {
			@Override
		    public void setValues(PreparedStatement ps, int i) throws SQLException {
				PartyType partytype = partytypeList.get(i);
				setPreparedStatement(partytype, ps);
			}
			@Override
		    public int getBatchSize() {
		        return partytypeList.size();
			}
		});
	}

	@Override
	public PartyType get(Identifier identifier) {
		return getV1(identifier).orElse(null);
	}

	@Override
	public Optional<PartyType> getV1(Identifier identifier) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("PartyType.get"), new Object[] {
				identifier.getLocaleCd(),
				identifier.getPk()
			}, partytypeRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public PartyType get(Identifier identifier, String whereClause) {
		return getV1(identifier, whereClause).orElse(null);
	}

	@Override
	public Optional<PartyType> getV1(Identifier identifier, String whereClause) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty(GET_BY + whereClause), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			}, partytypeRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<PartyType> list(Identifier identifier) {
		try {
			return jdbcTemplate.query(environment.getProperty("PartyType.list"), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, partytypeRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public List<PartyType> list(Identifier identifier, String whereClause) {
		try {
			return jdbcTemplate.query(environment.getProperty(LIST_BY + whereClause), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, partytypeRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public PartyType update(PartyType partytype, Identifier identifier) {
		return updateV1(partytype, identifier).orElse(null);
	}

	@Override
	public Optional<PartyType> updateV1(PartyType partytype, Identifier identifier) {
		jdbcTemplate.update(environment.getProperty("PartyType.update"), 
			partytype.getPartyId(),
			partytype.getGenericCd(),
			partytype.getStartTime(),
			partytype.getEndTime(),
			partytype.getRowCreationTime(),
			partytype.getRowUpdateTime(),
			partytype.getRowUpdateInfo(),
			partytype.getPartyTypeId());
		return getV1(new Identifier(partytype.getPartyTypeId(), identifier.getLocaleCd()));
	}

	@Override
	public int count(Identifier identifier, String whereClause) {
		return 0;
	}

	@Override
	public int delete(PartyType partytype) {
		return jdbcTemplate.update(environment.getProperty("PartyType.delete"), partytype.getPartyTypeId());
	}

	@Override
	public int deleteV1(Optional<PartyType> partytype) {
		return delete(partytype.orElseThrow(()->new AuthorizeException(ErrorFactory.RESOURCE_NOT_FOUND, "PartyType is not available")));
	}

	@Override
	public int delete(Identifier identifier, String whereClause) {
		return jdbcTemplate.update(environment.getProperty(DELETE_BY + whereClause), identifier.getPk());
	}

	RowMapper<PartyType> partytypeRowMapper = (rs, rowNum) -> {

		PartyType partytype = new PartyType();

		partytype.setPartyTypeId(rs.getObject("party_type_id") != null ? rs.getLong("party_type_id") : null);

		partytype.setPartyId(rs.getObject("party_id") != null ? rs.getLong("party_id") : null);

		partytype.setGenericCd(rs.getObject("generic_cd") != null ? rs.getInt("generic_cd") : null);

		partytype.setGenericLocale(rs.getObject("generic_locale") != null ? rs.getString("generic_locale") : null);

		partytype.setStartTime(rs.getObject("start_time") != null ? rs.getObject("start_time", OffsetDateTime.class) : null);

		partytype.setEndTime(rs.getObject("end_time") != null ? rs.getObject("end_time", OffsetDateTime.class) : null);

		partytype.setRowCreationTime(rs.getObject("row_creation_time") != null ? rs.getObject("row_creation_time", OffsetDateTime.class) : null);

		partytype.setRowUpdateTime(rs.getObject("row_update_time") != null ? rs.getObject("row_update_time", OffsetDateTime.class) : null);

		partytype.setRowUpdateInfo(rs.getObject("row_update_info") != null ? rs.getString("row_update_info") : null);

		return partytype;
	};

}


