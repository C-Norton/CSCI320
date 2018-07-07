package Models;

import Controllers.DatabaseController;
import Utilities.StatementTemplate;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Michael on 7/6/2018.
 */
public class Order {

    private int orderNum;
    private int storeId;
    private int customerId;
    private ArrayList<productQuantity> orderedProducts;

    public Order(int orderNum, int storeId, int customerId, ArrayList<productQuantity> orderedProducts){
        this.storeId = storeId;
        this.customerId = customerId;
        this.orderedProducts = orderedProducts;
        this.orderNum = orderNum;
    }

    //get all columns for a customer
    public static ArrayList<Order> getOrdersByCustIdQuery(DatabaseController dbController, StatementTemplate stmtUtil, int id){

        Statement stmt = stmtUtil.newNullStatement();
        ResultSet rs = null;

        String selectCustomer = "SELECT * FROM Orders WHERE userId =" + id;

        //create query statement
        try {
            stmt = stmtUtil.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Fetch Statement for Order");
        }

        //execute and get results of query
        try {
            rs = dbController.ExecuteSelectQuery(stmt, selectCustomer);
        }catch(Exception e){
            System.out.println("Error Executing Query for Order");
            e.printStackTrace();
        }

        return parseResultSet(rs);
    }

    //create cust object based off query
    private static ArrayList<Order> parseResultSet(ResultSet rs) {

        ArrayList<Order> orderHistory = new ArrayList<Order>();

        if (rs != null) {

            try {

                while (rs.next()) {
                    int orderNum = Integer.parseInt( rs.getString("orderId") );
                    int custId = Integer.parseInt( rs.getString("userId") );
                    int storeId = Integer.parseInt( rs.getString("shopId") );

                    Order order = new Order(orderNum,storeId,custId, null);
                    orderHistory.add(order);

                }
            } catch (Exception e) {
                System.out.print("Error Building Order" + '\n');
                e.printStackTrace();
            }
        }

        return orderHistory;
    }

    public String viewOrderDetails(){
        String orderDetails = "Order Number:" + this.orderNum +'\n';

        for(productQuantity product:orderedProducts){
            orderDetails += product.getProduct().getName()+'x'+product.getQuantity();
        }

        return orderDetails;
    }
}
