<template>
  <view>
    <u-toast ref="uToast"></u-toast>
    <view class="search">
      <u-search placeholder="请输入设施名称关键字" :show-action="false"
                @search="page" v-model="keyword" :focus="true" @click="page" @change="page"></u-search>
    </view>
    <u-card :key="item.id" v-for="item in facilities" :show-head="false" :show-foot="false" @click="select(item.id)">
      <view slot="body" class="card">
        <view class="u-body-item u-flex u-row-between u-p-b-0">
          <u-image src="@/static/sheshi.png" width="60rpx" height="60rpx"></u-image>
          <text>{{ item.name }}</text>
        </view>
      </view>
    </u-card>
    <view class="tips">
      <navigator :url="`/pages/center/login/index`">
        <text v-show="!loginStatus">登陆后查看设施信息</text>
      </navigator>
    </view>
  </view>
</template>

<script>
import UImage from "../../uview-ui/components/u-image/u-image";
import UCard from "../../uview-ui/components/u-card/u-card";

export default {
  components: {UCard, UImage},
  data() {
    return {
      keyword: '',
      facilities: null,
      token: null,
      loginStatus: false
    }
  },
  methods: {
    onShow() {
      this.loginStatus = getApp().globalData.loginStatus
      console.log(this.loginStatus)
      if (this.loginStatus) this.page()
    },
    // 分页查询设施
    page() {
      if (!this.loginStatus) return
      let headers = {
        "token": getApp().globalData.token
      }
      uni.request({
        url: `${this.$baseUrl}/cmn/facility/auth/1/1000`,
        method: 'GET',
        header: headers,
        success: ({data}) => {
          console.log(data)
          if (data.code === 200) {
            this.facilities = data.data.records
          }
        }
      })
    },
    select(id) {
      if (!this.loginStatus) return
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
</style>
