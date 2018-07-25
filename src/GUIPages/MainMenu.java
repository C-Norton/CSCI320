package GUIPages;

import Controllers.GuiController;
import Models.Products;
import Models.Store;
import com.googlecode.lanterna.gui2.*;

/**
 * Created by Channing Helmling-Cornell on 7/14/2018.
 */
public class MainMenu implements iPage
{

    private Panel panel;

    MainMenu(GuiController guiController)
    {

        panel = new Panel(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label("Welcome to FastMart! Where it's fast and easy to shop!\nUse the arrow Keys "
                                     + "to highlight desired option. Press ENTER to select\n" +
                                     "Text Fields CAN be pasted into, using the hotkey Ctrl+Shift+V.\nMultiline pasting not supported"));
        panel.addComponent(new Button("1. View Stores", new Runnable()
        {
            @Override
            public void run()
            {

                iPage Storelist = new DataTablePage(guiController, Store.retrieveStores(), "Stores");
                guiController.addAndDisplayPage(Storelist);

            }
        }));
        panel.addComponent(new Button("2. View Products", new Runnable()
        {
            @Override
            public void run()
            {

                iPage Productlist = new DataTablePage(guiController, Products.getProductList(), "Products");
                guiController.addAndDisplayPage(Productlist);
            }
        }));
        panel.addComponent(new Button("3. View Metrics/ Advanced Sample Queries", new Runnable()
        {
            @Override
            public void run()
            {

                iPage metricsMenu = new MetricsMenuPage(guiController);
                guiController.addAndDisplayPage(metricsMenu);
            }
        }));
        panel.addComponent(new Button("4. Execute Custom Query", new Runnable()
        {
            @Override
            public void run()
            {

                new AbitraryQueryPage(guiController);

            }
        }));
        panel.addComponent(new Button("5. Start Shopping", new Runnable()
        {
            @Override
            public void run()
            {

                guiController.addAndDisplayPage(new DataTablePage(guiController, Store.retrieveStores(), "Select "
                                                                                                         + "Store"));
            }
        }));
        panel.addComponent(new Button("6. Back", guiController::closePage));
    }

    @Override
    public Panel getPanel()
    {

        return panel;
    }
}
