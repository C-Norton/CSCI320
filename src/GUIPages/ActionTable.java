package GUIPages;

import Controllers.GuiController;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.table.Table;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Channing Helmling-Cornell on 7/18/2018.
 */
public class ActionTable implements iPage
{
    private Panel panel;

    public ActionTable(GuiController guiController, ResultSet rs, String PageName)
    {

        panel = new Panel(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label(PageName));
        int colcount = 0;
        ArrayList<String> headers = null;
        ArrayList<Integer> colSizes = null;
        ArrayList<Integer> colTypes = null;
        try //Get and unpack the metadata
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            colcount = rsmd.getColumnCount();
            headers = new ArrayList<>(colcount);
            colSizes = new ArrayList<>(colcount);
            colTypes = new ArrayList<>(colcount);
            for (int i = 1; i <= colcount; i++)
            {
                headers.add(rsmd.getColumnName(i));
                colSizes.add(rsmd.getColumnDisplaySize(i));
                colTypes.add(rsmd.getColumnType(i));
            }


        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return;
        }


        Table<String> data = new Table<String>(headers.toArray(new String[colcount]));

        panel.addComponent(data);

    }

    @Override
    public Panel getPanel()
    {

        return panel;
    }
}
