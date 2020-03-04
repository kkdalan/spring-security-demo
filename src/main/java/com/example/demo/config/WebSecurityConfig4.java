package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.example.demo.config.auth.MyInvalidSessionStrategy;

//@EnableWebSecurity
public class WebSecurityConfig4 extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.userDetailsService(userDetailsService)
			.authorizeRequests()
				.antMatchers("/admin/api/**").hasAuthority("ROLE_ADMIN")
				.antMatchers("/user/api/**").hasRole("USER")
				.antMatchers("/app/api/**").permitAll()
				.anyRequest().authenticated()
			.and().csrf().disable()
				.formLogin().permitAll()
			.and()
				.rememberMe()
				.userDetailsService(userDetailsService)
//				.key("brbear");
				.tokenRepository(jdbcTokenRepository())
			.and()
				.sessionManagement()
//				.sessionFixation().newSession()
//				.invalidSessionUrl("/session/invalid");
				.invalidSessionStrategy(new MyInvalidSessionStrategy())
				.maximumSessions(1)
				.maxSessionsPreventsLogin(true);
		
	}

	private JdbcTokenRepositoryImpl jdbcTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		return jdbcTokenRepository;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user").password("123").roles("user")
			.and()
			.withUser("admin").password("123").roles("admin");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return jdbcUserDetailsManager();
//		return inMemoryUserDetailsManager();
	}

	private UserDetailsService jdbcUserDetailsManager() {
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
		manager.setDataSource(dataSource);
		if(!manager.userExists("user")) {
			manager.createUser(User.withUsername("user").password("123").roles("USER").build());
		}
		if(!manager.userExists("admin")) {
			manager.createUser(User.withUsername("admin").password("123").roles("USER", "ADMIN").build());
		}
		return manager;
	}
}
