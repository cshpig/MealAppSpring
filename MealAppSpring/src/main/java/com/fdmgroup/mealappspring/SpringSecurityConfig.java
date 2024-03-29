package com.fdmgroup.mealappspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fdmgroup.mealappspring.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Autowired
	private MyUserDetailsService userDetailsService;

	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests((authz) -> {
			try {
				authz.antMatchers("/", "/login", "/register",  "/styles/**").permitAll().anyRequest().authenticated().and().formLogin()
						.loginPage("/login").permitAll().defaultSuccessUrl("/feed").and().logout()
						.invalidateHttpSession(true).clearAuthentication(true)
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
						.permitAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).httpBasic();

		http.headers().frameOptions().disable();
		return http.build();
	}

}
