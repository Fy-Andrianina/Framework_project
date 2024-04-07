package sunday.controllers;
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
import sunday.entities.Editeur;

@Controller
@Singleton

public class EditeurController {
    private DAO dao=new DAO("biblio", "localhost", "5432", "postgres", "postgres", false, 2);

    
    @URLMapping("insertediteur.do")
@Authenticate("admin")
public void insert(ServletEntity entity) throws Exception{
    Editeur o=new Editeur();
    o.setLabel(entity.getData().get("label"));
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        dao.insertWithoutPrimaryKey(connex, o);
        connex.commit();
       
    }
}
@URLMapping("tocrudediteur.do")
@Authenticate("admin")
public ModelRest crudpage(ServletEntity entity) throws Exception{
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        ModelRest model=new ModelRest();
        
        Editeur[] o=dao.select(connex, Editeur.class);
        model.addItem("viewpage", "editeur.jsp");
        model.addItem("title", "Editeur");
        model.addItem("o", o);
        
        return model;
    }
}
@URLMapping("updateediteur.do")
@Authenticate("admin")
public void update(ServletEntity entity) throws Exception{
    Editeur o=new Editeur();
    o.setLabel(entity.getData().get("label"));
    Editeur where=new Editeur();
    where.setId(Integer.parseInt(entity.getData().get("id")));
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        dao.update(connex, o, where);
        connex.commit();
        
    }
}
@URLMapping("deleteediteur.do")
@Authenticate("admin")
public void delete(ServletEntity entity) throws Exception{
    Editeur where=new Editeur();
    where.setId(Integer.parseInt(entity.getData().get("id")));
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        dao.delete(connex, where);
        connex.commit();
       
    }
}
@URLMapping("readByIdediteur.do")
@Authenticate("admin")
public ModelRest readById(ServletEntity entity) throws Exception{
   Editeur where=new Editeur();
    where.setId(Integer.parseInt(entity.getData().get("id")));
   

    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        ModelRest model=new ModelRest();
        Editeur[] o=dao.select(connex, Editeur.class,where);
        model.addItem("o", o);
        
        return model;
    }
}

}

