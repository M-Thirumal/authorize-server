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
import in.thirumal.persistence.model.PartyName;
import in.thirumal.persistence.model.shared.Identifier;

/**
 * Generated using erm-postgresql-spring-boot project
 * @see <a href="https://github.com/M-Thirumal/erm-postgresql-spring-boot">erm-postgresql-spring-boot</a>
 * @author erm-postgresql-spring-boot
 * @since 2020-03-29
 * @version 1.0
 */
@Repository
public class PartyNameDao implements GenericDao <PartyName, Identifier, String>  {

	private static final String CREATE    = "PartyName.create";
	private static final String GET_BY    = "PartyName.getBy";
	private static final String LIST_BY   = "PartyName.listBy";
	private static final String DELETE_BY = "PartyName.deleteBy";

	private final JdbcTemplate jdbcTemplate;
	private Environment environment;

	@Autowired
	public PartyNameDao(JdbcTemplate jdbcTemplate, Environment environment) {
		this.jdbcTemplate = jdbcTemplate;
		this.environment = environment;
	}

	@Override
	public PartyName create(PartyName partyname, Identifier identifier) { 
		return createV1(partyname, identifier).orElse(null);
	}

	@Override
	public Optional<PartyName> createV1(PartyName partyname, Identifier identifier) { 
		return getV1(new Identifier(insert(partyname, identifier), identifier.getLocaleCd()));
	}

