<template>
  <div class="winner-box">
    <div class="winner-img">
      <img src="../assets/Path.svg"/>
    </div>
    <span>@{{twitterName}}</span>
  </div>
</template>

<script>
export default {
  name: 'Winner',
  props: {
    twitterName: String
  }
}
</script>

<style scoped>
.winner-box{
  background: linear-gradient(#08C1F7, #1DA1F2);
  flex-direction: row;
  width: 300px;
  height: 50px;
  align-items: center;
  margin: 0;
  padding: 0px;
  display: flex;
  margin-top: 20px;
}

.winner-box > span { 
  font-size: 30px;
  color: white;
  margin: 0;
  padding-left: 40px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;  
}

.winner-img {
  margin-left: 20px;
  display: flex;
}
</style>


