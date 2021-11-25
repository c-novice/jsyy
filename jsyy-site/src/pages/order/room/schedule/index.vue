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
    <u-modal :show-title="false" :zoom="false" cancel-color="	#606266" confirm-color="#2979ff" v-model="show"
             content="确认是否进行预约" @confirm="order" :show-cancel-button="true"></u-modal>
    <u-modal :show-title="false" :zoom="false" cancel-color="	#606266" confirm-color="#2979ff" v-model="show4"
             content="是否立即进行支付" @confirm="runPay" :show-cancel-button="true"></u-modal>
    <u-calendar max-date="2022-01-01" v-model="show3" mode="date" @change="selectDate"></u-calendar>
    <u-dropdown @open="open" :border-bottom="true" duration="0" active-color="#ff9900">
      <u-dropdown-item @change="page" v-model="value1" title="预约状态" :options="options1"></u-dropdown-item>
      <u-dropdown-item @change="page" v-model="value2" title="最高审批人权限" :options="options2"></u-dropdown-item>
    </u-dropdown>
    <u-gap height="20"></u-gap>
    <view>
      <u-row gutter="16">
        <u-col span="6">
          <view class="riqi">
            <u-image @click="show3=true" width="40rpx" height="40rpx" src="@/static/rili.png"></u-image>
            <text @click="show3=true">预约日期</text>
          </view>
        </u-col>
        <u-col span="6">
          <view class="riqi">
            <u-image @click="cancelDate" width="40rpx" height="40rpx" src="@/static/close.png"></u-image>
            <text @click="cancelDate">取消日期</text>
          </view>
        </u-col>
      </u-row>
    </view>
    <u-card padding="0" v-for="item in schedules" :key="item.id" :show-foot="false" :show-head="false"
            @click="select(item)">
      <view slot="body" style="font-size: 25rpx;background-color: lightyellow">
        <u-gap height="10"></u-gap>
        <u-row gutter="16">
          <u-col span="6">
            <text>房间号：{{ item.roomId }}</text>
          </u-col>
          <u-col span="6">
            <text style="color: coral">预约日期：{{ item.workDate }}</text>
          </u-col>
        </u-row>
        <u-row gutter="16">
          <u-col span="6">
            <text>开放预约日期：{{ item.openDate }}</text>
          </u-col>
          <u-col span="6">
            <text>结束预约日期：{{ item.closeDate }}</text>
          </u-col>
        </u-row>
        <u-row gutter="16">
          <u-col span="6">
            <text style="color: coral">预约开始时间：{{ item.beginTime }}</text>
          </u-col>
          <u-col span="6">
            <text style="color: coral">预约结束时间：{{ item.endTime }}</text>
          </u-col>
        </u-row>
        <u-row gutter="16">
          <u-col span="6">
            <text>预约费用：{{ item.amount }}</text>
          </u-col>
          <u-col span="6">
            <text>最高审批人权限：{{ item.lastPendingPermission }}</text>
          </u-col>
        </u-row>
        <u-gap height="25"></u-gap>
        <text v-if="item.isOrdered ===0"
              style="color: #00C777;font-size: 30rpx;font-weight: bold;margin-left: 500rpx">
          可预约
        </text>
        <text v-if="item.isOrdered ===1" style="color: grey;font-size: 30rpx;font-weight: bold;margin-left: 500rpx">
          已被预约
        </text>
        <u-gap height="10"></u-gap>
      </view>
    </u-card>
  </view>
</template>

<script>
import UImage from "../../../../uview-ui/components/u-image/u-image";
import UCard from "../../../../uview-ui/components/u-card/u-card";
import uQRCode from '@/components/uqrcode/common/uqrcode.js'
import UGap from "../../../../uview-ui/components/u-gap/u-gap";
import UCol from "../../../../uview-ui/components/u-col/u-col";

export default {
  components: {UCol, UGap, UCard, UImage},

  data() {
    return {
      timestamp: 86400,
      value1: 1,
      value2: 1,
      options1: [{label: '全部', value: 1,}, {label: '可预约', value: 2,}, {label: '已被预约', value: 3,}],
      options2: null,
      keyword: '',
      facilityId: null,
      roomId: null,
      schedules: null,
      token: null,
      native: '',
      paying: true,
      show: false,
      show2: false,
      show3: false,
      workDate: null,
      show4: false,
      paymentInfo: null,
    }
  },
  methods: {
    runPay() {
      this.show2  = true
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
    open(index) {
      if (index !== 2) return
      this.show3 = true

    },
    cancelDate() {
      this.workDate = null
      this.$refs.uToast.show({
        title: "预约日期取消成功",
        type: 'success'
      })
      this.page()
    },
    selectDate(e) {
      this.workDate = e.result
      console.log(this.workDate)
      this.page()
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
    select(item) {
      if (item.isOrdered === 1) {
        return
      }
      this.selectItem = item
      this.show = true
    },
    // 预约
    order() {
      let headers = {
        "Content-Type": "application/x-www-form-urlencoded",
        "token": getApp().globalData.token
      }
      let params = {
        username: getApp().globalData.user.username,
        facilityId: this.facilityId,
        roomId: this.roomId,
        scheduleId: this.selectItem.id,
        lastPendingPermission: this.selectItem.lastPendingPermission
      }
      this.$u.post(`${this.$baseUrl}/order/orderInfo/auth/order`, params, headers)
          .then(data => {
            console.log(data)
            if (data.code === 200) {
              this.createNative(data.data.orderInfo.id)
            } else {
              this.$refs.uToast.show({
                title: data.message,
                type: 'warning'
              })
            }
          })
    },
    // 支付，获取支付二维码
    createNative(orderId) {
      let headers = {
        "Content-Type": "application/x-www-form-urlencoded",
        "token": getApp().globalData.token
      }
      let params = {
        orderId: orderId
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
    onLoad(options) {
      this.facilityId = options.facilityId
      this.roomId = options.roomId
    },
    onShow() {
      this.page()
      let headers = {
        "token": getApp().globalData.token
      }
      uni.request({
        url: `${this.$baseUrl}/cmn/permission/auth/getAllName`,
        method: 'GET',
        header: headers,
        success: ({data}) => {
          console.log(data)
          if (data.code === 200) {
            this.options2 = [{'value': 1, 'label': '全部'}]
            for (let i = 0; i < data.data.length; ++i) {
              this.options2.push({'value': i + 2, 'label': data.data[i]})
            }
          }
        }
      })
    },
    // 分页查询房间
    page() {
      let headers = {
        "token": getApp().globalData.token
      }
      let lastPendingPermission = ''
      if (this.options2 !== null) lastPendingPermission = this.options2[this.value2 - 1].label
      if (lastPendingPermission === '全部') lastPendingPermission = ''
      let isOrdered = this.options1[this.value1 - 1].value - 2
      let url = `${this.$baseUrl}/cmn/schedule/auth/1/1000?facilityId=` + this.facilityId + `&roomId=` + this.roomId +
          `&lastPendingPermission=` + lastPendingPermission
      if (isOrdered !== -1) url += '&isOrdered=' + isOrdered
      if (this.workDate !== null) url += '&workDate=' + this.workDate

      uni.request({
        url: url,
        method: 'GET',
        header: headers,
        success: ({data}) => {
          console.log(data)
          if (data.code === 200) {
            this.schedules = data.data.records
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
.search {
  margin-top: 20rpx;
}


.tips {
  margin-top: 20rpx;
  margin-bottom: 20rpx;
  display: flex;
  flex-direction: row;
  width: 100%;
  align-items: center; //垂直居中
  justify-content: center; //水平居中
  font-size: 20px;
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
