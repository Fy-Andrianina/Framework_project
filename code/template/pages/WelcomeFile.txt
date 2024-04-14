<template>
  <div>
    <SideBar/>
    <layout-div>
      <div class="conteneur">
        <div class="card">
          <div class="card-body">
            <p class="bounce">FOII PROJECT MANAGER</p>
            <img src="@/assets/bg.png" alt="Welcome Image" class="welcome-image">
          </div>
        </div>
      </div>
    </layout-div>
  </div>
</template>

<script>
import SideBar from './SideBar.vue';
import LayoutDiv from '../LayoutDiv.vue';

export default {
  name: 'WelcomeFile',
  components: {
    SideBar, 
    LayoutDiv,
  }
};
</script>

<style>
.card {
  margin-left: 250px;
  margin: 0% 30% 30% 20%;
  text-align: center;
  color: white; /* Text color */
  position: relative; /* Positioning for absolute elements */
  margin-top: 50px;
}

.card-header {
  margin-bottom: 20px; /* Spacing */
}

.card-header h2 {
  color: black; /* Header text color */
}

.card-body {
  position: relative; /* Positioning for absolute elements */
}

.bounce {
  color: pink; /* Text color */
  animation: bounce 0.5s ease infinite; /* Bouncing animation */
  font-size: 60px;
}

.welcome-image {
  width: 200px; /* Image width */
  margin-top: 20px; /* Spacing */
}



@keyframes bounce {
  0% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-20px);
  }
  100% {
    transform: translateY(0);
  }
}

.welcome-image{
  width: 500px;
}
.conteneur {
  display: grid;
  justify-items: stretch;
}
</style>
