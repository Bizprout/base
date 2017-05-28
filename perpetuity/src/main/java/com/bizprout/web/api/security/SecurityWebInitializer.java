package com.bizprout.web.api.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebInitializer extends
		AbstractSecurityWebApplicationInitializer {

	public SecurityWebInitializer() {
		System.out.println("SecurityWebInitializer");
	}

}
