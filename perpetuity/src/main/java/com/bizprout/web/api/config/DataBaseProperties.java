package com.bizprout.web.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataBaseProperties {

	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.driver_class}")
	private String driverClass;

	@Value("${spring.hibernate.show_sql}")
	private String showSql;
	@Value("${spring.hibernate.dialect}")
	private String dialect;
	@Value("${spring.hibernate.current_session_context_class}")
	private String currentSessionContextClass;
	@Value("${spring.hibernate.hbm2ddl.auto}")
	private String hbmAuto;
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String isShowSql() {
		return showSql;
	}

	public void setShowSql(String showSql) {
		this.showSql = showSql;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getCurrentSessionContextClass() {
		return currentSessionContextClass;
	}

	public void setCurrentSessionContextClass(String currentSessionContextClass) {
		this.currentSessionContextClass = currentSessionContextClass;
	}

	public String getHbmAuto() {
		return hbmAuto;
	}

	public void setHbmAuto(String hbmAuto) {
		this.hbmAuto = hbmAuto;
	}

}
