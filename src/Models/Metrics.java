package Models;

import Controllers.DatabaseController;
import Utilities.StatementType;

import java.sql.ResultSet;

/**
 * Created by Channing Helmling-Cornell on 7/19/2018.
 * <p>
 * Metrics is a class for more complicated queries on the database for getting overall statistics about the data. See
 * page 3 of the retail domain document, "Queries" section for more details
 */
public final class Metrics
{

    public static ResultSet TopTwentyProdStore(int store_Id) //calls productWithOrderNum view
    {

        return DatabaseController.SelectQuery("SELECT UPC, Name, sum( quantity ) as Total_Sold " +
                                              "FROM productWithOrderNum join Orders on " +
                                              "productWithOrderNum.orderNum = Orders.orderNum " +
                                              "WHERE storeId = " + store_Id + " " +
                                              "GROUP BY UPC, Name " +
                                              "ORDER BY Total_Sold DESC " +
                                              "LIMIT 20");
    }

    public static ResultSet TopTwentyProdOverall() // calls productWithOrderNum view
    {

        return DatabaseController.SelectQuery("SELECT UPC, Name, sum( quantity ) as Total_Sold " +
                                              "FROM productWithOrderNum join Orders on " +
                                              "productWithOrderNum.orderNum = Orders.orderNum " +
                                              "GROUP BY UPC, Name " +
                                              "ORDER BY Total_Sold DESC " +
                                              "LIMIT 20");
    }

    public static ResultSet HowManyStoresDoesProdAOutsellProdB(String prodIdA, String prodIdB){ //doesn't use a view

        return DatabaseController.SelectQuery("WITH productWithOrderNumCompareProd as ( " +
                                              "SELECT UPC, name, orderNum, quantity " +
                                              "FROM Product join prodQuantities on " +
                                              "Product.UPC = prodQuantities.productUPC " +
                                              "WHERE Product.UPC = '" + prodIdA + "' or Product.UPC = '" + prodIdB + "' ) " +
                                              "SELECT UPC, Name, storeId, sum(quantity) as Total_Sum " +
                                              "FROM productWithOrderNumCompareProd join Orders on " +
                                              "productWithOrderNumCompareProd.orderNum = Orders.orderNum " +
                                              "GROUP BY UPC, Name, storeId " +
                                              "ORDER BY storeId DESC");

    }


    public static ResultSet TopSalesForStores(){ //uses orderWithStoreName view

        return DatabaseController.SelectQuery("WITH productQuantityAndOrderNum as ( " +
                                              "SELECT orderNum, quantity " +
                                              "FROM Product join prodQuantities on " +
                                              "Product.UPC = prodQuantities.productUPC) " +
                                              "SELECT orderWithStoreName.storeId, orderWithStoreName.name," +
                                              "sum(quantity) as Total_Sum " +
                                              "FROM productQuantityAndOrderNum join orderWithStoreName on " +
                                              "productQuantityAndOrderNum.orderNum = orderWithStoreName.orderNum " +
                                              "GROUP BY storeId, name " +
                                              "ORDER BY Total_Sum DESC");
    }

    public static ResultSet TopRevenueForStores(){ //uses orderWithStoreName view

        return DatabaseController.SelectQuery("WITH productWithOrderNumWithRevenue as ( " +
                                              "SELECT orderNum, quantity, Product.price * quantity as money " +
                                              "FROM Product join prodQuantities on " +
                                              "Product.UPC = prodQuantities.productUPC) " +
                                              "SELECT orderWithStoreName.storeId, orderWithStoreName.name," +
                                              "sum(money) as Total_Money " +
                                              "FROM productWithOrderNumWithRevenue join orderWithStoreName on " +
                                              "productWithOrderNumWithRevenue.orderNum = orderWithStoreName.orderNum " +
                                              "GROUP BY storeId, name " +
                                              "ORDER BY Total_Money DESC");
    }

    public static ResultSet LinearRegressionRevenueSales(){
        //prototype univariate linear regression
        //RevenueLinearModel(sales) = thetaZero + thetaOne * sales
        //returns thetaZero as column four and thetaOne as column five
        String query =      "SELECT 'Y = thetaZero + X * thetaOne' AS model, '" +
                            "Store Revenue" + "' AS y, '" + "Number of Sales" + "' AS x, " +
                            "Average_Revenue - Average_Sum * thetaOne AS thetaZero, thetaOne " + "FROM (" +
                            "SELECT sum((Total_Sum - Average_Sum) * (Total_Money - Average_Revenue)) / " +
                            "sum((Total_Sum - Average_Sum) * (Total_Sum - Average_Sum)) as thetaOne, " +
                            "Average_Revenue, Average_Sum FROM (" +
                                "WITH tonsOfData as (" +
                                    "SELECT storeId, sum(quantity) as Total_Sum, sum(money) as Total_Money " + "FROM (" +
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
                                        "SELECT OrdersWithStoreId.storeId, quantity, money " + "FROM (" +
                                            "productWithOrderNum join OrdersWithStoreId on " +
                                            "productWithOrderNum.orderNum = OrdersWithStoreId.orderNum" +
                                        ") " +
                                    ") " +
                                    "GROUP BY storeId" +
                                ")" +
                                "SELECT Total_Sum, Total_Money, Average_Sum, Average_Revenue FROM (" +
                                    "tonsOfData" +
                                ") " +
                                "CROSS JOIN " +
                                "(SELECT avg(Total_Sum) as Average_Sum, avg(Total_Money) as Average_Revenue FROM (" +
                                    "tonsOfData" +
                                ")) " +
                            ")" +
                        ")";

        return DatabaseController.SelectQuery(query);

    }

    //does linear regression on any two attributes in a table(which can be customized by select queries)
    //requires a select statement that returns a table containing the two attributes and their name
    //returns null if unsuccessful
    public static ResultSet GenericUnivariateLinearRegression(String potentialQuery, String x, String y){
        String defaultTable = null;
        switch (DatabaseController.getQueryType(potentialQuery))
        {
            case SELECT:
                defaultTable = potentialQuery;
                break;
            default:
                defaultTable = "SELECT * FROM " + potentialQuery;
                if (DatabaseController.getQueryType(defaultTable) !=
                    StatementType.SELECT)
                {
                    return null;
                }
        }
        String query =  "SELECT 'Y = thetaZero + X * thetaOne' AS model, '" + y + "' AS y, '" + x + "' AS x, " +
                        "muY - muX * thetaOne AS thetaZero, thetaOne " + "FROM (" +
                            "SELECT sum((x - muX) * (y - muY)) / " +
                            "sum((x - muX) * (x - muX)) as thetaOne, muY, muX FROM (" +
                                "WITH sample as (" + defaultTable + ")" +
                                "SELECT " + x + " as x, " + y + " as y, muX, muY FROM (sample)" +
                                "CROSS JOIN (SELECT avg(" + x + ") as muX, avg(" + y + ") as muY FROM (sample)) " +
                            ")" +
                        ")";
        return DatabaseController.SelectQuery(query);
    }

}
