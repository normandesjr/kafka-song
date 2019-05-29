<template>
  <div class="hello">
    <h1>Winners</h1>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  }
}
</script>

<style scoped>
h1 {
  font-size: 60px;
  color: white;
}
.hello {
  height: 300px;
  background-color: blue;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  background-image: url('../assets/Bitmap.png');
  background-color: #08C1F7;
  background-size: cover;
}


</style>
