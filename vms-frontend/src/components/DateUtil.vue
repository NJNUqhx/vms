<template>
  <div className="date-util">
    <div className="date-container">
      <h1>现在是{{ year }}年{{ month }}月{{ day }}日{{ hour }}时{{ minute }}分{{ second }}秒</h1>
    </div>
  </div>
</template>

<script>
export default {
  name: "DateUtil",
  data() {
    return {
      year: '',
      month: '',
      day: '',
      hour: '',
      minute: '',
      second: ''
    };
  },
  methods: {
    updateTime() {
      const now = new Date();
      this.year = now.getFullYear();
      this.month = now.getMonth() + 1;  // 月份从0开始
      this.day = now.getDate();
      this.hour = String(now.getHours()).padStart(2, '0');
      this.minute = String(now.getMinutes()).padStart(2, '0');
      this.second = String(now.getSeconds()).padStart(2, '0');
    }
  },
  created() {
    this.updateTime();  // 初始化时间
    setInterval(this.updateTime, 1000);  // 每秒更新时间
  }
};
</script>

<style scoped>
.date-util {
  text-align: center;
  justify-content: center;
  font-size: 15px;
  font-family: Arial, sans-serif;
}

.date-container {
  justify-content: center;
  align-items: center;
  font-weight: bolder;
  margin-top: 10px;
}
</style>
