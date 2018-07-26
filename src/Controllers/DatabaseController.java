package Controllers;

import Utilities.StatementTemplate;
import Utilities.StatementType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.Statement.EXECUTE_FAILED;

public class DatabaseController
{

    private static Connection conn;
    private static Statement statementQueue;

    public DatabaseController(Connection connection)
    {

        conn = connection;

    }

    public static boolean newBatchStatement()
    {

        try
        {
            statementQueue = StatementTemplate.connUpdateStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean commitBatch()
    {

        int[] update;
        try
        {
            update = statementQueue.executeBatch();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        for (int i : update)
        {
            if (i == EXECUTE_FAILED)
            {
                return false;
            }
        }
        return true;
    }

    public static void discardBatch()
    {

        try
        {
            statementQueue.clearBatch();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        statementQueue = null;
    }

    public static boolean addQueryToBatch(String query)
    {

        if (DatabaseController.getQueryType(query, false) == (StatementType.UPDATE))
        {
            try
            {
                statementQueue.addBatch(query);
                return true;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static StatementType getQueryType(String Query, boolean Updateable)
    {

        try
        {
            Statement stmt = StatementTemplate.connQueryStatement(Updateable);
            stmt.executeQuery(Query);
        }
        catch (SQLException e)
        {
            if (e.getMessage().startsWith("Method is only allowed for a query."))
            {
                return StatementType.UPDATE;
            }
            else
            {
                return StatementType.INVALID;
            }
        }
        return (Updateable) ? StatementType.UPDATEABLESELECT : StatementType.NONUPDATEABLESELECT;

    }

    public static int UpdateQuery(String query)
    {

        int updated;
        try
        {
            updated = StatementTemplate.connUpdateStatement().executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return -1;
        }
        return updated;
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
    public static ResultSet SelectQuery(String Query, Boolean Updateable)
    {

        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            stmt = StatementTemplate.connQueryStatement(Updateable);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        try
        {
            rs = stmt.executeQuery(Query);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return rs;
    }

    //initialize a fresh instance of the retail DB
    public void InitializeNewDatabaseInstance() throws Exception
    {

        DatabaseInitializer.InitializeDatabase(conn);
    }

}
