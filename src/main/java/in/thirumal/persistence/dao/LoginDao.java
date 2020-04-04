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
import in.thirumal.persistence.model.Login;
import in.thirumal.persistence.model.shared.Identifier;

/**
 * Generated using erm-postgresql-spring-boot project
 * @see <a href="https://github.com/M-Thirumal/erm-postgresql-spring-boot">erm-postgresql-spring-boot</a>
 * @author erm-postgresql-spring-boot
 * @since 2020-03-29
 * @version 1.0
 */
@Repository
public class LoginDao implements GenericDao <Login, Identifier, String>  {

	private static final String CREATE    = "Login.create";
	private static final String GET_BY    = "Login.getBy";
	private static final String LIST_BY   = "Login.listBy";
	private static final String DELETE_BY = "Login.deleteBy";

	private final JdbcTemplate jdbcTemplate;
	private Environment environment;

	@Autowired
	public LoginDao(JdbcTemplate jdbcTemplate, Environment environment) {
		this.jdbcTemplate = jdbcTemplate;
		this.environment = environment;
	}

	@Override
	public Login create(Login login, Identifier identifier) { 
		return createV1(login, identifier).orElse(null);
	}

	@Override
	public Optional<Login> createV1(Login login, Identifier identifier) { 
		return getV1(new Identifier(insert(login, identifier), identifier.getLocaleCd()));
	}

	@Override
	public Long insert(Login login, Identifier identifier) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(con ->
			setPreparedStatement(login, con.prepareStatement(environment.getProperty(CREATE),
					new String[] { "login_id" })), holder);
		return holder.getKey().longValue();
	}

	public PreparedStatement setPreparedStatement(Login login, PreparedStatement ps) throws SQLException {
		if(login.getPartyId() == null) {
			ps.setObject(1, null);
		} else { 
			ps.setLong(1, login.getPartyId());
		}
		return ps;
	}

	/*Bulk/Batch Insert*/
	public void insertBatch(List<Login> loginList) {
		jdbcTemplate.batchUpdate(environment.getProperty(CREATE), new BatchPreparedStatementSetter() {
			@Override
		    public void setValues(PreparedStatement ps, int i) throws SQLException {
				Login login = loginList.get(i);
				setPreparedStatement(login, ps);
			}
			@Override
		    public int getBatchSize() {
		        return loginList.size();
			}
		});
	}

	@Override
	public Login get(Identifier identifier) {
		return getV1(identifier).orElse(null);
	}

	@Override
	public Optional<Login> getV1(Identifier identifier) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("Login.get"), new Object[] {
				identifier.getPk()
			}, loginRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public Login get(Identifier identifier, String whereClause) {
		return getV1(identifier, whereClause).orElse(null);
	}

	@Override
	public Optional<Login> getV1(Identifier identifier, String whereClause) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty(GET_BY + whereClause), new Object[] { 
				identifier.getPk()
			}, loginRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Login> list(Identifier identifier) {
		try {
			return jdbcTemplate.query(environment.getProperty("Login.list"), new Object[] { 
				identifier.getPk()
			 }, loginRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public List<Login> list(Identifier identifier, String whereClause) {
		try {
			return jdbcTemplate.query(environment.getProperty(LIST_BY + whereClause), new Object[] { 
				identifier.getPk()
			 }, loginRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public Login update(Login login, Identifier identifier) {
		return updateV1(login, identifier).orElse(null);
	}

	@Override
	public Optional<Login> updateV1(Login login, Identifier identifier) {
		jdbcTemplate.update(environment.getProperty("Login.update"), 
			login.getPartyId(),
			login.getRowUpdateInfo(),
			login.getLoginId());
		return getV1(new Identifier(login.getLoginId(), identifier.getLocaleCd()));
	}

	@Override
	public int count(Identifier identifier, String whereClause) {
		return 0;
	}

	@Override
	public int delete(Login login) {
		return jdbcTemplate.update(environment.getProperty("Login.delete"), login.getLoginId());
	}

	@Override
	public int deleteV1(Optional<Login> login) {
		return delete(login.orElseThrow(()->new AuthorizeException(ErrorFactory.RESOURCE_NOT_FOUND, "Login is not available")));
	}

	@Override
	public int delete(Identifier identifier, String whereClause) {
		return jdbcTemplate.update(environment.getProperty(DELETE_BY + whereClause), identifier.getPk());
	}

	RowMapper<Login> loginRowMapper = (rs, rowNum) -> {

		Login login = new Login();

		login.setLoginId(rs.getObject("login_id") != null ? rs.getLong("login_id") : null);

		login.setPartyId(rs.getObject("party_id") != null ? rs.getLong("party_id") : null);

		login.setRowCreationTime(rs.getObject("row_creation_time") != null ? rs.getObject("row_creation_time", OffsetDateTime.class) : null);

		login.setRowUpdateTime(rs.getObject("row_update_time") != null ? rs.getObject("row_update_time", OffsetDateTime.class) : null);

		login.setRowUpdateInfo(rs.getObject("row_update_info") != null ? rs.getString("row_update_info") : null);

		return login;
	};

}


