<template>
  <view class="wrap">
    <u-toast ref="uToast"></u-toast>
    <view class="content">
      <view class="title">
        <text v-show="way">账号密码登录</text>
        <text v-show="!way">验证码登录/注册</text>
      </view>
      <view class="form">
        <u-form label-width="100">
          <u-form-item v-show="way" label="用户名">
            <u-input v-model="username" placeholder="请输入用户名"/>
          </u-form-item>
          <u-form-item v-show="!way" label="手机号">
            <u-input v-model="username" placeholder="请输入手机号"/>
          </u-form-item>
          <u-form-item v-show="way" label="密码">
            <u-input type="password" v-model="password" placeholder="请输入密码"/>
          </u-form-item>
          <u-form-item v-show="!way" label="验证码">
            <u-input type="number" v-model="refCode" placeholder="请输入验证码"/>
          </u-form-item>
        </u-form>
      </view>
      <view v-show="!way">
        <u-verification-code :seconds="seconds" ref="uCode"
                             @change="codeChange"></u-verification-code>
        <view class="right">
          <u-button size="medium" @click="getCode" :ripple="true" type="primary">{{ tips }}</u-button>
        </view>
      </view>
      <u-gap height="20"></u-gap>
      <view class="bottom">
        <u-button v-show="way" @click="loginByPassword" :ripple="true" type="warning">登录</u-button>
        <u-button v-show="!way" @click="loginByCode" :ripple="true" type="warning">登录/注册</u-button>
      </view>
      <view class="qiehuan">
        <u-image width="40rpx" height="40rpx" src="/static/qiehuan.png" @click="this.way=!this.way"></u-image>
        <text @click="this.way=!this.way">{{ way ? "切换验证码登录/注册" : "切换账号密码登录" }}</text>
      </view>
    </view>
  </view>
</template>

<script>
import UImage from "../../../uview-ui/components/u-image/u-image";
import UButton from "../../../uview-ui/components/u-button/u-button";
import UForm from "../../../uview-ui/components/u-form/u-form";
import UFormItem from "../../../uview-ui/components/u-form-item/u-form-item";
import UInput from "../../../uview-ui/components/u-input/u-input";
import UToast from "../../../uview-ui/components/u-toast/u-toast";
import UGap from "../../../uview-ui/components/u-gap/u-gap";
import UVerificationCode from "../../../uview-ui/components/u-verification-code/u-verification-code";

export default {
  components: {UVerificationCode, UGap, UToast, UInput, UFormItem, UForm, UButton, UImage},
  data() {
    return {
      // 登录方式
      way: true,
      username: '',
      password: '',
      tips: '',
      refCode: null,
      seconds: 60,
    }
  },
  methods: {
    onShow() {
      if (null != getApp().globalData.user) {
        this.username = getApp().globalData.user.username
        this.password = getApp().globalData.user.password
      }
    },
    codeChange(text) {
      this.tips = text;
    },
    getCode() {
      if (this.$refs.uCode.canGetCode) {
        // 模拟向后端请求验证码
        uni.showLoading({
          title: '正在获取验证码'
        })
        setTimeout(() => {
          uni.hideLoading();
          // 这里此提示会被this.start()方法中的提示覆盖
          this.$u.toast('验证码已发送');
          // 通知验证码组件内部开始倒计时
          this.$refs.uCode.start();
        }, 2000);
      } else {
        this.$u.toast('倒计时结束后再发送');
      }
    },
    loginByCode() {

    },
    loginByPassword() {
      uni.request({
        url: `${this.$baseUrl}/user/loginByPassword?username=` + this.username + `&password=` + this.password,
        method: 'GET',
        success: ({data}) => {
          console.log(data)
          if (data.code === 200) {
            getApp().globalData.token = data.data.token
            getApp().globalData.user = data.data.user
            getApp().globalData.loginStatus = true
            this.$refs.uToast.show({
              title: '登录成功!',
              type: 'success',
              back: true
            })
          } else {
            this.$refs.uToast.show({
              title: data.message,
              type: 'warning'
            })
          }
        },
        fail: (err) => {
          this.$refs.uToast.show({
            title: '登陆失败',
            type: 'warning'
          })
        }
      })
    }

  }
}
</script>

<style lang="scss" scoped>
.wrap {
  font-size: 20rpx;

  .content {
    width: 600rpx;
    margin: 0 auto;
    padding-top: 80rpx;

    .form {
      margin-bottom: 30rpx;
    }

    .title {
      text-align: left;
      font-size: 60rpx;
      font-weight: 500;
      margin-bottom: 100rpx;
    }

    input {
      text-align: left;
      margin-bottom: 10rpx;
      padding-bottom: 6rpx;
    }


    .right {
      position: absolute;
      right: 75rpx;
    }

    .bottom {
      margin-top: 80rpx;
    }

    .qiehuan {
      margin-top: 70rpx;
      font-size: 12px;
      display: flex;
      text-align: center;
      justify-content: center;
      align-items: center;
    }
  }
}
</style>

