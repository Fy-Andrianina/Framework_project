package zaby.controllers;
import eriq.flamework.annotations.Controller;
import eriq.flamework.annotations.Singleton;
import eriq.flamework.annotations.URLMapping;
import eriq.flamework.annotations.Authenticate;
import eriq.flamework.model.ModelRedirect;
import eriq.flamework.model.ModelView;
import eriq.flamework.model.ModelRest;
import eriq.flamework.model.ModelMethod;
import java.lang.reflect.Method;
import eriq.flamework.servlet.ServletEntity;
import eriq.flamework.servlet.SessionEntity;
import veda.godao.DAO;
import veda.godao.utils.DAOConnexion;
import java.sql.Connection;
import traite.Pagination;
import zaby.entities.Utilisateur;

@Controller
@Singleton

public class UtilisateurController {
    private DAO dao=new DAO("biblio", "localhost", "5432", "postgres", "1864", false, 2);
private Pagination service=new Pagination("biblio", "localhost", "5432", "postgres", "1864", false, 2);

    
    @URLMapping("insertutilisateur.do")
public ModelRest insert(ServletEntity entity) throws Exception{
    Utilisateur o=new Utilisateur();
    o.setLogin(entity.getData().get("login"));o.setPwd(entity.getData().get("pwd"));
    ModelRest model=new ModelRest();
    if(entity.getData().get("role")!=null){

        try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "1864", false, true)){
            dao.insertWithoutPrimaryKey(connex, o);
            connex.commit();
            model.addItem("ok","200");
        }
    }
    return model;
}
@URLMapping("tocrudutilisateur.do")
public ModelRest crudpage(ServletEntity entity) throws Exception{
    ModelRest model=new ModelRest();

    if(entity.getData().get("role")!=null){
                int offset = 0;
        if(entity.getData().get("offset") == null){
            offset = 0;
        }else{
            offset = Integer.valueOf(entity.getData().get("offset"));
        }
        try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "1864", false, true)){
            
        Utilisateur[] o = service.select(connex, Utilisateur.class, offset);
        int pages = service.numRows(connex, Utilisateur.class);
        model.addItem("pages", pages);

            model.addItem("viewpage", "utilisateur.jsp");
            model.addItem("title", "Utilisateur");
            model.addItem("o", o);
            
            
        }
    }
    return model;

}
@URLMapping("updateutilisateur.do")
public ModelRest update(ServletEntity entity) throws Exception{
    ModelRest model= new ModelRest();
    Utilisateur o=new Utilisateur();
    o.setLogin(entity.getData().get("login"));o.setPwd(entity.getData().get("pwd"));
    Utilisateur where=new Utilisateur();
    where.setId(Integer.parseInt(entity.getData().get("id")));
    if(entity.getData().get("role")!=null){
        try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "1864", false, true)){
            dao.update(connex, o, where);
            connex.commit();
            model.addItem("ok","200");
            
        }
    }
    return model;
}
@URLMapping("deleteutilisateur.do")
public ModelRest delete(ServletEntity entity) throws Exception{
    Utilisateur where=new Utilisateur();
    where.setId(Integer.parseInt(entity.getData().get("id")));
    ModelRest model=new ModelRest();
    if(entity.getData().get("role")!=null){
        try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "1864", false, true)){
            dao.delete(connex, where);
            connex.commit();
        
        }
        model.addItem("ok","200");
        
    }
    return model;
    
}
@URLMapping("readByIdutilisateur.do")
public ModelRest readById(ServletEntity entity) throws Exception{
   Utilisateur where=new Utilisateur();
    where.setId(Integer.parseInt(entity.getData().get("id")));
   

    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "1864", false, true)){
        ModelRest model=new ModelRest();
        Utilisateur[] o=dao.select(connex, Utilisateur.class,where);
        model.addItem("o", o);
        
        return model;
    }
}
@URLMapping("login.do")
public ModelRest login(ServletEntity entity) throws Exception{
    Utilisateur o=new Utilisateur();

    o.setLogin(entity.getData().get("login"));o.setPwd(entity.getData().get("pwd"));
    ModelRest model = new ModelRest();
   
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "1864", false, true)){
          Utilisateur[] obj = dao.select(connex, Utilisateur.class, o);
           model.addItem("o",obj);
           if(obj.length !=0){
                model.addItem("role","admin");
                model.addItem("ok","200");
           }
           else{
                model.addItem("Bad_request","500");
           }

    }
    return model;

}
@URLMapping("logout.do")
public ModelMethod signOut(ServletEntity entity) throws Exception{
    
        ModelMethod model=new ModelMethod();
        model.setToInvalidate(true);// qui suprime les sessions
       
        return model;
    
}

}

