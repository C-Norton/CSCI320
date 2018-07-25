package GUIPages;

import Controllers.DatabaseController;
import Controllers.GuiController;
import Utilities.StatementType;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

import java.sql.ResultSet;

/**
 * Created by Channing Helmling-Cornell on 7/25/2018.
 */
public class AbitraryQueryPage
{
    AbitraryQueryPage(GuiController guiController)
    {

        String Query = guiController.textpopup("SQL Query", "");
        if (Query == null)
        {
            MessageDialog.showMessageDialog(guiController.textGUI, "INVALID SQL QUERY",
                    "Your query was invalid.\nNo changes have been made",
                    MessageDialogButton.Close);
            return;
        }
        StatementType queryType = DatabaseController.getQueryType(Query, false);
        switch (queryType)
        {
            case INVALID:
                MessageDialog.showMessageDialog(guiController.textGUI, "INVALID SQL QUERY",
                        "Your query was invalid.\nNo changes have been made",
                        MessageDialogButton.Close);
                break;
            case NONUPDATEABLESELECT:
                ResultSet rs = DatabaseController.SelectQuery(Query, false);
                iPage results = new DataTablePage(guiController, rs, "Custom Query Results: " + Query);
                guiController.addAndDisplayPage(results);
                break;
            case UPDATEABLESELECT:
                MessageDialog.showMessageDialog(guiController.textGUI, "INVALID SQL QUERY",
                        "Application requested invalid ResultSet type.",
                        MessageDialogButton.Close);
                break;
            case UPDATE:
                int updated = DatabaseController.UpdateQuery(Query);
                if (updated >= 0)
                {
                    MessageDialog.showMessageDialog(guiController.textGUI, "Successful update Query",
                            "Query executed successfully. " + updated + " rows were affected, \nexcluding cascading "
                            + "updates.",
                            MessageDialogButton.Close);
                    break;
                }
                else
                {
                    MessageDialog.showMessageDialog(guiController.textGUI, "INVALID SQL QUERY",
                            "Your query was invalid.\nNo changes have been made",
                            MessageDialogButton.Close);
                    break;
                }

        }
    }
}
