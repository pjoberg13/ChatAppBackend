package peter.finalprojectparallel.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class will contain all of our security configurations.
 * Extending WebSecurityConfigurerAdapter allows us to bring in all the methods afforded to us by Spring Security
 */
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * loads user data from given source, in our case DB, and provides that data to Spring.
     * because it is an interface we have to create an implementation class for the interface
     */
    private final UserDetailsService userDetailsService;

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
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
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

    /**
     * Because AuthenticationManager is an interface we have to specify which type of Bean we want to instantiate because
     * there are multiple possible implementations of authentication manager.
     * @return
     * @throws Exception
     */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
