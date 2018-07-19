package Models;
import Utilities.StatementTemplate;
import Controllers.DatabaseController;

import java.util.ArrayList;
import java.sql.*;

/**
 * Created by Michael on 7/6/2018.
 */
public class Customer {

    private int id;
    private String name;
    private String address;
    private String phone;
    private String creditCard;
    private Cart cart;
    private ArrayList<Order> orderHistory;

    private static Customer customer;//current logged in customer


    public Customer(int id, String name, String address, String phone, String creditCard, Cart cart, ArrayList<Order> orderHistory ){

        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.creditCard = creditCard;
        this.cart = cart;
        this.orderHistory = orderHistory;
    }

    public boolean isLoggedIn(){
        return customer!=null;
    }
    //--Queries--//

    //returns a Customer instance, if id does not exist it returns an anon
    public static Customer getSingleCustomerInfoQuery(DatabaseController dbController, StatementTemplate stmtUtil, int id){

        Statement stmt = stmtUtil.newNullStatement();
        ResultSet rs = null;

        String selectCustomer = "SELECT * FROM FrequentShopper WHERE userId =" + id ;

        //create query statement
        try {
            stmt = stmtUtil.connStatement(stmt);
        }catch(Exception e){
            System.out.println("Error Creating Fetch Statement for Customer");
        }

        //execute and get results of query
        try {
            rs = dbController.ExecuteSelectQuery(stmt, selectCustomer);
        }catch(Exception e){
            System.out.println("Error Executing Select Query for Customer");
        }

        Customer customer = parseResultSet(dbController, stmtUtil,rs);

        return customer;
    }

    //see if credentials combination exists in table, if so returns true and make a new customer object
    public static boolean logIn(DatabaseController dbController, StatementTemplate stmtUtil, String username, String password){

        Statement stmt = stmtUtil.newNullStatement();
        ResultSet rs = null;

        String selectCustomer = "SELECT * FROM FrequentShopper WHERE username = \'" + username + "\' and password = \'" + password + "\'";

        //create query statement
        try {
            stmt = stmtUtil.connStatement(stmt);
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

        Customer.customer = parseResultSet(dbController, stmtUtil,rs);

        return true;
    }


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


    //--Utils--//

    //create cust object based off query
    private static Customer parseResultSet(DatabaseController dbController, StatementTemplate stmtUtil, ResultSet rs) {
        if (rs != null) {

            try {

                while (rs.next()) {
                    int custId = Integer.parseInt(rs.getString("userId"));
                    String custName = rs.getString("name");
                    String custAddress = rs.getString("address");
                    String custPhone = rs.getString("phoneNum");
                    String custCard = rs.getString("creditCard");


                    Cart custCart = new Cart(5);

                    ArrayList<Order> custHistory = Order.getOrdersByCustIdQuery(dbController,stmtUtil,custId);

                    //triggers query to get order history
                    Customer customer = new Customer(custId,custName,custAddress,custPhone,custCard,custCart,custHistory);

                    return customer;
                }
            } catch (Exception e) {
                System.out.print("Error Building Customer" + '\n');
                e.printStackTrace();
            }
        }

        Cart emptyCart = new Cart(5);
        ArrayList<Order> noOrders = new ArrayList<Order>();
        Customer customer = new Customer(0, "Anon", "", "", "", emptyCart, noOrders);

        return customer;
    }


    //--Getters and Setters--//

    public void setName(String newName){
        this.name = newName;
    }

    public void setAddress(String newAddress){
        this.address = newAddress;
    }

    public void setPhone(String newPhone){
        this.phone = newPhone;
    }

    public void setCreditCard(String newCard){
        this.creditCard = newCard;
    }

    public void setCart(Cart newCart){
        this.cart = newCart;
    }

    public void addOrderToHistory(Order newOrder){
        this.orderHistory.add(newOrder);
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getAddress(){
        return this.address;
    }

    public String getPhone(){
        return this.phone;
    }

    public String getCreditCard(){
        return this.creditCard;
    }

    public Cart getCart(){
        return this.cart;
    }
 
    public ArrayList<Order> getOrderHistory(){
        return this.orderHistory;
    }




}
