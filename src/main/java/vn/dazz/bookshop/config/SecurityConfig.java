package vn.dazz.bookshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Bước 1: cấu hình security FilterChain
    //Bước 2: Cấu hình UserDetailService
    //Bước 3: Cấu hình PasswordEncoder

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((requets) -> requets
                        .requestMatchers("/order/**", "/cart/**", "/wishlist/**", "/user/**", "/review/**", "/contact").authenticated()
                        .requestMatchers("/adminPage/**").hasRole("ADMIN")
                        .anyRequest().permitAll())
                .formLogin((form) -> form.loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/doLogin")
                        .defaultSuccessUrl("/home")
                        .failureUrl("/login?error=true")
                        .permitAll())
                        .logout(logout -> logout
                                .logoutSuccessUrl("/login")
                                .permitAll());
        return http.build();
    }
}
