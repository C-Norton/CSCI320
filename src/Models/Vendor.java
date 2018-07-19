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
    private String rep;
    private String phone;

    public Vendor(int id, String name, String location, String rep, String phone)
    {
        this.id = id;
        this.name = name;
        this.location = location;
        this.rep = rep;
        this.phone = phone;
    }

    //Queries//

    //select all for a vendor based on id
    //parsing method still included but this just returns the ResultSet right now
    public static Vendor getSingleVendorQuery(DatabaseController dbController, StatementTemplate stmtUtil, int id){

        Statement stmt = StatementTemplate.newNullStatement();
        ResultSet rs = null;
        Vendor vendor = null;
        String selectVendor = "SELECT * FROM Vendor WHERE vendorId =" + id;
        //create query statement
        try {
            stmt = StatementTemplate.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Fetch Statement for Vendor");
        }
        //execute and get results of query
        try {
            rs = dbController.ExecuteSelectQuery(stmt, selectVendor);
        }catch(Exception e){
            System.out.println("Error Executing Select Query for Vendor");
        }
        vendor = parseResultSet(rs).get(0); // parsing the query
        return vendor;
    }

    //selects names of all vendors
    public static ResultSet getVendorNames(DatabaseController dbController, StatementTemplate stmtUtil){

        Statement stmt = StatementTemplate.newNullStatement();
        ResultSet rs = null;

        String selectAllVendors = "SELECT Name FROM Vendor";

        //create query statement
        try {
            stmt = StatementTemplate.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Fetch Statement for Vendor");
        }

        //execute and get results of query
        try {
            rs = dbController.ExecuteSelectQuery(stmt, selectAllVendors);
        }catch(Exception e){
            System.out.println("Error Executing Select Query for Vendor");
        }
        return rs;
    }


    //Utils//

    //returns an arraylist of all vendors in the result set
    private static ArrayList<Vendor> parseResultSet(ResultSet rs){
        ArrayList<Vendor> vendors = null;
        if (rs != null){
            try {
                vendors = new ArrayList<>();
                while(rs.next()) {
                    int vendorID = rs.getInt(1);
                    String vendorName = rs.getString(2);
                    String vendorLoc = null;
                    String vendorRep = null;
                    String vendorPhone = null;
                    try {
                        vendorLoc = rs.getString(3);
                        vendorPhone = rs.getString(4);
                        vendorRep = rs.getString(5);
                    }catch(NullPointerException e){
                        System.out.println();
                    }
                    vendors.add(new Vendor(vendorID, vendorName, vendorLoc, vendorRep, vendorPhone));
                }
            } catch (Exception e) {
                System.out.println("Error Building Vendor" + '\n');
                System.out.println(e.getMessage());
            }
        }
        return vendors;
    }

    //gets the list of vendors from a query
    public static ArrayList<String> parseResultSetList(ResultSet rs){
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

    public String getRep()
    {
        return rep;
    }

    public void setRep(String rep)
    {
        this.rep = rep;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
