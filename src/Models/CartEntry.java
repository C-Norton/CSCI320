package Models;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;

/**
 * Created by Channing Helmling-Cornell on 7/25/2018.
 */
public class CartEntry
{

    public String UPC, Name, Brand;
    public float Price;
    public int Quantity;

    CartEntry()
    {

    }

    static LinkedHashMap<String, CartEntry> RStoContents(ResultSet rs)
    {

        if (rs == null)
        {
            return null;
        }
        LinkedHashMap<String, CartEntry> contents = new LinkedHashMap<>();
        ResultSetMetaData metadata = null;
        try
        {
            metadata = rs.getMetaData();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        try
        {
            if (metadata.getColumnCount() != 5)
            {
                return null;
            }
            if (!(
                    (metadata.getColumnType(1) == Types.DECIMAL) &&
                    (metadata.getColumnType(2) == Types.VARCHAR) &&
                    (metadata.getColumnType(3) == Types.VARCHAR) &&
                    (metadata.getColumnType(4) == Types.DECIMAL) &&
                    (metadata.getColumnType(5) == Types.INTEGER)
            ))
            {
                return null;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        try
        {
            rs.beforeFirst();
            while (rs.next())
            {
                CartEntry item = new CartEntry();
                item.UPC = rs.getString(1);
                item.Name = rs.getString(2);
                item.Brand = rs.getString(3);
                item.Price = rs.getFloat(4);
                item.Quantity = rs.getInt(5);
                contents.put(rs.getString(1), item);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        return contents;
    }
}
