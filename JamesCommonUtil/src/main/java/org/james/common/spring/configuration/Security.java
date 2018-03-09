package org.james.common.spring.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {
	@Autowired private ComboPooledDataSource datasource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(datasource)
			.usersByUsernameQuery("select account username,password passwd,enabled enabled from service_account where account=?")
			.authoritiesByUsernameQuery("select acc.account username,auth.authority from service_account acc inner join service_account_authority auth on acc.id=auth.account_id where acc.account=?");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/common/**").permitAll()
		.antMatchers("/anonymous/**").anonymous()
		.antMatchers("/**").hasRole("ADMIN")
		.and()
		.formLogin().loginPage("/anonymous/loginPage.do").loginProcessingUrl("/anonymous/j_spring_security_check")
                .failureUrl("/anonymous/loginPage.do?error=true")
		.and().logout().logoutUrl("/anonymous/j_spring_security_logout").logoutSuccessUrl("/index.jsp");
        
    }

}
