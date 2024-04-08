<template>
   <div>
      <SideBar/>
<div class="container card">

<h2>Bienvenue sur Zaby</h2>

</div>
</div>
</template>
<script>
import SideBar from './SideBar.vue';

export default {
  name: 'WelcomeFile',
 components: {
   SideBar
  }
};
</script>
   <style>
.card{
    margin-left: 250px;
    margin: 0% 30% 30% 20%;
    text-align: center;
    /* background-image: url("~@/assets/welcome.jpg"); */
      background-size: cover; /* Ajuster la taille de l'image pour couvrir toute la zone */
  background-position: center; /* Centrer l'image */
  height: 100vh; /* Hauteur de l'élément égale à la hauteur de la fenêtre */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: white; /* Couleur du texte pour une meilleure visibilité */
  text-align: center;
   
 }
 </style>
