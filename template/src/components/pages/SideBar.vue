<template>
<div class="wrapper">
    <div class="sidebar">
    <router-link to="/"><h2>Biblio</h2></router-link>
    <ul>
    <li><router-link to="/AuteurList"> auteur</router-link></li>
		<li><router-link to="/LivreList"> livre</router-link></li>
		<li><router-link to="/EditeurList"> editeur</router-link></li>
		<li><router-link to="/UtilisateurList"> utilisateur</router-link></li>
		
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
  background: #f9eded; /* Couleur de fond girly */
  padding: 30px 0px;
  position: fixed;
  border-right: 1px solid #e0e4e8; /* Bordure droite */
}

.wrapper .sidebar h2{
  color: #ff6b6b; /* Couleur de titre */
  text-transform: uppercase;
  text-align: center;
  margin-bottom: 30px;
}

.wrapper .sidebar ul li{
  padding: 15px;
  border-bottom: 1px solid #e0e4e8; /* Bordure basse */
}    

.wrapper .sidebar ul li a{
  color: #717171; /* Couleur de texte */
  display: block;
}

.wrapper .sidebar ul li:hover{
  background-color: #f1e6e6; /* Couleur de survol */
}
    
.wrapper .sidebar ul li:hover a{
  color: #ff6b6b; /* Couleur de survol du texte */
}
 
.wrapper {
  background: #f9eded; /* Couleur de fond */
  color: #717171; /* Couleur de texte */
}

.wrapper {
  margin: 0;
  color: #717171;
  line-height: 25px;
}

.wrapper {
  margin-bottom: 20px;
}
.wrapper .sidebar ul li::first-letter {
  text-transform: uppercase; /* Met la première lettre en majuscule */
}
</style>
