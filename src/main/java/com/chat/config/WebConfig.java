package com.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Shreya Viramgama
 *
 */
@Configuration
@EnableAspectJAutoProxy
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}

	@Bean
	public WebSecurityConfig applicationSecurity() {
		return new WebSecurityConfig();
	}

	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().authenticated();

			http.csrf().disable().formLogin().failureUrl("/login?error").defaultSuccessUrl("/stb-jquery.html")
					.loginPage("/login").permitAll().and().logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").permitAll();
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService);
		}

	}
}
