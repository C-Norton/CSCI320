package Controllers;

import org.h2.tools.RunScript;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

class DatabaseInitializer
{

    static void InitializeDatabase(Connection conn) throws Exception
    {

        System.out.println("Initializing Retail Database");

        clearDatabase(conn);
        createTables(conn);
        dataPop(conn);

    }

    //clears the db
    private static void clearDatabase(Connection conn) throws SQLException
    {

        Connection c = conn;
        Statement s = c.createStatement();

        // Disable disable integrity checks
        s.execute("SET REFERENTIAL_INTEGRITY FALSE");

        // Find all tables and drop them
        Set<String> tables = new HashSet<String>();

        ResultSet rs = s.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES  where TABLE_SCHEMA='PUBLIC'");
        while (rs.next())
        {
            tables.add(rs.getString(1));
        }
        rs.close();
        for (String table : tables)
        {
            s.executeUpdate("DROP TABLE " + table);
        }

        // Re-enable integrity checks
        s.execute("SET REFERENTIAL_INTEGRITY TRUE");
        s.close();
    }

    private static void createTables(Connection conn)
    {

        Statement stmt = null;

        System.out.println("Creating Database Tables");

        try
        {
            String FrequentShopper = "CREATE TABLE   FrequentShopper " +
                                     "(userId INTEGER not NULL auto_increment, " +
                                     " name VARCHAR(32), " +
                                     " address VARCHAR(255), " +
                                     " phoneNum VARCHAR(10), " +
                                     " creditCard VARCHAR(19), " +
                                     " username VARCHAR(50), " +
                                     " password VARCHAR(50), " +
                                     " PRIMARY KEY ( userId ))";

            String Order = "CREATE TABLE   Orders " +
                           "(orderNum Integer not NULL," +
                           "userId INTEGER, " +
                           "storeId INTEGER, " +
                           "PRIMARY KEY ( orderNum ) )";

            String productQuantities = "CREATE TABLE   prodQuantities " +
                                       "(orderNum INTEGER not NULL," +
                                       "productUPC numeric(12,0), " +
                                       "quantity INTEGER, " +
                                       " PRIMARY KEY ( orderNum,productUPC ))";

            String Store = "CREATE TABLE   Store " +
                           "(storeId INTEGER not NULL auto_increment, " +
                           " name VARCHAR(32), " +
                           " location VARCHAR(32), " +
                           " phoneNum VARCHAR(10), " +
                           " hours VARCHAR(50), " +
                           " PRIMARY KEY ( storeId ))";

            String Vendor = "CREATE TABLE   Vendor " +
                            "(vendorId INTEGER not NULL auto_increment, " +
                            " name VARCHAR(32), " +
                            " location VARCHAR(32), " +
                            " phoneNum VARCHAR(10), " +
                            " salesRep VARCHAR(32), " +
                            " PRIMARY KEY ( vendorId ))";

            String Product = "CREATE TABLE   Product " +
                             "(UPC numeric(12,0) not NULL , " +
                             " name VARCHAR(50), " +
                             " brand VARCHAR(32), " +
                             " price decimal(19,2), " +
                             " vendor Integer, " +
                             " PRIMARY KEY ( UPC ))";

            String Inventory = "CREATE TABLE   Inventory " +
                               "(storeId INTEGER, " +
                               "productUPC numeric(12,0)," +
                               "quantity INTEGER," +
                               " PRIMARY KEY ( storeID, productUPC ))";


            stmt = conn.createStatement();

            stmt.executeUpdate(FrequentShopper);
            stmt.executeUpdate(Order);
            stmt.executeUpdate(Store);
            stmt.executeUpdate(Vendor);

            stmt.executeUpdate(Product);
            stmt.executeUpdate(Inventory);
            stmt.executeUpdate(productQuantities);


            stmt.close();

        }
        catch (Exception e)
        {
            System.out.println("Error Creating tables");
            e.printStackTrace();
        }
    }

    private static void dataPop(Connection conn) throws Exception
    {

        Reader reader = new FileReader("SQLScripts/LoadFrequentCustomers.sql");
        RunScript.execute(conn, reader);
        reader = new FileReader("SQLScripts/LoadInventories.sql");
        RunScript.execute(conn, reader);
        reader = new FileReader("SQLScripts/LoadOrders.sql");
        RunScript.execute(conn, reader);
        reader = new FileReader("SQLScripts/LoadProductQuantities.sql");
        RunScript.execute(conn, reader);
        reader = new FileReader("SQLScripts/LoadProducts.sql");
        RunScript.execute(conn, reader);
        reader = new FileReader("SQLScripts/LoadStores.sql");
        RunScript.execute(conn, reader);
        reader = new FileReader("SQLScripts/LoadVendors.sql");
        RunScript.execute(conn, reader);
    }

}
