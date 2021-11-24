<template>
  <view>
    <tki-qrcode v-if="paying" :val="native"></tki-qrcode>
    <qriously v-if="paying" :value="native" :size="200" />
    <u-modal @confirm="order" v-model="show" content="确认是否进行预约"></u-modal>
    <u-card :key="item.id" v-for="item in schedules" :show-head="false" :show-foot="false" @click="select(item)">
      <view slot="body" style="font-size: 21rpx">
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
        <text style="color: #00C777;font-size: 28rpx;font-weight: bold;margin-left: 500rpx" v-if="item.isOrdered ===0">
          可预约
        </text>
        <text style="color: grey;font-size: 28rpx;font-weight: bold;margin-left: 500rpx" v-if="item.isOrdered ===1">
          已被预约
        </text>
      </view>
    </u-card>
  </view>
</template>

<script>
import UImage from "../../../../uview-ui/components/u-image/u-image";
import UCard from "../../../../uview-ui/components/u-card/u-card";
import tkiQrcode from "../../../../tki-qrcode/tki-qrcode.vue"

export default {
  components: {UCard, UImage, tkiQrcode},
  data() {
    return {
      keyword: '',
      facilityId: null,
      roomId: null,
      schedules: null,
      token: null,
      native: "weixin://wxpay/bizpayurl?pr=Ul09xIUzz",
      paying: true,
      show: false
    }
  },
  methods: {
    select(item) {
      if (item.isOrdered === 1) return
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
        // username: getApp().globalData.user.username,
        username: '18355431182',
        facilityId: this.facilityId,
        roomId: this.roomId,
        scheduleId: this.selectItem.id,
        lastPendingPermission: this.selectItem.lastPendingPermission
      }
      this.$u.post(`${this.$baseUrl}/order/auth/order`, params, headers)
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
      this.$u.post(`${this.$baseUrl}/payment/auth/pay`, params, headers)
          .then(data => {
            console.log(data)
            if (data.code === 200) {
              this.native = data.data.native
            } else {
              this.$refs.uToast.show({
                title: data.message,
                type: 'warning'
              })
            }
          })
    },
    onLoad
        (options) {
      this.facilityId = options.facilityId
      this.roomId = options.roomId
    }
    ,
    onShow() {
      this.page()
    }
    ,
    // 分页查询房间
    page() {
      console.log(this.facilityId, this.roomId)
      let headers = {
        "token": getApp().globalData.token
      }
      uni.request({
        url: `${this.$baseUrl}/cmn/schedule/auth/1/1000?facilityId=` + this.facilityId + `&roomId=` + this.roomId,
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
</style>
