<template>
  <div class="login-container">
    <div class="login-left">
      <div class="bg-decoration">
        <div class="shape shape-1"></div>
        <div class="shape shape-2"></div>
      </div>
      
      <div class="brand-content">
        <div class="logo-area">
          <img src="@/assets/logo.png" alt="logo" class="brand-logo">
          <h1 class="brand-name">NOVATECH AI</h1>
        </div>
        <div class="slogan-area">
          <div class="main-slogan">构建万物互联的<br>智能新世界</div>
          <div class="sub-slogan">致力于通过前沿 AI 算法为行业赋能，<br>让智能触手可及。</div>
        </div>
      </div>
      
      <div class="copyright">Copyright © 2025 NovaTech AI Technology.</div>
    </div>

    <div class="login-right">
      <div class="form-content">
        <div class="welcome-header">
          <h2 class="welcome-title">欢迎回来</h2>
          <p class="welcome-desc">请使用您的账号登录系统</p>
        </div>

        <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              type="text"
              auto-complete="off"
              placeholder="请输入账号"
              class="custom-input"
            >
              <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
            </el-input>
          </el-form-item>
          
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              auto-complete="off"
              placeholder="请输入密码"
              class="custom-input"
              @keyup.enter.native="handleLogin"
            >
              <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
            </el-input>
          </el-form-item>
          
          <el-form-item prop="code" v-if="captchaEnabled">
            <div class="captcha-row">
              <el-input
                v-model="loginForm.code"
                auto-complete="off"
                placeholder="验证码"
                class="custom-input captcha-input"
                @keyup.enter.native="handleLogin"
              >
                <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
              </el-input>
              <div class="captcha-img-box">
                <img :src="codeUrl" @click="getCode" class="login-code-img" title="点击刷新验证码"/>
              </div>
            </div>
          </el-form-item>
          
          <div class="form-options">
            <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
            <a href="javascript:;" class="forget-pwd">忘记密码？</a>
          </div>
          
          <el-form-item style="width:100%;">
            <el-button
              :loading="loading"
              size="medium"
              type="primary"
              class="login-btn"
              @click.native.prevent="handleLogin"
            >
              <span v-if="!loading">立 即 登 录</span>
              <span v-else>登 录 中...</span>
            </el-button>
          </el-form-item>
          
          <div class="register-row" v-if="register">
            还没有账号？ <router-link to="/register" class="register-link">立即注册</router-link>
          </div>
        </el-form>
      </div>
      
      <div class="footer-beian">
        <span>{{ footerContent }}</span>
      </div>
    </div>
  </div>
</template>

<script>
// --- 以下 Script 代码完全保持原样，未做修改 ---
import { getCodeImg } from "@/api/login"
import Cookies from "js-cookie"
import { encrypt, decrypt } from '@/utils/jsencrypt'
import defaultSettings from '@/settings'

export default {
  name: "Login",
  data() {
    return {
      title: process.env.VUE_APP_TITLE,
      footerContent: defaultSettings.footerContent,
      codeUrl: "",
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    this.getCode()
    this.getCookie()
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img
          this.loginForm.uuid = res.uuid
        }
      })
    },
    getCookie() {
      const username = Cookies.get("username")
      const password = Cookies.get("password")
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 })
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 })
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 })
          } else {
            Cookies.remove("username")
            Cookies.remove("password")
            Cookies.remove('rememberMe')
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{})
          }).catch(() => {
            this.loading = false
            if (this.captchaEnabled) {
              this.getCode()
            }
          })
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
/* 全局容器：Flex布局，左右分栏 */
.login-container {
  display: flex;
  height: 100vh;
  width: 100vw;
  overflow: hidden;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
}