	@Override
	public Long insert(PartyName partyname, Identifier identifier) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(con ->
			setPreparedStatement(partyname, con.prepareStatement(environment.getProperty(CREATE),
					new String[] { "party_name_id" })), holder);
		return holder.getKey().longValue();
	}

	public PreparedStatement setPreparedStatement(PartyName partyname, PreparedStatement ps) throws SQLException {
		if(partyname.getPartyId() == null) {
			ps.setObject(1, null);
		} else { 
			ps.setLong(1, partyname.getPartyId());
		}
		if(partyname.getGenericCd() == null) {
			ps.setObject(2, null);
		} else { 
			ps.setInt(2, partyname.getGenericCd());
		}
		if(partyname.getFirstName() == null) {
			ps.setObject(3, null);
		} else { 
			ps.setString(3, partyname.getFirstName());
		}
		if(partyname.getRestOfName() == null) {
			ps.setObject(4, null);
		} else { 
			ps.setString(4, partyname.getRestOfName());
		}
		if(partyname.getStartTime() == null) {
			ps.setObject(5, null);
		} else { 
			ps.setObject(5, partyname.getStartTime());
		}
		if(partyname.getEndTime() == null) {
			ps.setObject(6, null);
		} else { 
			ps.setObject(6, partyname.getEndTime());
		}
		ps.setBoolean(7, partyname.isPreferred());
		if(partyname.getRowCreationTime() == null) {
			ps.setObject(8, null);
		} else { 
			ps.setObject(8, partyname.getRowCreationTime());
		}
		if(partyname.getRowUpdateTime() == null) {
			ps.setObject(9, null);
		} else { 
			ps.setObject(9, partyname.getRowUpdateTime());
		}
		if(partyname.getRowUpdateInfo() == null) {
			ps.setObject(10, null);
		} else { 
			ps.setString(10, partyname.getRowUpdateInfo());
		}
		return ps;
	}

	/*Bulk/Batch Insert*/
	public void insertBatch(List<PartyName> partynameList) {
		jdbcTemplate.batchUpdate(environment.getProperty(CREATE), new BatchPreparedStatementSetter() {
			@Override
		    public void setValues(PreparedStatement ps, int i) throws SQLException {
				PartyName partyname = partynameList.get(i);
				setPreparedStatement(partyname, ps);
			}
			@Override
		    public int getBatchSize() {
		        return partynameList.size();
			}
		});
	}

	@Override
	public PartyName get(Identifier identifier) {
		return getV1(identifier).orElse(null);
	}

	@Override
	public Optional<PartyName> getV1(Identifier identifier) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("PartyName.get"), new Object[] {
				identifier.getLocaleCd(),
				identifier.getPk()
			}, partynameRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public PartyName get(Identifier identifier, String whereClause) {
		return getV1(identifier, whereClause).orElse(null);
	}

	@Override
	public Optional<PartyName> getV1(Identifier identifier, String whereClause) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty(GET_BY + whereClause), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			}, partynameRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<PartyName> list(Identifier identifier) {
		try {
			return jdbcTemplate.query(environment.getProperty("PartyName.list"), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, partynameRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public List<PartyName> list(Identifier identifier, String whereClause) {
		try {
			return jdbcTemplate.query(environment.getProperty(LIST_BY + whereClause), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, partynameRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public PartyName update(PartyName partyname, Identifier identifier) {
		return updateV1(partyname, identifier).orElse(null);
	}

	@Override
	public Optional<PartyName> updateV1(PartyName partyname, Identifier identifier) {
		jdbcTemplate.update(environment.getProperty("PartyName.update"), 
			partyname.getPartyId(),
			partyname.getGenericCd(),
			partyname.getFirstName(),
			partyname.getRestOfName(),
			partyname.getStartTime(),
			partyname.getEndTime(),
			partyname.isPreferred(),
			partyname.getRowCreationTime(),
			partyname.getRowUpdateTime(),
			partyname.getRowUpdateInfo(),
			partyname.getPartyNameId());
		return getV1(new Identifier(partyname.getPartyNameId(), identifier.getLocaleCd()));
	}

	@Override
	public int count(Identifier identifier, String whereClause) {
		return 0;
	}

	@Override
	public int delete(PartyName partyname) {
		return jdbcTemplate.update(environment.getProperty("PartyName.delete"), partyname.getPartyNameId());
	}

	@Override
	public int deleteV1(Optional<PartyName> partyname) {
		return delete(partyname.orElseThrow(()->new AuthorizeException(ErrorFactory.RESOURCE_NOT_FOUND, "PartyName is not available")));
	}

	@Override
	public int delete(Identifier identifier, String whereClause) {
		return jdbcTemplate.update(environment.getProperty(DELETE_BY + whereClause), identifier.getPk());
	}

	RowMapper<PartyName> partynameRowMapper = (rs, rowNum) -> {

		PartyName partyname = new PartyName();

		partyname.setPartyNameId(rs.getObject("party_name_id") != null ? rs.getLong("party_name_id") : null);

		partyname.setPartyId(rs.getObject("party_id") != null ? rs.getLong("party_id") : null);

		partyname.setGenericCd(rs.getObject("generic_cd") != null ? rs.getInt("generic_cd") : null);

		partyname.setGenericLocale(rs.getObject("generic_locale") != null ? rs.getString("generic_locale") : null);

		partyname.setFirstName(rs.getObject("first_name") != null ? rs.getString("first_name") : null);

		partyname.setRestOfName(rs.getObject("rest_of_name") != null ? rs.getString("rest_of_name") : null);

		partyname.setStartTime(rs.getObject("start_time") != null ? rs.getObject("start_time", OffsetDateTime.class) : null);

		partyname.setEndTime(rs.getObject("end_time") != null ? rs.getObject("end_time", OffsetDateTime.class) : null);

		partyname.setPreferred(rs.getObject("preferred") != null ? rs.getBoolean("preferred") : null);

		partyname.setRowCreationTime(rs.getObject("row_creation_time") != null ? rs.getObject("row_creation_time", OffsetDateTime.class) : null);

		partyname.setRowUpdateTime(rs.getObject("row_update_time") != null ? rs.getObject("row_update_time", OffsetDateTime.class) : null);

		partyname.setRowUpdateInfo(rs.getObject("row_update_info") != null ? rs.getString("row_update_info") : null);

		return partyname;
	};

}


