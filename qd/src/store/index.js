import Vue from 'vue'
import Vuex from 'vuex'
import api from '../api/request'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        user: null,
        token: ''
    },
    mutations: {
        setUser(state, user) {
            state.user = user
            if (user) localStorage.setItem('user',JSON.stringify(user))
            else localStorage.removeItem('token')
        },
        setToken(state, token) {
            state.token = token
            if (token) localStorage.setItem('token', token)
            else localStorage.removeItem('token')
        },
        logout(state) {
            state.user = null
            state.token = ''
            localStorage.removeItem('user')
            localStorage.removeItem('token')
        }
    },
    actions: {
        async fetchUser({ commit, state }) {
            if (!state.token) return
            try {
                const user = await api.get('/user/me')
                commit('setUser', user)
            } catch (e) {
                commit('logout')
            }
        }
    },
    modules: {}
})