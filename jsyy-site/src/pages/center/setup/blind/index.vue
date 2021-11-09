<template>
  <view class="main">
    <u-gap height="20"></u-gap>
    <u-form label-width="140">
      <u-form-item label="校园账号">
        <u-input v-model="username" placeholder="请输入校园账号"/>
      </u-form-item>
      <u-form-item label="校园密码">
        <u-input v-model="password" placeholder="请输入校园密码"/>
      </u-form-item>
    </u-form>
    <u-gap height="20"></u-gap>
    <u-button :ripple="true" type="primary" size="medium" @click="submit">绑定校园信息</u-button>
  </view>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    submit() {

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
