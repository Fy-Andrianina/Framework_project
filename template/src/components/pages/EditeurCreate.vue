<template>
    <layout-div>
         <h2 class="text-center mt-5 mb-3">Create Project</h2>
         <div class="card">
             <div class="card-header">
                 <router-link 
                     class="btn btn-outline-info float-right"
                     to="/">View All Projects
                 </router-link>
             </div>
             <div class="card-body">
                <form>
                    <div class="form-group">
    <label htmlFor="label">label</label>
    <input 
        v-model="project.label"
        type="String"
        class="form-control"
        id="label"
        name="label"/>
</div>
		
                     <button 
                         @click="handleSave()"
                         :disabled="isSaving"
                         type="button"
                         class="btn btn-outline-primary mt-3">
                         Save Project
                     </button>
                </form>
             </div>
         </div>
    </layout-div>
 </template>
 <script>

import  axios from 'axios';

import   LayoutDiv from '../LayoutDiv.vue';

import   Swal from 'sweetalert2';

 export default {
   name: 'EditeurCreate',
   components: {
     LayoutDiv,
   },
   data() {
     return {
            formData:{
        id: '',
		label: '',
		
      },
       project: {
         id: '',
		label: '',
		
       },
       isSaving:false,
     };
   },
      created() {
        // prends la valeur de l'id specifie dans l'url /api/:id
     
     
     axios.get(`/My_Test/tocrudediteur.do`)
     .then(response => {
         let projectInfo = response.data.o[0]
         
         this.project.id = projectInfo.id
		this.project.label = projectInfo.label
		
     
          
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
    
     handleSave() {
               this.isSaving = true;
         const formData = new FormData();
        formData.append('id',this.project.id);
		formData.append('label',this.project.label);
		
         axios.post(`My_Test/insertediteur.do`, formData)
           .then(response => {
             Swal.fire({
                 icon: 'success',
                 title: 'Project updated successfully!',
                 showConfirmButton: false,
                 timer: 1500
             })
             this.isSaving = false
            this.project.id = ""
		this.project.label = ""
		
            this.$router.push('/editeurList');
             return response
           })
           .catch(error => {
             this.isSaving = false
             Swal.fire({
                 icon: 'error',
                 title: 'An Error Occured!',
                 showConfirmButton: false,
                 timer: 1500
             })
             return error
           });
     },
   },
 };
 </script>
    <style>
.card{
  margin-left: 250px;
 }
 </style>