<template>
<div class="wrapper">
    <div class="sidebar">
    <router-link to="/"><h2>My_Test</h2></router-link>
    <ul>
    <li><router-link to="/AuteurList"> auteur</router-link></li>
		<li><router-link to="/LivreList"> livre</router-link></li>
		<li><router-link to="/EditeurList"> editeur</router-link></li>
		
    </ul>
  </div>
</div>
</template>

<script>
export default {
  name: 'SideBar',
 
}
</script>

<style scoped>


*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  list-style: none;
  text-decoration: none;
  font-family: 'Josefin Sans', sans-serif;
}

.wrapper{
  display: flex;
  position: relative;
}

.wrapper .sidebar{
  width: 250px;
  height: 100%;
  background: #000;
  padding: 30px 0px;
  position: fixed;
  left: 20px;
}

.wrapper .sidebar h2{
  color: #fff;
  text-transform: uppercase;
  text-align: center;
  margin-bottom: 30px;
}

.wrapper .sidebar ul li{
  padding: 15px;
  border-bottom: 1px solid #bdb8d7;
  border-bottom: 1px solid rgba(0,0,0,0.05);
  border-top: 1px solid rgba(255,255,255,0.05);
}    

.wrapper .sidebar ul li a{
  color: #bdb8d7;
  display: block;
}

.wrapper .sidebar ul li a .fa{
  width: 25px;
}

.wrapper .sidebar ul li:hover{
  background-color: #7d777717;
}
    
.wrapper .sidebar ul li:hover a{
  color: #fff;
}
 
.wrapper .sidebar .social_media{
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
}

.wrapper .sidebar .social_media a {
    display: block;
    width: 40px;
    height: 40px;
    background: transparent;
    line-height: 45px;
    text-align: center;
    margin: 0 5px;
    color: #bdb8d7;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
}


.wrapper  {
    padding: 20px; 
    background: #000; 
    color: #fff;
    border-bottom: 1px solid #e0e4e8;
}

.wrapper {
  margin: 20px;
  color: #717171;
  line-height: 25px;
}

.wrapper {
  margin-bottom: 20px;
}
</style>
