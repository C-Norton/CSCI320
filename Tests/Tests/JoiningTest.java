package Tests;

import Controllers.DatabaseController;
import Utilities.RSParser;
import Utilities.StatementTemplate;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

class JoiningTest
{
    Connection conn;
    DatabaseController dbController;
    StatementTemplate stmtUtil;
    boolean result;

    @Test
    void retrieveInventory() throws Exception
    {

        initialize();
        ResultSet rs = (DatabaseController.SelectQuery("select Orders.orderNum,prodQuantities.productUPC,"
                                                       + "prodQuantities.Quantity from ("
                                                       + "Orders join "
                                                       + "prodQuantities on"
                                                       + " Orders.orderNum =prodQuantities.orderNum ) where userId "
                                                       + "= '3694'"
                                                       + ""));//get all the
        // order numbers and all the
        ArrayList<String[]> foo = RSParser.rsToStringHeaders(rs);
        for (int i = 0; i < foo.size(); i++)
        {
            for (int j = 0; j < foo.get(i).length; j++)
            {
                System.out.print(foo.get(i)[j] + "\t");
            }
            System.out.print("\n");
        }
        // products
        // of those
    }

    private void initialize() throws Exception
    {

        conn = DriverManager.getConnection("jdbc:h2:./Tests", "sa", "");

        dbController = new DatabaseController(conn);
        stmtUtil = new StatementTemplate(conn);
        dbController.InitializeNewDatabaseInstance();

        //order numbers for user 3694
        //So this means we need to join orders and orderquantities
        //

    }
}