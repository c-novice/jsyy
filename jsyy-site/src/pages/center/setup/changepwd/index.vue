<template>
  <view class="main">
    <u-gap height="20"></u-gap>
    <u-form label-width="100">
      <u-form-item label="密码">
        <u-input v-model="password" placeholder="请输入新密码"/>
      </u-form-item>
    </u-form>
    <u-gap height="20"></u-gap>
    <u-button :ripple="true" type="primary" size="medium" @click="submit">修改密码</u-button>
  </view>
</template>

<script>
import UFormItem from "../../../../uview-ui/components/u-form-item/u-form-item";
import UForm from "../../../../uview-ui/components/u-form/u-form";
import UInput from "../../../../uview-ui/components/u-input/u-input";
import UButton from "../../../../uview-ui/components/u-button/u-button";

export default {
  components: {UButton, UInput, UForm, UFormItem},
  data() {
    return {
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
