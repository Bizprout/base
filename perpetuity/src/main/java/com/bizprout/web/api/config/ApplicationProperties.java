package com.bizprout.web.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

	@Value("bizprout.welcome.page")
	private String welcomePage;

}
