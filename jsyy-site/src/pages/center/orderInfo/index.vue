<template>
  <view>
    <u-toast ref="uToast"></u-toast>
    <u-modal :show-title="false" :zoom="false" cancel-color="	#606266" confirm-color="#2979ff" v-model="show"
             content="确认是否取消订单" @confirm="cancelOrder" :show-cancel-button="true"></u-modal>
    <u-card v-if="item.orderStatus!==2" v-for="item in orders" :key="item.id" :show-foot="false" :show-head="false"
            @click="show=(item.orderStatus===3)">
      <view slot="body" class="card">
        <view slot="body" style="font-size: 25rpx">
          <u-gap height="10"></u-gap>
          <u-row gutter="16">
            <u-col span="6">
              <text>房间号：{{ item.roomId }}</text>
            </u-col>
            <u-col span="6">
              <text>预约日期：{{ item.workDate }}</text>
            </u-col>
          </u-row>
          <u-row gutter="16">
            <u-col span="6">
              <text>预约开始时间：{{ item.beginTime }}</text>
            </u-col>
            <u-col span="6">
              <text>预约结束时间：{{ item.endTime }}</text>
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
          <text v-if="item.orderStatus ===4"
                style="color: #00C777;font-size: 30rpx;font-weight: bold;margin-left: 500rpx">
            已预约
          </text>
          <text v-if="item.orderStatus ===3"
                style="color: #fcbd71;font-size: 30rpx;font-weight: bold;margin-left: 500rpx">
            审批中
          </text>
          <text v-if="item.orderStatus ===5"
                style="color: #dd6161;font-size: 30rpx;font-weight: bold;margin-left: 500rpx">
            审批失败
          </text>
          <u-gap height="10"></u-gap>
        </view>
      </view>
    </u-card>
    <view class="tips" v-if="!loginStatus">
      <navigator :url="`/pages/center/login/index`">
        <text>登陆后查看预约记录</text>
      </navigator>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      orderStatus: null,
      loginStatus: false,
      orders: [],
      show: false
    }
  },
  onLoad(options) {
    if (null !== options.orderStatus) this.orderStatus = options.orderStatus
    this.loginStatus = getApp().globalData.loginStatus
  },
  onShow() {
    if (this.loginStatus) this.page()
  },
  methods: {
    cancelOrder(){

    },
    page() {
      let headers = {
        "token": getApp().globalData.token
      }
      let url = `${this.$baseUrl}/order/orderInfo/auth/1/1000`
      if (this.orderStatus) url += '?orderStatus=' + this.orderStatus

      uni.request({
        url: url,
        method: 'GET',
        header: headers,
        success: ({data}) => {
          console.log(data)
          if (data.code === 200) {
            this.orders = data.data.records
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
