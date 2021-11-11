<template>
  <view>
    <u-toast ref="uToast"></u-toast>
    <view slot="body" class="card">
      <text class="tips">房间号：{{roomId}}</text>
      <u-table>
        <u-tr :key="item.id" v-for="item in schedules">
          <u-th>学校</u-th>
          <u-th>班级</u-th>
          <u-th>年龄</u-th>
        </u-tr>
      </u-table>
    </view>
  </view>
</template>

<script>
import UImage from "../../../../uview-ui/components/u-image/u-image";
import UCard from "../../../../uview-ui/components/u-card/u-card";

export default {
  components: {UCard, UImage},
  data() {
    return {
      keyword: '',
      facilityId: null,
      roomId: null,
      schedules: null,
      token: null,
      loginStatus: false
    }
  },
  methods: {
    onLoad(options) {
      this.facilityId = options.facilityId
      this.roomId = options.roomId
    },
    onShow() {
      this.loginStatus = getApp().globalData.loginStatus
      if (this.loginStatus) this.page()
    },
    // 分页查询房间
    page() {
      if (!this.loginStatus) return
      let headers = {
        "token": getApp().globalData.token
      }
      uni.request({
        url: `${this.$baseUrl}/cmn/schedule/auth/1/1000?&facilityId=` + this.facilityId,
        method: 'GET',
        header: headers,
        success: ({data}) => {
          console.log(data)
          if (data.code === 200) {
            this.schedules = data.data.records
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
