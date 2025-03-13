package com.publi.gestionpub.security;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.publi.gestionpub.config.JwtAuthenticationEntryPoint;
import com.publi.gestionpub.config.JwtRequestFilter;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(this.passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
	
		httpSecurity.csrf().disable()
		
		.authorizeRequests().antMatchers("/authenticate/**","/v3/api-docs/**","/api/**","/chercheuradd/**","/swagger-ui/*","/users/add/**","/type/all/**","/api/publications/download/**","/validevisiteur/all/**","/file/{idPublication}/**","/departements/**","/publications/comments/{publicationId}/**").permitAll();
		httpSecurity.authorizeRequests().antMatchers("/file/***").permitAll()
		.antMatchers("/menuadmin.html").hasRole("ADMIN")
        .antMatchers("/menuChercheur.html").hasRole("CHERCHEUR")
        .antMatchers("/menuReviseur.html").hasRole("REVISEUR")
		.anyRequest().authenticated()
		.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.cors();
		//Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		
	}
	
   
}
