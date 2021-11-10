<template>
  <view>
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
      loginStatus: false
    }
  },
  methods: {
    // 页面跳转
    switchTo(url) {
      uni.navigateTo({
        url: url
      });
    },
    exit() {
    }
  }
}
</script>

<style lang="scss" scoped>
.main {
  margin-left: 60rpx;
  margin-right: 60rpx;
}
</style>
