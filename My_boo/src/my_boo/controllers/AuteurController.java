package my_boo.controllers;
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
import my_boo.entities.Auteur;

@Controller
@Singleton

public class AuteurController {
    private DAO dao=new DAO("biblio", "localhost", "5432", "postgres", "postgres", false, 2);

    
    @URLMapping("insertauteur.do")
null
public void insert(ServletEntity entity) throws Exception{
    Auteur o=new Auteur();
    o.setLabel(entity.getData().get("label"));
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        dao.insertWithoutPrimaryKey(connex, o);
        connex.commit();
       
    }
}
@URLMapping("tocrudauteur.do")
null
public ModelRest crudpage(ServletEntity entity) throws Exception{
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        ModelRest model=new ModelRest();
        
        Auteur[] o=dao.select(connex, Auteur.class);
        model.addItem("viewpage", "auteur.jsp");
        model.addItem("title", "Auteur");
        model.addItem("o", o);
        
        return model;
    }
}
@URLMapping("updateauteur.do")
null
public void update(ServletEntity entity) throws Exception{
    Auteur o=new Auteur();
    o.setLabel(entity.getData().get("label"));
    Auteur where=new Auteur();
    where.setId(Integer.parseInt(entity.getData().get("id")));
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        dao.update(connex, o, where);
        connex.commit();
        
    }
}
@URLMapping("deleteauteur.do")
null
public void delete(ServletEntity entity) throws Exception{
    Auteur where=new Auteur();
    where.setId(Integer.parseInt(entity.getData().get("id")));
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        dao.delete(connex, where);
        connex.commit();
       
    }
}
@URLMapping("readByIdauteur.do")
null
public ModelRest readById(ServletEntity entity) throws Exception{
   Auteur where=new Auteur();
    where.setId(Integer.parseInt(entity.getData().get("id")));
   

    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        ModelRest model=new ModelRest();
        Auteur[] o=dao.select(connex, Auteur.class,where);
        model.addItem("o", o);
        
        return model;
    }
}

}

