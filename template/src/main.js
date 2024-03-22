import { createApp } from 'vue';
import App from './App.vue';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css';
import { createRouter, createWebHistory } from 'vue-router';
import AuteurEdit from './components/pages/AuteurEdit';
		import AuteurShow from './components/pages/AuteurShow';
		import LivreEdit from './components/pages/LivreEdit';
		import LivreShow from './components/pages/LivreShow';
		import EditeurEdit from './components/pages/EditeurEdit';
		import EditeurShow from './components/pages/EditeurShow';
		import WelcomeFile from './components/pages/WelcomeFile';
		import AuteurCreate from './components/pages/AuteurCreate';
		import AuteurList from './components/pages/AuteurList';
		import LivreCreate from './components/pages/LivreCreate';
		import LivreList from './components/pages/LivreList';
		import EditeurCreate from './components/pages/EditeurCreate';
		import EditeurList from './components/pages/EditeurList';
		
  
axios.defaults.baseURL = process.env.VUE_APP_API_URL
axios.interceptors.request.use(function (config) {
  config.headers['X-Binarybox-Api-Key'] = process.env.VUE_APP_API_KEY;
  return config;
});
  
const router = createRouter({
  history: createWebHistory(),
  routes: [
   { path: '/', component: WelcomeFile  },
    { path: '/AuteurEdit/:id', component: AuteurEdit  },
		{ path: '/AuteurShow/:id', component: AuteurShow  },
		{ path: '/LivreEdit/:id', component: LivreEdit  },
		{ path: '/LivreShow/:id', component: LivreShow  },
		{ path: '/EditeurEdit/:id', component: EditeurEdit  },
		{ path: '/EditeurShow/:id', component: EditeurShow  },
		{ path: '/WelcomeFile', component: WelcomeFile  },
		{ path: '/AuteurCreate', component: AuteurCreate  },
		{ path: '/AuteurList', component: AuteurList  },
		{ path: '/LivreCreate', component: LivreCreate  },
		{ path: '/LivreList', component: LivreList  },
		{ path: '/EditeurCreate', component: EditeurCreate  },
		{ path: '/EditeurList', component: EditeurList  },
		
  ],
});
  
createApp(App).use(router).mount('#app');