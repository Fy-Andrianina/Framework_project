package extra;

import java.sql.Connection;
import java.sql.Statement;

import code.generation.Component;

public class CreateUser {
    public static void create(Connection connect, String filepath) {
        Component component = new Component();
        try {
            String sql = component.ReadTemplate(filepath);
            System.out.println(sql);
            Statement stmt = connect.createStatement();
            stmt.execute(sql);
            connect.commit();

            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
