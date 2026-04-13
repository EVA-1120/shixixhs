import axios from 'axios'
import store from '../store'
import { Message } from 'element-ui'

const service = axios.create({
    baseURL: 'http://localhost:9000/api', // 后端接口前缀
    timeout: 5000
})

// 请求拦截器
service.interceptors.request.use(
    config => {
        const token = store.state.token
        if (token) {
            config.headers['Authorization'] = 'Bearer ' + token
        }
        return config
    },
    error => {
        return Promise.reject(error)
    }
)

// 响应拦截器
service.interceptors.response.use(
    response => response.data,
    error => {
        Message.error(error.response.data.message || '请求出错')
        return Promise.reject(error)
    }
)

export default service