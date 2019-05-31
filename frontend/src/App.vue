<template>
  <div id="app">
    <Header/>
    <Winners/>
  </div>
</template>

<script>
import Header from "./components/Header.vue";
import Winners from "./components/Winners";

export default {
  name: "app",
  components: {
    Header,
    Winners
  }
};
</script>

<style>
@import url("https://fonts.googleapis.com/css?family=Montserrat:500,600");
body {
  margin: 0px;
  padding: 0px;
}
#app {
  font-family: "Montserrat", sans-serif;
  font-weight: 500;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
