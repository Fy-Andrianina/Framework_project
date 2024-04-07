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
import my_second_Boo.entities.Livre;

@Controller
@Singleton

public class LivreController {
    private DAO dao=new DAO("biblio", "localhost", "5432", "postgres", "postgres", false, 2);

    
    @URLMapping("insertlivre.do")
public void insert(ServletEntity entity) throws Exception{
    Livre o=new Livre();
    o.setLabel(entity.getData().get("label"));o.setAuteur(new my_second_Boo.entities.Auteur(Integer.parseInt(entity.getData().get("auteur"))));o.setEditeur(new my_second_Boo.entities.Editeur(Integer.parseInt(entity.getData().get("editeur"))));o.setDatePublication(java.time.LocalDate.parse(entity.getData().get("datePublication")));
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        dao.insertWithoutPrimaryKey(connex, o);
        connex.commit();
       
    }
}
@URLMapping("tocrudlivre.do")
public ModelRest crudpage(ServletEntity entity) throws Exception{
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        ModelRest model=new ModelRest();
        
        Livre[] o=dao.select(connex, Livre.class);
        model.addItem("viewpage", "livre.jsp");
        model.addItem("title", "Livre");
        model.addItem("o", o);
        my_second_Boo.entities.Auteur[] auteur=dao.select(connex, my_second_Boo.entities.Auteur.class);
model.addItem("auteurs", auteur);my_second_Boo.entities.Editeur[] editeur=dao.select(connex, my_second_Boo.entities.Editeur.class);
model.addItem("editeurs", editeur);
        return model;
    }
}
@URLMapping("updatelivre.do")
public void update(ServletEntity entity) throws Exception{
    Livre o=new Livre();
    o.setLabel(entity.getData().get("label"));o.setAuteur(new my_second_Boo.entities.Auteur(Integer.parseInt(entity.getData().get("auteur"))));o.setEditeur(new my_second_Boo.entities.Editeur(Integer.parseInt(entity.getData().get("editeur"))));o.setDatePublication(java.time.LocalDate.parse(entity.getData().get("datePublication")));
    Livre where=new Livre();
    where.setId(Integer.parseInt(entity.getData().get("id")));
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        dao.update(connex, o, where);
        connex.commit();
        
    }
}
@URLMapping("deletelivre.do")
public void delete(ServletEntity entity) throws Exception{
    Livre where=new Livre();
    where.setId(Integer.parseInt(entity.getData().get("id")));
    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        dao.delete(connex, where);
        connex.commit();
       
    }
}
@URLMapping("readByIdlivre.do")
public ModelRest readById(ServletEntity entity) throws Exception{
   Livre where=new Livre();
    where.setId(Integer.parseInt(entity.getData().get("id")));
   

    try(Connection connex=DAOConnexion.getConnexion("org.postgresql.Driver", "postgresql", "localhost", "5432", "biblio", "postgres", "postgres", false, true)){
        ModelRest model=new ModelRest();
        Livre[] o=dao.select(connex, Livre.class,where);
        model.addItem("o", o);
        my_second_Boo.entities.Auteur[] auteur=dao.select(connex, my_second_Boo.entities.Auteur.class);
model.addItem("auteurs", auteur);my_second_Boo.entities.Editeur[] editeur=dao.select(connex, my_second_Boo.entities.Editeur.class);
model.addItem("editeurs", editeur);
        return model;
    }
}

}

