<template>
  <view>
    <u-toast ref="uToast"></u-toast>
    <view class="head">
      <u-image style="margin-left: 20rpx;margin-right: 60rpx" width="80rpx" height="80rpx"
               src="@/static/touxiang.png"></u-image>
      <!--      路由到登录/注册页面-->
      <navigator :url="`/pages/center/login/index`" v-show="!loginStatus">
        <text>登录 / 注册</text>
      </navigator>
      <text v-show="loginStatus">{{ user.username }}</text>
      <u-image style="margin-left: 270rpx" width="55rpx" height="55rpx" src="@/static/shezhi.png"
               @click="switchTo('setup/index')"></u-image>
    </view>
    <view class="normal">
      <u-card :show-foot="false" title="预约服务" title-size="32" :full="true">
        <u-grid :col="4" slot="body">
          <u-grid-item>
            <u-image width="60rpx" height="60rpx" src="@/static/yuyue2.png"></u-image>
            <view class="grid-text">当前预约</view>
          </u-grid-item>
          <u-grid-item>
            <u-image width="60rpx" height="60rpx" src="@/static/shenhe.png"></u-image>
            <view class="grid-text">正在审核</view>
          </u-grid-item>
          <u-grid-item>
            <u-image width="60rpx" height="60rpx" src="@/static/shibai.png"></u-image>
            <view class="grid-text">预约失败</view>
          </u-grid-item>
          <u-grid-item>
            <u-image width="60rpx" height="60rpx" src="@/static/jilu.png"></u-image>
            <view class="grid-text">预约记录</view>
          </u-grid-item>
        </u-grid>
      </u-card>
    </view>
    <view class="normal">
      <u-card :show-foot="false" title="我的订单" title-size="32" :full="true">
        <u-grid :col="3" slot="body">
          <u-grid-item>
            <u-image width="60rpx" height="60rpx" src="@/static/daizhifu.png"></u-image>
            <view class="grid-text">待支付</view>
          </u-grid-item>
          <u-grid-item>
            <u-image width="60rpx" height="60rpx" src="@/static/yiwancheng.png"></u-image>
            <view class="grid-text">已完成</view>
          </u-grid-item>
          <u-grid-item>
            <u-image width="60rpx" height="60rpx" src="@/static/dingdan.png"></u-image>
            <view class="grid-text">全部订单</view>
          </u-grid-item>
        </u-grid>
      </u-card>

    </view>
  </view>
</template>

<script>
import UImage from "../../uview-ui/components/u-image/u-image";
import UCard from "../../uview-ui/components/u-card/u-card";
import UNavbar from "../../uview-ui/components/u-navbar/u-navbar";

export default {
  components: {UNavbar, UCard, UImage},
  data() {
    return {
      // 登录状态
      loginStatus: false,
      // 当前用户
      user: null,
      token: null
    }
  },
  methods: {
    onShow() {
      this.token = getApp().globalData.token
      this.user = getApp().globalData.user
      this.loginStatus = getApp().globalData.loginStatus
    },
    // 页面跳转
    switchTo(url) {
      uni.navigateTo({
        url: url
      });
    }
  }
}
</script>

<style lang="scss" scoped>
.head {
  margin-top: 50rpx;
  margin-left: 20rpx;
  margin-bottom: 60rpx;
  display: flex;
  align-items: center;
  font-size: 18px;
  color: grey;
}

.grid-text {
  margin-top: 20rpx;
  font-size: 12px;
  color: #2b85e4;
  font-weight: normal;
}

.normal {
  margin-top: 30rpx;
  font-size: 13px;
  font-weight: bolder;
  margin-left: 20rpx;
}
</style>
