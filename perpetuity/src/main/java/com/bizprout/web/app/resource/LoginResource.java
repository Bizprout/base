package com.bizprout.web.app.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			logger.info(this.getClass().getSimpleName() + "Created...");
		} catch (Exception e) {
			logger.error(e.getMessage()+"...."+this.getClass());
		}
	}

	@PostMapping({ "security/account", "/login" })
	public ResponseEntity<Object> user() {	
		try {
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			if (authentication.isAuthenticated()) {
							
				UserDTO userDTO = loginService.findByUsername(authentication.getName());
				userDTO.setPassword(null);
				userDTO.setAuthentication(authentication);
				
				return new ResponseEntity<Object>(userDTO, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage()+"...."+this.getClass());
		}
		return new ResponseEntity<Object>("Auth Failed", HttpStatus.OK);
	}
	
	@PostMapping(value="session/logout")
	public void logoutSession(HttpServletRequest request, HttpServletResponse response)
	{		
		try {
			HttpSession session=request.getSession(false);
			SecurityContextHolder.clearContext();
			session=request.getSession(false);
			if(session != null)
			{
				session.invalidate();
			}
			
			for(Cookie cookie : request.getCookies())
			{
				cookie.setMaxAge(0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage()+"...."+this.getClass());
		}
		
	}

}
