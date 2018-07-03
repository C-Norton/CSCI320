import java.sql.*;
import Controllers.DatabaseController;

public class Main {

    public static void main(String[] a) throws Exception {

        Connection conn = DriverManager.getConnection("jdbc:h2:./test", "sa", "");

        DatabaseController.InitializeNewDatabaseInstance(conn);

        conn.close();
    }

}
