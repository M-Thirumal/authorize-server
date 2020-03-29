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
public interface GenericPartyService<T, I, V> extends GenericService<T, I, V> {
	/**
	 * Activate the party
	 * @param identifier
	 * @param isVerified
	 * @return T
	 */
	T activate(I identifier, V isVerified);
	/**
	 * In-activate the party
	 * @param identifier
	 * @param isVerified
	 * @return T
	 */
	T inActivate(I identifier, V isVerified);
	
}
