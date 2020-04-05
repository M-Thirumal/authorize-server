/**
 * 
 */
package in.thirumal.event;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thirumal
 *
 */
public class CacheEventLogger implements CacheEventListener<Object, Object> {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
		logger.info("Cache event {} for item with key {}. Old value = {}, New value = {}", cacheEvent.getType(), cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
	}

}
