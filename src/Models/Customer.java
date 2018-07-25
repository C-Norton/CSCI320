package Models;

import Controllers.DatabaseController;
import Utilities.RSParser;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Michael on 7/6/2018.
 */
public final class Customer
{

    //private static Customer customer;//current logged in customer

    private Customer()
    {

    }


    //--Queries--//
    //return boolean of whether the customer exists
    public static boolean existsCustomer(int customerId){
        String query = "select userid from frequentshopper where userid = " + customerId;
        ResultSet rs = DatabaseController.SelectQuery(query, false);
        ArrayList<String[]> parsedRs = RSParser.rsToStringHeaders(rs);
        return parsedRs != null && parsedRs.get(1)[0].equals(String.valueOf(customerId));
    }

    //returns a Customer instance, if id does not exist it returns an anon
    public static ResultSet getSingleCustomerInfoQuery(int id)
    {

        String selectCustomer = "SELECT * FROM FrequentShopper WHERE userId =" + id ;


        return DatabaseController.SelectQuery(selectCustomer, false);
    }

}
