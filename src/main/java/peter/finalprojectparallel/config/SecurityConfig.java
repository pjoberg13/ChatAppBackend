package peter.finalprojectparallel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class will contain all of our security configurations.
 * Extending WebSecurityConfigurerAdapter allows us to bring in all the methods afforded to us by Spring Security
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * we disable csrf because this type of attack mainly occurs in instances with session authentication
     * since this is a RESTful API and we are using JSON web tokens we can safely disable csrf
     * @param httpSecurity
     */
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                //tells spring security to only configure HttpSecurity if the path matches the pattern
                //specified by the antMatchers method
        .authorizeRequests()
        .antMatchers("/api/auth/**")
        .permitAll()
        .anyRequest()
        .authenticated();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) {

    }

    /**
     * Because PasswordEncoder is an interface and not a class a Bean must be created manually inside the class you
     * want to instantiate it. Wherever we Autowire this class we will get an instance of BCryptPasswordEncoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        //we use BCrypt instead of SHA-256 or other similar hashing algorithms because it is harder for hackers to decode
        //the hash through brute force. Specifically, SHA-256 can more efficiently be brute forced with a GPU than
        //BCrypt
        return new BCryptPasswordEncoder();
    }
}
