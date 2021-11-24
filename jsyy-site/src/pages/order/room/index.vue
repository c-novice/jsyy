<template>
  <view>
    <u-toast ref="uToast"></u-toast>
    <view class="search">
      <u-search v-model="keyword" :focus="true"
                :show-action="false" placeholder="请输入房间名称关键字" @change="page" @click="page" @search="page"></u-search>
    </view>
    <u-gap height="25"></u-gap>
    <view style="margin-left: 20rpx;margin-right: 20rpx">
      <u-table>
        <u-tr style="color: #f1f1f1">
          <u-td>房间类型</u-td>
          <u-td>座位数下限</u-td>
          <u-td>座位数上限</u-td>
        </u-tr>
        <u-tr>
          <u-td>
            <u-input v-model="type" :clearable="false" :placeholder="type" height="30" input-align="center"></u-input>
          </u-td>
          <u-td>
            <u-input v-model="seatingLow" :clearable="false" :placeholder="seatingLow" height="30"
                     input-align="center"></u-input>
          </u-td>
          <u-td>
            <u-input v-model="seatingHigh" :clearable="false" :placeholder="seatingHigh" height="30"
                     input-align="center"></u-input>
          </u-td>
        </u-tr>
      </u-table>
    </view>
    <u-card v-for="item in rooms" :key="item.id" :show-foot="false" :show-head="false" @click="select(item)">
      <view slot="body" class="card">
        <view class="u-body-item u-flex u-row-between u-p-b-0">
          <u-image height="60rpx" src="@/static/fangjian.png" width="60rpx"></u-image>
          <text style="color: #fcbd71">房间名称：{{ item.roomId }}</text>
        </view>
        <u-gap height="15"></u-gap>
        <view style="font-size: 24rpx">
          <text>座位数：{{ item.seating }}</text>
          <br>
          <text>房间类型：{{ item.type }}</text>
          <br>
          <text>房间描述：{{ item.description }}</text>
        </view>
      </view>
    </u-card>
  </view>
</template>

<script>
import UImage from "../../../uview-ui/components/u-image/u-image";
import UCard from "../../../uview-ui/components/u-card/u-card";
import UInput from "../../../uview-ui/components/u-input/u-input";

export default {
  components: {UInput, UCard, UImage},
  data() {
    return {
      keyword: '',
      facilityId: '',
      rooms: '',
      token: '',
      seatingLow: '',
      seatingHigh: '',
      type: ''
    }
  },
  watch: {
    seatingLow() {
      this.page()
    },
    seatingHigh() {
      this.page()
    },
    type() {
      this.page()
    }
  },
  methods: {
    onLoad(options) {
      this.facilityId = options.facilityId
    },
    onShow() {
      this.page()
    },
    // 分页查询房间
    page() {
      console.log(this.type)
      let headers = {
        "token": getApp().globalData.token
      }
      uni.request({
        url: `${this.$baseUrl}/cmn/room/auth/1/1000?facilityId=` + this.facilityId + `&roomId=`
            + this.keyword + '&type=' + this.type + '&seatingLow=' + this.seatingLow + '&seatingHigh=' + this.seatingHigh,
        method: 'GET',
        header: headers,
        success: ({data}) => {
          console.log(data)
          if (data.code === 200) {
            this.rooms = data.data.records
          } else {
            this.$refs.uToast.show({
              title: data.message,
              type: 'warning'
            })
          }
        }
      })
    },
    select(item) {
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
