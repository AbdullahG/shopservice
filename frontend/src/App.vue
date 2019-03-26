<template>
  <div id="app">
    <div class="container">
      <navbar class="p-3"></navbar>
      <router-view class="p-3"></router-view>
    </div>
  </div>
</template>

<script>
import Navbar from "./Navbar.vue";

export default {
    components: {Navbar},
    name: 'app',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  }
}
</script>
