package Models;

import Controllers.DatabaseController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Michael on 7/6/2018.
 */
public final class Order
{

    private Order()
    {

    }


    //TODO: I think this needs a rewrite
    public static boolean makeOrder(ArrayList<ProductQuantity> items, int customerId, int storeId)
    {
        //updates Order table
        Statement stmt;
        String insertOrder = "INSERT INTO Order(userId, storeId) VALUES (" + customerId + ", " + storeId + ")";

        int orderNumber = 1;
        ResultSet orderNum = DatabaseController.SelectQuery("SELECT max(orderNum) from Order");
        if (orderNum != null)
        {
            try
            {
                orderNum.next();
                orderNumber = orderNum.getInt(1) + 1;
            }
            catch (SQLException e)
            {
                System.out.println("Error Generating Order Number");
                System.out.println(e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (ProductQuantity item : items)
        {
            stringBuilder.append("INSERT INTO ProductQuantities VALUES (" + orderNumber + ", " + item.getProductUPC()
                                 + ", " + item.getQuantity() + ");");
        }
        String insertItems = stringBuilder.toString();


        int rowsupdated = DatabaseController.UpdateQuery(insertOrder);
        if (rowsupdated == -1)
        {
            System.out.println("Error updating orders table");
            return false;
        }
        rowsupdated = DatabaseController.UpdateQuery(insertItems);

        if (rowsupdated == -1)
        {
            System.out.println("Error Updating ProductQuantities Table");
            return false;
        }
        return true;
    }

    //get all orders for a store
    public static ResultSet getOrdersByStoreIdQuery(int id)
    {


        String selectCustomer = "SELECT * FROM Orders WHERE storeId =" + id;

        //create query statement

        return DatabaseController.SelectQuery(selectCustomer);

    }

}
