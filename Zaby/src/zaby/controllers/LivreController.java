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
import zaby.entities.Livre;

@Controller
@Singleton

public class LivreController {
    private DAO dao=new DAO("biblio", "localhost", "5432", "postgres", "1864", false, 2);
private Pagination service=new Pagination("biblio", "localhost", "5432", "postgres", "1864", false, 2);

    
    @URLMapping("insertlivre.do")
public ModelRest insert(ServletEntity entity) throws Exception{
    Livre o=new Livre();
    o.setLabel(entity.getData().get("label"));o.setAuteur(new zaby.entities.Auteur(Integer.parseInt(entity.getData().get("auteur"))));o.setEditeur(new zaby.entities.Editeur(Integer.parseInt(entity.getData().get("editeur"))));o.setDatePublication(java.time.LocalDate.parse(entity.getData().get("datePublication")));
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
@URLMapping("tocrudlivre.do")
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
            
        Livre[] o = service.select(connex, Livre.class, offset);
        int pages = service.numRows(connex, Livre.class);
        model.addItem("pages", pages);

            model.addItem("viewpage", "livre.jsp");
            model.addItem("title", "Livre");
            model.addItem("o", o);
            zaby.entities.Auteur[] auteur=service.select(connex, zaby.entities.Auteur.class);
model.addItem("auteurs", auteur);zaby.entities.Editeur[] editeur=service.select(connex, zaby.entities.Editeur.class);
model.addItem("editeurs", editeur);
            
        }
    }
    return model;

}
@URLMapping("updatelivre.do")
public ModelRest update(ServletEntity entity) throws Exception{
    ModelRest model= new ModelRest();
    Livre o=new Livre();
    o.setLabel(entity.getData().get("label"));o.setAuteur(new zaby.entities.Auteur(Integer.parseInt(entity.getData().get("auteur"))));o.setEditeur(new zaby.entities.Editeur(Integer.parseInt(entity.getData().get("editeur"))));o.setDatePublication(java.time.LocalDate.parse(entity.getData().get("datePublication")));
    Livre where=new Livre();
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
@URLMapping("deletelivre.do")
public ModelRest delete(ServletEntity entity) throws Exception{
    Livre where=new Livre();
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
@URLMapping("readByIdlivre.do")
public ModelRest readById(ServletEntity entity) throws Exception{
   Livre where=new Livre();
    where.setId(Integer.parseInt(entity.getData().get("id")));
   

    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "1864", false, true)){
        ModelRest model=new ModelRest();
        Livre[] o=dao.select(connex, Livre.class,where);
        model.addItem("o", o);
        zaby.entities.Auteur[] auteur=service.select(connex, zaby.entities.Auteur.class);
model.addItem("auteurs", auteur);zaby.entities.Editeur[] editeur=service.select(connex, zaby.entities.Editeur.class);
model.addItem("editeurs", editeur);
        return model;
    }
}

}

