package com.bizprout.web.api.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler implements
		AuthenticationEntryPoint, AccessDeniedHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString("Auth Failed");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(jsonInString);
	}

	public void commence(HttpServletRequest arg0, HttpServletResponse response,
			AuthenticationException arg2) throws IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString("Auth Failed");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(jsonInString);

	}

	public void handle(HttpServletRequest arg0, HttpServletResponse response,
			AccessDeniedException arg2) throws IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString("Auth Failed");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(jsonInString);

	}

}
