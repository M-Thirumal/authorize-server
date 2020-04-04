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
import in.thirumal.persistence.model.LoginIdentifier;
import in.thirumal.persistence.model.shared.Identifier;

/**
 * Generated using erm-postgresql-spring-boot project
 * @see <a href="https://github.com/M-Thirumal/erm-postgresql-spring-boot">erm-postgresql-spring-boot</a>
 * @author erm-postgresql-spring-boot
 * @since 2020-03-29
 * @version 1.0
 */
@Repository
public class LoginIdentifierDao implements GenericDao <LoginIdentifier, Identifier, String>  {

	private static final String CREATE    = "LoginIdentifier.create";
	private static final String GET_BY    = "LoginIdentifier.getBy";
	private static final String LIST_BY   = "LoginIdentifier.listBy";
	private static final String DELETE_BY = "LoginIdentifier.deleteBy";
	//
	public static final String BY_IDENTIFIER = "Identifier";

	private final JdbcTemplate jdbcTemplate;
	private Environment environment;

	@Autowired
	public LoginIdentifierDao(JdbcTemplate jdbcTemplate, Environment environment) {
		this.jdbcTemplate = jdbcTemplate;
		this.environment = environment;
	}

	@Override
	public LoginIdentifier create(LoginIdentifier loginidentifier, Identifier identifier) { 
		return createV1(loginidentifier, identifier).orElse(null);
	}

	@Override
	public Optional<LoginIdentifier> createV1(LoginIdentifier loginidentifier, Identifier identifier) { 
		return getV1(new Identifier(insert(loginidentifier, identifier), identifier.getLocaleCd()));
	}

