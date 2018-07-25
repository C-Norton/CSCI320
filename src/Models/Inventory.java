package Models;
import Controllers.DatabaseController;
import Utilities.RSParser;
import Utilities.StatementTemplate;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Michael on 7/6/2018.
 */
public class Inventory {
    private ArrayList<ProductQuantity> inventory;

    public ArrayList<ProductQuantity> getInventory() {
        return inventory;
    }

    //constructor
    private Inventory(ArrayList<ProductQuantity> inventory){
        this.inventory = inventory;
    }

    /*
    //retrieves the inventory for a specific store from the db
    public static Inventory retrieveInventory(DatabaseController dbController, StatementTemplate stmtUtil, int storeId)
    throws Exception{

        Statement stmt = StatementTemplate.newNullStatement();
        ResultSet rs = null;

        //hypebeast complex sql statement
        String query = "SELECT productUPC, quantity from (SELECT productUPC,quantity FROM Inventory WHERE storeId = " + storeId + ") as X; "
                + "X left join Product on X.productUPC = Product.UPC as Y; " + "ALTER TABLE Y RENAME COLUMN name to productName;" +
                "Y left join Vendor on Y.vendor = Vendor.vendorID as Z;"
                + "select * from Z;";

        //create query statement
        try {
            stmt = StatementTemplate.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Fetch Statement for Inventory");
            System.out.println(e.getMessage());
        }

        //execute and get results of query
        try {
            rs = dbController.ExecuteSelectQuery(stmt, query);
        }catch(Exception e){
            System.out.println("Error Executing Select Query for Inventory");
            System.out.println(e.getMessage());
        }

        ArrayList<ProductQuantity> inventory = parseResultSet(rs);
        if (inventory!=null){
            return new Inventory(inventory);
        }else {
            throw new Exception("Error Retrieving Inventory");
        }
    }
    */

    //checks if product is stocked in the store inventory
    public static int productStock(int storeId, String upc){
        ResultSet rs = DatabaseController.MakeSelQuery("SELECT quantity FROM Inventory WHERE storeId = " + storeId +
        " and productUPC = " + upc);
        ArrayList<String[]> results = RSParser.rsToStringHeaders(rs);
        if(results == null || results.size() < 2){
            return 0;
        }
        return Integer.parseInt(results.get(1)[0]);
    }

    //retrieves a list of names for available items in a store
    public static ResultSet retrieveAvailableItems(int storeId)
    {

        Statement stmt = StatementTemplate.newNullStatement();
        ResultSet rs = null;

        //join Inventory and Product
        String query = "WITH upc AS (SELECT productUPC FROM Inventory WHERE storeId = " + storeId + ")" +
                "SELECT name FROM (upc join Product on upc.productUPC = Product.upc)";

        //create query statement
        try {
            stmt = StatementTemplate.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Fetch Statement for Inventory");
            System.out.println(e.getMessage());
        }

        //execute and get results of query
        try {
            rs = DatabaseController.ExecuteSelectQuery(stmt, query);
        }catch(Exception e){
            System.out.println("Error Executing Select Query for Inventory");
            System.out.println(e.getMessage());
        }

        return rs;
    }

    //parses result set, returns list of prod quantities
    private static ArrayList<ProductQuantity> parseResultSet(ResultSet rs) {
        ArrayList<ProductQuantity> inventory = null;

        if (rs != null) {

            try {
                inventory = new ArrayList<ProductQuantity>();
                while (rs.next()) {
                    String upc = rs.getString("upc");
                    int quantity = rs.getInt("quantity");

                    ProductQuantity prodQuant = new ProductQuantity(upc, quantity);
                    inventory.add(prodQuant);
                }
            } catch (Exception e) {
                System.out.print("Error Building Customer" + '\n');
                System.out.println(e.getMessage());
            }
        }

        return inventory;
    }
}
