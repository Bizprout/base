package com.bizprout.web.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@EnableWebSecurity
@Order(2147483640)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsServiceImpl")
	private UserDetailsService userDetailsServiceImpl;
	@Autowired
	private RestAuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private RestAuthenticationFailureHandler authenticationFailureHandler;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/html/Index.html", "/html/Login.html",
				"/login/**", "/css/**", "/js/**", "/img/**", "/docsupport/**",
				"/");
	}

	public AppSecurityConfig() {
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	// @Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(20);
	}

	// TODO set above to auth
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsServiceImpl);
		return authProvider;
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver resolver=new CommonsMultipartResolver();
	    resolver.setDefaultEncoding("utf-8");
	    return resolver;
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/html/Index.html", "/html/Login.html",
						"/login/**", "/css/**", "/js/**", "/img/**",
						"/docsupport/**", "/error.html",
						"/", "/company/**", "/ppmaster/**", "/pptallymapping/**")
				.permitAll()
				.and()
				.formLogin()
				.loginProcessingUrl("/authenticate")
				.successForwardUrl("/security/account")
				.failureHandler(authenticationFailureHandler)
				.usernameParameter("username")
				.passwordParameter("password")
				.permitAll()
				.and()
				.authorizeRequests()
				.antMatchers()
				.permitAll()
				.antMatchers("/**")
				.hasAnyRole("PPAdmin","PPsuperadmin")
				.anyRequest()
				.authenticated()
				.and()
				.csrf().disable()
				.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)							
				.logout()
				.and()
				.exceptionHandling()
				.authenticationEntryPoint(authenticationFailureHandler)
				.accessDeniedHandler(authenticationFailureHandler)
				.and()
				.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/")
				.logoutSuccessHandler(
						new HttpStatusReturningLogoutSuccessHandler())
				.deleteCookies("JSESSIONID", "XSRF-TOKEN", "X-XSRF-TOKEN")
				.invalidateHttpSession(true).permitAll()
				.clearAuthentication(true);

	}

	@SuppressWarnings("unused")
	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

}
