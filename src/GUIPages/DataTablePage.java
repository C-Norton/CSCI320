package GUIPages;

import Controllers.GuiController;
import Models.Cart;
import Models.Store;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Channing Helmling-Cornell on 7/18/2018.
 */
public class DataTablePage implements iPage
{
    private Panel panel;

    DataTablePage(GuiController guiController, ResultSet rs, String PageName)
    {

        panel = new Panel(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label(PageName));

        Label refreshWarning = new Label("");
        refreshWarning.setLabelWidth(guiController.getWidth() - 8);
        refreshWarning.setText("Note: Changes to the database are not fetched when accessing this page via "
                               + "the \"Back\" button on future pages. To refresh the data on this page, "
                               + "please hit the Back button at the bottom of this page, and run the command "
                               + "that generated this page again.");
        panel.addComponent(refreshWarning);
        panel.addComponent(new Separator(Direction.HORIZONTAL));
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
        data.setSelectAction(new Runnable()
        {
            @Override
            public void run()
            {

                int storeid;
                switch (PageName)
                {

                    case "Stores": //Todo:This should likely be replaced with an ENUM at some point to make less fragile
                        storeid = Integer.parseInt(data.getTableModel().getCell(0, data.getSelectedRow()));
                        guiController.addAndDisplayPage(new DataTablePage(guiController,
                                Store.retrieveStoreById(storeid), "Store Details"));
                        break;
                    case "Select Store":
                        storeid = Integer.parseInt(data.getTableModel().getCell(0, data.getSelectedRow()));
                        Cart.newCart(storeid);
                        guiController.addAndDisplayPage(new ShoppingPage(guiController, storeid));
                        break;
                    default:
                        break;
                }
            }
        });
        panel.addComponent(data);
        panel.addComponent(new Button("Back", guiController::closePage));

    }

    @Override
    public Panel getPanel()
    {

        return panel;
    }
}
