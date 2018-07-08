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


    private ArrayList<productQuantity> boughtProducts;
    private int storeId;


    public Cart(int storeId){
        this.boughtProducts = new ArrayList<productQuantity>();
        this.storeId = storeId;
    }


    public void submitOrder(DatabaseController dbController, StatementTemplate stmtUtil, int customerId){

        String getLatestOrderNum = "orderNum";

        int latestOrderNum = getLatestId(dbController, stmtUtil,getLatestOrderNum);

        Order checkout = new Order(latestOrderNum,this.storeId, customerId, this.boughtProducts);
        Statement stmt = stmtUtil.newNullStatement();

        //String getLatestOrderId = "orderId";

        //int latestOrderId = getLatestId(dbController, stmtUtil,getLatestOrderId);

        for(productQuantity product:this.boughtProducts ){


            //TODO: Rewrite
            //latestOrderId++;
            String orderItem = "INSERT INTO  orders VALUES (" + Integer.toString(customerId)+
                    Integer.toString(storeId)+product.getProduct().getName()+product.getQuantity()+")";

            try{
                stmt = stmtUtil.connStatement(stmt);
                dbController.ExecuteUpdateQuery(stmt,orderItem);
            }catch(Exception e){

            }

        }
    }

    //--Util--//

    //returns the max value of a given column
    private int getLatestId(DatabaseController dbController, StatementTemplate stmtUtil, String getLatestItem){
        Statement stmt = stmtUtil.newNullStatement();
        ResultSet rs = null;

        String getLatestId = "SELECT max("+getLatestItem+") FROM orders";

        //create query statement
        try {
            stmt = stmtUtil.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Select Statement");
        }

        //execute and get results of query
        try {
            rs = dbController.ExecuteSelectQuery(stmt, getLatestId);
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

    public void addItem(productQuantity product){
        boughtProducts.add(product);
    }
    
    public void removeItem(productQuantity product){
        boughtProducts.remove(product);
    }

    public ArrayList<productQuantity>getCartContents(){
        return boughtProducts;
    }
    
    public void setStore(int storeId){
        this.storeId = storeId;
    }
}
