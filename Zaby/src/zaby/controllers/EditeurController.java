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
import zaby.entities.Editeur;

@Controller
@Singleton

public class EditeurController {
    private DAO dao=new DAO("biblio", "localhost", "5432", "postgres", "1864", false, 2);
private Pagination service=new Pagination("biblio", "localhost", "5432", "postgres", "1864", false, 2);

    
    @URLMapping("insertediteur.do")
public ModelRest insert(ServletEntity entity) throws Exception{
    Editeur o=new Editeur();
    o.setLabel(entity.getData().get("label"));
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
@URLMapping("tocrudediteur.do")
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
            
        Editeur[] o = service.select(connex, Editeur.class, offset);
        int pages = service.numRows(connex, Editeur.class);
        model.addItem("pages", pages);

            model.addItem("viewpage", "editeur.jsp");
            model.addItem("title", "Editeur");
            model.addItem("o", o);
            
            
        }
    }
    return model;

}
@URLMapping("updateediteur.do")
public ModelRest update(ServletEntity entity) throws Exception{
    ModelRest model= new ModelRest();
    Editeur o=new Editeur();
    o.setLabel(entity.getData().get("label"));
    Editeur where=new Editeur();
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
@URLMapping("deleteediteur.do")
public ModelRest delete(ServletEntity entity) throws Exception{
    Editeur where=new Editeur();
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
@URLMapping("readByIdediteur.do")
public ModelRest readById(ServletEntity entity) throws Exception{
   Editeur where=new Editeur();
    where.setId(Integer.parseInt(entity.getData().get("id")));
   

    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "1864", false, true)){
        ModelRest model=new ModelRest();
        Editeur[] o=dao.select(connex, Editeur.class,where);
        model.addItem("o", o);
        
        return model;
    }
}

}

