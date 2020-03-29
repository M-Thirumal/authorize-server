/**
 * 
 */
package in.thirumal.service;

/**
 * @author Thirumal
 * @since 05/06/2018
 * @version 1.0
 */
public interface NotificationService <T> {
	
	public void send(T notificationResource);
	
}
