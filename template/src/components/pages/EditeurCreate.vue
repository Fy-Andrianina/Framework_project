<template>
<div>
    <SideBar/>
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
</div>

 </template>
 <script>

import  axios from 'axios';

import   Swal from 'sweetalert2';

import   SideBar from './SideBar';

 export default {
   name: 'EditeurCreate',
   components: {
     SideBar,
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
     
 const formData = new FormData();
 if(sessionStorage.getItem('role') !=null){
formData.append('role',sessionStorage.getItem('role'));
 }

     axios.post(`/Zaby/tocrudediteur.do`,formData)
     .then(response => {
            if(Object.keys(response.data).length === 0 ){
              this.$router.push('/');
            }
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
          if(sessionStorage.getItem('role') !=null){
formData.append('role',sessionStorage.getItem('role'));
 }
        formData.append('id',this.project.id);
		formData.append('label',this.project.label);
		
         axios.post(`Zaby/insertediteur.do`, formData)
           .then(response => {
                        if(Object.keys(response.data).length === 0 ){
              this.$router.push('/');
            }
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
    <style scoped>
.card{
    DISPLAY: block;
    left: 500px;
    top:100px;
    HEIGHT: FIT-CONTENT;
    width: 500px;
 }
 .card{
  margin-top: 20px;
    font-family: Courier, monospace;
 }
 *{
      font-size: 11px;
 }
 </style>