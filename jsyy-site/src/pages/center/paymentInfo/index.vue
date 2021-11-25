<template>
  <view>
    <u-toast ref="uToast"></u-toast>
    <u-popup width="450" height="auto" v-model="show2" mode="center" @make="make">
      <view>
        <u-gap height="20"></u-gap>
        <u-image class="logo" mode="widthFix" width="50%" src="@/static/WePayLogo.png"></u-image>
        <canvas id="qrcode" canvas-id="qrcode" style="height: 450rpx"/>
        <u-count-down class="logo" ref="uCountDown" :timestamp="7200" :autoplay="false"
                      @change="changeTime"></u-count-down>
      </view>
    </u-popup>
    <u-modal :show-title="false" :zoom="false" cancel-color="	#606266" confirm-color="#2979ff" v-model="show4"
             content="是否立即进行支付" @confirm="runPay" :show-cancel-button="true"></u-modal>
    <u-card v-for="item in payments" :key="item.id" :show-foot="false" :show-head="false"
            @click="createNative(item)">
      <view slot="body" class="card">
        <view slot="body" style="font-size: 25rpx">
          <u-gap height="10"></u-gap>
          <text>订单编号：{{ item.outTradeNo }}</text>
          <br>
          <text>订单金额：{{ item.totalAmount }}</text>
          <br>
          <text>订单编号：{{ item.outTradeNo }}</text>
          <br>
          <text>订单创建：{{ item.createTime }}</text>
          <u-gap height="25"></u-gap>
          <text v-if="item.paymentStatus ===6"
                style="color: #dd6161;font-size: 30rpx;font-weight: bold;margin-left: 500rpx">
            待支付
          </text>
          <text v-if="item.paymentStatus ===3"
                style="color: #00C777;font-size: 30rpx;font-weight: bold;margin-left: 500rpx">
            已支付
          </text>
          <text v-if="item.paymentStatus ===4"
                style="color: grey;font-size: 30rpx;font-weight: bold;margin-left: 500rpx">
            已取消
          </text>
          <u-gap height="10"></u-gap>
        </view>
      </view>
    </u-card>
    <view class="tips">
      <navigator :url="`/pages/center/login/index`">
        <text v-show="!loginStatus">登陆后查看订单记录</text>
      </navigator>
    </view>
  </view>
</template>

<script>
import uQRCode from '@/components/uqrcode/common/uqrcode.js'

export default {
  data() {
    return {
      show2: false,
      show4: false,
      paymentStatus: null,
      loginStatus: false,
      payments: [],
      native: null,
      paymentInfo: null
    }
  },
  onLoad(options) {
    if (null !== options.paymentStatus) this.paymentStatus = options.paymentStatus
    this.loginStatus = getApp().globalData.loginStatus
  },
  onShow() {
    if (this.loginStatus) this.page()
  },
  methods: {
    createNative(item) {
      if (item.paymentStatus !== 6) return
      this.paymentInfo = item
      let headers = {
        "Content-Type": "application/x-www-form-urlencoded",
        "token": getApp().globalData.token
      }
      let params = {
        orderId: this.paymentInfo.orderId
      }
      this.$u.post(`${this.$baseUrl}/order/payment/auth/pay`, params, headers)
          .then(data => {
            console.log(data)
            if (data.code === 200) {
              this.native = data.data.native.codeUrl
              this.paymentInfo = data.data.paymentInfo
              this.make()
              this.show4 = true
            } else {
              this.$refs.uToast.show({
                title: data.message,
                type: 'warning'
              })
            }
          })
    },
    runPay() {
      console.log(this.paymentInfo)
      this.show2 = true
      setTimeout(() => {
        this.$refs.uCountDown.start()
      })
    },
    // 事件触发，每秒一次
    changeTime(timestamp) {
      if (!this.show2) return
      let headers = {
        "Content-Type": "application/x-www-form-urlencoded",
        "token": getApp().globalData.token
      }
      let params = {
        outTradeNo: this.paymentInfo.outTradeNo
      }
      this.$u.get(`${this.$baseUrl}/order/payment/auth/queryPayStatus`, params, headers)
          .then(data => {
            if (data.code === 200) {
              console.log(data)
              this.show2 = false
              this.$refs.uToast.show({
                title: "支付成功",
                type: 'success'
              })
              this.page()
            }
          })
    },
    make() {
      uQRCode.make({
        canvasId: 'qrcode',
        componentInstance: this,
        size: 220,
        margin: 10,
        text: this.native,
        backgroundColor: '#ffffff',
        foregroundColor: 'green',
        fileType: 'png',
        errorCorrectLevel: uQRCode.errorCorrectLevel.H
      })
    },
    page() {
      let headers = {
        "token": getApp().globalData.token
      }
      let url = `${this.$baseUrl}/order/payment/auth/1/1000`
      if (this.paymentStatus) url += '?paymentStatus=' + this.paymentStatus

      uni.request({
        url: url,
        method: 'GET',
        header: headers,
        success: ({data}) => {
          console.log(data)
          if (data.code === 200) {
            this.payments = data.data.records
          } else {
            this.$refs.uToast.show({
              title: data.message,
              type: 'warning'
            })
          }
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>

.tips {
  margin-top: 20rpx;
  margin-bottom: 20rpx;
  display: flex;
  flex-direction: row;
  width: 100%;
  align-items: center; //垂直居中
  justify-content: center; //水平居中
  font-size: 20px;
  color: #fa3534;
}

.body {
  .card {
    display: flex;
    align-items: center;
  }
}

.logo {
  display: flex;
  justify-content: center;
}

.riqi {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
