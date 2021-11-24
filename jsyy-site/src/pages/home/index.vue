<!--<template>-->
<!--  <view>-->
<!--    <qriously v-if="paying" :value="native" :size="200"/>-->
<!--    &lt;!&ndash;    <u-toast ref="uToast"></u-toast>&ndash;&gt;-->
<!--    &lt;!&ndash;    <text class="main">校园统一挂号平台</text>&ndash;&gt;-->
<!--    &lt;!&ndash;    <u-swiper :effect3d="true" height="400" :list="list"></u-swiper>&ndash;&gt;-->
<!--  </view>-->
<!--</template>-->

<!--<script>-->
<!--import USwiper from "../../uview-ui/components/u-swiper/u-swiper";-->

<!--export default {-->
<!--  components: {USwiper},-->
<!--  data() {-->
<!--    return {-->
<!--      native: "weixin://wxpay/bizpayurl?pr=Ul09xIUzz",-->
<!--      paying: true,-->
<!--      list: [-->
<!--        {image: '/static/lb1.jpg', title: 'hello world'},-->
<!--        {image: '/static/lb2.jpg', title: 'hello world'},-->
<!--        {image: '/static/lb3.jpg', title: 'hello world'}-->
<!--      ]-->
<!--    }-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style lang="scss" scoped>-->
<!--.main {-->
<!--  margin-left: 20rpx;-->
<!--  margin-top: 20rpx;-->
<!--  margin-bottom: 20rpx;-->
<!--  font-size: 16px;-->
<!--  color: grey;-->
<!--  display: flex;-->
<!--  align-items: center;-->
<!--  justify-content: center;-->
<!--  font-weight: bolder;-->
<!--}-->
<!--</style>-->
<template>
  <canvas id="qrcode" canvas-id="qrcode" style="width: 354px;height: 354px;" />
</template>

<script>
import uQRCode from '@/components/uqrcode/common/uqrcode.js'

export default {
  onReady() {
    uQRCode.make({
      canvasId: 'qrcode',
      componentInstance: this,
      size: 354,
      margin: 10,
      text: 'weixin://wxpay/bizpayurl?pr=XLsUE4Kzz',
      backgroundColor: '#ffffff',
      foregroundColor: '#ff0000',
      fileType: 'png',
      errorCorrectLevel: uQRCode.errorCorrectLevel.H
    })
        .then(res => {
          console.log(res)
        })
  }
}
</script>

