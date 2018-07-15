import java.sql.*;
import Controllers.DatabaseController;
import Controllers.GuiController;
import Models.Customer;
import Utilities.StatementTemplate;

public class Main {

    public static void main(String[] a) throws Exception {

        Connection conn = DriverManager.getConnection("jdbc:h2:./test", "sa", "");

        DatabaseController dbController = new DatabaseController(conn);
        StatementTemplate stmtUtil = new StatementTemplate(conn);


        dbController.InitializeNewDatabaseInstance();

        Customer cust =  Customer.getSingleCustomerInfoQuery(dbController,stmtUtil,4);

        System.out.println(cust.getName() + " ");
        System.out.println(cust.getAddress() + " ");
        System.out.println(cust.getCreditCard() + '\n');

        cust.setName("Jillian");
        cust.setCreditCard("999999999");

        Customer.updateCustomerInfoQuery(dbController, stmtUtil, cust);

        Customer custAgain =  Customer.getSingleCustomerInfoQuery(dbController,stmtUtil,4);

        System.out.println(custAgain.getName() + " ");
        System.out.println(custAgain.getAddress() + " ");
        System.out.println(custAgain.getCreditCard() + " ");
        GuiController gui = new GuiController();
        conn.close();
    }

}
