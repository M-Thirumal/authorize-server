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
import in.thirumal.persistence.model.Password;
import in.thirumal.persistence.model.shared.Identifier;

/**
 * Generated using erm-postgresql-spring-boot project
 * @see <a href="https://github.com/M-Thirumal/erm-postgresql-spring-boot">erm-postgresql-spring-boot</a>
 * @author erm-postgresql-spring-boot
 * @since 2020-03-29
 * @version 1.0
 */
@Repository
public class PasswordDao implements GenericDao <Password, Identifier, String>  {

	private static final String CREATE    = "Password.create";
	private static final String GET_BY    = "Password.getBy";
	private static final String LIST_BY   = "Password.listBy";
	private static final String DELETE_BY = "Password.deleteBy";

	private final JdbcTemplate jdbcTemplate;
	private Environment environment;

	@Autowired
	public PasswordDao(JdbcTemplate jdbcTemplate, Environment environment) {
		this.jdbcTemplate = jdbcTemplate;
		this.environment = environment;
	}

	@Override
	public Password create(Password password, Identifier identifier) { 
		return createV1(password, identifier).orElse(null);
	}

	@Override
	public Optional<Password> createV1(Password password, Identifier identifier) { 
		return getV1(new Identifier(insert(password, identifier), identifier.getLocaleCd()));
	}

	@Override
	public Long insert(Password password, Identifier identifier) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(con ->
			setPreparedStatement(password, con.prepareStatement(environment.getProperty(CREATE),
					new String[] { "password_id" })), holder);
		return holder.getKey().longValue();
	}

	public PreparedStatement setPreparedStatement(Password password, PreparedStatement ps) throws SQLException {
		if(password.getLoginId() == null) {
			ps.setObject(1, null);
		} else { 
			ps.setLong(1, password.getLoginId());
		}
		if(password.getSecret() == null) {
			ps.setObject(2, null);
		} else { 
			ps.setString(2, password.getSecret());
		}
		if(password.getStartTime() == null) {
			ps.setObject(3, null);
		} else { 
			ps.setObject(3, password.getStartTime());
		}
		if(password.getEndTime() == null) {
			ps.setObject(4, null);
		} else { 
			ps.setObject(4, password.getEndTime());
		}
		return ps;
	}

	/*Bulk/Batch Insert*/
	public void insertBatch(List<Password> passwordList) {
		jdbcTemplate.batchUpdate(environment.getProperty(CREATE), new BatchPreparedStatementSetter() {
			@Override
		    public void setValues(PreparedStatement ps, int i) throws SQLException {
				Password password = passwordList.get(i);
				setPreparedStatement(password, ps);
			}
			@Override
		    public int getBatchSize() {
		        return passwordList.size();
			}
		});
	}

	@Override
	public Password get(Identifier identifier) {
		return getV1(identifier).orElse(null);
	}

	@Override
	public Optional<Password> getV1(Identifier identifier) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("Password.get"), new Object[] {
				identifier.getLocaleCd(),
				identifier.getPk()
			}, passwordRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public Password get(Identifier identifier, String whereClause) {
		return getV1(identifier, whereClause).orElse(null);
	}

	@Override
	public Optional<Password> getV1(Identifier identifier, String whereClause) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty(GET_BY + whereClause), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			}, passwordRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Password> list(Identifier identifier) {
		try {
			return jdbcTemplate.query(environment.getProperty("Password.list"), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, passwordRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public List<Password> list(Identifier identifier, String whereClause) {
		try {
			return jdbcTemplate.query(environment.getProperty(LIST_BY + whereClause), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, passwordRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public Password update(Password password, Identifier identifier) {
		return updateV1(password, identifier).orElse(null);
	}

	@Override
	public Optional<Password> updateV1(Password password, Identifier identifier) {
		jdbcTemplate.update(environment.getProperty("Password.update"), 
			password.getLoginId(),
			password.getSecret(),
			password.getStartTime(),
			password.getEndTime(),
			password.getPasswordId());
		return getV1(new Identifier(password.getPasswordId(), identifier.getLocaleCd()));
	}

	@Override
	public int count(Identifier identifier, String whereClause) {
		return 0;
	}

	@Override
	public int delete(Password password) {
		return jdbcTemplate.update(environment.getProperty("Password.delete"), password.getPasswordId());
	}

	@Override
	public int deleteV1(Optional<Password> password) {
		return delete(password.orElseThrow(()->new AuthorizeException(ErrorFactory.RESOURCE_NOT_FOUND, "Password is not available")));
	}

	@Override
	public int delete(Identifier identifier, String whereClause) {
		return jdbcTemplate.update(environment.getProperty(DELETE_BY + whereClause), identifier.getPk());
	}

	RowMapper<Password> passwordRowMapper = (rs, rowNum) -> {

		Password password = new Password();

		password.setPasswordId(rs.getObject("password_id") != null ? rs.getLong("password_id") : null);

		password.setLoginId(rs.getObject("login_id") != null ? rs.getLong("login_id") : null);

		password.setSecret(rs.getObject("secret") != null ? rs.getString("secret") : null);

		password.setStartTime(rs.getObject("start_time") != null ? rs.getObject("start_time", OffsetDateTime.class) : null);

		password.setEndTime(rs.getObject("end_time") != null ? rs.getObject("end_time", OffsetDateTime.class) : null);

		return password;
	};

}


