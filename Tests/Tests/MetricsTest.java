package Tests;

import Controllers.DatabaseController;
import Models.Metrics;
import Utilities.StatementTemplate;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

public class MetricsTest {

    Connection conn;
    DatabaseController dbController;
    StatementTemplate stmtUtil;

    private void initialize() throws Exception{

        conn = DriverManager.getConnection("jdbc:h2:./Tests", "sa", "");

        dbController = new DatabaseController(conn);
        stmtUtil = new StatementTemplate(conn);


        dbController.InitializeNewDatabaseInstance();
    }


    @Test
    void topTwentyProdStoreTest() throws Exception{
        initialize();

        int store_Id = 1; //testing store with id 1

        ResultSet rs = Metrics.TopTwentyProdStore(store_Id);
        assertNotNull(rs);
        System.out.println(rs);
        System.out.println("\n                          || Top Twenty Products For Store " + store_Id + " ||\n");
        while(rs.next()){
            String string = String.format("%1$15s %2$-40s  %3$10s", rs.getString(1), "| "+rs.getString(2), "|      "+rs.getString(3));
            //System.out.println(rs.getString("Name") + " | " + rs.getString("Total_Sold"));
            System.out.println(string);
        }
        /*
        System.out.println(rs.getMetaData().getColumnName(1));
        System.out.println(rs.getMetaData().getColumnName(2));
        System.out.println(rs.getMetaData().getColumnDisplaySize(1));
        System.out.println(rs.getMetaData().getColumnDisplaySize(2));
        System.out.println(rs.next());
        System.out.println(rs);
        System.out.println(rs.next());
        System.out.println(rs);
        System.out.println(rs.getString("NAME"));
        System.out.println(rs.getString("TOTAL_SOLD"));
        */

    }

    @Test
    void TopTwentyProdOverallTest() throws Exception{
        initialize();

        ResultSet rs = Metrics.TopTwentyProdOverall();
        assertNotNull(rs);
        System.out.println(rs);
        System.out.println("\n                          || Top Twenty Products ||\n");
        while(rs.next()){
            String string = String.format("%1$15s %2$-40s  %3$10s", rs.getString(1), "| "+rs.getString(2), "|      "+rs.getString(3));
            System.out.println(string);
        }

    }

    @Test
    void HowManyStoresDoesProdAOutsellProdBTest() throws Exception{
        initialize();

        String a = "Tea - Black Currant";
        String b = "Wheat bread";

        ResultSet rs = Metrics.HowManyStoresDoesProdAOutsellProdB(a,b);
        assertNotNull(rs);
        System.out.println(rs);
        System.out.println("\n                          || Product "+a+" Vs. Product "+b+" ||\n");
        String header = String.format("%1$15s %2$-30s  %3$15s %4$-10s", "UPC", "| "+"ProductName", "|"+"StoreId", "| "+"Quantity");
        System.out.println(header);
        while(rs.next()){
            String string = String.format("%1$15s %2$-30s  %3$15s %4$-10s", rs.getString(1), "| "+rs.getString(2), "|      "+rs.getString(3), "| "+rs.getString(4));
            System.out.println(string);
        }

    }


}
