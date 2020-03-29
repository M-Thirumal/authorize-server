/**
 * 
 */
package in.thirumal.persistence;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import in.thirumal.exception.ErrorFactory;
import in.thirumal.exception.AuthorizeException;
import lombok.Getter;
import lombok.ToString;

/**
 * Generic Data Access Object interface for repository
 * All ICMS DAO must implement this interface
 * @author Thirumal[திருமால்]
 * @since 14/04/2017
 * @version 1.0
 */
public interface GenericDao <T, I, S> {
	 
    /** Persist the new instance (Model) object into database, I will hold localeCd value */
    default T create(@NotNull T model, I identifier) {
    	return createV1(Objects.requireNonNull(model, "Model can't be null"), identifier).orElse(null);
    }
   
    /** Persist the new instance (Model) object into database, I will hold localeCd value */
    Optional<T> createV1(T model, I identifier);
    
    /** Persist the new instance (Model) object into database, I will hold localeCd value */
    Long insert(T model, I identifier);
    /**
     * @since 0.6
     * Retrieve an object that was previously persisted to the database using
     * the indicated identifier (contains primary key & locale Cd) else insert new row and retrieve
     */
    default T upsert(T model, I identifier) {
    	return getV1(identifier).orElseGet(()->create(model, identifier));
    }
    /**
     * @since 0.6
     * Retrieve an object that was previously persisted to the database using
     * the indicated identifier (contains primary key & locale Cd) and where clause else insert new row and retrieve
     */
    default T upsert(T model, I identifier, S whereClause) {
    	return getV1(identifier, whereClause).orElseGet(()->create(model, identifier));
    }
    /** Retrieve an object that was previously persisted to the database using
     *  the indicated identifier (contains primary key & locale Cd)
     */
    default T get(@NotNull I identifier) {
    	return getV1(Objects.requireNonNull(identifier, "Identifier in the get method should not be null")).orElse(null);
    }
    
    /** Retrieve an Optional<object> that was previously persisted to the database using
     *  the indicated identifier (contains primary key & locale Cd)
     */
    Optional<T> getV1(I identifier);
    
    /** Retrieve an object that was previously persisted to the database using
     *  the indicated identifier (contains primary key & locale Cd) and whereClause
     */
    default T get(I identifier, S whereClause) {
    	return getV1(identifier, whereClause).orElse(null);
    }
    
    /** Retrieve an Optional<object> that was previously persisted to the database using
     *  the indicated identifier (contains primary key & locale Cd) and whereClause
     */
    Optional<T> getV1(I identifier, S whereClause);
   
    /**
     * Retrieve all instance using identifier ( id & LocaleCd)
     */
    List<T> list(I identifier);
    
    /**
     * Retrieve all instance using the indicated identifier (id
     *  & localeCd) and where clause
     */
    List<T> list(I identifier, S whereClause);
    
    /**
  	 * Count all instance using the indicated identifier (id
     *  & localeCd) and where clause
     */
    int count(I identifier, S whereClause);
    
    /** Save changes made to a persistent object. */
    default T update(T transientObject, I identifier) {
    	return updateV1(transientObject, identifier).orElse(null);
    }
    
    /** Save changes made to a persistent object. */
    Optional<T> updateV1(T transientObject, I identifier);
 
    /** Remove an object from persistent storage in the database */
    int delete(T persistentObject);

    /** Remove an object from persistent storage in the database */
    default int deleteV1(Optional<T> persistentObject) {
    	return delete(persistentObject.orElseThrow(()->new AuthorizeException(ErrorFactory.RESOURCE_NOT_FOUND, "The requested object is not found in database")));
    }
    
    /** Remove all objects from persistent storage in the database
     * using Identifier and where clause (mostly Foreign key)*/
    int delete(I identifier, S whereClause);
    
    default int findAndDelete(I identifier) {
    	return deleteV1(getV1(identifier));
    }
	
    /**
     * @author Thirumal
     * @since 0.6
     * Enum for PostgreSQL constant 
     */
	@Getter@ToString
	enum postgreSqlConstant {
		INFINITY("infinity"), //Should be used for end date
		INTEGER("INTEGER"),
		TAGS("tags");
		private String constant;
		postgreSqlConstant(final String constant) {
			this.constant = constant;
		}
	}
	
	/**
	 * Replace the string with invalues with default delimitter "," or the requested delimitter
	 * @since 0.6
	 * @param <E extends Number>
	 * @param query
	 * @param replaceString
	 * @param inValues
	 * @param delimitter
	 * @return query with inValues
	 */
	default <E extends Number> String setInvalues(String query, String replaceString, Set<E> inValues, String... delimitter) {
		return query.replace(replaceString, inValues.stream().map(Object::toString)
				.collect(Collectors.joining(delimitter.length > 0 ? delimitter[0]:",")));
	}
	
}