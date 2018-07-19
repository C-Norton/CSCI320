package Controllers;

import Utilities.StatementTemplate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class DatabaseController {

    private static Connection conn;

    public DatabaseController(Connection connection)
    {

        conn = connection;

    }

    public static void ExecuteUpdateQuery(Statement stmt, String query) throws Exception
    {
        stmt.executeUpdate(query);
    }

    /**
     * MakeQuery is a quick static class to execute a query string on the static DB. While I know this technically
     * infringes upon the other methods in this class, it was done quickly, as I saw that basically every select
     * query was the same codeblock with the string changed. After discussing with groupmates, I suggest moving this
     * into "ExecuteSelectQuery," and making similar changes to "ExecuteUpdateQuery." The one note is that errors are
     * harder to pin down this way with the normal code block used elsewhere in project. However, the
     * .printStackTrace() method eliminates this downside, as it WILL show the call being made via whatever caused
     * the problem
     */
    public static ResultSet MakeSelQuery(String Query)
    {

        Statement stmt = StatementTemplate.newNullStatement();
        ResultSet rs = null;
        try
        {
            stmt = StatementTemplate.connStatement(stmt);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        try
        {
            rs = DatabaseController.ExecuteSelectQuery(stmt, Query);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return rs;
    }

    public static ResultSet ExecuteSelectQuery(Statement stmt, String query) throws Exception
    {

        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }

    //initialize a fresh instance of the retail DB
    public void InitializeNewDatabaseInstance() throws Exception
    {

        DatabaseInitializer.InitializeDatabase(conn);
    }

}
