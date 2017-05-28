package com.bizprout.web.app.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizprout.web.api.service.LoginService;
import com.bizprout.web.app.dto.UserDTO;

@RestController
@RequestMapping("/")
public class LoginResource {

	@Autowired
	private LoginService<UserDTO> loginService;
	@Autowired
	@Qualifier("userDetailsServiceImpl")
	private UserDetailsService detailsService;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public LoginResource() {
		try {
			System.out.println(this.getClass().getSimpleName() + "Created...");
			logger.info(this.getClass().getSimpleName() + "Created...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@PostMapping({ "security/account", "/login" })
	public ResponseEntity<Object> user() {	
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (authentication.isAuthenticated()) {
						
			UserDTO userDTO = loginService.findByUsername(authentication.getName());
			userDTO.setPassword(null);
			userDTO.setAuthentication(authentication);
			
			return new ResponseEntity<Object>(userDTO, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Auth Failed", HttpStatus.FORBIDDEN);
	}

}
