// src/api/index.js
import axios from 'axios';
import router from '@/router';
import { Message } from 'element-ui';

const API = axios.create({
    baseURL: process.env.NODE_ENV === 'production'
        ? 'https://your-production-domain.com/api'
        : 'http://localhost:8080/api',
    timeout: 15000, // 增加超时时间
    headers: {
        'Content-Type': 'application/json',
        'X-Requested-With': 'XMLHttpRequest'
    }
});

// 请求拦截器
API.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, error => {
    return Promise.reject(error);
});

// 响应拦截器
API.interceptors.response.use(response => {
    return response.data;
}, error => {
    console.error('API Error:', error);

    let errorMessage = '请求处理失败';
    let serverError = '';

    if (error.response) {
        const { status, data } = error.response;

        // 记录服务器返回的错误详情
        if (data && data.message) {
            serverError = `服务器错误: ${data.message}`;
            console.error(serverError);
        }

        // 处理401未授权错误
        if (status === 401) {
            errorMessage = '登录已过期，请重新登录';
            localStorage.removeItem('token');
            localStorage.removeItem('userInfo');

            if (router.currentRoute.path !== '/login') {
                const redirect = encodeURIComponent(router.currentRoute.fullPath);
                setTimeout(() => {
                    router.replace(`/login?redirect=${redirect}`);
                }, 1500);
            }
        }
        // 处理500服务器错误
        else if (status >= 500) {
            errorMessage = '服务器内部错误，请稍后再试';
            console.error('服务器错误详情:', data);

            // 添加重试机制 (仅限GET请求)
            if (error.config.method === 'get') {
                console.log('将重试请求:', error.config.url);
                return API.request(error.config);
            }
        }
        // 其他状态码处理
        else {
            errorMessage = data?.message || `请求错误 [${status}]`;
        }
    }
    // 处理网络错误
    else if (error.request) {
        errorMessage = '网络连接异常，请检查网络';
    }
    // 处理超时错误
    else if (error.code === 'ECONNABORTED') {
        errorMessage = '请求超时，请重试';
    }

    // 显示友好的错误提示
    if (error.config.showError !== false) {
        Message.error({
            message: errorMessage,
            duration: 5000
        });
    }

    // 返回统一的错误格式
    return Promise.reject({
        message: errorMessage,
        serverError: serverError,
        code: error.response?.status || 0,
        raw: error
    });
});

export default API;