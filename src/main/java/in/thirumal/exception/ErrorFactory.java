package in.thirumal.exception;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author Thirumal
 * @version 1.0
 * @since 26/04/2018
 */
@Component
public class ErrorFactory {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Environment environment;
	
	public ErrorFactory() {
	}
	
	@Autowired
	public ErrorFactory(Environment environment) {
		this.environment = environment;
	}
	
	// some commons and generic errors
	public static final ErrorDefinition RESOURCE_FAILED_VALIDATION = new ErrorDefinition("RESOURCE_FAILED_VALIDATION",
			42201, ErrorLevel.BUSINESS, ErrorSeverity.FATAL, "Resource failed validation", HttpStatus.PRECONDITION_FAILED, null);
	
	public static final ErrorDefinition EXPECTATION_FAILED = new ErrorDefinition("EXPECTATION_FAILED",
			42202, ErrorLevel.BUSINESS, ErrorSeverity.FATAL, "Expectation failed", HttpStatus.EXPECTATION_FAILED, null);
	
	public static final ErrorDefinition FAILED_TO_CREATE_XML= new ErrorDefinition("FAILED_TO_CREATE_XML",
			42203, ErrorLevel.BUSINESS, ErrorSeverity.FATAL, "Failed to create XML", HttpStatus.EXPECTATION_FAILED, null);
	
	public static final ErrorDefinition IDENTIFICATION_MISMATCH = new ErrorDefinition("IDENTIFICATION_MISMATCH",
			42204, ErrorLevel.BUSINESS, ErrorSeverity.FATAL, "Individual - PAN, Company -CIN, LLP - LLPIN, Partnership Firm - Business PAN",
			HttpStatus.EXPECTATION_FAILED, null);
	
	public static final ErrorDefinition RESOURCE_CONFLICT = new ErrorDefinition("CONFLICT",
			409009, ErrorLevel.CONFLICT, ErrorSeverity.NONFATAL, "Resource Confilit", HttpStatus.CONFLICT, null);
	
	public static final ErrorDefinition INVALID_TIME = new ErrorDefinition("INVALID_TIME", 40003, ErrorLevel.REQUEST,
			ErrorSeverity.NONFATAL, "Invalid Time Numerical Offset", HttpStatus.BAD_REQUEST, null);
	
	public static final ErrorDefinition CONTAINS_VIRUS = new ErrorDefinition("CONTAINS_VIRUS", 4000312, ErrorLevel.REQUEST,
			ErrorSeverity.NONFATAL, "The uploaded file contains virus", HttpStatus.BAD_REQUEST, null);
	
	
	public static final ErrorDefinition RESOURCE_NOT_FOUND = new ErrorDefinition("RESOURCE_NOT_FOUND", 4040140,
			ErrorLevel.RESOURCE, ErrorSeverity.FATAL, "Resource not found", HttpStatus.NOT_FOUND, null);
	
	public static final ErrorDefinition NOT_MODIFIED = new ErrorDefinition("NOT_MODIFIED", 3040001, ErrorLevel.RESOURCE,
			ErrorSeverity.FATAL, "This resource is not modified.", HttpStatus.NOT_MODIFIED, null);
	
	public static final ErrorDefinition RESOURCE_LOCKED = new ErrorDefinition("RESOURCE_LOCKED", 40901,
			ErrorLevel.RESOURCE, ErrorSeverity.FATAL, "Resource locked", HttpStatus.LOCKED, null);

	public static final ErrorDefinition INTERNAL_SERVER_ERROR = new ErrorDefinition("INTERNAL_SERVER_ERROR", 50001,
			ErrorLevel.REQUEST, ErrorSeverity.FATAL, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR, null);

	public static final ErrorDefinition DATABASE_EXCEPTION = new ErrorDefinition("DATABASE_EXCEPTION", 5000002,
			ErrorLevel.REQUEST, ErrorSeverity.FATAL, "Database Exception", HttpStatus.INTERNAL_SERVER_ERROR, null);
	
	public static final ErrorDefinition DATABASE_TIMEOUT = new ErrorDefinition("DATABASE_TIMEOUT", 50003,
			ErrorLevel.REQUEST, ErrorSeverity.FATAL, "Database server timeout", HttpStatus.INTERNAL_SERVER_ERROR, null);
	
