/**
 * 
 */
package in.thirumal.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;

import in.thirumal.exception.AuthorizeException;
import in.thirumal.exception.ErrorDefinition;
import in.thirumal.exception.ErrorFactory;
import in.thirumal.persistence.dao.LocaleCdDao;

/**
 * @author Thirumal
 * @since 20/03/2019
 * @version 1.0
 */
@ControllerAdvice
public class GenericController {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected LocaleCdDao localeCdDao; 
	@Autowired
	private ErrorFactory errorFactory;
	
	@ExceptionHandler(AuthorizeException.class)
	public ResponseEntity<Object> indsolvException(AuthorizeException indsolvException, HttpServletResponse httpServletResponse) {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		if (indsolvException.getErrorDefinition() != null) {
			return new ResponseEntity<>(indsolvException.getErrorDefinition(), new HttpHeaders(), indsolvException.getErrorDefinition().getHttpStatus());
		}
		ErrorDefinition errorDefinition = errorFactory.getErrorDefinitionByCode(indsolvException.getMessage());
		return new ResponseEntity<>(errorDefinition, new HttpHeaders(), errorDefinition.getHttpStatus());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException e, HttpServletResponse httpServletResponse) {
	    List<FieldError> errors = e.getBindingResult().getFieldErrors();	   
	    StringBuilder errorMessage = new StringBuilder();
	    errors.stream().forEach(e1 -> errorMessage.append(e1.getField().toUpperCase() + " " + e1.getDefaultMessage()));
	    ErrorDefinition errorDefinition = ErrorFactory.BAD_REQUEST;
	    errorDefinition.setMessage(errorMessage.toString());
	    return new ResponseEntity<>(errorDefinition, new HttpHeaders(), HttpStatus.PRECONDITION_FAILED);
	}

	protected boolean isPdfFile(MultipartFile file) {
		if (file.isEmpty()) {
            throw new AuthorizeException(ErrorFactory.FAILED_TO_UPLOAD, "Uploaded file is empty!");
         }
 		if (!file.getContentType().equalsIgnoreCase("application/pdf")) {
 			throw new AuthorizeException(ErrorFactory.RESOURCE_FAILED_VALIDATION, "Current version of app accepts only PDF");
 		}
 		logger.debug("file: " + file.getContentType());
		return true;
	}
	
	public int getLocaleCd(String locale) {
		return localeCdDao.getLocaleCd(locale);
	}
	
}
