package ru.sadykov.katacourse.PP3_1_2_Security.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.sadykov.katacourse.PP3_1_2_Security.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final SuccessUserHandler successUserHandler;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, CustomUserDetailsService customUserDetailsService) {
        this.successUserHandler = successUserHandler;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    //настраивает фильтры безопасности HTTP-запросов: авторизация запросов к URL, аутентификация через форму логина, выход
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/login", "/error", "/registration").permitAll()  // Разрешить доступ
                        .anyRequest().hasAnyRole("USER","ADMIN")
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .loginProcessingUrl("/process_login")
                                .successHandler(successUserHandler)  // Указать обработчик успешной аутентификации
                                .failureUrl("/login?error=true") // Обработка ошибки.
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                ).userDetailsService(customUserDetailsService);
        return http.build();
    }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
  }
}