package Controllers;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;



class DatabaseInitializer {

    static void InitializeDatabase(Connection conn) throws Exception {

        System.out.println("Initializing Retail Database");

        clearDatabase(conn);
        createTables(conn);
        createUsers(conn);
        createStoreInventories(conn);
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
                    "(userId INTEGER not NULL auto_increment, " +
                    " name VARCHAR(32), " +
                    " address VARCHAR(255), " +
                    " phoneNum VARCHAR(10), " +
                    " creditCard VARCHAR(19), " +
                    " username VARCHAR(50), " +
                    " password VARCHAR(50), " +
                    " PRIMARY KEY ( userId ))";

            String Order = "CREATE TABLE   Orders " +
                    "(orderNum Integer not NULL,"+
                    "userId INTEGER, " +
                    "storeId INTEGER, " +
                    "PRIMARY KEY ( orderNum ) )";

            String productQuantities = "CREATE TABLE   prodQuantities " +
                    "(orderNum INTEGER not NULL,"+
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
                    " Brand VARCHAR(32), " +
                    " Price decimal(19,2), " +
                    " Vendor Integer, " +
                    " PRIMARY KEY ( UPC ))";

            String Inventory = "CREATE TABLE   Inventory " +
                    "(storeId INTEGER, " +
                    "productUPC numeric(12,0)," +
                    "quantity INTEGER,"+
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

        } catch (Exception e) {
            System.out.println("Error Creating tables");
            e.printStackTrace();
        }
    }

    private static void createUsers(Connection conn) throws Exception {

        Statement stmt = null;

        System.out.println("Populating DB with users" + '\n');

        try {
            String FrequentShopper1 = "INSERT INTO  FrequentShopper VALUES (1,'Bob','3 Washington Lane','1234567899','1452365478','bobby','password123')";
            String FrequentShopper2 = "INSERT INTO  FrequentShopper VALUES (2,'John','6 Washington Lane','9876543211','','johnny','password123')";
            String FrequentShopper3 = "INSERT INTO  FrequentShopper VALUES (3,'Laura','5 Washington Lane','9873216544','','larua','password123')";
            String FrequentShopper4 = "INSERT INTO  FrequentShopper VALUES (4,'Jill','13 Washington Lane','1237894566','8452156625746','jill','password123')";
            String FrequentShopper5 = "INSERT INTO  FrequentShopper VALUES (5,'Gorlarth','1 Blorth Drive','6666666666','6666666666666666666','Gorlarth','password123')";

            stmt = conn.createStatement();
            stmt.executeUpdate(FrequentShopper1);
            stmt.executeUpdate(FrequentShopper2);
            stmt.executeUpdate(FrequentShopper3);
            stmt.executeUpdate(FrequentShopper4);
            stmt.executeUpdate(FrequentShopper5);


        } catch (Exception e) {
            System.out.println("Error Creating users");
            e.printStackTrace();
        }
    }


    private static void createStoreInventories(Connection conn) throws Exception{

        Statement stmt = null;

        System.out.println("Populating DB with stores" + '\n');

        try {

            String Store1 = "INSERT INTO  Store(name, location, phoneNum, hours) VALUES ('NYC','NA','420','24hrs')";
            String Product1 = "INSERT INTO Product VALUES (101010101010, 'ten', 'boring corp', 420.69, 123)";
            String Vendor1 = "Insert INTO Vendor VALUES (123, 'lame corp', 'NA', 911, 'Bobby')";
            String Inventory1 = "Insert INTO Inventory VALUES (1, 101010101010, 69)";

            stmt = conn.createStatement();
            stmt.executeUpdate(Store1);
            stmt.executeUpdate(Product1);
            stmt.executeUpdate(Vendor1);
            stmt.executeUpdate(Inventory1);


        } catch (Exception e) {
            System.out.println("Error Creating users");
            e.printStackTrace();
        }
    }

}
