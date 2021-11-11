<template>
  <view>
    <u-toast ref="uToast"></u-toast>
    <view class="search">
      <u-search placeholder="请输入房间名称关键字" :show-action="false"
                @search="page" v-model="keyword" :focus="true" @click="page" @change="page"></u-search>
    </view>
    <u-card :key="item.id" v-for="item in rooms" :show-head="false" :show-foot="false" @click="select(item)">
      <view slot="body" class="card">
        <view class="u-body-item u-flex u-row-between u-p-b-0">
          <u-image src="@/static/sheshi.png" width="60rpx" height="60rpx"></u-image>
          <text>{{ item.roomId }}</text>
        </view>
      </view>
    </u-card>
  </view>
</template>

<script>
import UImage from "../../../uview-ui/components/u-image/u-image";
import UCard from "../../../uview-ui/components/u-card/u-card";

export default {
  components: {UCard, UImage},
  data() {
    return {
      keyword: '',
      facilityId: null,
      rooms: null,
      token: null,
      loginStatus: false
    }
  },
  methods: {
    onLoad(options) {
      this.facilityId = options.facilityId
    },
    onShow() {
      this.loginStatus = getApp().globalData.loginStatus
      console.log(this.loginStatus)
      if (this.loginStatus) this.page()
    },
    // 分页查询房间
    page() {
      if (!this.loginStatus) return
      let headers = {
        "token": getApp().globalData.token
      }
      uni.request({
        url: `${this.$baseUrl}/cmn/room/auth/1/1000?&facilityId=` + this.facilityId,
        method: 'GET',
        header: headers,
        success: ({data}) => {
          console.log(data)
          if (data.code === 200) {
            this.rooms = data.data.records
          }
        }
      })
    },
    select(item) {
      if (!this.loginStatus) return
      uni.navigateTo({
        url: './schedule/index?facilityId=' + this.facilityId + "&roomId=" + item.roomId
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
