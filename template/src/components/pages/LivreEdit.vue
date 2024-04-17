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
		<div class="form-group">
    <label htmlFor="auteur">auteur</label>
<select name="auteur" class="form-control" v-model="project.auteur.id">
 <option :value="project.auteur.id">{{ project.auteur.label }}</option>
   <option v-for="auteur in project.auteurs" :key="auteur.id" :value="auteur.id">{{ auteur.label }}</option>
</select>
</div>
		<div class="form-group">
    <label htmlFor="editeur">editeur</label>
<select name="editeur" class="form-control" v-model="project.editeur.id">
 <option :value="project.editeur.id">{{ project.editeur.label }}</option>
   <option v-for="editeur in project.editeurs" :key="editeur.id" :value="editeur.id">{{ editeur.label }}</option>
</select>
</div>
		<div class="form-group">
    <label htmlFor="datePublication">datePublication</label>
    <input 
        v-model="project.datePublication"
        type="date"
        class="form-control"
        id="datePublication"
        name="datePublication"/>
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
   name: 'LivreEdit',
   components: {
     SideBar,
   },
   data() {
    return {
    formData:{
        id: '',
		label: '',
		auteur: '',
		editeur: '',
		datePublication: '',
		
      },
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
 const formData = new FormData();
 if(sessionStorage.getItem('role') !=null){
formData.append('role',sessionStorage.getItem('role'));
 }

     axios.post(`/Zaby/readByIdlivre.do?id=${id}`,formData)
     .then(response => {
            if(Object.keys(response.data).length === 0 ){
              this.$router.push('/');
            }
         let projectInfo = response.data.o[0]
         
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
     handleSave() {
             this.isSaving = true;
         const formData = new FormData();
          if(sessionStorage.getItem('role') !=null){
formData.append('role',sessionStorage.getItem('role'));
 }
        formData.append('id',this.project.id);
		formData.append('label',this.project.label);
		formData.append('auteur',this.project.auteur.id);
		formData.append('editeur',this.project.editeur.id);
		formData.append('datePublication',this.project.datePublication);
		
         axios.post(`Zaby/updatelivre.do?id=${this.project.id}`, formData)
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
		this.project.auteurs = ""
		this.project.auteur = ""
		this.project.editeurs = ""
		this.project.editeur = ""
		this.project.datePublication = ""
		
            this.$router.push('/livreList');
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