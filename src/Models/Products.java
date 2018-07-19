package Models;

import Controllers.DatabaseController;

import java.sql.ResultSet;

/**
 * Created by Michael on 7/6/2018.
 */
public class Products
{
    public ResultSet getProductList()
    {

        return DatabaseController.MakeSelQuery("SELECT * FROM Product");
    }

    public ResultSet getProductsOfBrand(String brand)
    {

        return DatabaseController.MakeSelQuery("SELECT (UPC,Name,Price,VendorID) FROM Product WHERE Brand = "
                                               + "\'" + brand + "\'");
    }
}
