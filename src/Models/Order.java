package Models;

import Controllers.DatabaseController;
import Utilities.StatementTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Michael on 7/6/2018.
 */
public class Order {

    private Order()
    {

    }

    //Queries//
    //TODO Find out if we want to get all of the products ordered with the intital query or do we want just the
    //TODO ordernum, storeid, and customerId to be selectable and when selected itll show all of the products purchased

    //make an order
    public static boolean makeOrder(ArrayList<ProductQuantity> items, int customerId, int storeId){
        //updates Order table
        Statement stmt = StatementTemplate.newNullStatement();
        String insertOrder = "INSERT INTO Order(userId, storeId) VALUES (" + customerId + ", " + storeId + ")";

        int orderNumber = 1;
        ResultSet orderNum = DatabaseController.MakeSelQuery("SELECT max(orderNum) from Order");
        if (orderNum != null){
            try {
                orderNum.next();
                orderNumber = orderNum.getInt(1) + 1;
            }catch (SQLException e){
                System.out.println("Error Generating Order Number");
                System.out.println(e.getMessage());
                e.printStackTrace();
                return false;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (ProductQuantity item : items){
            stringBuilder.append("INSERT INTO ProductQuantities VALUES (" + orderNumber + ", " + item.getProductUPC()
            + ", " + item.getQuantity() + ");");
        }
        String insertItems = stringBuilder.toString();

        try {
            stmt = StatementTemplate.connStatement(stmt);
        }catch(SQLException e){
            System.out.println("Error Creating Fetch Statement for Order");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        try {
            DatabaseController.ExecuteUpdateQuery(stmt, insertOrder);
        }catch (SQLException e){
            System.out.println("Error Updating Order Table");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        try{
            DatabaseController.ExecuteUpdateQuery(stmt, insertItems);
        }catch (SQLException e){
            System.out.println("Error Updating ProductQuantities Table");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }



    //get all orders for a store
    public static ResultSet getOrdersByStoreIdQuery(int id)
    {

        Statement stmt = StatementTemplate.newNullStatement();
        ResultSet rs = null;

        String selectCustomer = "SELECT * FROM Orders WHERE storeId =" + id;

        //create query statement
        try {
            stmt = StatementTemplate.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Fetch Statement for Order");
        }

        //execute and get results of query
        try {
            rs = DatabaseController.ExecuteSelectQuery(stmt, selectCustomer);
        }catch(Exception e){
            System.out.println("Error Executing Query for Order");
            e.printStackTrace();
        }

        //return parseResultSet(rs); //used for when returning an arraylist
        return rs;
    }

    //Utils//

    //create cust object based off query
}
