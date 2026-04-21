package pants.pro.analyst_registry.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration              // create a bean from a method
@RequiredArgsConstructor
@EnableMethodSecurity       // to enable @PreAuthorize annotation
@EnableWebSecurity          // filter security
public class SecurityConfig {

    private final AuthenticationSuccessHandler authSuccessHandler;
    private final AuthenticationFailureHandler authFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                //.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/index.html").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/users/register", "/users/success").permitAll()
                        .requestMatchers("/analysts/insert").hasAuthority("INSERT_ANALYST")
                        .requestMatchers(HttpMethod.GET, "/analysts/edit/{uuid}").hasAuthority("EDIT_ANALYST")
                        .requestMatchers(HttpMethod.POST, "/analysts/edit").hasAuthority("EDIT_ANALYST")
                        .requestMatchers(HttpMethod.GET, "/analysts/update-success").hasAuthority("EDIT_ANALYST")
                        .requestMatchers(HttpMethod.POST, "/analysts/delete/{uuid}").hasAuthority("DELETE_ANALYST")
                        .requestMatchers(HttpMethod.GET, "/analysts/delete-success").hasAuthority("DELETE_ANALYST")
                        .requestMatchers("/analysts/**").hasAnyRole("ADMIN", "EMPLOYEE")
                        .requestMatchers("/users/**").hasRole("ADMIN")
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .successHandler(authSuccessHandler)
                        .failureHandler(authFailureHandler)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );
                return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
