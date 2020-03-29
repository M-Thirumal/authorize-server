/**
 * 
 */
package in.thirumal.config;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Thirumal
 * @version 1.0
 * @since 13/05/2018
 */
@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) 
    		throws IOException {
        logger.debug("Inside custom basic authenticate");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("WWW-Authenticate", "INDsolv realm=" + getRealmName());
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader("Origin"));
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, PATCH, GET, OPTIONS, DELETE");
	    response.setHeader("Access-Control-Max-Age", "3600");
	    
	    OutputStream outputStream = response.getOutputStream();
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.writeValue(outputStream, authException.getMessage());
	    outputStream.flush();
        /*PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 : " + authException.getMessage());*/
    }
     
    @Override
    public void afterPropertiesSet() {
        setRealmName("indsolv");
        try {
			super.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
