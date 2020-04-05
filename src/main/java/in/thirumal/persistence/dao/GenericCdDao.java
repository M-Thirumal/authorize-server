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
import in.thirumal.persistence.model.shared.GenericCd;
import in.thirumal.persistence.model.shared.Identifier;

/**
 * Generated using erm-postgresql-spring-boot project
 * @see <a href="https://github.com/M-Thirumal/erm-postgresql-spring-boot">erm-postgresql-spring-boot</a>
 * @author erm-postgresql-spring-boot
 * @since 2020-04-04
 * @version 1.0
 */
@Repository
public class GenericCdDao implements GenericDao <GenericCd, Identifier, String>  {

	private static final String CREATE    = "GenericCd.create";
	private static final String GET_BY    = "GenericCd.getBy";
	private static final String LIST_BY   = "GenericCd.listBy";
	private static final String DELETE_BY = "GenericCd.deleteBy";
	//
	public static final String BY_PARENT_NULL = "ParentNull";
	public static final String BY_PARENT_CD = "ParentCd";

	private final JdbcTemplate jdbcTemplate;
	private Environment environment;

	@Autowired
	public GenericCdDao(JdbcTemplate jdbcTemplate, Environment environment) {
		this.jdbcTemplate = jdbcTemplate;
		this.environment = environment;
	}

	@Override
	public GenericCd create(GenericCd genericcd, Identifier identifier) { 
		return createV1(genericcd, identifier).orElse(null);
	}

	@Override
	public Optional<GenericCd> createV1(GenericCd genericcd, Identifier identifier) { 
		return getV1(new Identifier(insert(genericcd, identifier), identifier.getLocaleCd()));
	}

	@Override
	public Long insert(GenericCd genericcd, Identifier identifier) {
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(con ->
			setPreparedStatement(genericcd, con.prepareStatement(environment.getProperty(CREATE),
					new String[] { "generic_cd" })), holder);
		return holder.getKey().longValue();
	}

	public PreparedStatement setPreparedStatement(GenericCd genericcd, PreparedStatement ps) throws SQLException {
		if(genericcd.getCode() == null) {
			ps.setObject(1, null);
		} else { 
			ps.setString(1, genericcd.getCode());
		}
		if(genericcd.getStartDate() == null) {
			ps.setObject(2, null);
		} else { 
			ps.setObject(2, genericcd.getStartDate());
		}
		if(genericcd.getEndDate() == null) {
			ps.setObject(3, null);
		} else { 
			ps.setObject(3, genericcd.getEndDate());
		}
		if(genericcd.getRowCreatedBy() == null) {
			ps.setObject(4, null);
		} else { 
			ps.setString(4, genericcd.getRowCreatedBy());
		}
		if(genericcd.getRowUpdatedBy() == null) {
			ps.setObject(5, genericcd.getRowCreatedBy());
		} else { 
			ps.setString(5, genericcd.getRowUpdatedBy());
		}
		ps.setLong(6, genericcd.getParentGenericCd());
		return ps;
	}

	/*Bulk/Batch Insert*/
	public void insertBatch(List<GenericCd> genericcdList) {
		jdbcTemplate.batchUpdate(environment.getProperty(CREATE), new BatchPreparedStatementSetter() {
			@Override
		    public void setValues(PreparedStatement ps, int i) throws SQLException {
				GenericCd genericcd = genericcdList.get(i);
				setPreparedStatement(genericcd, ps);
			}
			@Override
		    public int getBatchSize() {
		        return genericcdList.size();
			}
		});
	}

	@Override
	public GenericCd get(Identifier identifier) {
		return getV1(identifier).orElse(null);
	}

	@Override
	public Optional<GenericCd> getV1(Identifier identifier) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty("GenericCd.get"), new Object[] {
				identifier.getLocaleCd(),
				identifier.getPk()
			}, genericcdRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public GenericCd get(Identifier identifier, String whereClause) {
		return getV1(identifier, whereClause).orElse(null);
	}

