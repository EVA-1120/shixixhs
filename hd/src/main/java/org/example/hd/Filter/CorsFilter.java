package org.example.hd.Filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebFilter(filterName = "CorsFilter")
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        // 允许所有源（生产环境建议指定具体前端域名，如 "http://your-front.com" ）
        ((HttpServletResponse) res).setHeader("Access-Control-Allow-Origin", "*");
        // 允许携带凭证（如 Cookie ，若前端开启 withCredentials 需对应设置）
        ((HttpServletResponse) res).setHeader("Access-Control-Allow-Credentials", "true");
        // 允许的请求方法
        ((HttpServletResponse) res).setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // 允许的请求头（可根据前端实际传的头补充，如自定义 Token 头）
        ((HttpServletResponse) res).setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        chain.doFilter(req, res);
    }
}