	public static final ErrorDefinition ALFRESCO_TIMEOUT = new ErrorDefinition("ALFRESCO_TIMEOUT", 50004,
			ErrorLevel.REQUEST, ErrorSeverity.FATAL, "Database server timeout", HttpStatus.INTERNAL_SERVER_ERROR, null);
	
	public static final ErrorDefinition FAILED_TO_UPLOAD =  new ErrorDefinition("FAILED_TO_UPLOAD", 500305, ErrorLevel.REQUEST,
			ErrorSeverity.FATAL, "Failed to upload document to alfresco", HttpStatus.SERVICE_UNAVAILABLE, null);
	
	public static final ErrorDefinition FAILED_TO_GENERATE =  new ErrorDefinition("FAILED_TO_GENERATE", 500306, ErrorLevel.REQUEST,
			ErrorSeverity.FATAL, "Failed to generate report using Report Generation", HttpStatus.SERVICE_UNAVAILABLE, null);

	public static final ErrorDefinition SEARCH_TIMEOUT = new ErrorDefinition("SEARCH_TIMEOUT", 50301,
			ErrorLevel.REQUEST, ErrorSeverity.FATAL, "Search engine server timeout", HttpStatus.REQUEST_TIMEOUT, null);

	public static final ErrorDefinition NOT_IMPLEMENTED = new ErrorDefinition("NOT_IMPLEMENTED", 50100,
			ErrorLevel.REQUEST, ErrorSeverity.FATAL, "Not implemented contact Admin for detail", HttpStatus.NOT_IMPLEMENTED, null);
	
	public static final ErrorDefinition FAIL_DELETING_PERMANENTLY = new ErrorDefinition("FAIL_DELETING_PERMANENTLY",
			5000003, ErrorLevel.REQUEST, ErrorSeverity.FATAL, "Fails to delete permanently the resource", HttpStatus.INTERNAL_SERVER_ERROR, null);

	public static final ErrorDefinition FAILED_UPDATE = new ErrorDefinition("FAILED_UPDATE",
			5000003, ErrorLevel.REQUEST, ErrorSeverity.FATAL, "Failed to update resource", HttpStatus.INTERNAL_SERVER_ERROR, null);

	public static final ErrorDefinition FAIL_LOADING_CONFIG = new ErrorDefinition("FAIL_LOADING_CONFIG", 5000004,
			ErrorLevel.REQUEST, ErrorSeverity.FATAL, "Fails to load config file to initialize the application", HttpStatus.INTERNAL_SERVER_ERROR,
			null);

	public static final ErrorDefinition READ_ONLY = new ErrorDefinition("READ_ONLY", 40902, ErrorLevel.CONFLICT,
			ErrorSeverity.FATAL, "This resource is read only.", HttpStatus.FORBIDDEN, null);
	
	public static final ErrorDefinition BAD_REQUEST = new ErrorDefinition("BAD_REQUEST", 400301, ErrorLevel.REQUEST,
			ErrorSeverity.FATAL, "Bad requst", HttpStatus.BAD_REQUEST, null);

	public static final ErrorDefinition DUPLICATION = new ErrorDefinition("DUPLICATION", 400302, ErrorLevel.REQUEST,
			ErrorSeverity.FATAL, "Duplication", HttpStatus.BAD_REQUEST, null);

	public static final ErrorDefinition FORBIDDEN = new ErrorDefinition("FORBIDDEN", 400303, ErrorLevel.REQUEST,
			ErrorSeverity.FATAL, "Forbidden, Requires high level permission, contact Admin/Thirumal", HttpStatus.FORBIDDEN, null);
	
	public static final ErrorDefinition NO_PERMISSION = new ErrorDefinition("ACCESS DENIED", 400304, ErrorLevel.REQUEST,
			ErrorSeverity.NONFATAL, "User does not have enough permission", HttpStatus.FORBIDDEN, null);

	public static final ErrorDefinition CREDENTIALS_EXPIRED = new ErrorDefinition("CREDENTIALS_EXPIRED", 400303, ErrorLevel.REQUEST,
			ErrorSeverity.FATAL, "User credentials have expired", HttpStatus.FORBIDDEN, null);

	public static final ErrorDefinition UNAUTHORIZED = new ErrorDefinition("UNAUTHORIZED", 401004, ErrorLevel.REQUEST,
			ErrorSeverity.FATAL, "Incorrect userId & password combination or userId has been locked.", HttpStatus.UNAUTHORIZED, null);

