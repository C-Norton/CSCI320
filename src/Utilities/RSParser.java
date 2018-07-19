package Utilities;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Channing Helmling-Cornell on 7/19/2018.
 */
public class RSParser
{
    public static ArrayList<String[]> rsToStringHeaders(ResultSet rs)
    {

        int colcount = 0;
        ArrayList<String> headers = null;
        ArrayList<String> colNames = null;
        ArrayList<Integer> colSizes = null;
        ArrayList<Integer> colTypes = null;
        try //Get and unpack the metadata
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            colcount = rsmd.getColumnCount();
            headers = new ArrayList<>(colcount);
            colNames = new ArrayList<>(colcount);
            colSizes = new ArrayList<>(colcount);
            colTypes = new ArrayList<>(colcount);

            for (int i = 1; i <= colcount; i++)
            {
                headers.add(rsmd.getColumnLabel(i));
                colNames.add(rsmd.getColumnName(i));
                colSizes.add(rsmd.getColumnDisplaySize(i));
                colTypes.add(rsmd.getColumnType(i));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        ArrayList<String[]> Table = new ArrayList<>();
        Table.add(headers.toArray(new String[colcount]));
        try
        {
            rs.beforeFirst();
            while (rs.next())
            {
                String[] row = new String[colcount];
                for (int j = 0; j < colcount; j++)
                {
                    row[j] = (rs.getString(colNames.get(j)));
                }
                Table.add(row);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return Table;
    }
}
