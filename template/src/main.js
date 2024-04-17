import { createApp } from 'vue';
import App from './App.vue';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css';
import { createRouter, createWebHistory } from 'vue-router';
import Login from './components/pages/Login_auth';
import AuteurEdit from './components/pages/AuteurEdit';
		import AuteurShow from './components/pages/AuteurShow';
		import LivreEdit from './components/pages/LivreEdit';
		import LivreShow from './components/pages/LivreShow';
		import EditeurEdit from './components/pages/EditeurEdit';
		import EditeurShow from './components/pages/EditeurShow';
		import UtilisateurEdit from './components/pages/UtilisateurEdit';
		import UtilisateurShow from './components/pages/UtilisateurShow';
		import WelcomeFile from './components/pages/WelcomeFile';
		import AuteurCreate from './components/pages/AuteurCreate';
		import AuteurList from './components/pages/AuteurList';
		import LivreCreate from './components/pages/LivreCreate';
		import LivreList from './components/pages/LivreList';
		import EditeurCreate from './components/pages/EditeurCreate';
		import EditeurList from './components/pages/EditeurList';
		import UtilisateurCreate from './components/pages/UtilisateurCreate';
		import UtilisateurList from './components/pages/UtilisateurList';
		
  
axios.defaults.baseURL = process.env.VUE_APP_API_URL
axios.interceptors.request.use(function (config) {
  config.headers['X-Binarybox-Api-Key'] = process.env.VUE_APP_API_KEY;
  return config;
});
  
const router = createRouter({
  history: createWebHistory(),
  routes: [
 
   { path: '/', component: Login   },
   { path: '/welcome', component: WelcomeFile,meta:{requiresAuth:true} },
    { path: '/AuteurEdit/:id', component: AuteurEdit ,meta:{requiresAuth:true}  },
		{ path: '/AuteurShow/:id', component: AuteurShow ,meta:{requiresAuth:true}  },
		{ path: '/LivreEdit/:id', component: LivreEdit ,meta:{requiresAuth:true}  },
		{ path: '/LivreShow/:id', component: LivreShow ,meta:{requiresAuth:true}  },
		{ path: '/EditeurEdit/:id', component: EditeurEdit ,meta:{requiresAuth:true}  },
		{ path: '/EditeurShow/:id', component: EditeurShow ,meta:{requiresAuth:true}  },
		{ path: '/UtilisateurEdit/:id', component: UtilisateurEdit ,meta:{requiresAuth:true}  },
		{ path: '/UtilisateurShow/:id', component: UtilisateurShow ,meta:{requiresAuth:true}  },
		{ path: '/WelcomeFile', component: WelcomeFile ,meta:{requiresAuth:true}  },
		{ path: '/AuteurCreate', component: AuteurCreate ,meta:{requiresAuth:true}  },
		{ path: '/AuteurList', component: AuteurList ,meta:{requiresAuth:true}  },
		{ path: '/LivreCreate', component: LivreCreate ,meta:{requiresAuth:true}  },
		{ path: '/LivreList', component: LivreList ,meta:{requiresAuth:true}  },
		{ path: '/EditeurCreate', component: EditeurCreate ,meta:{requiresAuth:true}  },
		{ path: '/EditeurList', component: EditeurList ,meta:{requiresAuth:true}  },
		{ path: '/UtilisateurCreate', component: UtilisateurCreate ,meta:{requiresAuth:true}  },
		{ path: '/UtilisateurList', component: UtilisateurList ,meta:{requiresAuth:true}  },
		
  ],
});
  


router.beforeEach((to, from, next) => {
    const isLoggedIn = sessionStorage.getItem('role');

    if (to.path === '/' && isLoggedIn) {
        next('/welcome');
    } else if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!isLoggedIn) {
            next('/');
        } else {
            next();
        }
    } else {
        next();
    }
});

createApp(App).use(router).mount('#app');