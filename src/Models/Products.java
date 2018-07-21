package Models;

import Controllers.DatabaseController;

import java.sql.ResultSet;

/**
 * Created by Michael on 7/6/2018.
 */
public class Products
{
    public static ResultSet getProductList()
    {

        return DatabaseController.MakeSelQuery("SELECT * FROM Product");
    }

    public static ResultSet getProductsOfBrand(String brand)
    {

        return DatabaseController.MakeSelQuery("SELECT (Product.UPC,Product.Name,Product.Price,Vendor.Name) FROM "
                                               + "(Product LEFT JOIN Vendor on Product.Vendor=Vendor.vendorID)"
                                               + "WHERE Brand = "
                                               + "\'" + brand + "\'");
    }

    public static ResultSet getDetailsOfProduct(String upc){
        return DatabaseController.MakeSelQuery("With selectedProduct as SELECT * FROM Product where upc = " + upc +
                "select * from (selectedProduct LEFT JOIN Vendor on Product.Vendor = Vendor.vendorID)");
    }
}
