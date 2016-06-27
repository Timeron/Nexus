package com.timeron.nexus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(
				passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

//		http.authorizeRequests().antMatchers("/jtask/**")
//				.access("hasRole('ROLE_ADMIN')")
//				.and()
//				.formLogin()
//				.failureUrl("/login?error")
//				// .loginPage("/login")
//				.usernameParameter("username").passwordParameter("password")
//				.and().logout().logoutSuccessUrl("/login?logout").and()
//				.exceptionHandling().accessDeniedPage("/403").and().csrf()
//				.disable();

		http.authorizeRequests().antMatchers("/wallet/**")
				.access("hasRole('ROLE_WALLET')").and()
				.formLogin()
				.failureUrl("/login?error")
				// .loginPage("/login")
				.usernameParameter("username").passwordParameter("password")
				.and().logout().logoutSuccessUrl("/login?logout")
				// and().csrf().disable()
				.and().exceptionHandling().accessDeniedPage("/403").and()
				.csrf().disable();

		http.authorizeRequests().antMatchers("/jtask/**")
				.access("hasRole('ROLE_JTASK')")
				.and()
				.formLogin()
				.failureUrl("/login?error")
				// .loginPage("/login")
				.usernameParameter("username").passwordParameter("password")
				.and().logout().logoutSuccessUrl("/login?logout").and()
				.exceptionHandling().accessDeniedPage("/403").and().csrf()
				.disable();

		http.authorizeRequests().antMatchers("/adminPanel/**")
				.access("hasRole('ROLE_ADMIN')").and()
				.formLogin()
				.failureUrl("/login?error")
				// .loginPage("/login")
				.usernameParameter("username").passwordParameter("password")
				.and().logout().logoutSuccessUrl("/login?logout")
				// and().csrf().disable()
				.and().exceptionHandling().accessDeniedPage("/403").and()
				.csrf().disable();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}
