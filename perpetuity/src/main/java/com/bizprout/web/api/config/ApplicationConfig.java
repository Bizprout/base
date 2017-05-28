package com.bizprout.web.api.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.bizprout.web")
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class ApplicationConfig extends WebMvcConfigurerAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DataBaseProperties dataBaseProperties;

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("/html/Index.html");
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/img/**").addResourceLocations("/img/");
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setDriverClassName(dataBaseProperties.getDriverClass());
		datasource.setUrl(dataBaseProperties.getUrl());
		datasource.setUsername(dataBaseProperties.getUsername());
		datasource.setPassword(dataBaseProperties.getPassword());
		return datasource;
	}

	@Bean
	public SessionFactory sessionFactory() {
		try {
			LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
			sessionFactory.setDataSource(dataSource());
			sessionFactory
					.setPackagesToScan(new String[] { "com.bizprout.web.app" });
			sessionFactory.setHibernateProperties(hibernateProperties());

			sessionFactory.afterPropertiesSet();
			return sessionFactory.getObject();
		} catch (IOException e) {
			logger.error("Failed to configure sessionFactory");

		}
		return null;
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager getTransactionManager(
			SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	}

	@Bean
	public CommonsMultipartResolver commonsMultipartResolver() {
		final CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(-1);
		return commonsMultipartResolver;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put(AvailableSettings.SHOW_SQL,
				dataBaseProperties.isShowSql());
		properties.put(AvailableSettings.DIALECT,
				dataBaseProperties.getDialect());
		properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS,
				dataBaseProperties.getCurrentSessionContextClass());
		properties.put(AvailableSettings.HBM2DDL_AUTO,
				dataBaseProperties.getHbmAuto());
		return properties;
	}
}
