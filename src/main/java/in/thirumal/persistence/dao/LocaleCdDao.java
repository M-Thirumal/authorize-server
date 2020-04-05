package in.thirumal.persistence.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
import in.thirumal.persistence.model.shared.Identifier;
import in.thirumal.persistence.model.shared.LocaleCd;

/**
 * Generated using erm-postgresql-spring-boot project
 * @see <a href="https://github.com/M-Thirumal/erm-postgresql-spring-boot">erm-postgresql-spring-boot</a>
 * @author erm-postgresql-spring-boot
 * @since 2020-04-04
 * @version 1.0
 */
@Repository
public class LocaleCdDao implements GenericDao <LocaleCd, Identifier, String>  {

	private static final String CREATE    = "LocaleCd.create";
	private static final String GET_BY    = "LocaleCd.getBy";
	private static final String LIST_BY   = "LocaleCd.listBy";
	private static final String DELETE_BY = "LocaleCd.deleteBy";

	private final JdbcTemplate jdbcTemplate;
	private Environment environment;

	@Autowired
	public LocaleCdDao(JdbcTemplate jdbcTemplate, Environment environment) {
		this.jdbcTemplate = jdbcTemplate;
		this.environment = environment;
	}

	@Override
	public LocaleCd create(LocaleCd localecd, Identifier identifier) { 
		return createV1(localecd, identifier).orElse(null);
	}

	@Override
	public Optional<LocaleCd> createV1(LocaleCd localecd, Identifier identifier) { 
		return getV1(new Identifier(insert(localecd, identifier), identifier.getLocaleCd()));
	}

	@Override
	public Long insert(LocaleCd localecd, Identifier identifier) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(con ->
			setPreparedStatement(localecd, con.prepareStatement(environment.getProperty(CREATE),
					new String[] { "locale_cd" })), holder);
		return holder.getKey().longValue();
	}

	public PreparedStatement setPreparedStatement(LocaleCd localecd, PreparedStatement ps) throws SQLException {
		if(localecd.getCode() == null) {
			ps.setObject(1, null);
		} else { 
			ps.setString(1, localecd.getCode());
		}
		if(localecd.getDescription() == null) {
			ps.setObject(2, null);
		} else { 
			ps.setString(2, localecd.getDescription());
		}
		if(localecd.getStartDate() == null) {
			ps.setObject(3, null);
		} else { 
			ps.setObject(3, localecd.getStartDate());
		}
		if(localecd.getEndDate() == null) {
			ps.setObject(4, null);
		} else { 
			ps.setObject(4, localecd.getEndDate());
		}
		return ps;
	}

	/*Bulk/Batch Insert*/
	public void insertBatch(List<LocaleCd> localecdList) {
		jdbcTemplate.batchUpdate(environment.getProperty(CREATE), new BatchPreparedStatementSetter() {
			@Override
		    public void setValues(PreparedStatement ps, int i) throws SQLException {
				LocaleCd localecd = localecdList.get(i);
				setPreparedStatement(localecd, ps);
			}
			@Override
		    public int getBatchSize() {
		        return localecdList.size();
			}
		});
	}

	@Override
	public LocaleCd get(Identifier identifier) {
		return getV1(identifier).orElse(null);
	}

	@Override
	public Optional<LocaleCd> getV1(Identifier identifier) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("LocaleCd.get"), new Object[] {
				identifier.getPk()
			}, localecdRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public LocaleCd get(Identifier identifier, String whereClause) {
		return getV1(identifier, whereClause).orElse(null);
	}

	@Override
	public Optional<LocaleCd> getV1(Identifier identifier, String whereClause) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty(GET_BY + whereClause), new Object[] { 
				identifier.getPk()
			}, localecdRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Cacheable(value = "locale")
	@Override
	public List<LocaleCd> list(Identifier identifier) {
		try {
			return jdbcTemplate.query(environment.getProperty("LocaleCd.list"), localecdRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public List<LocaleCd> list(Identifier identifier, String whereClause) {
		try {
			return jdbcTemplate.query(environment.getProperty(LIST_BY + whereClause), new Object[] { 
				identifier.getPk()
			 }, localecdRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public LocaleCd update(LocaleCd localecd, Identifier identifier) {
		return updateV1(localecd, identifier).orElse(null);
	}

	@Override
	public Optional<LocaleCd> updateV1(LocaleCd localecd, Identifier identifier) {
		jdbcTemplate.update(environment.getProperty("LocaleCd.update"), 
			localecd.getCode(),
			localecd.getDescription(),
			localecd.getStartDate(),
			localecd.getEndDate(),
			localecd.getRowUpdatedBy(),
			localecd.getRowUpdateInfo(),
			localecd.getLocaleCd());
		return getV1(new Identifier(localecd.getLocaleCd(), identifier.getLocaleCd()));
	}

	@Override
	public int count(Identifier identifier, String whereClause) {
		return 0;
	}

	@Override
	public int delete(LocaleCd localecd) {
		return jdbcTemplate.update(environment.getProperty("LocaleCd.delete"), localecd.getLocaleCd());
	}

	@Override
	public int deleteV1(Optional<LocaleCd> localecd) {
		return delete(localecd.orElseThrow(()->new AuthorizeException(ErrorFactory.RESOURCE_NOT_FOUND, "LocaleCd is not available")));
	}

	@Override
	public int delete(Identifier identifier, String whereClause) {
		return jdbcTemplate.update(environment.getProperty(DELETE_BY + whereClause), identifier.getPk());
	}

	RowMapper<LocaleCd> localecdRowMapper = (rs, rowNum) -> {

		LocaleCd localecd = new LocaleCd();

		localecd.setLocaleCd(rs.getObject("locale_cd") != null ? rs.getLong("locale_cd") : null);

		localecd.setCode(rs.getObject("code") != null ? rs.getString("code") : null);

		localecd.setDescription(rs.getObject("description") != null ? rs.getString("description") : null);

		localecd.setStartDate(rs.getObject("start_date") != null ? rs.getObject("start_date", LocalDate.class) : null);

		localecd.setEndDate(rs.getObject("end_date") != null ? rs.getObject("end_date", LocalDate.class) : null);

		localecd.setRowCreationTime(rs.getObject("row_creation_time") != null ? rs.getObject("row_creation_time", OffsetDateTime.class) : null);

		localecd.setRowCreatedBy(rs.getObject("row_created_by") != null ? rs.getString("row_created_by") : null);

		localecd.setRowUpdatedBy(rs.getObject("row_updated_by") != null ? rs.getString("row_updated_by") : null);

		localecd.setRowUpdateTime(rs.getObject("row_update_time") != null ? rs.getObject("row_update_time", OffsetDateTime.class) : null);

		localecd.setRowUpdateInfo(rs.getObject("row_update_info") != null ? rs.getString("row_update_info") : null);

		return localecd;
	};

}


