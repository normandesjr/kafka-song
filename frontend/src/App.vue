<template>
  <div id="app">
    <HelloWorld/>
    <Winners/>
  </div>
</template>

<script>
import HelloWorld from './components/Header.vue'
import Winners from './components/Winners'

export default {
  name: 'app',
  components: {
    HelloWorld,
    Winners
  },
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
