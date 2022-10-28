package softtek.ecommerce.users_service.config;

/*import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import softtek.ecommerce.users_service.services.UserService;

import static org.springframework.security.config.Customizer.withDefaults;
*/
/*@Configuration
@EnableWebSecurity
@EnableMethodSecurity*/
public class SecurityConfig {

    /*private final UserService myUserDetailsService;

    public SecurityConfig(UserService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                //.csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**")).disable()
                .authorizeRequests(auth -> auth
                        .antMatchers("/h2-console/**").permitAll()
                        //.antMatchers("/admin/**").permitAll()//hasRole("Administrador")
                        .mvcMatchers("/users/**").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(myUserDetailsService)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

}