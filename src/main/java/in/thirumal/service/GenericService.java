package in.thirumal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Thirumal
 * @version 1.0
 */
public interface GenericService <T, I> {
	/**
	 * resource to create new instances in database
	 * Identifier will hold localeCd 
	 */
	T create(T resource, I identifier);

	/**
	 * resource to update already existing instances in database
	 * Identifier will hold primary key id and localeCd 
	 */
	T update(T resource, I identifier);
	/** 
	 * Unique identifier with locale to get instance 
	 */
	T get(I identifier);

	/** 
	 * Unique identifier with locale to get instance for list method 
	 */
	T getForList(I identifier);
	/**
	 * Method is used for list
	 */
	T forList(T t, I identifier);
	/**
	 * List all instances for service
	 */
	List<T> list(I identifier);
	/**
	 * Delete instance 
	 */
	boolean delete(I identifier);
		
	default <M> List<M> filter(List<M> list, Predicate<M> p) {
		List<M> result = new ArrayList<>();
		for (M m : list) {
			if (p.test(m)) {
				result.add(m);
			}
		}
		return result;
	}
	
}
