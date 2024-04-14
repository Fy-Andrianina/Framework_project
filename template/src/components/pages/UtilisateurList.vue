<template>
<div>
    <SideBar/>
    <layout-div>
          <div class="conteneur">
              <div class="card">
                  <div class="card-header">
                      <router-link to="/UtilisateurCreate"
                          class="btn btn-outline-primary"
                          >Create 
                      </router-link>
                  </div>
                  <div class="card-body">
               
                      <table class="table table-bordered">
                          <thead>
                              <tr>
                                  <th>id</th>
		<th>login</th>
		<th>pwd</th>
		
                                  <th width="320px">Action</th>
                              </tr>
                          </thead>
                          <tbody>
                               
                              <tr v-for="project in projects" :key="project.id">
                                <td>{{project.id}}</td>
		<td>{{project.login}}</td>
		<td>{{project.pwd}}</td>
		
                                  <td>
                                  <router-link :to="`/UtilisateurEdit/${project.id}`" class="btn btn-outline-info mx-1">Edit</router-link>
		<router-link :to="`/UtilisateurShow/${project.id}`" class="btn btn-outline-info mx-1">Show</router-link>
		<router-link :to="`/UtilisateurCreate`" class="btn btn-outline-info mx-1">Create</router-link>
		
                                      <button 
                                          @click="handleDelete(project.id)"
                                          className="btn btn-outline-danger mx-1">
                                          Delete
                                      </button>
                                  </td>
                              </tr>
                                   
                          </tbody>
                      </table>
                  </div>
                            <nav aria-label="Page navigation">
    <ul class="pagination justify-content-center mt-4">
        <li class="page-item" v-for="pageNumber in totalPages" :key="pageNumber">
            <button class="page-link" @click="goToPage(pageNumber)">{{ pageNumber }}</button>
        </li>
    </ul>
</nav>
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
    name: 'UtilisateurList',
    components: {
      LayoutDiv,
      SideBar,
    },
    data() {
    return {
      projects:[],
      currentPage: 1,
      itemsPerPage: 4, // Nombre d'éléments par page
      totalPages: 0,
      };
    },
    created() {
      this.fetchProjectList();
    },
    methods: {
      fetchProjectList() {
       
         const formData = new FormData();
 if(sessionStorage.getItem('role') !=null){
formData.append('role',sessionStorage.getItem('role'));
 }
const offset = (this.currentPage - 1) * this.itemsPerPage;
axios.post(`Biblio/tocrudutilisateur.do?offset=${offset}`,formData)
          .then(response => {
                        if(Object.keys(response.data).length === 0 ){
              this.$router.push('/');
            }
              this.projects = response.data.o;
               this.totalPages = Math.ceil(response.data.pages / this.itemsPerPage);
                      for (let i = 0; i < this.projects.length; i++) {
            let obj = this.projects[i];
          for (let key in obj) {
              let value = obj[key];
              if (typeof value === 'object' && value !== null) {
                obj[key] = obj[key].label;
              }

    }
}
              return response
          })
          .catch(error => {
            return error
          });
      },
          goToPage(pageNumber) {
      console.log('goToPage called with pageNumber:', pageNumber);
      this.currentPage = pageNumber;
      this.fetchProjectList();
    },
      handleDelete(id){
                  Swal.fire({
              title: 'Are you sure?',
              text: "You won't be able to revert this!",
              icon: 'warning',
              showCancelButton: true,
              confirmButtonColor: '#3085d6',
              cancelButtonColor: '#d33',
              confirmButtonText: 'Yes, delete it!'
            }).then((result) => {
              if (result.isConfirmed) {
            const formData = new FormData();
            if(sessionStorage.getItem('role') !=null){
formData.append('role',sessionStorage.getItem('role'));
 }
                  axios.post(`/Biblio/deleteutilisateur.do?id=${id}`,formData)
                  .then( response => {
                                if(Object.keys(response.data).length === 0 ){
              this.$router.push('/');
            }
                      Swal.fire({
                          icon: 'success',
                          title: 'Project deleted successfully!',
                          showConfirmButton: false,
                          timer: 1500
                      })
                      this.fetchProjectList();
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
                  });
              }
            })
      }
    },
  };
  </script>
      <style>
.card{
    DISPLAY: block;
    HEIGHT: FIT-CONTENT;
    width: 100vh;
 }
 p{
    color:black;
 }

 table th::first-letter {
  text-transform: uppercase; /* Met la première lettre en majuscule */
}
 table td::first-letter {
  text-transform: uppercase; /* Met la première lettre en majuscule */
}
 .card-header{
  background-color: #f9eded;
 }
 table {
  border-collapse: collapse;
  text-align: start;
}
.conteneur{
        display: grid;
    justify-items: stretch;
}

 </style>