package GUIPages;

import Controllers.GuiController;
import com.googlecode.lanterna.gui2.*;
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
            return;
        }


        Table<String> data = new Table<String>(headers.toArray(new String[colcount]));
        try
        {//So we might want to have two try catch blocks here. I didn't for time, but right now, if there's an error
            // ANYWHERE in the table, it can't display ANY of it. This is something that we might want to change if
            // we run into later problems
            rs.beforeFirst();
            while (rs.next())
            {
                String[] row = new String[colcount];
                for (int i = 0; i < colcount; i++)
                {
                    row[i] = (rs.getString(colNames.get(i)));
                }
                data.getTableModel().addRow(row);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();

        }
        panel.addComponent(data);
        panel.addComponent(new Button("Back", new Runnable()
        {
            @Override
            public void run()
            {

                guiController.closePage();
            }
        }));

    }

    @Override
    public Panel getPanel()
    {

        return panel;
    }
}
