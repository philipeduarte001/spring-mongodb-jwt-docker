package com.covid.secure;

import com.covid.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	JwtTokenProvider jwt;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    UserDetailsService userDetailsService = mongoUserDetails();
	    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.httpBasic().disable().csrf().disable().sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
	            .antMatchers("/auth/login").permitAll().antMatchers("/auth/register").permitAll()
	            .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
	            .antMatchers("/api/books/**").hasAuthority("ADMIN").anyRequest().authenticated().and().csrf()
	            .disable().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint()).and()
	            .apply(new JwtConfigurer(jwt));
	}
	
	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationEntryPoint unauthorizedEntryPoint() {
	    return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}

	@Bean
	public UserDetailsService mongoUserDetails() {
	    return new CustomUserDetailsService();
	}
}
