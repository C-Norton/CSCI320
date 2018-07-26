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
                                                    "WHERE Product.name = '"+prodIdA+"' or Product.name = '"+prodIdB+"' ) " +
                                                "SELECT UPC, Name, storeId, sum(quantity) as Total_Sum " +
                                                "FROM productWithOrderNum join Orders on " +
                                                "productWithOrderNum.orderNum = Orders.orderNum " +
                                                "GROUP BY UPC, Name, storeId " +
                                                "ORDER BY storeId DESC", false);

    }
}
