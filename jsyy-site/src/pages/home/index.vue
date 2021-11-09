<template>
  <view>
    <text class="main">校园统一挂号平台</text>
    <u-swiper :effect3d="true" height="400" :list="list"></u-swiper>
  </view>
</template>

<script>
import USwiper from "../../uview-ui/components/u-swiper/u-swiper";

export default {
  components: {USwiper},
  data() {
    return {
      list: [
        {image: '/static/lb1.jpg', title: 'hello world'},
        {image: '/static/lb2.jpg', title: 'hello world'},
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
