package Controllers;
import java.sql.*;


public class DatabaseController {


    //initalize a fresh instance of the retail DB
    public static void InitializeNewDatabaseInstance(Connection conn) throws Exception{
        DatabaseInitializer.InitializeDatabase(conn);
    }
}
