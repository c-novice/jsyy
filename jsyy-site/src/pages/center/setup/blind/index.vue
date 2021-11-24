<template>
  <view class="main">
    <u-toast ref="uToast"></u-toast>
    <u-gap height="20"></u-gap>
    <u-form label-width="140">
      <u-form-item label="校园账号">
        <u-input v-model="studentNumber" placeholder="请输入校园账号"/>
      </u-form-item>
      <u-form-item label="校园密码">
        <u-input v-model="password" placeholder="请输入校园密码"/>
      </u-form-item>
    </u-form>
    <u-gap height="20"></u-gap>
    <u-button :ripple="true" size="medium" type="primary" @click="submit">绑定校园信息</u-button>
  </view>
</template>

<script>
export default {
  data() {
    return {
      studentNumber: '',
      password: ''
    }
  },
  methods: {
    submit() {
      let headers = {
        "Content-Type": "application/x-www-form-urlencoded",
        "token": getApp().globalData.token
      }
      let params = {
        "userId": getApp().globalData.user.userId,
        "studentNumber": this.studentNumber,
        "password": this.password
      }
      uni.request({
        url: `${this.$baseUrl}/user/auth/binding`,
        method: 'GET',
        data: params,
        header: headers,
        success: ({data}) => {
          console.log(data)
          if (data.code === 200) {
            getApp().globalData.user = data.data.user
            this.$refs.uToast.show({
              title: '绑定信息成功!',
              type: 'success',
              back: true
            })
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
.main {
  margin-top: 20rpx;
  margin-left: 20rpx;
  margin-right: 20rpx;

  u-button {
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
</style>
