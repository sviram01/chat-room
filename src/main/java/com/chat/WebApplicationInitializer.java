package com.chat;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * @author Shreya Viramgama
 *
 */
public class WebApplicationInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		application.sources(Application.class);
		return super.configure(application);
	}
	
}
