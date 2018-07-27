package Models;

import Controllers.DatabaseController;

import java.sql.ResultSet;

/**
 * Created by Michael on 7/6/2018.
 */
public final class Products
{
    public static ResultSet getProductList()
    {

        return DatabaseController.SelectQuery("SELECT * FROM Product");
    }

    public static ResultSet getProductsOfBrand(String brand)
    {

        return DatabaseController.SelectQuery("SELECT (Product.UPC,Product.Name,Product.Price,Vendor.Name) FROM "
                                              + "(Product LEFT JOIN Vendor on Product.Vendor=Vendor.vendorID)"
                                              + "WHERE Brand = "
                                              + "\'" + brand + "\'");
    }

    public static ResultSet getDetailsOfProduct(String upc){

        return DatabaseController.SelectQuery("SELECT * FROM Product WHERE UPC = " + upc);
    }

}
