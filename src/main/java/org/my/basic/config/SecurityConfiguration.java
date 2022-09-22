package org.my.basic.config;

import org.my.basic.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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

    // 设置验证机制
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService); // 使用自定义的用户服务
        authProvider.setPasswordEncoder(passwordEncoder()); // 使用 BCrypt
        return authProvider;
    }

    // 写入内存的账户
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    // 设置 Spring Security 的 filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
        // 设置访问权限
        http
                .csrf().disable() // 禁用此项使 POST 请求不被阻拦
                .authorizeRequests()
                .antMatchers("/", "/register**").permitAll() // 任何用户都能访问这些地址
                .antMatchers("/user").hasRole("USER") // 仅 USER 可访问
                .antMatchers("/admin").hasRole("ADMIN") // 仅 ADMIN 可访问
                .anyRequest().authenticated() // 访问其他地址均需要登录认证
                .and()
                .formLogin() // 展示登录界面
                .loginProcessingUrl("/login") // 前端传入登录数据的地址
                .defaultSuccessUrl("/", true) // 登录后跳转地址
                .failureUrl("/login?error=true") // 登录失败地址
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID");
        return http.build();
    }
}