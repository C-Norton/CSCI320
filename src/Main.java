import java.sql.*;
import Controllers.DatabaseController;
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
        System.out.println(cust.getCreditCard() + " ");

        conn.close();
    }

}
