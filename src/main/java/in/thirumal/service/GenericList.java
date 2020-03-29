/**
 * 
 */
package in.thirumal.service;

import java.util.ArrayList;

/**
 * @author Thirumal
 * @since 11/06/2019
 * @version 1.0
 */
public class GenericList<T> extends ArrayList<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7659476177296505770L;
	
	private Class<T> genericType;
	/**
	 * 
	 */
	public GenericList() {
	}
	
	public GenericList(Class<T> c) {
		this.genericType = c;
    }

    public Class<T> getGenericType() {
    	return genericType;
    }

}
