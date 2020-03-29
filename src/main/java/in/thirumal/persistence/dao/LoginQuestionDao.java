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
import in.thirumal.persistence.model.LoginQuestion;
import in.thirumal.persistence.model.shared.Identifier;

/**
 * Generated using erm-postgresql-spring-boot project
 * @see <a href="https://github.com/M-Thirumal/erm-postgresql-spring-boot">erm-postgresql-spring-boot</a>
 * @author erm-postgresql-spring-boot
 * @since 2020-03-29
 * @version 1.0
 */
@Repository
public class LoginQuestionDao implements GenericDao <LoginQuestion, Identifier, String>  {

	private static final String CREATE    = "LoginQuestion.create";
	private static final String GET_BY    = "LoginQuestion.getBy";
	private static final String LIST_BY   = "LoginQuestion.listBy";
	private static final String DELETE_BY = "LoginQuestion.deleteBy";

	private final JdbcTemplate jdbcTemplate;
	private Environment environment;

	@Autowired
	public LoginQuestionDao(JdbcTemplate jdbcTemplate, Environment environment) {
		this.jdbcTemplate = jdbcTemplate;
		this.environment = environment;
	}

	@Override
	public LoginQuestion create(LoginQuestion loginquestion, Identifier identifier) { 
		return createV1(loginquestion, identifier).orElse(null);
	}

	@Override
	public Optional<LoginQuestion> createV1(LoginQuestion loginquestion, Identifier identifier) { 
		return getV1(new Identifier(insert(loginquestion, identifier), identifier.getLocaleCd()));
	}

