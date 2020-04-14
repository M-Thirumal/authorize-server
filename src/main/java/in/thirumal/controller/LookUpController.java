/**
 * 
 */
package in.thirumal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.thirumal.persistence.dao.GenericCdDao;
import in.thirumal.persistence.model.shared.GenericCd;
import in.thirumal.persistence.model.shared.Identifier;
import in.thirumal.persistence.model.shared.LocaleCd;

/**
 * @author thirumal
 *
 */
@RestController
@RequestMapping("/look-up")
public class LookUpController extends GenericController{

	@Autowired
	private GenericCdDao genericCdDao;
	  
	@Cacheable(value = "listParentCd")
    @GetMapping(value = "/parent")
  	public List<GenericCd> listParentCd(@RequestHeader(value = "User-Accept-Language", defaultValue = "en-IN") String locale) {
  		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
  		return genericCdDao.list(Identifier.builder().localeCd(localeCdDao.getLocaleCd(locale)).build(), GenericCdDao.BY_PARENT_NULL);
  	}
    
    @Cacheable(value = "listCd", key = "#parentCd")
    @GetMapping(value = "/{parentCd}")
  	public List<GenericCd> listCd(@RequestParam(name="parentCd") Long parentCd, 
  			@RequestHeader(value = "User-Accept-Language", defaultValue = "en-IN") String locale) {
  		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
  		return genericCdDao.list(Identifier.builder().pk(parentCd).localeCd(localeCdDao.getLocaleCd(locale)).build(), GenericCdDao.BY_PARENT_CD);
  	}
    
    @GetMapping(value = "/locale")
  	public List<LocaleCd> listLocale() {
  		logger.debug(this.getClass().getSimpleName() + ": " + Thread.currentThread().getStackTrace()[1].getMethodName());
  		return localeCdDao.list(Identifier.builder().build());
  	}
    
}
