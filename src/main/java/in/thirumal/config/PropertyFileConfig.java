/**
 * 
 */
package in.thirumal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author திருமால்
 * To get all SQL queries from properties file
 */
@Configuration
/*@PropertySources({
		@PropertySource("classpath:sql.properties"),
		@PropertySource("classpath:errorDefinition.properties")
})*/
@PropertySource("classpath:sql.properties")
//@PropertySource("classpath:errorDefinition.properties")
public class PropertyFileConfig {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

}
