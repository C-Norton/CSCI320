package GUIPages;

import Controllers.GuiController;
import Models.Store;
import com.googlecode.lanterna.gui2.*;


/**
 * Created by Channing Helmling-Cornell on 7/14/2018.
 */
public class MainMenu implements iPage
{

    private Panel panel;

    public MainMenu(GuiController guiController)
    {

        panel = new Panel(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label("Welcome to FastMart! Where it's fast and easy to shop!\nUse the arrow Keys "
                                     + "to highlight desired option. Press ENTER to select"));
        panel.addComponent(new Button("1. Select Store", new Runnable()
        {
            @Override
            public void run()
            {

                iPage Storelist = new DataTablePage(guiController, Store.retrieveStores(guiController.dbController,
                        guiController.stmtUtil), "Stores");
                guiController.addAndDisplayPage(Storelist);

            }
        }));
        panel.addComponent(new Button("2. Back", new Runnable()
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
