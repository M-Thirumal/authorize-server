package in.thirumal.persistence.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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
import in.thirumal.persistence.model.Party;
import in.thirumal.persistence.model.shared.Identifier;

/**
 * Generated using erm-postgresql-spring-boot project
 * @see <a href="https://github.com/M-Thirumal/erm-postgresql-spring-boot">erm-postgresql-spring-boot</a>
 * @author erm-postgresql-spring-boot
 * @since 2020-03-29
 * @version 1.0
 */
@Repository
public class PartyDao implements GenericDao <Party, Identifier, String>  {

	private static final String CREATE    = "Party.create";
	private static final String GET_BY    = "Party.getBy";
	private static final String LIST_BY   = "Party.listBy";
	private static final String DELETE_BY = "Party.deleteBy";

	private final JdbcTemplate jdbcTemplate;
	private Environment environment;

	@Autowired
	public PartyDao(JdbcTemplate jdbcTemplate, Environment environment) {
		this.jdbcTemplate = jdbcTemplate;
		this.environment = environment;
	}

	@Override
	public Party create(Party party, Identifier identifier) { 
		return createV1(party, identifier).orElse(null);
	}

	@Override
	public Optional<Party> createV1(Party party, Identifier identifier) { 
		return getV1(new Identifier(insert(party, identifier), identifier.getLocaleCd()));
	}

	@Override
	public Long insert(Party party, Identifier identifier) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(con ->
			setPreparedStatement(party, con.prepareStatement(environment.getProperty(CREATE),
					new String[] { "party_id" })), holder);
		return holder.getKey().longValue();
	}

	public PreparedStatement setPreparedStatement(Party party, PreparedStatement ps) throws SQLException {
		if(party.getBirthDate() == null) {
			ps.setObject(1, null);
		} else { 
			ps.setObject(1, party.getBirthDate());
		}
		if(party.getDeathDate() == null) {
			ps.setObject(2, null);
		} else { 
			ps.setObject(2, party.getDeathDate());
		}
		if(party.getRowCreationTime() == null) {
			ps.setObject(3, null);
		} else { 
			ps.setObject(3, party.getRowCreationTime());
		}
		if(party.getRowUpdateTime() == null) {
			ps.setObject(4, null);
		} else { 
			ps.setObject(4, party.getRowUpdateTime());
		}
		if(party.getRowUpdateInfo() == null) {
			ps.setObject(5, null);
		} else { 
			ps.setString(5, party.getRowUpdateInfo());
		}
		return ps;
	}

	/*Bulk/Batch Insert*/
	public void insertBatch(List<Party> partyList) {
		jdbcTemplate.batchUpdate(environment.getProperty(CREATE), new BatchPreparedStatementSetter() {
			@Override
		    public void setValues(PreparedStatement ps, int i) throws SQLException {
				Party party = partyList.get(i);
				setPreparedStatement(party, ps);
			}
			@Override
		    public int getBatchSize() {
		        return partyList.size();
			}
		});
	}

	@Override
	public Party get(Identifier identifier) {
		return getV1(identifier).orElse(null);
	}

	@Override
	public Optional<Party> getV1(Identifier identifier) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("Party.get"), new Object[] {
				identifier.getLocaleCd(),
				identifier.getPk()
			}, partyRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public Party get(Identifier identifier, String whereClause) {
		return getV1(identifier, whereClause).orElse(null);
	}

	@Override
	public Optional<Party> getV1(Identifier identifier, String whereClause) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty(GET_BY + whereClause), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			}, partyRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Party> list(Identifier identifier) {
		try {
			return jdbcTemplate.query(environment.getProperty("Party.list"), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, partyRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public List<Party> list(Identifier identifier, String whereClause) {
		try {
			return jdbcTemplate.query(environment.getProperty(LIST_BY + whereClause), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, partyRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public Party update(Party party, Identifier identifier) {
		return updateV1(party, identifier).orElse(null);
	}

	@Override
	public Optional<Party> updateV1(Party party, Identifier identifier) {
		jdbcTemplate.update(environment.getProperty("Party.update"), 
			party.getPartyUuid(),
			party.getBirthDate(),
			party.getDeathDate(),
			party.getRowCreationTime(),
			party.getRowUpdateTime(),
			party.getRowUpdateInfo(),
			party.getPartyId());
		return getV1(new Identifier(party.getPartyId(), identifier.getLocaleCd()));
	}

	@Override
	public int count(Identifier identifier, String whereClause) {
		return 0;
	}

	@Override
	public int delete(Party party) {
		return jdbcTemplate.update(environment.getProperty("Party.delete"), party.getPartyId());
	}

	@Override
	public int deleteV1(Optional<Party> party) {
		return delete(party.orElseThrow(()->new AuthorizeException(ErrorFactory.RESOURCE_NOT_FOUND, "Party is not available")));
	}

	@Override
	public int delete(Identifier identifier, String whereClause) {
		return jdbcTemplate.update(environment.getProperty(DELETE_BY + whereClause), identifier.getPk());
	}

	RowMapper<Party> partyRowMapper = (rs, rowNum) -> {

		Party party = new Party();

		party.setPartyId(rs.getObject("party_id") != null ? rs.getLong("party_id") : null);

		party.setPartyUuid(rs.getObject("party_uuid") != null ? (java.util.UUID) rs.getObject("party_uuid") : null);

		party.setBirthDate(rs.getObject("birth_date") != null ? rs.getObject("birth_date", OffsetDateTime.class) : null);

		party.setDeathDate(rs.getObject("death_date") != null ? rs.getObject("death_date", LocalDate.class) : null);

		party.setRowCreationTime(rs.getObject("row_creation_time") != null ? rs.getObject("row_creation_time", OffsetDateTime.class) : null);

		party.setRowUpdateTime(rs.getObject("row_update_time") != null ? rs.getObject("row_update_time", OffsetDateTime.class) : null);

		party.setRowUpdateInfo(rs.getObject("row_update_info") != null ? rs.getString("row_update_info") : null);

		return party;
	};

}


