package Models;

import Controllers.DatabaseController;
import Utilities.RSParser;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Michael on 7/6/2018.
 */
public final class Inventory
{

    //checks if product is stocked in the store inventory
    public static int productStock(int storeId, String upc){

        ResultSet rs = DatabaseController.SelectQuery("SELECT quantity FROM Inventory WHERE storeId = " + storeId +
                                                      " and productUPC = " + upc, true);
        ArrayList<String[]> results = RSParser.rsToStringHeaders(rs);
        if(results == null || results.size() < 2){
            return 0;
        }
        return Integer.parseInt(results.get(1)[0]);
    }

}
