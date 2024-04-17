<template>
<div class="wrapper">
    <div class="sidebar">
    <router-link to="/"><h2>Zaby</h2></router-link>
    <ul>
    <li><router-link to="/AuteurList"> auteur</router-link></li>
		<li><router-link to="/LivreList"> livre</router-link></li>
		<li><router-link to="/EditeurList"> editeur</router-link></li>
		<li><router-link to="/UtilisateurList"> utilisateur</router-link></li>
		
     <li @click="confirmLogout"><router-link to="#"> Déconnexion </router-link></li>
    </ul>
  </div>
</div>
</template>

<script>
   import Swal from 'sweetalert2';
export default {
  name: 'SideBar',

       methods: {
        confirmLogout() {
          Swal.fire({
            title: 'Vous voulez vraiment vous déconnecter ?',
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Oui',
            cancelButtonText: 'Non',
          }).then((result) => {
            if (result.isConfirmed) {
              sessionStorage.removeItem('role');
              this.$router.push('/');
            }
          });
        }
      }
}
</script>

<style scoped>


*{
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
  padding: 30px 0px;
  position: fixed;
}

.wrapper .sidebar h2{
  color: #fff;
  text-transform: uppercase;
  text-align: center;
}

.wrapper .sidebar ul li{
  background-color: #fff;
  font-weight: bold;
  padding: 15px;
  border-bottom: 1px solid rgb(182, 182, 182);
  /* border-top: 1px solid rgb(182, 182, 182); */
}    

.wrapper .sidebar ul li a{
  color: #141414;
  font-size: medium;
  font-family: Courier, monospace;
  text-transform: uppercase;
  display: block;
}

.wrapper .sidebar ul li a .fa{
  width: 25px;
}

.wrapper .sidebar ul li:hover{
  border-bottom: 1px solid #FF4081;
}
    
.wrapper .sidebar ul li:hover a{
  color: #FF4081;
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
    color: #bdb8d7;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
}


.wrapper  {
    padding: 20px; 
}

.wrapper {
  color: #1f1e1e;
  line-height: 25px;
}


</style>