	public static final ErrorDefinition NO_CONTENT = new ErrorDefinition("NO_CONTENT", 204001, ErrorLevel.REQUEST,
			ErrorSeverity.FATAL, "No Content", HttpStatus.NO_CONTENT, null);
	//ZOHO
	public static final ErrorDefinition FAILED_TO_CONNECT_ZOHO = new ErrorDefinition("FAILED_TO_CONNECT_ZOHO", 500500, ErrorLevel.REQUEST,
			ErrorSeverity.NONFATAL, "FAILED_TO_CONNECT_ZOHO", HttpStatus.INTERNAL_SERVER_ERROR, null);

	public static final ErrorDefinition ITEXT = new ErrorDefinition("ITEXT", 415100, ErrorLevel.REQUEST,
			ErrorSeverity.NONFATAL, "Itext error", HttpStatus.UNSUPPORTED_MEDIA_TYPE, null);

	public static final ErrorDefinition GOOGLE_RECAPTCHA = new ErrorDefinition("GOOGLE_RECAPTCHA", 401500, ErrorLevel.REQUEST,
			ErrorSeverity.NONFATAL, "Recaptcha not verified", HttpStatus.UNAUTHORIZED, null);
	
	public static final ErrorDefinition TOO_MANY_REQUESTS = new ErrorDefinition("TOO_MANY_REQUESTS", 429001, ErrorLevel.REQUEST,
			ErrorSeverity.NONFATAL, "Too Many Requests", HttpStatus.TOO_MANY_REQUESTS, null);

	public static final ErrorDefinition TOO_LARGE = new ErrorDefinition("TOO_LARGE", 413001, ErrorLevel.REQUEST,
			ErrorSeverity.NONFATAL, "Size exceeds the limit", HttpStatus.PAYLOAD_TOO_LARGE, null);

	public static final ErrorDefinition FAILED_TO_INSERT = new ErrorDefinition("FAILED_TO_INSERT", 500045, ErrorLevel.REQUEST,
			ErrorSeverity.NONFATAL, "Dao Failed to insert", HttpStatus.INTERNAL_SERVER_ERROR, null);

	public static final ErrorDefinition FEIGN_EXCEPTION = new ErrorDefinition("FEIGN_EXCEPTION", 500046, ErrorLevel.REQUEST,
			ErrorSeverity.NONFATAL, "Microservice may not be available", HttpStatus.INTERNAL_SERVER_ERROR, null);
	
	final Map<String, HttpStatus> httpStatus = Map.ofEntries(
			Map.entry("204", HttpStatus.NO_CONTENT),
			Map.entry("304", HttpStatus.NOT_MODIFIED),
			Map.entry("400", HttpStatus.BAD_REQUEST),
			Map.entry("401", HttpStatus.FORBIDDEN),
			Map.entry("404", HttpStatus.NOT_FOUND),
			Map.entry("406", HttpStatus.NOT_ACCEPTABLE),
			Map.entry("409", HttpStatus.CONFLICT),
			Map.entry("500", HttpStatus.INTERNAL_SERVER_ERROR),
			Map.entry("501", HttpStatus.NOT_IMPLEMENTED),
			Map.entry("503", HttpStatus.SERVICE_UNAVAILABLE),
			Map.entry("413", HttpStatus.PAYLOAD_TOO_LARGE));
	
	/**
	 * 
	 * @param key
	 * @param cause
	 * @return ErrorDefinition
	 */
	public ErrorDefinition getErrorDefinitionByCode(String key){
		logger.info(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (environment.getProperty(key) != null) {
			return getErrorDefinition(key).clone();
		} else {
			return getErrorDefinition("79004").clone();	
		}		
	}
	/**
	 * 
	 * @param key
	 * @param cause
	 * @return ErrorDefinition
	 */
	public ErrorDefinition getErrorDefinition(String key) {
		logger.info(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		String[] errorDefinitions = environment.getProperty(key).split("\\+");
		if (errorDefinitions != null && errorDefinitions.length == 5) {
			String label = errorDefinitions[0];
			String level = errorDefinitions[1];
			String severity = errorDefinitions[2];
			String msg = errorDefinitions[3];
			String sstatus = errorDefinitions[4].trim();
			int code = Integer.parseInt(key);
			return new ErrorDefinition(label, code, ErrorLevel.getErrorLevel(level), ErrorSeverity.getErrorSeverity(severity), msg, httpStatus.get(sstatus));
		}
		logger.error("There is no definition for input key: {}", key);
		throw new AuthorizeException("There is no Error definition. Please contact development team");
	}

}

