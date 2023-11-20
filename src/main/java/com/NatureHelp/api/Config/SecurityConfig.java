package com.NatureHelp.api.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	 @Bean
	    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .authorizeRequests(authorizeRequests ->
	                authorizeRequests
						.antMatchers("/**").permitAll()
						.antMatchers("/api/**").permitAll()
						.antMatchers("/api/auth/register").permitAll()
						.antMatchers("/api/auth/login").permitAll()
						.antMatchers("/api/auth/logout").permitAll()
						.antMatchers("/api/auth/refresh").permitAll()
						.antMatchers("/api/appointments/create").permitAll()
	                    .anyRequest().permitAll()
	            )
				.csrf().disable()
	            .formLogin().disable();

	        return http.build();
	    }
	

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

}

