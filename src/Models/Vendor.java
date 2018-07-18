package Models;

import Controllers.DatabaseController;
import Utilities.StatementTemplate;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Michael on 7/6/2018.
 */
public class Vendor {


    private int id;
    private String name;
    private String location;
    private int rep;
    private String phone;

    public Vendor(int id, String name, String location, int rep, String phone) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.rep = rep;
        this.phone = phone;
    }

    //Queries//

    //select all for a vendor based on id
    //parsing method still included but this just returns the ResultSet right now
    public static ResultSet getSingleVendorQuery(DatabaseController dbController, StatementTemplate stmtUtil, int id){

        Statement stmt = stmtUtil.newNullStatement();
        ResultSet rs = null;

        String selectVendor = "SELECT * FROM Vendor WHERE vendorId =" + id;

        //create query statement
        try {
            stmt = stmtUtil.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Fetch Statement for Vendor");
        }

        //execute and get results of query
        try {
            rs = dbController.ExecuteSelectQuery(stmt, selectVendor);
        }catch(Exception e){
            System.out.println("Error Executing Select Query for Vendor");
        }

        //Vendor vendor = parseResultSet(rs); // parsing the query

        return rs;
    }

    //selects names of all vendors
    //returns ResultSet but code for parsing is still included
    public static ResultSet getListOfVendors(DatabaseController dbController, StatementTemplate stmtUtil){

        Statement stmt = stmtUtil.newNullStatement();
        ResultSet rs = null;

        String selectAllVendors = "SELECT Name FROM Vendor";

        //create query statement
        try {
            stmt = stmtUtil.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Fetch Statement for Vendor");
        }

        //execute and get results of query
        try {
            rs = dbController.ExecuteSelectQuery(stmt, selectAllVendors);
        }catch(Exception e){
            System.out.println("Error Executing Select Query for Vendor");
        }

        //ArrayList<String> vendorNames = parseResultSetList(rs); //calls the parsing method
        return rs;

    }




    //Utils//
    //not used because we are assuming that parsing is going on on the GUI end

    //gets a vendor from a query
    private static Vendor parseResultSet(ResultSet rs){
        if (rs != null){
            try {
                while (rs.next()) {
                    int vendorID = Integer.parseInt(rs.getString("vendorId"));
                    String vendorName = rs.getString("name");
                    String vendorLoc = rs.getString("location");
                    int vendorRep = Integer.parseInt(rs.getString("rep"));
                    String vendorPhone = rs.getString("phone");

                    Vendor vendor = new Vendor(vendorID, vendorName, vendorLoc, vendorRep, vendorPhone);
                    return vendor;
                }

            }catch (Exception e){
                System.out.println("Error Building Vendor" + '\n');
                e.printStackTrace();
            }
        }
        System.out.println("Error: Vendor doesn't exist");
        return null;

    }

    //gets the list of vendors from a query
    private static ArrayList<String> parseResultSetList(ResultSet rs){
        ArrayList<String> vendors = new ArrayList<String>();

        if (rs != null){
            try {
                while (rs.next()) {
                    String vendorName = rs.getString("name");

                    //int vendorID = Integer.parseInt(rs.getString("vendorId"));
                    //String vendorLoc = rs.getString("location");
                    //int vendorRep = Integer.parseInt(rs.getString("rep"));
                    //String vendorPhone = rs.getString("phone");

                    //Vendor vendor = new Vendor(vendorID, vendorName, vendorLoc, vendorRep, vendorPhone);
                    vendors.add(vendorName);
                }

            }catch (Exception e){
                System.out.println("Error Getting Vendor Name" + '\n');
                e.printStackTrace();
            }
        }

        return vendors;
    }

    //Getters and Setters//

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

    public int getRep() {
        return rep;
    }

    public void setRep(int rep) {
        this.rep = rep;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