	@Override
	public Long insert(LoginIdentifier loginidentifier, Identifier identifier) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(con ->
			setPreparedStatement(loginidentifier, con.prepareStatement(environment.getProperty(CREATE),
					new String[] { "login_identifier_id" })), holder);
		return holder.getKey().longValue();
	}

	public PreparedStatement setPreparedStatement(LoginIdentifier loginidentifier, PreparedStatement ps) throws SQLException {
		if(loginidentifier.getLoginId() == null) {
			ps.setObject(1, null);
		} else { 
			ps.setLong(1, loginidentifier.getLoginId());
		}
		if(loginidentifier.getGenericCd() == null) {
			ps.setObject(2, null);
		} else { 
			ps.setInt(2, loginidentifier.getGenericCd());
		}
		if(loginidentifier.getIdentifier() == null) {
			ps.setObject(3, null);
		} else { 
			ps.setString(3, loginidentifier.getIdentifier());
		}
		if(loginidentifier.getStartTime() == null) {
			ps.setObject(4, null);
		} else { 
			ps.setObject(4, loginidentifier.getStartTime());
		}
		if(loginidentifier.getEndTime() == null) {
			ps.setObject(5, null);
		} else { 
			ps.setObject(5, loginidentifier.getEndTime());
		}
		return ps;
	}

	/*Bulk/Batch Insert*/
	public void insertBatch(List<LoginIdentifier> loginidentifierList) {
		jdbcTemplate.batchUpdate(environment.getProperty(CREATE), new BatchPreparedStatementSetter() {
			@Override
		    public void setValues(PreparedStatement ps, int i) throws SQLException {
				LoginIdentifier loginidentifier = loginidentifierList.get(i);
				setPreparedStatement(loginidentifier, ps);
			}
			@Override
		    public int getBatchSize() {
		        return loginidentifierList.size();
			}
		});
	}

	@Override
	public LoginIdentifier get(Identifier identifier) {
		return getV1(identifier).orElse(null);
	}

	@Override
	public Optional<LoginIdentifier> getV1(Identifier identifier) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("LoginIdentifier.get"), new Object[] {
				identifier.getLocaleCd(),
				identifier.getPk()
			}, loginidentifierRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public LoginIdentifier get(Identifier identifier, String whereClause) {
		return getV1(identifier, whereClause).orElse(null);
	}

	@Override
	public Optional<LoginIdentifier> getV1(Identifier identifier, String whereClause) {
		try {
			switch(whereClause) {
				case BY_IDENTIFIER -> { 
					return Optional.of(jdbcTemplate.queryForObject(environment.getProperty(GET_BY + whereClause), new Object[] { 
						identifier.getLocaleCd(),
						identifier.getText()
					}, loginidentifierRowMapper));
				}
				
				default -> throw new AuthorizeException(ErrorFactory.NOT_IMPLEMENTED, whereClause + " is not implemented");
			}
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public List<LoginIdentifier> list(Identifier identifier) {
		try {
			return jdbcTemplate.query(environment.getProperty("LoginIdentifier.list"), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, loginidentifierRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public List<LoginIdentifier> list(Identifier identifier, String whereClause) {
		try {
			return jdbcTemplate.query(environment.getProperty(LIST_BY + whereClause), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, loginidentifierRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public LoginIdentifier update(LoginIdentifier loginidentifier, Identifier identifier) {
		return updateV1(loginidentifier, identifier).orElse(null);
	}

	@Override
	public Optional<LoginIdentifier> updateV1(LoginIdentifier loginidentifier, Identifier identifier) {
		jdbcTemplate.update(environment.getProperty("LoginIdentifier.update"), 
			loginidentifier.getLoginId(),
			loginidentifier.getGenericCd(),
			loginidentifier.getIdentifier(),
			loginidentifier.getStartTime(),
			loginidentifier.getEndTime(),
			loginidentifier.getRowUpdateInfo(),
			loginidentifier.getLoginIdentifierId());
		return getV1(new Identifier(loginidentifier.getLoginIdentifierId(), identifier.getLocaleCd()));
	}

	@Override
	public int count(Identifier identifier, String whereClause) {
		return 0;
	}

	@Override
	public int delete(LoginIdentifier loginidentifier) {
		return jdbcTemplate.update(environment.getProperty("LoginIdentifier.delete"), loginidentifier.getLoginIdentifierId());
	}

	@Override
	public int deleteV1(Optional<LoginIdentifier> loginidentifier) {
		return delete(loginidentifier.orElseThrow(()->new AuthorizeException(ErrorFactory.RESOURCE_NOT_FOUND, "LoginIdentifier is not available")));
	}

	@Override
	public int delete(Identifier identifier, String whereClause) {
		return jdbcTemplate.update(environment.getProperty(DELETE_BY + whereClause), identifier.getPk());
	}

	RowMapper<LoginIdentifier> loginidentifierRowMapper = (rs, rowNum) -> {

		LoginIdentifier loginidentifier = new LoginIdentifier();

		loginidentifier.setLoginIdentifierId(rs.getObject("login_identifier_id") != null ? rs.getLong("login_identifier_id") : null);

		loginidentifier.setLoginId(rs.getObject("login_id") != null ? rs.getLong("login_id") : null);

		loginidentifier.setGenericCd(rs.getObject("generic_cd") != null ? rs.getInt("generic_cd") : null);

		loginidentifier.setGenericLocale(rs.getObject("generic_locale") != null ? rs.getString("generic_locale") : null);

		loginidentifier.setIdentifier(rs.getObject("identifier") != null ? rs.getString("identifier") : null);

		loginidentifier.setStartTime(rs.getObject("start_time") != null ? rs.getObject("start_time", OffsetDateTime.class) : null);

		loginidentifier.setEndTime(rs.getObject("end_time") != null ? rs.getObject("end_time", OffsetDateTime.class) : null);

		loginidentifier.setRowCreationTime(rs.getObject("row_creation_time") != null ? rs.getObject("row_creation_time", OffsetDateTime.class) : null);

		loginidentifier.setRowUpdateTime(rs.getObject("row_update_time") != null ? rs.getObject("row_update_time", OffsetDateTime.class) : null);

		loginidentifier.setRowUpdateInfo(rs.getObject("row_update_info") != null ? rs.getString("row_update_info") : null);

		return loginidentifier;
	};

}


