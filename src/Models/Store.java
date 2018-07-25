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
public class Store {
    private int id;
    private String name;
    private String location;
    private String hours;
    private String phone;
    private Inventory inventory;
    private ArrayList<Order> orders;

    private static Store currentStore;

    private Store(int id, String name, String location, String hours, String phone, Inventory inventory, ArrayList<Order> orders) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.hours = hours;
        this.phone = phone;
        this.inventory = inventory;
        this.orders = orders;
    }

    public static Boolean existsStore(int storeId){
        String query = "select userid from Store where storeId = " + storeId;
        ResultSet rs = DatabaseController.MakeSelQuery(query);
        ArrayList<String[]> parsedRs = RSParser.rsToStringHeaders(rs);
        return parsedRs != null && parsedRs.get(1)[0].equals(String.valueOf(storeId));
    }

    public static ResultSet getInventoryMetadata(int id)
    {

        return DatabaseController.MakeSelQuery("Select product.UPC, product.Name, product.Brand, product.Price from "
                                               + "product join inventory on product.UPC = inventory.productUPC where storeId="
                                               + id);
    }

    //gets a list of all stores
    public static ResultSet retrieveStores(){

        Statement stmt = StatementTemplate.newNullStatement();
        ResultSet rs = null;

        String selectCustomer = "SELECT storeID, name FROM Store";

        //create query statement
        try {
            stmt = StatementTemplate.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Fetch Statement for Stores");
            System.out.println(e.getMessage());
        }

        //execute and get results of query
        try {
            rs = DatabaseController.ExecuteSelectQuery(stmt, selectCustomer);
        }catch(Exception e){
            System.out.println("Error Executing Select Query for Stores");
            System.out.println(e.getMessage());
        }

        return rs;
    }

    //updates the static instance of current store
    public static boolean enterStore(Store store){
        try {
            Store.currentStore = store;
        }catch (Exception e){
            System.out.println("Error Entering Store");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    //get one store by store id, returns a result set
    public static ResultSet retrieveStoreById(int storeid){
        Statement stmt = StatementTemplate.newNullStatement();
        ResultSet rs = null;

        String selectCustomer = "SELECT * FROM Store WHERE storeId = " + storeid ;

        //create query statement
        try {
            stmt = StatementTemplate.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Fetch Statement for Store");
        }

        //execute and get results of query
        try {
            rs = DatabaseController.ExecuteSelectQuery(stmt, selectCustomer);
        }catch(Exception e){
            System.out.println("Error Executing Select Query for Store");
        }

        return rs;
    }

    //turns a table of stores into array of objects
    private static ArrayList<Store> parseStores(ResultSet rs){
        ArrayList<Store> stores = null;
        if (rs != null) {

            try {
                stores = new ArrayList<>();
                while (rs.next()) {
                    int id;
                    String name;
                    String location = null;
                    String hours = null;
                    String phone = null;
                    Inventory inventory = null;
                    ArrayList<Order> orders = new ArrayList<>();

                    id = rs.getInt("storeID");
                    name = rs.getString("name");
                    try {
                        location = rs.getString("location");
                        hours = rs.getString("hours");
                        phone = rs.getString("phoneNum");
                    }catch(Exception e){
                        System.out.println();
                    }

                    stores.add(new Store(id,name, location, hours, phone, inventory, orders));
                }
            } catch (Exception e) {
                System.out.print("Error Building Customer" + '\n');
                System.out.println(e.getMessage());
            }
        }

        return stores;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
