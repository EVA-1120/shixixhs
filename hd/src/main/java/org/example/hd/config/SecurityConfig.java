package org.example.hd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // ⭐ 第一处修改：关键！通知 Security 开启跨域支持
                .csrf(csrf -> csrf.disable()) // 关闭CSRF
                .authorizeHttpRequests(auth -> auth
                        // 预检请求 OPTIONS 必须无条件放行
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll() // ⭐ 第二处修改：放行OPTIONS请求
                        .requestMatchers("/api/user/login", "/api/user/register", "/api/user").permitAll()
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}