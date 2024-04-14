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
                 <form>
                   <div class="form-group">
    <label htmlFor="login">login</label>
    <input 
        v-model="project.login"
        type="String"
        class="form-control"
        id="login"
        name="login"/>
</div>
		<div class="form-group">
    <label htmlFor="pwd">pwd</label>
    <input 
        v-model="project.pwd"
        type="String"
        class="form-control"
        id="pwd"
        name="pwd"/>
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
</div>
 </template>
<script>

import  axios from 'axios';

import   LayoutDiv from '../LayoutDiv.vue';

import   Swal from 'sweetalert2';

import   SideBar from './SideBar';
 
 export default {
   name: 'UtilisateurEdit',
   components: {
     LayoutDiv,
     SideBar,
   },
   data() {
    return {
    formData:{
        id: '',
		login: '',
		pwd: '',
		
      },
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
     handleSave() {
             this.isSaving = true;
         const formData = new FormData();
          if(sessionStorage.getItem('role') !=null){
formData.append('role',sessionStorage.getItem('role'));
 }
        formData.append('id',this.project.id);
		formData.append('login',this.project.login);
		formData.append('pwd',this.project.pwd);
		
         axios.post(`Biblio/updateutilisateur.do?id=${this.project.id}`, formData)
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
		this.project.login = ""
		this.project.pwd = ""
		
            this.$router.push('/utilisateurList');
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
    DISPLAY: block;
    HEIGHT: FIT-CONTENT;
 }
 </style>