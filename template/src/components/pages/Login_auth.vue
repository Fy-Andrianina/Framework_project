<template>

<div class="panel-lite">
  <div class="thumbur">
    <div class="icon-lock"></div>
  </div>
  <h4>Login</h4>
  <form @submit.prevent="handleSave">
  <div class="form-group">
    <input class="form-control" required="required"    v-model="formData.login" placeholder="Username"/>
  </div>
  <div class="form-group">
    <input class="form-control" type="password" required="required"    v-model="formData.pwd" placeholder="Password"/>
  </div>
  <button class="floating-btn" type="submit" style="margin: -20px; "><i class="icon-arrow"></i></button>
</form>
</div>
</template>
<script>

import  axios from 'axios';

import   Swal from 'sweetalert2';
export default {

  data() {
    return {
      formData:{
        login: '',
      pwd: '',
      error: ''
      }

    };
  },
  methods: {
   async handleSave() {
               this.isSaving = true;
         const formData = new FormData();
        formData.append('login',this.formData.login);
		formData.append('pwd',this.formData.pwd);
		
         axios.post(`Zaby/login.do`, formData)
           .then(response => {
            if(response.data.Bad_request!= null){
              Swal.fire({
                 icon: 'error',
                 title: 'An Error Occured!',
                 showConfirmButton: false,
                 timer: 1500
             })
            }else{
              Swal.fire({
                 icon: 'success',
                 title: 'Project updated successfully!',
                 showConfirmButton: false,
                 timer: 1500
             })
             this.isSaving = false
            
             sessionStorage.setItem('role','admin');
            this.$router.push('/welcome');
             return response
            }

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
  }
};
</script>
<style scoped>
@import url(https://fonts.googleapis.com/css?family=Roboto:400,500,100);
*, *:after, *:before {
  box-sizing: border-box;
}

html {
  position: relative;
  background: #FF4081;
  font-family: 'Roboto', sans-serif;
}

.thumbur {
  width: 150px;
  height: 150px;
  position: relative;
  background-color: #efefef;
  filter: progid:DXImageTransform.Microsoft.gradient(gradientType=1, startColorstr='#FFEFEFEF', endColorstr='#FFE1E1E1');
  background-image: url('data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4gPHN2ZyB2ZXJzaW9uPSIxLjEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PGRlZnM+PGxpbmVhckdyYWRpZW50IGlkPSJncmFkIiBncmFkaWVudFVuaXRzPSJvYmplY3RCb3VuZGluZ0JveCIgeDE9IjAuMCIgeTE9IjAuNSIgeDI9IjEuMCIgeTI9IjAuNSI+PHN0b3Agb2Zmc2V0PSIwJSIgc3RvcC1jb2xvcj0iI2VmZWZlZiIvPjxzdG9wIG9mZnNldD0iNTAlIiBzdG9wLWNvbG9yPSIjZWZlZmVmIi8+PHN0b3Agb2Zmc2V0PSI1MCUiIHN0b3AtY29sb3I9IiNlMWUxZTEiLz48c3RvcCBvZmZzZXQ9IjEwMCUiIHN0b3AtY29sb3I9IiNlMWUxZTEiLz48L2xpbmVhckdyYWRpZW50PjwvZGVmcz48cmVjdCB4PSIwIiB5PSIwIiB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSJ1cmwoI2dyYWQpIiAvPjwvc3ZnPiA=');
  background-size: 100%;
  background-image: linear-gradient(to right, #efefef 0%, #efefef 50%, #e1e1e1 50%, #e1e1e1 100%);
  margin: auto;
  border-radius: 100%;
}
.thumbur:before {
  content: '';
  position: absolute;
  width: 6px;
  height: 12px;
  background-color: #efefef;
  left: 50%;
  bottom: 50px;
  z-index: 5;
  transform: translateX(-50%);
  border-bottom-left-radius: 2px;
  border-bottom-right-radius: 2px;
}

.icon-lock {
  position: relative;
  width: 80px;
  height: 60px;
  background: #FFA000;
  margin: auto;
  transform: translateY(60px);
  border-radius: 8px;
  box-shadow: 0 0 2px #F57C00 inset;
}
.icon-lock:after {
  content: '';
  position: absolute;
  width: 50px;
  height: 35px;
  border: 9px solid #F57C00;
  border-bottom: none;
  bottom: 100%;
  left: 50%;
  transform: translateX(-50%);
  border-top-left-radius: 50px;
  border-top-right-radius: 50px;
}
.icon-lock:before {
  content: '';
  position: absolute;
  width: 12px;
  height: 12px;
  background-color: #efefef;
  left: 50%;
  top: 20px;
  transform: translateX(-50%);
  border-radius: 100%;
}

.panel-lite {
  margin: 20px auto;
  max-width: 360px;
  background: #fff;
  padding: 45px 20px;
  border-radius: 4px;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
  position: relative;
  margin-top: 180px;  
}
.panel-lite h4 {
  font-weight: 400;
  font-size: 24px;
  text-align: center;
  color: #FF4081;
  margin: 15px auto;
}
.panel-lite a {
  display: inline-block;
  margin-top: 25px;
  text-decoration: none;
  color: #FF4081;
  font-size: 14px;
}

.form-group {
  position: relative;
  font-size: 15px;
  color: #666;
}
.form-group + .form-group {
  margin-top: 30px;
}
.form-group .form-label {
  position: absolute;
  z-index: 1;
  left: 0;
  top: 5px;
  transition: 0.3s;
}
.form-group .form-control {
  width: 100%;
  position: relative;
  z-index: 3;
  height: 35px;
  background: none;
  border: none;
  padding: 5px 0;
  transition: 0.3s;
  border-bottom: 1px solid #777;
}
.form-group .form-control:invalid {
  outline: none;
}
.form-group .form-control:focus, .form-group .form-control:valid {
  outline: none;
  color: #FF4081;
  box-shadow: 0 1px #FF4081;
  border-color: #FF4081;
}
.form-group .form-control:focus + .form-label, .form-group .form-control:valid + .form-label {
  font-size: 12px;
  transform: translateY(-15px);
}

.floating-btn {
  background: #FF4081;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  color: #fff;
  font-size: 32px;
  border: none;
  position: absolute;
  margin: auto;
  transition: 0.3s;
  box-shadow: 1px 0px 0px rgba(0, 0, 0, 0.3) inset;
  margin: auto;
  right: -30px;
  bottom: 90px;
  cursor: pointer;
}
.floating-btn:hover {
  box-shadow: 0 0 0 rgba(0, 0, 0, 0.3) inset, 0 3px 6px rgba(0, 0, 0, 0.16), 0 5px 11px rgba(0, 0, 0, 0.23);
}
.floating-btn:hover .icon-arrow {
  transform: rotate(45deg) scale(1.2);
}
.floating-btn:focus, .floating-btn:active {
  outline: none;
}

.icon-arrow {
  position: relative;
  width: 13px;
  height: 13px;
  border-right: 3px solid #fff;
  border-top: 3px solid #fff;
  display: block;
  transform: rotate(45deg);
  margin: auto;
  transition: 0.3s;
}
.icon-arrow:after {
  content: '';
  position: absolute;
  width: 18px;
  height: 3px;
  background: #fff;
  left: -5px;
  top: 5px;
  transform: rotate(-45deg);
}

</style>