/* --- 左侧品牌区 --- */
.login-left {
  position: relative;
  width: 55%;
  background: linear-gradient(135deg, #020024 0%, #090979 35%, #00d4ff 100%); /* 科技深蓝渐变 */
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 0 100px;
  overflow: hidden;
  color: #fff;

  // 装饰光斑
  .bg-decoration {
    position: absolute;
    top: 0; left: 0; width: 100%; height: 100%;
    overflow: hidden;
    z-index: 0;
    
    .shape {
      position: absolute;
      border-radius: 50%;
      filter: blur(80px);
      opacity: 0.4;
    }
    .shape-1 {
      width: 500px; height: 500px;
      background: #409EFF;
      top: -100px; left: -100px;
      animation: float 10s infinite ease-in-out;
    }
    .shape-2 {
      width: 400px; height: 400px;
      background: #00d4ff;
      bottom: -50px; right: -50px;
      animation: float 12s infinite ease-in-out reverse;
    }
  }

  .brand-content {
    position: relative;
    z-index: 1;
    animation: slideUp 0.8s ease-out;
  }

  .logo-area {
    display: flex;
    align-items: center;
    margin-bottom: 40px;
    
    .brand-logo {
      height: 48px;
      margin-right: 16px;
    }
    .brand-name {
      font-size: 28px;
      font-weight: 700;
      letter-spacing: 1px;
      margin: 0;
    }
  }

  .slogan-area {
    .main-slogan {
      font-size: 56px;
      font-weight: 800;
      line-height: 1.2;
      margin-bottom: 24px;
      background: linear-gradient(to right, #fff, #b3e5fc);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
    }
    .sub-slogan {
      font-size: 18px;
      color: rgba(255, 255, 255, 0.8);
      line-height: 1.6;
    }
  }

  .copyright {
    position: absolute;
    bottom: 30px;
    left: 100px;
    font-size: 12px;
    color: rgba(255, 255, 255, 0.4);
    z-index: 1;
  }
}

/* --- 右侧表单区 --- */
.login-right {
  flex: 1;
  background: #ffffff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: relative;

  .form-content {
    width: 100%;
    max-width: 400px;
    padding: 20px;
    animation: fadeIn 1s ease-out;
  }

  .welcome-header {
    margin-bottom: 40px;
    text-align: left;
    
    .welcome-title {
      font-size: 32px;
      font-weight: 600;
      color: #303133;
      margin-bottom: 12px;
    }
    .welcome-desc {
      font-size: 14px;
      color: #909399;
    }
  }

  .login-form {
    .custom-input {
      ::v-deep input {
        height: 48px;
        line-height: 48px;
        background: #f5f7fa;
        border: 1px solid transparent;
        border-radius: 8px;
        padding-left: 45px;
        transition: all 0.3s;
        
        &:focus {
          background: #fff;
          border-color: #409EFF;
          box-shadow: 0 0 0 4px rgba(64, 158, 255, 0.1);
        }
      }
      .input-icon {
        height: 100%;
        width: 18px;
        margin-left: 10px;
        color: #909399;
      }
    }

    .captcha-row {
      display: flex;
      gap: 15px;
      align-items: center;
      
      .captcha-input {
        flex: 1;
      }
      .captcha-img-box {
        width: 130px;
        height: 48px;
        background: #fff;
        border-radius: 8px;
        overflow: hidden;
        border: 1px solid #dcdfe6;
        cursor: pointer;
        
        img {
          width: 100%;
          height: 100%;
          object-fit: cover;
        }
      }
    }

    .form-options {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 30px;
      margin-top: -5px;
      
      .forget-pwd {
        font-size: 14px;
        color: #409EFF;
        text-decoration: none;
      }
    }

    .login-btn {
      width: 100%;
      height: 50px;
      font-size: 16px;
      font-weight: 600;
      letter-spacing: 2px;
      border-radius: 8px;
      background: linear-gradient(90deg, #409EFF 0%, #3a8ee6 100%);
      border: none;
      box-shadow: 0 8px 20px rgba(64, 158, 255, 0.3);
      transition: transform 0.2s, box-shadow 0.2s;

      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 12px 24px rgba(64, 158, 255, 0.4);
      }
      &:active {
        transform: translateY(0);
      }
    }

    .register-row {
      margin-top: 24px;
      text-align: center;
      font-size: 14px;
      color: #606266;
      
      .register-link {
        color: #409EFF;
        font-weight: 600;
        margin-left: 5px;
        text-decoration: none;
        
        &:hover {
          text-decoration: underline;
        }
      }
    }
  }

  .footer-beian {
    position: absolute;
    bottom: 24px;
    font-size: 12px;
    color: #c0c4cc;
  }
}

/* 移动端适配 */
@media (max-width: 1000px) {
  .login-left {
    display: none;
  }
  .login-right {
    background: url("../assets/images/login-background.jpg") center center no-repeat;
    background-size: cover;
    
    // 增加遮罩层确保文字清晰
    &::before {
      content: '';
      position: absolute;
      top: 0; left: 0; width: 100%; height: 100%;
      background: rgba(255, 255, 255, 0.9);
      backdrop-filter: blur(10px);
    }
    
    .form-content, .footer-beian {
      position: relative;
      z-index: 1;
    }
  }
}

/* 动画定义 */
@keyframes float {
  0%, 100% { transform: translate(0, 0); }
  50% { transform: translate(20px, 30px); }
}

@keyframes slideUp {
  from { opacity: 0; transform: translateY(30px); }
  to { opacity: 1; transform: translateY(0); }
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}
</style>