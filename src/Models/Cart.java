package Models;

import Controllers.DatabaseController;
import Utilities.StatementTemplate;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Michael on 7/6/2018.
 */
public class Cart {
    private ArrayList<ProductQuantity> boughtProducts;
    private int storeId;
    private static Cart currentCart;

    private Cart(int storeId){
        this.boughtProducts = new ArrayList<ProductQuantity>();
        this.storeId = storeId;
    }

    //updates the current cart
    public static void newCart(int storeId){
        Cart.currentCart = new Cart(storeId);
    }

    public static boolean addItem(String upc){
        return false;
    }

    public void submitOrder(DatabaseController dbController, StatementTemplate stmtUtil, int customerId){

        String getLatestOrderNum = "orderNum";

        int latestOrderNum = getLatestId(dbController, stmtUtil,getLatestOrderNum);

        Statement stmt = StatementTemplate.newNullStatement();

        //adds new order

        //adds new prodQuant
        for(ProductQuantity prodQuant:this.boughtProducts ) {

/*
            //TODO: Rewrite
            //latestOrderId++;
            String orderItem = "INSERT INTO  orders VALUES (" + Integer.toString(customerId)+
                    Integer.toString(storeId)+product.getProduct().getName()+product.getQuantity()+")";

            try{
                stmt = stmtUtil.connStatement(stmt);
                dbController.ExecuteUpdateQuery(stmt,orderItem);
            }catch(Exception e){

            }*/
        }
    }

    //--Util--//

    //returns the max value of a given column
    private int getLatestId(DatabaseController dbController, StatementTemplate stmtUtil, String getLatestItem){

        Statement stmt = StatementTemplate.newNullStatement();
        ResultSet rs = null;

        String getLatestId = "SELECT max("+getLatestItem+") FROM orders";

        //create query statement
        try {
            stmt = StatementTemplate.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Select Statement");
        }

        //execute and get results of query
        try {
            rs = DatabaseController.ExecuteSelectQuery(stmt, getLatestId);
        }catch(Exception e){
            System.out.println("Error Executing Query  for Cart");
        }

        int latestOrderId = parseResultSet(rs);

        return latestOrderId;
    }

    private int parseResultSet(ResultSet rs){
        try{
            if (rs.next()) {
                return rs.getInt(1);
            }
        }catch (Exception e){
            System.out.println("Error parsing ResultSet");
        }
        return 0;
    }
    
    //--Getters and Setters--//

    public void addItem(ProductQuantity product){
        boughtProducts.add(product);
    }
    
    public void removeItem(ProductQuantity product){
        boughtProducts.remove(product);
    }

    public ArrayList<ProductQuantity>getCartContents(){
        return boughtProducts;
    }
    
    public void setStore(int storeId){
        this.storeId = storeId;
    }
}
