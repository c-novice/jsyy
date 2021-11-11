<template>
  <view>
    <u-toast ref="uToast"></u-toast>
    <view>
      <u-cell-group>
        <u-cell-item icon="pushpin-fill" title="校园账号绑定"
                     @click="switchTo('blind/index')"></u-cell-item>
        <u-cell-item icon="lock-fill" title="修改密码"
                     @click="switchTo('changepwd/index')"></u-cell-item>
        <u-cell-item icon="account-fill" title="关于我们"
                     @click="switchTo('aboutwe/index')"></u-cell-item>
      </u-cell-group>
    </view>
    <view class="main">
      <u-button @click="exit">退出登录</u-button>
    </view>
  </view>
</template>

<script>
import UCellGroup from "../../../uview-ui/components/u-cell-group/u-cell-group";
import UCellItem from "../../../uview-ui/components/u-cell-item/u-cell-item";
import UButton from "../../../uview-ui/components/u-button/u-button";

export default {
  components: {UButton, UCellItem, UCellGroup},
  data() {
    return {
      loginStatus: false,
      token: null
    }
  },
  methods: {
    onShow() {
      this.token = getApp().globalData.token
      this.loginStatus = getApp().globalData.loginStatus
    },
    // 页面跳转
    switchTo(url) {
      if (false === this.loginStatus) {
        this.$refs.uToast.show({
          title: '请先登录！',
          type: 'warning'
        })
      } else {
        uni.navigateTo({
          url: url
        })
      }
    },
    exit() {
      if (false === this.loginStatus) {
        this.$refs.uToast.show({
          title: '请先登录！',
          type: 'warning'
        })
      } else {
        getApp().globalData.token = null
        getApp().globalData.loginStatus = false
        getApp().globalData.user = null
        this.$refs.uToast.show({
          title: '退出登录成功！',
          type: 'success',
          back: true
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.main {
  margin-top: 40rpx;
  margin-left: 80rpx;
  margin-right: 80rpx;
}
</style>
