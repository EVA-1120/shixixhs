<template>
  <div id="app">
    <el-header style="background:#fff;box-shadow:0 2px 8px rgba(0,0,0,0.04);">
      <div class="navbar">
        <span class="logo" @click="$router.push('/')">小红书仿真</span>
        <div class="nav-right">
          <template v-if="user">
            <span>你好，{{ user.nickname }}</span>
            <el-link @click="logout" type="primary">退出</el-link>
            <el-link @click="$router.push('/profile')" type="primary">个人中心</el-link>
            <el-link @click="$router.push('/myposts')" type="primary">我的内容</el-link>
          </template>
          <template v-else>
            <el-link @click="$router.push('/login')" type="primary">登录</el-link>
            <el-link @click="$router.push('/register')" type="primary">注册</el-link>
          </template>
        </div>
      </div>
    </el-header>
    <el-main>
      <router-view />
    </el-main>
  </div>
</template>

<script>
export default {
  computed: {
    user() {
      return this.$store.state.user
    }
  },
  methods: {
    logout() {
      this.$store.commit('logout')
      this.$router.push('/login')
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  color: #333;
  min-height: 100vh;
}
.navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  max-width: 900px;
  margin: 0 auto;
  height: 60px;
}
.logo {
  font-weight: bold;
  font-size: 22px;
  color: #ec4141;
  cursor: pointer;
}
.nav-right {
  display: flex;
  gap: 16px;
  align-items: center;
}
.el-main {
  background: #fafbfc;
  min-height: calc(100vh - 60px);
  padding: 24px 0;
}
</style>