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
    public static boolean existsCustomer(int customerId)
    {

        String query = "select userid from frequentshopper where userid = " + customerId;
        ResultSet rs = DatabaseController.SelectQuery(query);
        ArrayList<String[]> parsedRs = RSParser.rsToStringHeaders(rs);
        return parsedRs != null && parsedRs.get(1)[0].equals(String.valueOf(customerId));
    }

    public static ResultSet getOrdersOfCustomer(int customerId)
    {

        String query = "with prod as (Select * from orders natural join prodquantities "
                       + " WHERE userId =" + customerId + ")  Select prod.ordernum, prod.userid, "
                       + " prod.productupc, prod.quantity, product.name, product.brand from prod "
                       + " join product on prod.productupc = product.upc order by prod.ordernum";
        return DatabaseController.SelectQuery(query);
    }

}
