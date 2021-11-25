<template>
  <view>
    <u-toast ref="uToast"></u-toast>
    <view v-if="loginStatus&&binding&&isStudent">
      <view class="search">
        <u-search v-model="keyword" :focus="true"
                  :show-action="false" placeholder="请输入设施名称关键字" @change="page" @click="page" @search="page"></u-search>
      </view>
      <u-card v-for="item in facilities" :key="item.id" :show-foot="false" :show-head="false" @click="select(item.id)">
        <view slot="body" class="card">
          <view class="u-body-item u-flex u-row-between u-p-b-0">
            <u-image height="60rpx" src="@/static/sheshi.png" width="60rpx"></u-image>
            <text style="color: #fcbd71">{{ item.name }}</text>
          </view>
          <u-gap height="15"></u-gap>
          <view style="font-size: 24rpx">
            <text>设施描述：{{ item.description }}</text>
          </view>
        </view>
      </u-card>
    </view>
    <view v-if="loginStatus&&binding&&!isStudent">
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
                <text>预约人：{{ item.username }}</text>
              </u-col>
            </u-row>
            <u-gap height="25"></u-gap>
            <u-row gutter="16">
              <u-col span="6" class="">
                <u-button class="button" type="success" size="medium" @click="pass(item)">通过</u-button>
              </u-col>
              <u-col span="6">
                <u-button class="button" type="error" size="medium" @click="refuse(item)">拒绝</u-button>
              </u-col>
            </u-row>
            <u-gap height="10"></u-gap>
          </view>
        </view>
      </u-card>
    </view>
    <view class="tips" v-if="!loginStatus">
      <navigator :url="`/pages/center/login/index`">
        <text>登陆后查看信息</text>
      </navigator>
    </view>
    <view class="tips" v-if="loginStatus&&!binding">
      <navigator :url="`/pages/center/setup/blind/index`">
        <text>绑定后查看信息</text>
      </navigator>
    </view>
  </view>
</template>

<script>
import UImage from "../../uview-ui/components/u-image/u-image";
import UCard from "../../uview-ui/components/u-card/u-card";
import UButton from "../../uview-ui/components/u-button/u-button";

export default {
  components: {UButton, UCard, UImage},
  data() {
    return {
      keyword: '',
      facilities: null,
      token: null,
      loginStatus: false,
      binding: false,
      isStudent: true,
      orders: null
    }
  },
  onShow() {
    this.loginStatus = getApp().globalData.loginStatus
    console.log(getApp().globalData.user)
    if (getApp().globalData.user && getApp().globalData.user.isAuth && getApp().globalData.user.isAuth === 1) {
      this.binding = true
      this.isStudent = (getApp().globalData.user.permission === '学生')
    }
    if (this.loginStatus && this.binding && this.isStudent) this.page()
    if (this.loginStatus && this.binding && !this.isStudent) this.page2()
  },
  methods: {
    pass(item) {
      let headers = {
        "token": getApp().globalData.token
      }
      let param = {
        'username': getApp().globalData.user.name,
        'outTradeNo': item.outTradeNo,
        'status': 3
      }
      this.$u.get(`${this.$baseUrl}/order/orderInfo/auth/pending`, param, headers)
          .then(data => {
            if (data.code === 200) {
              console.log(data)
              this.$refs.uToast.show({
                title: '审批成功',
                type: 'success'
              })
              this.page2()
            } else {
              this.$refs.uToast.show({
                title: data.message,
                type: 'warning'
              })
            }
          })
    },
    refuse(item) {
      let headers = {
        "token": getApp().globalData.token
      }
      let param = {
        'username': getApp().globalData.user.name,
        'outTradeNo': item.outTradeNo,
        'status': 5
      }
      this.$u.get(`${this.$baseUrl}/order/orderInfo/auth/pending`, param, headers)
          .then(data => {
            if (data.code === 200) {
              console.log(data)
              this.$refs.uToast.show({
                title: '拒绝成功',
                type: 'success'
              })
              this.page2()
            } else {
              this.$refs.uToast.show({
                title: data.message,
                type: 'warning'
              })
            }
          })
    },
    page2() {
      let headers = {
        "token": getApp().globalData.token
      }
      let param = {
        'permissionName': getApp().globalData.user.permission
      }
      this.$u.get(`${this.$baseUrl}/order/orderInfo/auth/remain/1/1000`, param, headers)
          .then(data => {
            if (data.code === 200) {
              console.log(data)
              this.orders = data.data.records
            } else {
              this.$refs.uToast.show({
                title: data.message,
                type: 'warning'
              })
            }
          })
    },
    // 分页查询设施
    page() {
      let headers = {
        "token": getApp().globalData.token
      }
      uni.request({
        url: `${this.$baseUrl}/cmn/facility/auth/1/1000?name=` + this.keyword,
        method: 'GET',
        header: headers,
        success: ({data}) => {
          console.log(data)
          if (data.code === 200) {
            this.facilities = data.data.records
          } else {
            this.$refs.uToast.show({
              title: data.message,
              type: 'warning'
            })
          }
        }
      })
    },
    select(id) {
      uni.navigateTo({
        url: './room/index?facilityId=' + id
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
  margin-top: 100rpx;
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

.button {
  display: flex;
  align-items: center;
  vertical-align: center;
}
</style>
