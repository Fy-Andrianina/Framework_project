<template>
    <layout-div>
         <h2 class="text-center mt-5 mb-3">Show Project</h2>
         <div class="card">
             <div class="card-header">
                 <router-link 
                     class="btn btn-outline-info float-right"
                     to="/">View All Projects
                 </router-link>
             </div>
             <div class="card-body">
                <b className="text-muted">id:</b>
<p>{{project.id}}</p>
		<b className="text-muted">label:</b>
<p>{{project.label}}</p>
		<b className="text-muted">auteur:</b>
<p>{{project.auteur}}</p>
		<b className="text-muted">editeur:</b>
<p>{{project.editeur}}</p>
		<b className="text-muted">datePublication:</b>
<p>{{project.datePublication}}</p>
		
             </div>
         </div>
    </layout-div>
 </template>
  
<script>
 
import  axios from 'axios';

import   LayoutDiv from '../LayoutDiv.vue';

import   Swal from 'sweetalert2';

  
 export default {
   name: 'LivreShow',
   components: {
     LayoutDiv,
   },
   data() {
     return {
       project: {
        id: '',
		label: '',
		auteur: '',
		editeur: '',
		datePublication: '',
		
       },
       isSaving:false,
     };
   },
   created() {
         // prends la valeur de l'id specifie dans l'url /api/:id
     const id = this.$route.params.id;
     
     axios.get(`/My_Test/readByIdlivre.do?id=${id}`)
     .then(response => {
         let projectInfo = response.data.o[0]
           let obj = projectInfo;for (let key in obj) {let value = obj[key];if (typeof value === 'object' && value !== null) {obj[key] = obj[key].label;} }
         this.project.id = projectInfo.id
		this.project.label = projectInfo.label
		this.project.auteurs = response.data.auteurs
		this.project.auteur = projectInfo.auteur
		this.project.editeurs = response.data.editeurs
		this.project.editeur = projectInfo.editeur
		this.project.datePublication = projectInfo.datePublication
		
     
          
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
  margin-left: 250px;
 }
 </style>