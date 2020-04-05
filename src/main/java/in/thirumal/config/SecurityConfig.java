/**
 * 
 */
package in.thirumal.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * @author Thirumal
 * Resource server is enabled in Resource server config (OAuth2 implementation)
 * Authorization config is available in OAuth2Config class
 * Bcrypt is a password encoder
 * @version 1.0
 */
@Configuration
@EnableWebSecurity//(debug=true)
//@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true) //Required for @PreAuthorize("hasRole('ROLE_SYSADMIN')")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	@Value("${jwt.secret}")
//	private String signingKey;
	//private Integer encodingStrength = 256;
	
	//private UserDetailsService userDetailsService;
	private CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;

	@Autowired
	public SecurityConfig(/*UserDetailsService userDetailsService,*/
			CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint) {
	//	this.userDetailsService = userDetailsService;
		this.customBasicAuthenticationEntryPoint = customBasicAuthenticationEntryPoint;
	}

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
        .withUser("thirumal").password("$2a$11$WWZlUCd4XndWGpriAx7Pv.HpZ042awTnlAKr9VDiN9xEdPNS1Xy1q").roles("ADMIN").roles("ACTUATOR");
	}
	
	//@Order(1)
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		//http.csrf().disable();
		http
			.headers().frameOptions().sameOrigin()
			.and()
			.csrf().disable()
	/*		.sessionManagement()
        	.maximumSessions(1)               //(1)
        	.maxSessionsPreventsLogin(false)    //(2)
        //	.expiredUrl("/user/logout")          //(3)
        	.sessionRegistry(sessionRegistry()).and().and() //(4)
        */
			//.exceptionHandling().authenticationEntryPoint((request, response, authException)
			//		-> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)).and()
        	.httpBasic().and()
        	.authorizeRequests()
        	.antMatchers("/error").permitAll()
        	.antMatchers("/oauth/token/revokeById/**").permitAll()
        	.antMatchers("/tokens/**").permitAll()
        	.antMatchers("/actuator/**").hasAnyRole("ACTUATOR")//.anyRequest().authenticated()
        		.antMatchers("/admin/indsolvproperties").hasAnyRole("ACTUATOR")//.anyRequest().authenticated()
        	//	.antMatchers(HttpMethod.OPTIONS, "/**", "/**/*.css", "/img/**", "/third-party/**").permitAll()
        	//	.antMatchers(HttpMethod.POST, "/**", "/**/*.css", "/img/**", "/third-party/**").permitAll()
        		.anyRequest().authenticated()
        		//.and().requiresChannel().anyRequest().requiresSecure()
        		.and().httpBasic().authenticationEntryPoint(customBasicAuthenticationEntryPoint)
        		
        		.and()//.addFilter(basicAuthenticationFilter(super.authenticationManagerBean()))
        		//.formLogin().failureHandler(authenticationFailureHandler)
        		.logout().deleteCookies().invalidateHttpSession(true).logoutSuccessUrl("/user/logout").permitAll();
		//Only one session is allowed
	//	http.sessionManagement().maximumSessions(1).expiredUrl("/login?expired=true");
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        //web.ignoring().antMatchers("/**");
		//web.debug(true);
		web.ignoring()
		.requestMatchers(CorsUtils::isPreFlightRequest)
		.antMatchers("/swagger-ui.html", "/swagger-ui/**", "/webjars/**", "/swagger-resources/**", "/v2/**", "/v3/**", "/api-docs/**")		
		.antMatchers("/look-up/**")
		.antMatchers("/search/individual/service-provider")
		//.antMatchers("/search/individual/**")
		.antMatchers("/search/identification", HttpMethod.POST.toString())
		.antMatchers("/user")
		.antMatchers("/user/service-provider")
		.antMatchers("/user/request-otp-again").antMatchers("/user/request-otp")
		.antMatchers("/user/forgotPassword/**")
		.antMatchers("/user/otp-validate")
		.antMatchers("/user/validate-username")
		.antMatchers("/user/validate-moblie")
		.antMatchers("/user/is-identification-available/**")
		.antMatchers("/user/logout")
		.antMatchers("/login", HttpMethod.POST.toString())
		.antMatchers("/general/report-type", HttpMethod.GET.toString())
		.antMatchers("/dropdown/**")
		//Hystrix
		.antMatchers("/hystrix/**")
		.antMatchers("/proxy.stream/**");
    }
	
	@Bean 
	public PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder();
	//	return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	public BasicAuthenticationFilter basicAuthenticationFilter(AuthenticationManager authManager) throws Exception {
		return new BasicAuthenticationFilter(authManager, customBasicAuthenticationEntryPoint);
	}
	
	 @Override
	 @Bean 
     public AuthenticationManager authenticationManagerBean() throws Exception {
         return super.authenticationManagerBean();
     }
	
    @Bean
    SessionRegistry sessionRegistry() {			
        return new SessionRegistryImpl();
    }
    
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey(signingKey);
//        converter.setKeyPair(new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "eticms".toCharArray()).getKeyPair("jwt"));
//        return converter;
//    }
	/*
	 * @Bean public AuthenticationFailureHandler
	 * customAuthenticationFailureHandler() { return new
	 * CustomAuthenticationFailureHandler(); }
	 */
 
}
