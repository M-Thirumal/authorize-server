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
import in.thirumal.persistence.model.PartyPicture;
import in.thirumal.persistence.model.shared.Identifier;

/**
 * Generated using erm-postgresql-spring-boot project
 * @see <a href="https://github.com/M-Thirumal/erm-postgresql-spring-boot">erm-postgresql-spring-boot</a>
 * @author erm-postgresql-spring-boot
 * @since 2020-03-29
 * @version 1.0
 */
@Repository
public class PartyPictureDao implements GenericDao <PartyPicture, Identifier, String>  {

	private static final String CREATE    = "PartyPicture.create";
	private static final String GET_BY    = "PartyPicture.getBy";
	private static final String LIST_BY   = "PartyPicture.listBy";
	private static final String DELETE_BY = "PartyPicture.deleteBy";

	private final JdbcTemplate jdbcTemplate;
	private Environment environment;

	@Autowired
	public PartyPictureDao(JdbcTemplate jdbcTemplate, Environment environment) {
		this.jdbcTemplate = jdbcTemplate;
		this.environment = environment;
	}

	@Override
	public PartyPicture create(PartyPicture partypicture, Identifier identifier) { 
		return createV1(partypicture, identifier).orElse(null);
	}

	@Override
	public Optional<PartyPicture> createV1(PartyPicture partypicture, Identifier identifier) { 
		return getV1(new Identifier(insert(partypicture, identifier), identifier.getLocaleCd()));
	}

	@Override
	public Long insert(PartyPicture partypicture, Identifier identifier) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(con ->
			setPreparedStatement(partypicture, con.prepareStatement(environment.getProperty(CREATE),
					new String[] { "party_picture_id" })), holder);
		return holder.getKey().longValue();
	}

	public PreparedStatement setPreparedStatement(PartyPicture partypicture, PreparedStatement ps) throws SQLException {
		if(partypicture.getPartyId() == null) {
			ps.setObject(1, null);
		} else { 
			ps.setLong(1, partypicture.getPartyId());
		}
		if(partypicture.getPicture() == null) {
			ps.setObject(2, null);
		} else { 
			ps.setBytes(2, partypicture.getPicture());
		}
		ps.setBoolean(3, partypicture.isPreferred());
		if(partypicture.getRowCreationTime() == null) {
			ps.setObject(4, null);
		} else { 
			ps.setObject(4, partypicture.getRowCreationTime());
		}
		if(partypicture.getRowUpdateTime() == null) {
			ps.setObject(5, null);
		} else { 
			ps.setObject(5, partypicture.getRowUpdateTime());
		}
		if(partypicture.getRowUpdateInfo() == null) {
			ps.setObject(6, null);
		} else { 
			ps.setString(6, partypicture.getRowUpdateInfo());
		}
		return ps;
	}

	/*Bulk/Batch Insert*/
	public void insertBatch(List<PartyPicture> partypictureList) {
		jdbcTemplate.batchUpdate(environment.getProperty(CREATE), new BatchPreparedStatementSetter() {
			@Override
		    public void setValues(PreparedStatement ps, int i) throws SQLException {
				PartyPicture partypicture = partypictureList.get(i);
				setPreparedStatement(partypicture, ps);
			}
			@Override
		    public int getBatchSize() {
		        return partypictureList.size();
			}
		});
	}

	@Override
	public PartyPicture get(Identifier identifier) {
		return getV1(identifier).orElse(null);
	}

	@Override
	public Optional<PartyPicture> getV1(Identifier identifier) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("PartyPicture.get"), new Object[] {
				identifier.getLocaleCd(),
				identifier.getPk()
			}, partypictureRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public PartyPicture get(Identifier identifier, String whereClause) {
		return getV1(identifier, whereClause).orElse(null);
	}

	@Override
	public Optional<PartyPicture> getV1(Identifier identifier, String whereClause) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty(GET_BY + whereClause), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			}, partypictureRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<PartyPicture> list(Identifier identifier) {
		try {
			return jdbcTemplate.query(environment.getProperty("PartyPicture.list"), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, partypictureRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public List<PartyPicture> list(Identifier identifier, String whereClause) {
		try {
			return jdbcTemplate.query(environment.getProperty(LIST_BY + whereClause), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, partypictureRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public PartyPicture update(PartyPicture partypicture, Identifier identifier) {
		return updateV1(partypicture, identifier).orElse(null);
	}

	@Override
	public Optional<PartyPicture> updateV1(PartyPicture partypicture, Identifier identifier) {
		jdbcTemplate.update(environment.getProperty("PartyPicture.update"), 
			partypicture.getPartyId(),
			partypicture.getPicture(),
			partypicture.isPreferred(),
			partypicture.getRowCreationTime(),
			partypicture.getRowUpdateTime(),
			partypicture.getRowUpdateInfo(),
			partypicture.getPartyPictureId());
		return getV1(new Identifier(partypicture.getPartyPictureId(), identifier.getLocaleCd()));
	}

	@Override
	public int count(Identifier identifier, String whereClause) {
		return 0;
	}

	@Override
	public int delete(PartyPicture partypicture) {
		return jdbcTemplate.update(environment.getProperty("PartyPicture.delete"), partypicture.getPartyPictureId());
	}

	@Override
	public int deleteV1(Optional<PartyPicture> partypicture) {
		return delete(partypicture.orElseThrow(()->new AuthorizeException(ErrorFactory.RESOURCE_NOT_FOUND, "PartyPicture is not available")));
	}

	@Override
	public int delete(Identifier identifier, String whereClause) {
		return jdbcTemplate.update(environment.getProperty(DELETE_BY + whereClause), identifier.getPk());
	}

	RowMapper<PartyPicture> partypictureRowMapper = (rs, rowNum) -> {

		PartyPicture partypicture = new PartyPicture();

		partypicture.setPartyPictureId(rs.getObject("party_picture_id") != null ? rs.getLong("party_picture_id") : null);

		partypicture.setPartyId(rs.getObject("party_id") != null ? rs.getLong("party_id") : null);

		partypicture.setPicture(rs.getObject("picture") != null ? rs.getBytes("picture") : null);

		partypicture.setPreferred(rs.getObject("preferred") != null ? rs.getBoolean("preferred") : null);

		partypicture.setRowCreationTime(rs.getObject("row_creation_time") != null ? rs.getObject("row_creation_time", OffsetDateTime.class) : null);

		partypicture.setRowUpdateTime(rs.getObject("row_update_time") != null ? rs.getObject("row_update_time", OffsetDateTime.class) : null);

		partypicture.setRowUpdateInfo(rs.getObject("row_update_info") != null ? rs.getString("row_update_info") : null);

		return partypicture;
	};

}


