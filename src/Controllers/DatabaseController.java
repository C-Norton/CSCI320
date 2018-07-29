package Controllers;

import Models.CartEntry;
import Utilities.StatementTemplate;
import Utilities.StatementType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseController
{

    private static Connection conn;

    public DatabaseController(Connection connection)
    {

        conn = connection;

    }

    public static int UpdateQuery(String query)
    {

        Statement stmt = null;

        int updated;
        try
        {
            stmt = StatementTemplate.connUpdateStatement();
            updated = stmt.executeUpdate(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return -1;
        }
        finally
        {
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
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
    public static ResultSet SelectQuery(String Query)
    {

        Statement stmt;
        ResultSet rs = null;
        try
        {
            stmt = StatementTemplate.connQueryStatement();
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

    public static StatementType getQueryType(String Query)
    {

        Statement stmt = null;

        try
        {
            stmt = StatementTemplate.connQueryStatement();
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
        finally
        {
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return StatementType.SELECT;

    }

    public static boolean createOrder(int storeId, Integer userId, ArrayList<CartEntry> order)
    {

        String sstoreId = String.valueOf(storeId);

        try
        {
            conn.setAutoCommit(false);
            Statement stmt = StatementTemplate.connUpdateStatement();
            long orderNum;
            if (userId == null)
            {
                stmt.executeUpdate("INSERT into Orders (storeId) values ("
                                   + sstoreId + ")", Statement.RETURN_GENERATED_KEYS);
            }
            else
            {
                stmt.executeUpdate("INSERT into Orders (userId, storeId) values ("
                                   + userId.toString() + ", " + sstoreId + ")", Statement.RETURN_GENERATED_KEYS);
            }
            ResultSet key = stmt.getGeneratedKeys();
            if (key != null)
            {
                if (key.next())
                {
                    orderNum = key.getLong(1);
                }
                else
                {
                    throw new SQLException("Generated Key is nonnull but of wrong type or empty");
                }

            }
            else
            {
                throw new SQLException("Generated Key is null");
            }

            for (CartEntry item : order)
            {
                if (item.Quantity <= 0)
                {
                    continue;
                }
                ResultSet inventory = stmt.executeQuery("Select quantity from inventory where (productUPC = "
                                                        + item.UPC + " AND storeId = " + sstoreId + ")");
                if (inventory != null)
                {
                    if (inventory.next())
                    {
                        int storeQuantity = inventory.getInt(1);
                        if (storeQuantity >= item.Quantity)
                        {
                            long updatecount = stmt.executeUpdate("UPDATE inventory SET quantity = "
                                                                  + String.valueOf(storeQuantity - item.Quantity)
                                                                  + " WHERE productUPC = " + item.UPC
                                                                  + " AND storeId = " + sstoreId);
                            if (updatecount != 1)
                            {
                                throw new SQLException("Incorrect update count to inventory");
                            }

                            updatecount = stmt.executeUpdate(
                                    String.format("Insert into prodQuantities VALUES (%d,%s,%s)",
                                            orderNum, item.UPC, item.Quantity)
                            );
                            if (updatecount != 1)
                            {
                                throw new SQLException("Incorrect update count to product Quantities");
                            }

                        }
                        else
                        {
                            throw new SQLException("Insufficient quantity in store");
                        }
                    }
                    else
                    {
                        throw new SQLException("Store does not have product, and quantity is non zero");
                    }
                }
                else
                {
                    throw new SQLException("Quantity is null");
                }
            }
            conn.commit();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            try
            {
                conn.rollback();
                return false;
            }
            catch (SQLException f)
            {
                f.printStackTrace();
                return false;
            }
        }
        finally
        {
            try
            {
                conn.setAutoCommit(true);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return true;
    }

    public static boolean enableRestockingTrigger()
    {

        try
        {
            Statement stmt = StatementTemplate.connUpdateStatement();

            String statement =  "create trigger autorestock after update on inventory " +
                                "for each row " +
                                "call \"Controllers.Restock\"";

            stmt.executeUpdate(statement);
        }
        catch (SQLException e){
            e.printStackTrace();
            try
            {
                conn.rollback();
                return false;
            }
            catch (SQLException f)
            {
                f.printStackTrace();
                return false;
            }
        }

        return true;

    }

    public static boolean disableRestockingTrigger()
        {

            try
            {
                Statement stmt = StatementTemplate.connUpdateStatement();

                String statement =  "DROP TRIGGER autorestock";

                stmt.executeUpdate(statement);
            }
            catch (SQLException e){
                e.printStackTrace();
                try
                {
                    conn.rollback();
                    return false;
                }
                catch (SQLException f)
                {
                    f.printStackTrace();
                    return false;
                }
            }

            return true;

        }

    //initialize a fresh instance of the retail DB
    public void InitializeNewDatabaseInstance() throws Exception
    {

        DatabaseInitializer.InitializeDatabase(conn);
    }

}