	@Override
	public Long insert(LoginQuestion loginquestion, Identifier identifier) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(con ->
			setPreparedStatement(loginquestion, con.prepareStatement(environment.getProperty(CREATE),
					new String[] { "login_question_id" })), holder);
		return holder.getKey().longValue();
	}

	public PreparedStatement setPreparedStatement(LoginQuestion loginquestion, PreparedStatement ps) throws SQLException {
		if(loginquestion.getLoginId() == null) {
			ps.setObject(1, null);
		} else { 
			ps.setInt(1, loginquestion.getLoginId());
		}
		if(loginquestion.getGenericCd() == null) {
			ps.setObject(2, null);
		} else { 
			ps.setInt(2, loginquestion.getGenericCd());
		}
		if(loginquestion.getAnswer() == null) {
			ps.setObject(3, null);
		} else { 
			ps.setString(3, loginquestion.getAnswer());
		}
		if(loginquestion.getStartTime() == null) {
			ps.setObject(4, null);
		} else { 
			ps.setObject(4, loginquestion.getStartTime());
		}
		if(loginquestion.getEndTime() == null) {
			ps.setObject(5, null);
		} else { 
			ps.setObject(5, loginquestion.getEndTime());
		}
		if(loginquestion.getRowCreationTime() == null) {
			ps.setObject(6, null);
		} else { 
			ps.setObject(6, loginquestion.getRowCreationTime());
		}
		if(loginquestion.getRowUpdateTime() == null) {
			ps.setObject(7, null);
		} else { 
			ps.setObject(7, loginquestion.getRowUpdateTime());
		}
		if(loginquestion.getRowUpdateInfo() == null) {
			ps.setObject(8, null);
		} else { 
			ps.setString(8, loginquestion.getRowUpdateInfo());
		}
		return ps;
	}

	/*Bulk/Batch Insert*/
	public void insertBatch(List<LoginQuestion> loginquestionList) {
		jdbcTemplate.batchUpdate(environment.getProperty(CREATE), new BatchPreparedStatementSetter() {
			@Override
		    public void setValues(PreparedStatement ps, int i) throws SQLException {
				LoginQuestion loginquestion = loginquestionList.get(i);
				setPreparedStatement(loginquestion, ps);
			}
			@Override
		    public int getBatchSize() {
		        return loginquestionList.size();
			}
		});
	}

	@Override
	public LoginQuestion get(Identifier identifier) {
		return getV1(identifier).orElse(null);
	}

	@Override
	public Optional<LoginQuestion> getV1(Identifier identifier) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("LoginQuestion.get"), new Object[] {
				identifier.getLocaleCd(),
				identifier.getPk()
			}, loginquestionRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public LoginQuestion get(Identifier identifier, String whereClause) {
		return getV1(identifier, whereClause).orElse(null);
	}

	@Override
	public Optional<LoginQuestion> getV1(Identifier identifier, String whereClause) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty(GET_BY + whereClause), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			}, loginquestionRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<LoginQuestion> list(Identifier identifier) {
		try {
			return jdbcTemplate.query(environment.getProperty("LoginQuestion.list"), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, loginquestionRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public List<LoginQuestion> list(Identifier identifier, String whereClause) {
		try {
			return jdbcTemplate.query(environment.getProperty(LIST_BY + whereClause), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, loginquestionRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public LoginQuestion update(LoginQuestion loginquestion, Identifier identifier) {
		return updateV1(loginquestion, identifier).orElse(null);
	}

	@Override
	public Optional<LoginQuestion> updateV1(LoginQuestion loginquestion, Identifier identifier) {
		jdbcTemplate.update(environment.getProperty("LoginQuestion.update"), 
			loginquestion.getLoginId(),
			loginquestion.getGenericCd(),
			loginquestion.getAnswer(),
			loginquestion.getStartTime(),
			loginquestion.getEndTime(),
			loginquestion.getRowCreationTime(),
			loginquestion.getRowUpdateTime(),
			loginquestion.getRowUpdateInfo(),
			loginquestion.getLoginQuestionId());
		return getV1(new Identifier(loginquestion.getLoginQuestionId(), identifier.getLocaleCd()));
	}

	@Override
	public int count(Identifier identifier, String whereClause) {
		return 0;
	}

	@Override
	public int delete(LoginQuestion loginquestion) {
		return jdbcTemplate.update(environment.getProperty("LoginQuestion.delete"), loginquestion.getLoginQuestionId());
	}

	@Override
	public int deleteV1(Optional<LoginQuestion> loginquestion) {
		return delete(loginquestion.orElseThrow(()->new AuthorizeException(ErrorFactory.RESOURCE_NOT_FOUND, "LoginQuestion is not available")));
	}

	@Override
	public int delete(Identifier identifier, String whereClause) {
		return jdbcTemplate.update(environment.getProperty(DELETE_BY + whereClause), identifier.getPk());
	}

	RowMapper<LoginQuestion> loginquestionRowMapper = (rs, rowNum) -> {

		LoginQuestion loginquestion = new LoginQuestion();

		loginquestion.setLoginQuestionId(rs.getObject("login_question_id") != null ? rs.getLong("login_question_id") : null);

		loginquestion.setLoginId(rs.getObject("login_id") != null ? rs.getInt("login_id") : null);

		loginquestion.setGenericCd(rs.getObject("generic_cd") != null ? rs.getInt("generic_cd") : null);

		loginquestion.setGenericLocale(rs.getObject("generic_locale") != null ? rs.getString("generic_locale") : null);

		loginquestion.setAnswer(rs.getObject("answer") != null ? rs.getString("answer") : null);

		loginquestion.setStartTime(rs.getObject("start_time") != null ? rs.getObject("start_time", OffsetDateTime.class) : null);

		loginquestion.setEndTime(rs.getObject("end_time") != null ? rs.getObject("end_time", OffsetDateTime.class) : null);

		loginquestion.setRowCreationTime(rs.getObject("row_creation_time") != null ? rs.getObject("row_creation_time", OffsetDateTime.class) : null);

		loginquestion.setRowUpdateTime(rs.getObject("row_update_time") != null ? rs.getObject("row_update_time", OffsetDateTime.class) : null);

		loginquestion.setRowUpdateInfo(rs.getObject("row_update_info") != null ? rs.getString("row_update_info") : null);

		return loginquestion;
	};

}


