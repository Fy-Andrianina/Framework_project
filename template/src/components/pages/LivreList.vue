<template>
<div>
    <SideBar/>
          <div class="container">
              <div class="card">
                  <div class="card-header">
                      <router-link to="/LivreCreate"
                          class="btn btn-warning "
                          >Create 
                      </router-link>
                  </div>
                  <div class="card-body">
               
                      <table class="table table-bordered mb-0">
                          <thead>
                              <tr>
                                  <th>id</th>
		<th>label</th>
		<th>auteur</th>
		<th>editeur</th>
		<th>datePublication</th>
		
                                  <th width="240px">Action</th>
                              </tr>
                          </thead>
                          <tbody>
                               
                              <tr v-for="project in projects" :key="project.id">
                                <td>{{project.id}}</td>
		<td>{{project.label}}</td>
		<td>{{project.auteur}}</td>
		<td>{{project.editeur}}</td>
		<td>{{project.datePublication}}</td>
		
                                  <td>
                                  <router-link :to="`/LivreEdit/${project.id}`" class="btn btn-success mx-1">Edit</router-link>
		<router-link :to="`/LivreShow/${project.id}`" class="btn btn-success mx-1">Show</router-link>
		
                                      <button 
                                          @click="handleDelete(project.id)"
                                          className="btn btn-danger mx-1">
                                          Delete
                                      </button>
                                  </td>
                              </tr>
                                   
                          </tbody>
                      </table>
                  </div>
                  <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center mt-1">
                        <li class="page-item" v-for="pageNumber in totalPages" :key="pageNumber">
                            <button class="page-link" @click="goToPage(pageNumber)">{{ pageNumber }}</button>
                        </li>
                    </ul>
                </nav>
              </div>
        </div>
</div>
</template>
   <script>

import  axios from 'axios';

import   Swal from 'sweetalert2';

import   SideBar from './SideBar';

   
  export default {
    name: 'LivreList',
    components: {
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
axios.post(`Zaby/tocrudlivre.do?offset=${offset}`,formData)
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
                  axios.post(`/Zaby/deletelivre.do?id=${id}`,formData)
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
      <style scoped>
.card{
  margin-top: 40px;
    font-family: Courier, monospace;
 }
 .container{
  margin-left: 200px;
 }

  *{
      font-size: 11px;
 }
 .btn{
    color:#fff;
 }
 </style>






