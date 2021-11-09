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
          <u-form-item label="用户名">
            <u-input v-model="username" placeholder="请输入用户名"/>
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
        <u-button @click="submit" :ripple="true" type="warning">{{ way ? "登录" : "登录/注册" }}</u-button>
      </view>
      <view class="qiehuan">
        <u-image width="40rpx" height="40rpx" src="/static/qiehuan.png" @click="this.way=!this.way"></u-image>
        <text v-show="way" @click="this.way=!this.way">切换验证码登录/注册</text>
        <text v-show="!way" @click="this.way=!this.way">切换账号密码登录</text>
      </view>
    </view>
  </view>
</template>

<script>
import UImage from "../../../uview-ui/components/u-image/u-image";
import UButton from "../../../uview-ui/components/u-button/u-button";
import UForm from "../../../uview-ui/components/u-form/u-form";

export default {
  components: {UForm, UButton, UImage},
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
    submit() {
      this.$post({
        url: '/security/session',
        data: {
          username: this.userName,
          password: this.password,
        },
      }).then(res => {
        if (res.data.code === 0) {
          uni.setStorage({
            key: 'fx-auth-token',
            data: res.data.ok.token,
          });
          this.$u.route({
            type: 'switchTab',
            url: '/pages/function/function'
          })
        } else {
          uni.showToast({
            title: '用户名或密码错误！',
            icon: 'none',
          })
        }
        uni.setStorage({
          key: 'user',
          data: JSON.stringify(res.data.ok.data),
        });
      }).catch(err => {
        uni.showToast({
          title: '用户名或密码错误异常！',
          icon: 'none',
        })
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

    .tips {
      color: $u-type-info;
      font-size: 20rpx;
      margin-bottom: 60rpx;
      margin-top: 30rpx;
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

  .bottom {
    .loginType {
      display: flex;
      padding: 260rpx 150rpx 150rpx 150rpx;
      justify-content: space-between;

      .item {
        display: flex;
        flex-direction: column;
        align-items: center;
        color: $u-content-color;
        font-size: 28rpx;
      }
    }

    .hint {
      padding: 20rpx 40rpx;
      font-size: 20rpx;
      color: $u-tips-color;

      .link {
        color: $u-type-warning;
      }
    }
  }
}
</style>

