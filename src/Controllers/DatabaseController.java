package Controllers;
import java.sql.*;


public class DatabaseController {

    private Connection conn;

    public DatabaseController(Connection conn){
        this.conn = conn;
    }

    //initialize a fresh instance of the retail DB
    public void InitializeNewDatabaseInstance() throws Exception{
        DatabaseInitializer.InitializeDatabase(this.conn);
    }

    public ResultSet ExecuteQuery(Statement stmt, String query) throws Exception{
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }
}
