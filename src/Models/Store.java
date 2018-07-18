package Models;

import Controllers.DatabaseController;
import Utilities.StatementTemplate;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Michael on 7/6/2018.
 */
public class Store {
    public static ResultSet retrieveStores(DatabaseController dbController, StatementTemplate stmtUtil){
        Statement stmt = stmtUtil.newNullStatement();
        ResultSet rs = null;

        String selectCustomer = "SELECT storeID, name FROM Store";

        //create query statement
        try {
            stmt = stmtUtil.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Fetch Statement for Stores");
            System.out.println(e.getMessage());
        }

        //execute and get results of query
        try {
            rs = dbController.ExecuteSelectQuery(stmt, selectCustomer);
        }catch(Exception e){
            System.out.println("Error Executing Select Query for Stores");
            System.out.println(e.getMessage());
        }

        return rs;
    }
}
