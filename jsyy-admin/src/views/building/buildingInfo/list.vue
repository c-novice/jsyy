<template>
  <el-table :data="tableData" stripe style="width: 100%">
    <el-table-column prop="date" label="设施id" width="180"></el-table-column>
    <el-table-column prop="name" label="设施名称" width="180"></el-table-column>
    <el-table-column prop="address" label="设施预约信息"></el-table-column>
  </el-table>
</template>

<script>
export default {
  data() {
    return {
      // 设施列表
      tableData: null
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
