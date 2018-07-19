package Models;
import Controllers.DatabaseController;
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

    //retrieves the inventory for a specific store from the db
    public static Inventory retrieveInventory(DatabaseController dbController, StatementTemplate stmtUtil, int storeId)
    throws Exception{
        Statement stmt = stmtUtil.newNullStatement();
        ResultSet rs = null;

        //hypebeast complex sql statement
        String query = "SELECT productUPC, quantity from (SELECT productUPC,quantity FROM Inventory WHERE storeId = " + storeId + ") as X; "
                + "X left join Product on X.productUPC = Product.UPC as Y; " + "ALTER TABLE Y RENAME COLUMN name to productName;" +
                "Y left join Vendor on Y.vendor = Vendor.vendorID as Z;"
                + "select * from Z;";

        //create query statement
        try {
            stmt = stmtUtil.connStatement(stmt);
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

    //parses result set, returns list of prod quantities
    private static ArrayList<ProductQuantity> parseResultSet(ResultSet rs) {
        ArrayList<ProductQuantity> inventory = null;

        if (rs != null) {

            try {
                inventory = new ArrayList<ProductQuantity>();
                while (rs.next()) {
                    String upc = rs.getString("upc");
                    int quantity = rs.getInt("quantity");
                    String name = rs.getString("name");
                    String brand = rs.getString("brand");
                    float price = rs.getFloat("price");
                    int vendorID = rs.getInt("vendorID");
                    String vendorName = rs.getString("vendorName");
                    String location = rs.getString("location");
                    String phoneNum = rs.getString("phoneNum");
                    String salesRep = rs.getString("salesRep");

                    ProductQuantity prodQuant = new ProductQuantity(upc, quantity);
                }
            } catch (Exception e) {
                System.out.print("Error Building Customer" + '\n');
                System.out.println(e.getMessage());
            }
        }

        return inventory;
    }
}
