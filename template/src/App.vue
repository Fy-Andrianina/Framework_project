<template>
  <div id="app">
    <SideBar/>
   <router-view></router-view>
  </div>
</template>
 
<script>
import SideBar from './components/pages/SideBar.vue';


export default {
  name: 'App',
 components: {
    SideBar ,
  }
};
</script>