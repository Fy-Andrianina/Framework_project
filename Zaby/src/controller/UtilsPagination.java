package traite;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import veda.godao.DAO;
import veda.godao.annotations.Column;
import veda.godao.annotations.ForeignKey;
import veda.godao.annotations.PrimaryKey;
import veda.godao.annotations.Table;
import veda.godao.utils.QueryUtils;
import veda.godao.utils.StringUtils;

public class UtilsPagination extends QueryUtils {
     public UtilsPagination() {
          super();
     }

     public static String getSelectPaginate(Class c, int offset) throws Exception {
          Annotation annote = (Annotation) c.getAnnotation(Table.class);
          String table = annote.annotationType().getMethod("value", new Class[0]).invoke(annote, new Object[0])
                    .toString();
          String query = "select * from " + table + " order by id asc LIMIT 4 OFFSET " + offset;
          return query;
     }
     public static String getCountRows(Class c) throws Exception {
          Annotation annote = (Annotation) c.getAnnotation(Table.class);
          String table = annote.annotationType().getMethod("value", new Class[0]).invoke(annote, new Object[0])
                    .toString();
          String query = "select count(*) as count from " + table;
          return query;
     }
}
