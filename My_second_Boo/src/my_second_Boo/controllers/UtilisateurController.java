package my_second_Boo.controllers;
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
import my_second_Boo.entities.Utilisateur;

@Controller
@Singleton

public class UtilisateurController {
    private DAO dao=new DAO("biblio", "localhost", "5432", "postgres", "postgres", false, 2);

    
    @URLMapping("insertutilisateur.do")
public void insert(ServletEntity entity) throws Exception{
    Utilisateur o=new Utilisateur();
    o.setLogin(entity.getData().get("login"));o.setPwd(entity.getData().get("pwd"));
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        dao.insertWithoutPrimaryKey(connex, o);
        connex.commit();
       
    }
}
@URLMapping("tocrudutilisateur.do")
public ModelRest crudpage(ServletEntity entity) throws Exception{
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        ModelRest model=new ModelRest();
        
        Utilisateur[] o=dao.select(connex, Utilisateur.class);
        model.addItem("viewpage", "utilisateur.jsp");
        model.addItem("title", "Utilisateur");
        model.addItem("o", o);
        
        return model;
    }
}
@URLMapping("updateutilisateur.do")
public void update(ServletEntity entity) throws Exception{
    Utilisateur o=new Utilisateur();
    o.setLogin(entity.getData().get("login"));o.setPwd(entity.getData().get("pwd"));
    Utilisateur where=new Utilisateur();
    where.setId(Integer.parseInt(entity.getData().get("id")));
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        dao.update(connex, o, where);
        connex.commit();
        
    }
}
@URLMapping("deleteutilisateur.do")
public void delete(ServletEntity entity) throws Exception{
    Utilisateur where=new Utilisateur();
    where.setId(Integer.parseInt(entity.getData().get("id")));
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        dao.delete(connex, where);
        connex.commit();
       
    }
}
@URLMapping("readByIdutilisateur.do")
public ModelRest readById(ServletEntity entity) throws Exception{
   Utilisateur where=new Utilisateur();
    where.setId(Integer.parseInt(entity.getData().get("id")));
   

    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        ModelRest model=new ModelRest();
        Utilisateur[] o=dao.select(connex, Utilisateur.class,where);
        model.addItem("o", o);
        
        return model;
    }
}
@URLMapping("login.do")
public ModelMethod login(ServletEntity entity) throws Exception{
    Utilisateur o=new Utilisateur();

    o.setLogin(entity.getData().get("login"));o.setPwd(entity.getData().get("pwd"));
   
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        ModelMethod model = new ModelMethod();
            Utilisateur[] obj = dao.select(connex, Utilisateur.class, o);
            if (obj == null) {
                throw new Exception("Verifier votre login ou mot de passe");
            }
            SessionEntity session = new SessionEntity();
            session.getData().put("role", "admin");
            session.getData().put("o", obj);

            Class<?> modelClass = model.getClass();

            // Récupérer la méthode setSessionEntity
            Method setSessionEntityMethod = modelClass.getDeclaredMethod("setSessionEntity", SessionEntity.class);

            setSessionEntityMethod.setAccessible(true);

            setSessionEntityMethod.invoke(model, session);
            
       
        return model;
    }
}
@URLMapping("logout.do")
public ModelMethod signOut(ServletEntity entity) throws Exception{
    
        ModelMethod model=new ModelMethod();
        model.setToInvalidate(true);// qui suprime les sessions
       
        return model;
    
}

}

