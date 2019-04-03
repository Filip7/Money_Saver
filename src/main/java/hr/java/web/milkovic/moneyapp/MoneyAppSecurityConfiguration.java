package hr.java.web.milkovic.moneyapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class MoneyAppSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .inMemoryAuthentication()
                .withUser("king")
                .password(passwordEncoder().encode("kingpass"))
                .roles("ADMIN", "USER")
                .and()
                .withUser("user")
                .password(passwordEncoder().encode("userpass"))
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                    .antMatchers("/res")
                        .permitAll()
                    .antMatchers("/css")
                        .permitAll()
                    .antMatchers("/js")
                        .permitAll()
                    .antMatchers("/login")
                        .permitAll()
                    .antMatchers("/")
                        .authenticated()
                    .antMatchers("/expenses/**")
                        .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                     .permitAll()
                .defaultSuccessUrl("/", true)
                .and()
                .logout()
                    .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
