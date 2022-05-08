package io.nadonhwi.boot.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/", "/css/**", "/h2-console/**", "/account/register").permitAll()
				.anyRequest().authenticated()
				.and()
			.csrf()
               	.ignoringAntMatchers("/h2-console/**")
               	.and()
            .headers()
                .addHeaderWriter(
                    new XFrameOptionsHeaderWriter(
                        new WhiteListedAllowFromStrategy(Arrays.asList("localhost"))
                    )
                )
                .frameOptions().sameOrigin()
            .and()
			.formLogin()
				.loginPage("/account/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}
	
	// https://www.baeldung.com/spring-security-jdbc-authentication
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) 
	  throws Exception {
	    auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .passwordEncoder(passwordEncoder()) // 비밀번호 암호화
	      .usersByUsernameQuery("select username, password, enabled " // 인증(로그인) 처리
	        + "from user "
	        + "where username = ?")
	      .authoritiesByUsernameQuery("select u.username, r.name " // 권한 처리 
	        + "from user_role ur inner join user u on ur.user_id = u.id "
	        + "inner join role r on ur.role_id = r.id "
	        + "where u.username = ?");
	}
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}