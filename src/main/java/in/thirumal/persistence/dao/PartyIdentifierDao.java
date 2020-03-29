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
import in.thirumal.persistence.model.PartyIdentifier;
import in.thirumal.persistence.model.shared.Identifier;

/**
 * Generated using erm-postgresql-spring-boot project
 * @see <a href="https://github.com/M-Thirumal/erm-postgresql-spring-boot">erm-postgresql-spring-boot</a>
 * @author erm-postgresql-spring-boot
 * @since 2020-03-29
 * @version 1.0
 */
@Repository
public class PartyIdentifierDao implements GenericDao <PartyIdentifier, Identifier, String>  {

	private static final String CREATE    = "PartyIdentifier.create";
	private static final String GET_BY    = "PartyIdentifier.getBy";
	private static final String LIST_BY   = "PartyIdentifier.listBy";
	private static final String DELETE_BY = "PartyIdentifier.deleteBy";

	private final JdbcTemplate jdbcTemplate;
	private Environment environment;

	@Autowired
	public PartyIdentifierDao(JdbcTemplate jdbcTemplate, Environment environment) {
		this.jdbcTemplate = jdbcTemplate;
		this.environment = environment;
	}

	@Override
	public PartyIdentifier create(PartyIdentifier partyidentifier, Identifier identifier) { 
		return createV1(partyidentifier, identifier).orElse(null);
	}

	@Override
	public Optional<PartyIdentifier> createV1(PartyIdentifier partyidentifier, Identifier identifier) { 
		return getV1(new Identifier(insert(partyidentifier, identifier), identifier.getLocaleCd()));
	}

