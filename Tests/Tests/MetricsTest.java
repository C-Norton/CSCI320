package Tests;

import Controllers.DatabaseController;
import Models.Metrics;
import Utilities.StatementTemplate;
import Utilities.StatementType;
import org.junit.jupiter.api.Test;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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

        String a = "810770474429";
        String b = "915693126004";

        ResultSet rs = Metrics.HowManyStoresDoesProdAOutsellProdB(a,b);
        assertNotNull(rs);
        System.out.println(rs);
        System.out.println("\n                          || Product "+a+" Vs. Product "+b+" ||\n");
        String header = String.format("%1$15s %2$-30s  %3$-15s %4$-10s", "UPC", "| "+"ProductName", "|"+" StoreId", "| "+"Quantity");
        System.out.println(header);
        while(rs.next()){
            String string = String.format("%1$15s %2$-30s  %3$-15s %4$-10s", rs.getString(1), "| "+rs.getString(2), "|       "+rs.getString(3), "| "+rs.getString(4));
            System.out.println(string);
        }

    }

    @Test
    void TopSalesForStoresTest() throws Exception{
        initialize();

        ResultSet rs = Metrics.TopSalesForStores();
        assertNotNull(rs);
        System.out.println(rs);
        System.out.println("\n                          || List of Stores by Sales ||\n");
        String header = String.format("%1$15s %2$-35s %3$-10s %4$-10s", "StoreId", "| "+"Name", "| "+"Sales | ", "Revenue");
        System.out.println(header);
        while(rs.next()){
            String string = String.format("%1$15s %2$-35s %3$-10s %4$-10s", rs.getString(1), "| "+rs.getString(2), "| "+rs.getString(3)+" |", "$"+rs.getString(4));
            System.out.println(string);
        }

    }

    private float divisor(float[] data){
        float sum = 0;

        for(int i=0; i < 8; i++){
            sum += data[i];
        }
        float average = sum / data.length;
        float divisor = 0;
        for(int i=0; i < 8; i++){
            divisor += (data[i] - average) * (data[i] - average);
        }
        return divisor;
    }

    private float dividend(float[] data, float[] data1){
        float sum = 0;
        float sum1 = 0;
        for(int i=0; i < 8; i++){
            sum += data[i];
            sum1 += data1[i];
        }
        float average = sum / data.length;
        float average1 = sum1 / data.length;
        float dividend = 0;
        for(int i=0; i < 8; i++){
            dividend += (data[i] - average) * (data1[i] - average1);
        }
        return dividend;
    }

    @Test
    void LinearRegressionRevenueSalesTest() throws Exception{
        initialize();/*
        String query = "SELECT thetaOne, ybar - xbar * thetaOne AS thetaZero " +
                    "FROM (" +
                        "SELECT sum(convert(((Total_Sum - avg(Total_Sum)) " +
                        "* (Total_Money - avg(Total_Money))), decimal(18, 2))) " +
                        "/ sum(convert(((Total_Sum - avg(Total_Sum)) * (Total_Sum - avg(Total_Sum))), decimal(18, 2))) "
                        + "as thetaOne, max(avg(Total_Money)) as ybar, max(avg(Total_Sum)) as xbar " +
                        "FROM (" +
                            "SELECT storeId, sum(quantity) as Total_Sum, sum(money) as Total_Money " +
                            "FROM (" +
                                "WITH productWithOrderNum as ( " +
                                    "SELECT orderNum, quantity, Product.price * quantity as money " +
                                    "FROM Product join prodQuantities on " +
                                    "Product.UPC = prodQuantities.productUPC" +
                                "), " +
                                "OrdersWithStoreId as ( " +
                                    "SELECT Store.storeId, orderNum " +
                                    "FROM Store join Orders on " +
                                    "Store.storeId = Orders.storeId" +
                                ") " +
                                "SELECT OrdersWithStoreId.storeId, quantity, money " +
                                "FROM (" +
                                    "productWithOrderNum join OrdersWithStoreId on " +
                                    "productWithOrderNum.orderNum = OrdersWithStoreId.orderNum" +
                                ") " +
                            ") " +
                            "GROUP BY storeId" +
                        ")" +
                    ")"
        ;
        StatementType stmt =
                DatabaseController.getQueryType(query, false);
        assertEquals(StatementType.NONUPDATEABLESELECT, stmt);*/
        ResultSet rs;
        rs = Metrics.TopSalesForStores();
        float[] data = new float[8];
        float[] data1 = new float[8];
        int i = 0;
        while(rs.next()){
            data1[i] = rs.getFloat(4);
            data[i] = rs.getFloat(3);
            i++;
        }
        float mockThetaOne = dividend(data, data1) / divisor(data);
//        System.out.println(dividend(data,data1));
//        System.out.println(divisor(data));
        System.out.println(mockThetaOne);
        rs = Metrics.LinearRegressionRevenueSales();

        rs.next();

        System.out.println(rs.getBigDecimal(2));
        assertEquals(mockThetaOne, rs.getBigDecimal(2).floatValue());
    }

    @Test
    void genericUnivariateLinearRegression() throws Exception{
        initialize();

        ResultSet rs;
        rs = Metrics.GenericUnivariateLinearRegression("FrequentShopper", "doesNotCompute", "userId");
        assertNull(rs);
        rs = Metrics.GenericUnivariateLinearRegression("FrequentShopper", "userId", "userId");
        assertNotNull(rs);
        rs.next();
        assertEquals(0, rs.getFloat(1));
        assertEquals(1, rs.getFloat(2));
        rs = Metrics.GenericUnivariateLinearRegression("SELECT * FROM FrequentShopper", "userId", "userId");
        assertNotNull(rs);
        rs.next();
        assertEquals(0, rs.getFloat(1));
        assertEquals(1, rs.getFloat(2));
    }
}
