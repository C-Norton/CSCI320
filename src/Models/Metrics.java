package Models;

import Controllers.DatabaseController;

import javax.xml.transform.Result;
import java.sql.ResultSet;

/**
 * Created by Channing Helmling-Cornell on 7/19/2018.
 * <p>
 * Metrics is a class for more complicated queries on the database for getting overall statistics about the data. See
 * page 3 of the retail domain document, "Queries" section for more details
 */
public class Metrics
{

    public static ResultSet TopTwentyProdStore(int store_Id)
    {

        return DatabaseController.SelectQuery("WITH productWithOrderNum as ( " +
                                                    "SELECT UPC, name, orderNum, quantity " +
                                                    "FROM Product join prodQuantities on " +
                                                    "Product.UPC = prodQuantities.productUPC) " +
                                                "SELECT UPC, Name, sum( quantity ) as Total_Sold " +
                                                "FROM productWithOrderNum join Orders on " +
                                                "productWithOrderNum.orderNum = Orders.orderNum " +
                                                "WHERE storeId = "+ store_Id + " " +
                                                "GROUP BY UPC, Name " +
                                                "ORDER BY Total_Sold DESC " +
                                                "LIMIT 20", false);
    }

    public static ResultSet TopTwentyProdOverall()
    {

        return DatabaseController.SelectQuery("WITH productWithOrderNum as ( " +
                                                    "SELECT Product.UPC, Product.name, prodQuantities.orderNum, prodQuantities.quantity " +
                                                    "FROM Product join prodQuantities on " +
                                                    "Product.UPC = prodQuantities.productUPC) " +
                                                "SELECT UPC, Name, sum( quantity ) as Total_Sold " +
                                                "FROM productWithOrderNum join Orders on " +
                                                "productWithOrderNum.orderNum = Orders.orderNum " +
                                                "GROUP BY UPC, Name " +
                                                "ORDER BY Total_Sold DESC " +
                                                "LIMIT 20", false);
    }

    public static ResultSet HowManyStoresDoesProdAOutsellProdB(String prodIdA, String prodIdB){

        return DatabaseController.SelectQuery("WITH productWithOrderNum as ( " +
                                                    "SELECT UPC, name, orderNum, quantity " +
                                                    "FROM Product join prodQuantities on " +
                                                    "Product.UPC = prodQuantities.productUPC " +
                                                    "WHERE Product.UPC = '"+prodIdA+"' or Product.UPC = '"+prodIdB+"' ) " +
                                                "SELECT UPC, Name, storeId, sum(quantity) as Total_Sum " +
                                                "FROM productWithOrderNum join Orders on " +
                                                "productWithOrderNum.orderNum = Orders.orderNum " +
                                                "GROUP BY UPC, Name, storeId " +
                                                "ORDER BY storeId DESC", false);

    }


    public static ResultSet TopSalesForStores(){

        return DatabaseController.SelectQuery("WITH productWithOrderNum as ( " +

                                                    "SELECT orderNum, quantity, Product.price * quantity as money " +

                                                    "FROM Product join prodQuantities on " +

                                                    "Product.UPC = prodQuantities.productUPC), " +

                                                    "OrdersWithStoreName as ( " +

                                                    "SELECT Store.storeId, Store.name, orderNum " +

                                                    "FROM Store join Orders on " +

                                                    "Store.storeId = Orders.storeId) " +

                                                "SELECT OrdersWithStoreName.storeId, OrdersWithStoreName.name," +

                                                "sum(quantity) as Total_Sum, sum(money) as Total_Money " +

                                                "FROM productWithOrderNum join OrdersWithStoreName on " +

                                                "productWithOrderNum.orderNum = OrdersWithStoreName.orderNum " +

                                                "GROUP BY storeId, name " +

                                                "ORDER BY Total_Sum DESC", false);

    }

    public static ResultSet LinearRegressionRevenueSales(){
        //univariate linear regression
        //RevenueLinearModel(sales) = thetaZero + thetaOne * sales
        //returns thetaZero as column one and thetaOne as column two
        String query = "SELECT ybar - xbar * thetaOne AS thetaZero, thetaOne " +
                                    "FROM (" +
                                        "SELECT sum((Total_Sum - xbar) " +
                                        "* (Total_Money - ybar)) " +
                                        "/ sum((Total_Sum - xbar) * (Total_Sum - xbar)) "
                                        + "as thetaOne, ybar, xbar FROM" +

                "(SELECT Total_Sum, Total_Money, xbar, ybar FROM (" +
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
                                                                ") as table1 CROSS JOIN (SELECT avg(Total_Sum) as xbar, avg(Total_Money) as ybar FROM (" +
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
                                                                ")) as table2))";

        return DatabaseController.SelectQuery(query, false);

    }

}
