package hr.java.web.milkovic.moneyapp;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class MoneyAppSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;

    public MoneyAppSecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from USERS where username=?")
                .authoritiesByUsernameQuery("select username, authority from AUTHORITIES where username=?")
                .passwordEncoder(new BCryptPasswordEncoder(10));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/login")
                .permitAll()
                .antMatchers("/expenses/reset-wallet")
                .access("hasRole('ROLE_ADMIN')")
                .antMatchers("/h2-console/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest()
                .authenticated()
                .antMatchers("/**")
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/", true)
                .and()
                .logout()
                .permitAll();

        http
                .csrf()
                .disable();
        http
                .headers()
                .frameOptions()
                .disable();
    }

}
