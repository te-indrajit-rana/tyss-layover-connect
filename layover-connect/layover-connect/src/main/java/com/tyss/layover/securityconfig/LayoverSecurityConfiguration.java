package com.tyss.layover.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class LayoverSecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthorizationFilter authorizationFilter)
			throws Exception {
		http.csrf().disable();
		http.cors().disable();
//		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//		http.cors().configurationSource(request -> {
//			final CorsConfiguration cors = new CorsConfiguration();
//			cors.setAllowCredentials(true);
//			cors.setAllowedOrigins(Arrays.asList("*"));
//			cors.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS"));
//			cors.setAllowedHeaders(Arrays.asList(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION));
//			cors.setMaxAge(3600L);
//			return cors;
//		});
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
