<template>
  <view>
    <u-toast ref="uToast"></u-toast>
    <text class="main">校园统一挂号平台</text>
    <u-swiper :effect3d="true" :list="list" height="400"></u-swiper>
  </view>
</template>

<script>
import USwiper from "../../uview-ui/components/u-swiper/u-swiper";

export default {
  components: {USwiper},
  data() {
    return {
      native: "weixin://wxpay/bizpayurl?pr=Ul09xIUzz",
      paying: true,
      list: [
        {image: '/static/lb3.jpg', title: 'hello world'},
        {image: '/static/lb3.jpg', title: 'hello world'},
        {image: '/static/lb3.jpg', title: 'hello world'}
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
.main {
  margin-left: 20rpx;
  margin-top: 20rpx;
  margin-bottom: 20rpx;
  font-size: 16px;
  color: grey;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bolder;
}
</style>
