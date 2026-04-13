// src/utils/request.js
import axios from 'axios';

// 1. 创建 axios 实例
const request = axios.create({
    // 如果你的 Vue 项目配置了跨域代理，这里填 '/api'
    // 如果没配跨域，直接填你后端的完整地址，例如 'http://localhost:9000/api'
    baseURL: '/api',
    timeout: 5000 // 请求超时时间
});

// 2. 请求拦截器：每次发请求前，自动把 Token 塞进请求头
request.interceptors.request.use(
    config => {
        // 假设你在登录成功后，把 token 存在了 localStorage 的 'token' 字段里
        // 根据你实际存 token 的逻辑调整这里
        const token = localStorage.getItem('token');
        if (token) {
            // 加上 Bearer 前缀，对应后端的解析逻辑
            config.headers['Authorization'] = 'Bearer ' + token;
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

// 3. 响应拦截器：统一处理后端的报错
request.interceptors.response.use(
    response => {
        return response.data; // 直接返回核心数据
    },
    error => {
        return Promise.reject(error);
    }
);

export default request;