	@Override
	public Long insert(PartyIdentifier partyidentifier, Identifier identifier) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(con ->
			setPreparedStatement(partyidentifier, con.prepareStatement(environment.getProperty(CREATE),
					new String[] { "party_identifier_id" })), holder);
		return holder.getKey().longValue();
	}

	public PreparedStatement setPreparedStatement(PartyIdentifier partyidentifier, PreparedStatement ps) throws SQLException {
		if(partyidentifier.getPartyId() == null) {
			ps.setObject(1, null);
		} else { 
			ps.setLong(1, partyidentifier.getPartyId());
		}
		if(partyidentifier.getGenericCd() == null) {
			ps.setObject(2, null);
		} else { 
			ps.setInt(2, partyidentifier.getGenericCd());
		}
		if(partyidentifier.getIdentification() == null) {
			ps.setObject(3, null);
		} else { 
			ps.setString(3, partyidentifier.getIdentification());
		}
		if(partyidentifier.getStartTime() == null) {
			ps.setObject(4, null);
		} else { 
			ps.setObject(4, partyidentifier.getStartTime());
		}
		if(partyidentifier.getEndTime() == null) {
			ps.setObject(5, null);
		} else { 
			ps.setObject(5, partyidentifier.getEndTime());
		}
		if(partyidentifier.getRowCreationTime() == null) {
			ps.setObject(6, null);
		} else { 
			ps.setObject(6, partyidentifier.getRowCreationTime());
		}
		if(partyidentifier.getRowUpdateTime() == null) {
			ps.setObject(7, null);
		} else { 
			ps.setObject(7, partyidentifier.getRowUpdateTime());
		}
		if(partyidentifier.getRowUpdateInfo() == null) {
			ps.setObject(8, null);
		} else { 
			ps.setString(8, partyidentifier.getRowUpdateInfo());
		}
		return ps;
	}

	/*Bulk/Batch Insert*/
	public void insertBatch(List<PartyIdentifier> partyidentifierList) {
		jdbcTemplate.batchUpdate(environment.getProperty(CREATE), new BatchPreparedStatementSetter() {
			@Override
		    public void setValues(PreparedStatement ps, int i) throws SQLException {
				PartyIdentifier partyidentifier = partyidentifierList.get(i);
				setPreparedStatement(partyidentifier, ps);
			}
			@Override
		    public int getBatchSize() {
		        return partyidentifierList.size();
			}
		});
	}

	@Override
	public PartyIdentifier get(Identifier identifier) {
		return getV1(identifier).orElse(null);
	}

	@Override
	public Optional<PartyIdentifier> getV1(Identifier identifier) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("PartyIdentifier.get"), new Object[] {
				identifier.getLocaleCd(),
				identifier.getPk()
			}, partyidentifierRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public PartyIdentifier get(Identifier identifier, String whereClause) {
		return getV1(identifier, whereClause).orElse(null);
	}

	@Override
	public Optional<PartyIdentifier> getV1(Identifier identifier, String whereClause) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty(GET_BY + whereClause), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			}, partyidentifierRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<PartyIdentifier> list(Identifier identifier) {
		try {
			return jdbcTemplate.query(environment.getProperty("PartyIdentifier.list"), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, partyidentifierRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public List<PartyIdentifier> list(Identifier identifier, String whereClause) {
		try {
			return jdbcTemplate.query(environment.getProperty(LIST_BY + whereClause), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, partyidentifierRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public PartyIdentifier update(PartyIdentifier partyidentifier, Identifier identifier) {
		return updateV1(partyidentifier, identifier).orElse(null);
	}

	@Override
	public Optional<PartyIdentifier> updateV1(PartyIdentifier partyidentifier, Identifier identifier) {
		jdbcTemplate.update(environment.getProperty("PartyIdentifier.update"), 
			partyidentifier.getPartyId(),
			partyidentifier.getGenericCd(),
			partyidentifier.getIdentification(),
			partyidentifier.getStartTime(),
			partyidentifier.getEndTime(),
			partyidentifier.getRowCreationTime(),
			partyidentifier.getRowUpdateTime(),
			partyidentifier.getRowUpdateInfo(),
			partyidentifier.getPartyIdentifierId());
		return getV1(new Identifier(partyidentifier.getPartyIdentifierId(), identifier.getLocaleCd()));
	}

	@Override
	public int count(Identifier identifier, String whereClause) {
		return 0;
	}

	@Override
	public int delete(PartyIdentifier partyidentifier) {
		return jdbcTemplate.update(environment.getProperty("PartyIdentifier.delete"), partyidentifier.getPartyIdentifierId());
	}

	@Override
	public int deleteV1(Optional<PartyIdentifier> partyidentifier) {
		return delete(partyidentifier.orElseThrow(()->new AuthorizeException(ErrorFactory.RESOURCE_NOT_FOUND, "PartyIdentifier is not available")));
	}

	@Override
	public int delete(Identifier identifier, String whereClause) {
		return jdbcTemplate.update(environment.getProperty(DELETE_BY + whereClause), identifier.getPk());
	}

	RowMapper<PartyIdentifier> partyidentifierRowMapper = (rs, rowNum) -> {

		PartyIdentifier partyidentifier = new PartyIdentifier();

		partyidentifier.setPartyIdentifierId(rs.getObject("party_identifier_id") != null ? rs.getLong("party_identifier_id") : null);

		partyidentifier.setPartyId(rs.getObject("party_id") != null ? rs.getLong("party_id") : null);

		partyidentifier.setGenericCd(rs.getObject("generic_cd") != null ? rs.getInt("generic_cd") : null);

		partyidentifier.setGenericLocale(rs.getObject("generic_locale") != null ? rs.getString("generic_locale") : null);

		partyidentifier.setIdentification(rs.getObject("identification") != null ? rs.getString("identification") : null);

		partyidentifier.setStartTime(rs.getObject("start_time") != null ? rs.getObject("start_time", OffsetDateTime.class) : null);

		partyidentifier.setEndTime(rs.getObject("end_time") != null ? rs.getObject("end_time", OffsetDateTime.class) : null);

		partyidentifier.setRowCreationTime(rs.getObject("row_creation_time") != null ? rs.getObject("row_creation_time", OffsetDateTime.class) : null);

		partyidentifier.setRowUpdateTime(rs.getObject("row_update_time") != null ? rs.getObject("row_update_time", OffsetDateTime.class) : null);

		partyidentifier.setRowUpdateInfo(rs.getObject("row_update_info") != null ? rs.getString("row_update_info") : null);

		return partyidentifier;
	};

}


