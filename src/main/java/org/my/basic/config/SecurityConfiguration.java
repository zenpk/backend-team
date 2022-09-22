package org.my.basic.config;

import org.my.basic.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final UserService userService;

    public SecurityConfiguration(UserService userService) {
        this.userService = userService;
    }

    // 提供 BCrypt 加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 写入内存的账户
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails user = User.builder().username("user").password(passwordEncoder().encode("user")).roles("USER").build();
        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    // 设置验证机制
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        //authProvider.setUserDetailsService(inMemoryUserDetailsManager()); // 使用写入内存的账户
        authProvider.setUserDetailsService(userService); // 使用和数据库连接到用户服务
        authProvider.setPasswordEncoder(passwordEncoder()); // 使用 BCrypt
        return authProvider;
    }


    // 配置 CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // 定义 CORS 规则
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(java.util.List.of("http://localhost:5173", "http://101.43.179.27:5173")); // 允许来自前端 Vue 的请求
        configuration.setAllowedMethods(java.util.List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        // 所有 URL 应用此规则
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // 设置 Spring Security 的 filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
        // 设置访问权限
        http.cors().and() // 允许 cross-origin
                .csrf().disable() // 禁用此项使 POST 请求不被阻拦？
                .authorizeRequests().antMatchers("/", "/register**").permitAll() // 任何用户都能访问这些地址
                .antMatchers("/user").hasRole("USER") // 仅 USER 可访问
                .antMatchers("/admin").hasRole("ADMIN") // 仅 ADMIN 可访问
                .anyRequest().authenticated() // 访问其他地址均需要登录认证
                .and().formLogin().loginPage("/login").permitAll() // 不显示 Spring Security 自带的登录界面
                .loginProcessingUrl("/login") // 前端传入登录数据的地址
                .defaultSuccessUrl("/") // 登录成功后的默认跳转地址
                .failureHandler(new AuthenticationFailureHandler() { // 登录失败时
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        // 自定义逻辑
                        response.setStatus(401); // Unauthorized
                    }
                })
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID");
        return http.build();
    }
}