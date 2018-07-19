package Controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class DatabaseController {

    public static DatabaseController DB;
    private final Connection conn;
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
