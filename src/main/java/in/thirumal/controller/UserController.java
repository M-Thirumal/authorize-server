/**
 * 
 */
package in.thirumal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import in.thirumal.persistence.model.LoginQuestion;
import in.thirumal.persistence.model.shared.Identifier;
import in.thirumal.service.resource.UserResource;
import in.thirumal.service.rest.UserService;

/**
 * @author Thirumal
 *
 */
@RestController
@RequestMapping("/user")
public class UserController extends GenericController {
	
	@Autowired
	private UserService userService; 

	@PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.CREATED)
	public UserResource create(@Valid @RequestBody UserResource userResource, 
			@RequestParam(name="recaptcha") String recaptchaResponse, HttpServletRequest request,
			@RequestHeader(value = "User-Accept-Language", defaultValue = "en-IN") String locale) {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		//Start of Verify reCaptcha
		verifyCaptcha(recaptchaResponse, request);
		//End of reCaptcha
		return userService.create(userResource, Identifier.builder().localeCd(getLocaleCd(locale)).build());
	}
	
	private void verifyCaptcha(String recaptchaResponse, HttpServletRequest request) {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		logger.debug(recaptchaResponse, request);
	}

	@PutMapping(value = "", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public boolean changePassword(@Valid @RequestBody UserResource userResource, 
			@RequestParam(name="recaptcha") String recaptchaResponse, HttpServletRequest request,
			@RequestHeader(value = "User-Accept-Language", defaultValue = "en-IN") String locale) {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		//Start of Verify reCaptcha
		verifyCaptcha(recaptchaResponse, request);
		//End of reCaptcha
		return userService.changePassword(userResource, Identifier.builder().localeCd(getLocaleCd(locale)).build());
	}
	
	@PostMapping(value = "/question", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public boolean setQuestions(@RequestBody List<LoginQuestion> loginQuestions, @RequestParam(name="recaptcha") String recaptchaResponse, 
			HttpServletRequest request,	@RequestHeader(value = "User-Accept-Language", defaultValue = "en-IN") String locale) {
		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
		//Start of Verify reCaptcha
		verifyCaptcha(recaptchaResponse, request);
		//End of reCaptcha
		return userService.setQuestions(loginQuestions, Identifier.builder().localeCd(getLocaleCd(locale)).build());
	}
	
}