	@Override
	public Optional<GenericCd> getV1(Identifier identifier, String whereClause) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(environment.getProperty(GET_BY + whereClause), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			}, genericcdRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<GenericCd> list(Identifier identifier) {
		try {
			return jdbcTemplate.query(environment.getProperty("GenericCd.list"), new Object[] { 
				identifier.getLocaleCd(),
				identifier.getPk()
			 }, genericcdRowMapper);
		} catch (Exception e) {
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public List<GenericCd> list(Identifier identifier, String whereClause) {
		try {
			var parameters = switch(whereClause) {
				case BY_PARENT_NULL -> new Object[] { identifier.getLocaleCd() };
				case BY_PARENT_CD -> new Object[] { identifier.getLocaleCd(), identifier.getPk() };
				default -> throw new AuthorizeException(ErrorFactory.NOT_IMPLEMENTED);
			};
			return jdbcTemplate.query(environment.getProperty(LIST_BY + whereClause), parameters, genericcdRowMapper);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthorizeException(ErrorFactory.DATABASE_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public GenericCd update(GenericCd genericcd, Identifier identifier) {
		return updateV1(genericcd, identifier).orElse(null);
	}

	@Override
	public Optional<GenericCd> updateV1(GenericCd genericcd, Identifier identifier) {
		jdbcTemplate.update(environment.getProperty("GenericCd.update"), 
			genericcd.getCode(),
			genericcd.getStartDate(),
			genericcd.getEndDate(),
			genericcd.getRowUpdatedBy(),
			genericcd.getRowUpdateInfo(),
			genericcd.getParentGenericCd(),
			genericcd.getGenericCd());
		return getV1(new Identifier(genericcd.getGenericCd(), identifier.getLocaleCd()));
	}

	@Override
	public int count(Identifier identifier, String whereClause) {
		return 0;
	}

	@Override
	public int delete(GenericCd genericcd) {
		return jdbcTemplate.update(environment.getProperty("GenericCd.delete"), genericcd.getGenericCd());
	}

	@Override
	public int deleteV1(Optional<GenericCd> genericcd) {
		return delete(genericcd.orElseThrow(()->new AuthorizeException(ErrorFactory.RESOURCE_NOT_FOUND, "GenericCd is not available")));
	}

	@Override
	public int delete(Identifier identifier, String whereClause) {
		return jdbcTemplate.update(environment.getProperty(DELETE_BY + whereClause), identifier.getPk());
	}

	RowMapper<GenericCd> genericcdRowMapper = (rs, rowNum) -> {

		GenericCd genericcd = new GenericCd();

		genericcd.setGenericCd(rs.getObject("generic_cd") != null ? rs.getLong("generic_cd") : null);

		genericcd.setGenericLocale(rs.getObject("generic_locale") != null ? rs.getString("generic_locale") : null);
		
		genericcd.setCode(rs.getObject("code") != null ? rs.getString("code") : null);

		genericcd.setStartDate(rs.getObject("start_date") != null ? rs.getObject("start_date", LocalDate.class) : null);

		genericcd.setEndDate(rs.getObject("end_date") != null ? rs.getObject("end_date", LocalDate.class) : null);

		genericcd.setRowCreationTime(rs.getObject("row_creation_time") != null ? rs.getObject("row_creation_time", OffsetDateTime.class) : null);

		genericcd.setRowCreatedBy(rs.getObject("row_created_by") != null ? rs.getString("row_created_by") : null);

		genericcd.setRowUpdatedBy(rs.getObject("row_updated_by") != null ? rs.getString("row_updated_by") : null);

		genericcd.setRowUpdateTime(rs.getObject("row_update_time") != null ? rs.getObject("row_update_time", OffsetDateTime.class) : null);

		genericcd.setRowUpdateInfo(rs.getObject("row_update_info") != null ? rs.getString("row_update_info") : null);

		genericcd.setParentGenericCd(rs.getObject("parent_generic_cd") != null ? rs.getLong("parent_generic_cd") : 0);

		genericcd.setParentGenericLocale(rs.getObject("parent_generic_locale") != null ? rs.getString("parent_generic_locale") : null);

		return genericcd;
	};

}


