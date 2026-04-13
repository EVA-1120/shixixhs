package org.example.hd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许跨域访问的路径：对所有接口生效
                .allowedOriginPatterns("*") // 允许所有来源（开发环境使用 *，生产环境建议填具体域名）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的请求方法
                .allowCredentials(true) // 允许携带 Cookie 或 Authorization 头
                .maxAge(3600); // 预检请求 (OPTIONS) 的缓存时间，单位为秒
    }
}