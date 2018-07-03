package Controllers;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;



class DatabaseInitializer {

     static void InitializeDatabase(Connection conn)throws Exception{

        System.out.println("Initializing Retail Database");

        clearDatabase(conn);
        createTables(conn);
    }

    //clears the db
    private static void clearDatabase(Connection conn) throws SQLException {
        Connection c = conn;
        Statement s = c.createStatement();

        // Disable disable integrity checks
        s.execute("SET REFERENTIAL_INTEGRITY FALSE");

        // Find all tables and drop them
        Set<String> tables = new HashSet<String>();

        ResultSet rs = s.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES  where TABLE_SCHEMA='PUBLIC'");
        while (rs.next()) {
            tables.add(rs.getString(1));
        }
        rs.close();
        for (String table : tables) {
            s.executeUpdate("DROP TABLE " + table);
        }

        // Re-enable integrity checks
        s.execute("SET REFERENTIAL_INTEGRITY TRUE");
        s.close();
    }

    private static void createTables(Connection conn) throws Exception {
        Statement stmt = null;

        System.out.println("Creating Database Tables");

        try {
            String FrequentShopper = "CREATE TABLE   FrequentShopper " +
                    "(userId INTEGER not NULL, " +
                    " name VARCHAR(255), " +
                    " address VARCHAR(255), " +
                    " phoneNum VARCHAR(10), " +
                    " creditCard VARCHAR(19), " +
                    " PRIMARY KEY ( userId ))";

            String Order = "CREATE TABLE   shopOrder " +
                    "(orderId INTEGER not NULL, " +
                    " userId INTEGER, " +
                    " storeId INTEGER, " +
                    " PRIMARY KEY ( orderId ))";

            String Store = "CREATE TABLE   Store " +
                    "(storeId INTEGER not NULL, " +
                    " name VARCHAR(255), " +
                    " location VARCHAR(255), " +
                    " phoneNum VARCHAR(10), " +
                    " hours VARCHAR(255), " +
                    " PRIMARY KEY ( storeId ))";

            String Vendor = "CREATE TABLE   Vendor " +
                    "(vendorId INTEGER not NULL, " +
                    " name VARCHAR(255), " +
                    " location VARCHAR(255), " +
                    " phoneNum VARCHAR(10), " +
                    " salesRep VARCHAR(255), " +
                    " PRIMARY KEY ( vendorId ))";

            String Product = "CREATE TABLE   Product " +
                    "(UPC INTEGER not NULL, " +
                    " name VARCHAR(255), " +
                    " Brand VARCHAR(255), " +
                    " Price decimal(19,2), " +
                    " Vendor Integer, " +
                    " PRIMARY KEY ( UPC ))";

            stmt = conn.createStatement();

            stmt.executeUpdate(FrequentShopper);
            stmt.executeUpdate(Order);
            stmt.executeUpdate(Store);
            stmt.executeUpdate(Vendor);
            stmt.executeUpdate(Product);

            stmt.close();

        } catch (Exception e) {
            System.out.println("Error Creating tables");
            e.printStackTrace();
        }
    }


}
