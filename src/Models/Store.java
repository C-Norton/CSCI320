package Models;

import Controllers.DatabaseController;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Michael on 7/6/2018.
 */
public final class Store
{

    private Store()
    {

    }

    public static boolean existsStore(int storeId)
    {

        String query = "select storeId from Store where storeId = " + storeId;
        ResultSet rs = DatabaseController.SelectQuery(query);
        try
        {
            return rs != null && rs.next();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
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

    public static ResultSet getOrdersFromStore(int id)
    {


        String Query = "with prod as (Select * from orders natural join prodquantities "
                       + " WHERE storeId =" + id + ")  Select prod.ordernum, prod.userid, "
                       + " prod.productupc, prod.quantity, product.name, product.brand from prod "
                       + " join product on prod.productupc = product.upc order by prod.ordernum";


        return DatabaseController.SelectQuery(Query);

    }

}
