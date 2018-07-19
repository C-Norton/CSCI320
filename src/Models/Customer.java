package Models;

import Controllers.DatabaseController;
import Utilities.StatementTemplate;

import java.sql.ResultSet;
import java.sql.Statement;

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

    //returns a Customer instance, if id does not exist it returns an anon
    public static ResultSet getSingleCustomerInfoQuery(DatabaseController dbController, StatementTemplate stmtUtil, int
            id)
    {

        Statement stmt = StatementTemplate.newNullStatement();
        ResultSet rs = null;

        String selectCustomer = "SELECT * FROM FrequentShopper WHERE userId =" + id ;

        //create query statement
        try {
            stmt = StatementTemplate.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Fetch Statement for Customer");
        }

        //execute and get results of query
        try {
            rs = dbController.ExecuteSelectQuery(stmt, selectCustomer);
        }catch(Exception e){
            System.out.println("Error Executing Select Query for Customer");
        }
        return rs;
    }

    /*
    //see if credentials combination exists in table, if so returns true and make a new customer object
    public static boolean logIn(DatabaseController dbController, StatementTemplate stmtUtil, String username, String password){

        Statement stmt = StatementTemplate.newNullStatement();
        ResultSet rs = null;

        String selectCustomer = "SELECT * FROM FrequentShopper WHERE username = \'" + username + "\' and password = \'" + password + "\'";

        //create query statement
        try {
            stmt = StatementTemplate.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Fetch Statement for Customer");
            System.out.println(e.getMessage());
            return false;
        }

        //execute and get results of query
        try {
            rs = dbController.ExecuteSelectQuery(stmt, selectCustomer);
        }catch(Exception e){
            System.out.println("Error Executing Select Query for Customer");
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
    */

/*
    public static void updateCustomerInfoQuery(DatabaseController dbController, StatementTemplate stmtUtil,Customer customer){
        Statement stmt = stmtUtil.newNullStatement();

        String updateName = "UPDATE FrequentShopper SET name ="+"'"+customer.getName()+"'"+" WHERE userId="+ customer.getId();
        String updateAddress = "UPDATE FrequentShopper SET address ="+"'"+customer.getAddress()+"'"+ " WHERE userId="+ customer.getId();
        String updateCard = "UPDATE FrequentShopper SET creditCard ="+"'"+customer.getCreditCard()+"'"+ " WHERE userId="+ customer.getId();
        String updatePhone = "UPDATE FrequentShopper SET phoneNum ="+"'"+customer.getPhone()+"'"+ " WHERE userId="+ customer.getId();

        //create query statement
        try {
            stmt = stmtUtil.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Update Statement for Customer");
        }

        //execute update
        try {
            dbController.ExecuteUpdateQuery(stmt, updateName);
            dbController.ExecuteUpdateQuery(stmt, updateAddress);
            dbController.ExecuteUpdateQuery(stmt, updateCard);
            dbController.ExecuteUpdateQuery(stmt, updatePhone);
        }catch(Exception e){
            System.out.println("Error Executing Update Query for Customer");
            e.printStackTrace();
        }
    }
*/


}
