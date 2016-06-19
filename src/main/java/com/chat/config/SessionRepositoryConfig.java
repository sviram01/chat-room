package com.chat.config;

import java.util.Arrays;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.SessionRepositoryFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * @author Shreya Viramgama
 *
 */
@Configuration
public class SessionRepositoryConfig {

	@SuppressWarnings("rawtypes")
	@Bean
	public SessionRepository inMemorySessionRepository() {
		return new MapSessionRepository();
	}

	@SuppressWarnings("unchecked")
	@Bean
	public FilterRegistrationBean sessionRepositoryFilterRegistration() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new DelegatingFilterProxy(new SessionRepositoryFilter<>(inMemorySessionRepository())));
		filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
		return filterRegistrationBean;
	}
}
