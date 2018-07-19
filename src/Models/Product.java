package Models;

import Controllers.DatabaseController;
import Utilities.StatementTemplate;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Michael on 7/6/2018.
 */
public class Product {

    private String name;
    private String brand;
    private float price;
    private String upc;
    private Vendor vendor;

    //constructor
    public Product(String upc, String name, String brand, float price, int vendorID, DatabaseController dbController,
                   StatementTemplate stmtUtil){
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.upc = upc;
        this.vendor = Vendor.getSingleVendorQuery(dbController, stmtUtil, vendorID);
    }

    public static ResultSet getProducts(DatabaseController dbController, StatementTemplate stmtUtil){
        Statement stmt = stmtUtil.newNullStatement();
        ResultSet rs = null;

        String selectAllProducts = "SELECT * FROM Product";

        //create query statement
        try {
            stmt = stmtUtil.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Fetch Statement for Products");
        }

        //execute and get results of query
        try {
            rs = dbController.ExecuteSelectQuery(stmt, selectAllProducts);
        }catch(Exception e){
            System.out.println("Error Executing Select Query for Products");
        }
        return rs;
    }

    public static ResultSet getProductByUpc(DatabaseController dbController, StatementTemplate stmtUtil, String upc){
        Statement stmt = stmtUtil.newNullStatement();
        ResultSet rs = null;

        String selectProductByUpc = "SELECT * FROM Product WHERE UPC = " + upc;

        //create query statement
        try {
            stmt = stmtUtil.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Fetch Statement for Product");
        }

        //execute and get results of query
        try {
            rs = dbController.ExecuteSelectQuery(stmt, selectProductByUpc);
        }catch(Exception e){
            System.out.println("Error Executing Select Query for Product");
        }
        return rs;
    }

    private static ArrayList<Product> parseResultSet(ResultSet rs){
        return null;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public float getPrice() {
        return price;
    }

    public String getUpc() {
        return upc;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
