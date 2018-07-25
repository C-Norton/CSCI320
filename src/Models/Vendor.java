package Models;

import Controllers.DatabaseController;

import java.sql.ResultSet;
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
    public static ResultSet getSingleVendorQuery(int id)
    {

        String selectVendor = "SELECT * FROM Vendor WHERE vendorId =" + id;
        //create query statement

        return DatabaseController.SelectQuery(selectVendor, true);

    }


    //Utils//


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

    public String getPhone() {
        return phone;
    }

}
