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

    public ResultSet ExecuteSelectQuery(Statement stmt, String query) throws Exception{
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }

    public void ExecuteUpdateQuery(Statement stmt, String query) throws Exception{
        stmt.executeUpdate(query);
    }
}
