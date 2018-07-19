package GUIPages;

import Controllers.DatabaseController;
import Controllers.GuiController;
import Models.Products;
import Models.Store;
import com.googlecode.lanterna.gui2.*;

import java.sql.ResultSet;

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
        panel.addComponent(new Button("3. Execute Query", new Runnable()
        {
            @Override
            public void run()
            {

                String Query = guiController.textpopup("SQL Query", "Warning: Validity of statement is not \n"
                                                                    + "checked. Syntactically invalid \n"
                                                                    + "statements result in crashes or \n"
                                                                    + "unsupported behavior.");
                if (Query == null)
                {
                    return;
                }
                ResultSet rs = DatabaseController.MakeSelQuery(Query);
                if (rs == null)
                {
                    guiController.closePage();
                }
                else
                {
                    iPage results = new DataTablePage(guiController, rs, "Custom Query Results: " + Query);
                    guiController.addAndDisplayPage(results);
                }
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
