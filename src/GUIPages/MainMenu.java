package GUIPages;

import Controllers.DatabaseController;
import Controllers.GuiController;
import Models.Products;
import Models.Store;
import Utilities.StatementTemplate;
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

                iPage Storelist = new DataTablePage(guiController, Store.retrieveStores(DatabaseController.DB,
                        StatementTemplate.Template), "Stores");
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
        panel.addComponent(new Button("3. ExecuteQuery", new Runnable()
        {
            @Override
            public void run()
            {

                iPage QueryResults = new DataTablePage(guiController, Products.getProductList(), "Products");
                guiController.addAndDisplayPage(QueryResults);
            }
        }));
        panel.addComponent(new Button("4. Back", new Runnable()
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
