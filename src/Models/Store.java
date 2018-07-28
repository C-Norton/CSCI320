package Models;

import Controllers.DatabaseController;
import Utilities.RSParser;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Michael on 7/6/2018.
 */
public final class Store
{

    private Store()
    {

    }

    public static Boolean existsStore(int storeId)
    {

        String query = "select storeId from Store where storeId = " + storeId;
        ResultSet rs = DatabaseController.SelectQuery(query);
        ArrayList<String[]> parsedRs = RSParser.rsToStringHeaders(rs);
        return parsedRs != null && parsedRs.get(1)[0].equals(String.valueOf(storeId));
    }

    public static ResultSet getInventoryMetadata(int id)
    {

        return DatabaseController.SelectQuery("Select product.UPC, product.Name, product.Brand, product.Price from "
                                              + "product join inventory on product.UPC = inventory.productUPC where "
                                              + "storeId="
                                              + id);
    }

    //gets a list of all stores
    public static ResultSet retrieveStores()
    {


        ResultSet rs = null;

        String selectCustomer = "SELECT storeID, name FROM Store";


        //execute and get results of query

        rs = DatabaseController.SelectQuery(selectCustomer);


        return rs;
    }

    //get one store by store id, returns a result set
    public static ResultSet retrieveStoreById(int storeid)
    {


        ResultSet rs = null;

        String selectCustomer = "SELECT * FROM Store WHERE storeId = " + storeid;


        rs = DatabaseController.SelectQuery(selectCustomer);


        return rs;
    }

}
