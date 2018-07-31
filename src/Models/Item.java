package Models;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;

/**
 * Created by Channing Helmling-Cornell on 7/25/2018.
 */
public class Item
{

    //Man, I wish java had structs. That's what this is. This is a struct.
    public String UPC, Name, Brand;
    public float Price;
    public int Quantity;

    Item()
    {

    }

    static LinkedHashMap<String, Item> RStoContents(ResultSet rs)
    {

        if (rs == null)
        {
            return null;
        }
        LinkedHashMap<String, Item> contents = new LinkedHashMap<>();
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
                Item item = new Item();
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
