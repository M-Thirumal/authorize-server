/**
 * 
 */
package in.thirumal.service;

/**
 * @author Thirumal
 * @param <T>
 * @param <I>
 * @param <V>
 *
 */
public interface GenericPartyService<T, I> extends GenericService<T, I> {
	/**
	 * Activate the party
	 * @param identifier
	 * @return T
	 */
	T activate(I identifier);
	/**
	 * In-activate the party
	 * @param identifier
	 * @return T
	 */
	T inActivate(I identifier);
	
}
