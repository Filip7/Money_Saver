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
                .passwordEncoder(new BCryptPasswordEncoder(10));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/expenses/reset-wallet", "/h2-console/**", "/swagger-ui.html/**")
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
                .ignoringAntMatchers("/h2-console/**", "/api/**", "/login");
        http
                .headers()
                .frameOptions()
                .disable();
    }

}
