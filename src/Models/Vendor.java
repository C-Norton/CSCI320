package Models;

import Controllers.DatabaseController;

import java.sql.ResultSet;

/**
 * Created by Michael on 7/6/2018.
 */
public class Vendor {


    //Queries//

    //select all for a vendor based on id
    //parsing method still included but this just returns the ResultSet right now
    public static ResultSet getSingleVendorQuery(int id)
    {

        String selectVendor = "SELECT * FROM Vendor WHERE vendorId =" + id;
        //create query statement

        return DatabaseController.SelectQuery(selectVendor);

    }




    //Getters and Setters//


}
