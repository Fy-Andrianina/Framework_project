<template>
<div>
    <SideBar/>
    <layout-div>
         <div class="card">
             <div class="card-header">
                 <router-link 
                     class="btn btn-outline-info float-right"
                     to="/">View All Projects
                 </router-link>
             </div>
             <div class="card-body">
                <b className="text-muted"><span class="first-letter-uppercase">id:</span></b>
<p class="first-letter-uppercase">{{project.id}}</p>
		<b className="text-muted"><span class="first-letter-uppercase">login:</span></b>
<p class="first-letter-uppercase">{{project.login}}</p>
		<b className="text-muted"><span class="first-letter-uppercase">pwd:</span></b>
<p class="first-letter-uppercase">{{project.pwd}}</p>
		
             </div>
         </div>
    </layout-div>
</div>
 </template>
  
<script>
 
import  axios from 'axios';

import   LayoutDiv from '../LayoutDiv.vue';

import   Swal from 'sweetalert2';

import   SideBar from './SideBar';

  
 export default {
   name: 'UtilisateurShow',
   components: {
     LayoutDiv,
     SideBar,
   },
   data() {
     return {
       project: {
        id: '',
		login: '',
		pwd: '',
		
       },
       isSaving:false,
     };
   },
   created() {
         // prends la valeur de l'id specifie dans l'url /api/:id
     const id = this.$route.params.id;
 const formData = new FormData();
 if(sessionStorage.getItem('role') !=null){
formData.append('role',sessionStorage.getItem('role'));
 }

     axios.post(`/Biblio/readByIdutilisateur.do?id=${id}`,formData)
     .then(response => {
            if(Object.keys(response.data).length === 0 ){
              this.$router.push('/');
            }
         let projectInfo = response.data.o[0]
           let obj = projectInfo;for (let key in obj) {let value = obj[key];if (typeof value === 'object' && value !== null) {obj[key] = obj[key].label;} }
         this.project.id = projectInfo.id
		this.project.login = projectInfo.login
		this.project.pwd = projectInfo.pwd
		
     
          
         return response
     })
     .catch(error => {
         Swal.fire({
             icon: 'error',
             title: 'An Error Occured!',
             showConfirmButton: false,
             timer: 1500
         })
         return error
     })
   },
   methods: {
      
   },
 };
 </script>
   <style>
.card{
    DISPLAY: block;
    HEIGHT: FIT-CONTENT;
    color: black;
 }
 </style>