package traite;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import veda.godao.DAO;
import veda.godao.exceptions.ConnectionException;
import veda.godao.utils.DAOConnexion;
import veda.godao.utils.QueryUtils;
public class Pagination extends DAO{
     public Pagination(String database, String host, String port, String user, String pwd, boolean useSSL, int sgbd) {
          super(database, host, port,  user,  pwd,  useSSL, sgbd);
     }
     public <T> int numRows(Connection connex, Class<T> c) throws Exception{
      boolean opened = false;
      Connection connect = connex;
      if (connect == null) {
        switch (getSgbd()) {
          case 1:
            connect = DAOConnexion.getConnexionMySql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
            break;
          case 2:
            connect = DAOConnexion.getConnexionPostgreSql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
            break;
          default:
            throw new ConnectionException(1);
        } 
        opened = true;
      } 
      PreparedStatement statemnt = connect.prepareStatement(UtilsPagination.getCountRows(c));
      try {
        int i = 0;
        ResultSet result = statemnt.executeQuery();
        while (result.next()) {
          i = result.getInt("count");
        } 
        return i;
      } finally {
        if (opened)
          connect.close(); 
        statemnt.close();
      } 
     }
     public <T> T[] select(Connection connex, Class<T> c, int offset) throws Exception {
          boolean opened = false;
          Connection connect = connex;
          if (connect == null) {
            switch (getSgbd()) {
              case 1:
                connect = DAOConnexion.getConnexionMySql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
                break;
              case 2:
                connect = DAOConnexion.getConnexionPostgreSql(getDatabase(), getUser(), getPwd(), getHost(), isUseSSL());
                break;
              default:
                throw new ConnectionException(1);
            } 
            opened = true;
          } 
          PreparedStatement statemnt = connect.prepareStatement(UtilsPagination.getSelectPaginate(c, offset));
          HashMap<Field, String> columns = QueryUtils.getColumnsWithField(c);
          try {
            LinkedList<T> liste = new LinkedList();
            ResultSet result = statemnt.executeQuery();
            while (result.next()) {
              T obj = c.getConstructor(new Class[0]).newInstance(new Object[0]);
              obj = (T)QueryUtils.mapResultSet(connect, result, obj, columns, this);
              liste.add(obj);
            } 
            Object[] objets = (Object[])Array.newInstance(c, liste.size());
            for (int i = 0; i < objets.length; i++)
              objets[i] = liste.get(i); 
            return (T[])objets;
          } finally {
            if (opened)
              connect.close(); 
            statemnt.close();
          } 
        }
      
}
