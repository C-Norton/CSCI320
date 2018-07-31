package GUIPages;

import Controllers.GuiController;
import Models.Metrics;
import Models.Products;
import Models.Store;
import com.googlecode.lanterna.gui2.*;

/**
 * Created by Channing Helmling-Cornell on 7/19/2018.
 */
public class MetricsMenuPage implements iPage
{
    private Panel panel;

    MetricsMenuPage(GuiController guiController)
    {

        panel = new Panel(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label("Below is a list of sample metric queries. Select one to execute it and view "
                                     + "\n results. 'Back' to return to previous menu."));
        panel.addComponent(new Button("1. View Products from Brand", new Runnable()
        {
            @Override
            public void run()
            {

                String Brandname = guiController.textpopup("BrandName", "");
                if (Brandname != null)
                {
                    iPage Results = new DataTablePage(guiController, Products.getProductsOfBrand(Brandname),
                            "All products offered by " + Brandname);
                    guiController.addAndDisplayPage(Results);
                }
            }
        }));
        panel.addComponent(new Button("2. View all orders from store", new Runnable()
        {
            @Override
            public void run()
            {

                Integer storeId = guiController.numPopup("Which store would you like to know about?");
                if (storeId != null && Store.existsStore(storeId))
                {
                    iPage results = new DataTablePage(
                            guiController,
                            Store.getOrdersFromStore(storeId),
                            "Orders from Store");
                    guiController.addAndDisplayPage(results);
                }
            }
        }));

        panel.addComponent(new Button("3. View top 20 selling items (by volume) for a store", new Runnable()
        {
            @Override
            public void run()
            {

                Integer storeId = guiController.numPopup("Which store would you like to know about?");
                if (storeId != null && Store.existsStore(storeId))
                {
                    iPage results = new DataTablePage(
                            guiController,
                            Metrics.TopTwentyProdStore(storeId),
                            "Top Sales from Store");
                    guiController.addAndDisplayPage(results);
                }
            }
        }));
        panel.addComponent(new Button("4. View top 20 selling items (by volume) for the chain", new Runnable()
        {
            @Override
            public void run()
            {

                iPage results = new DataTablePage(guiController,
                        Metrics.TopTwentyProdOverall(), "Top selling products across the franchise");
                guiController.addAndDisplayPage(results);
            }
        }
        ));
        panel.addComponent(new Button("5. View linear regression of revenue to sales", new Runnable()
        {
            @Override
            public void run()
            {

                iPage results = new DataTablePage(guiController, Metrics.LinearRegressionRevenueSales(),
                        "Linear regression"
                        + " of franchise "
                        + "revenue");
                guiController.addAndDisplayPage(results);
            }
        }));
        panel.addComponent(new Button("6. Compare sales volume of products across stores", new Runnable()
        {
            @Override
            public void run()
            {

                String prod1 = guiController.textpopup("Enter UPC 1", "Which products would you like to know about?");
                String prod2 = guiController.textpopup("Enter UPC 2", "Which products would you like to know about?");
                Long prod1L, prod2L;
                try
                {
                    //So contrary to what your IDE says, these do something. Its a really quick isparsable thing that
                    //SHOULD be replaced by a regex, but it was done quickly, and this stupid way was the first thing I
                    //thought of that wasn't writing a string parser.
                    prod1L = Long.valueOf(prod1);
                    prod2L = Long.valueOf(prod2);
                }
                catch (NumberFormatException e)
                {
                    prod1 = null;
                    prod2 = null;
                }
                if (prod1 != null && prod2 != null)
                {
                    iPage results = new DataTablePage(
                            guiController,
                            Metrics.HowManyStoresDoesProdAOutsellProdB(prod1, prod2),
                            "Product sales across stores");
                    guiController.addAndDisplayPage(results);
                }
            }
        }));
        panel.addComponent(new Button("7. View total items shipped by each store", new Runnable()
        {
            @Override
            public void run()
            {

                iPage results = new DataTablePage(guiController, Metrics.TopSalesForStores(),
                        "Sales of each store by volume");
                guiController.addAndDisplayPage(results);
            }
        }));
        panel.addComponent(new Button("8. View total revenue of each store", new Runnable()
        {
            @Override
            public void run()
            {

                iPage results = new DataTablePage(guiController, Metrics.TopRevenueForStores(),
                        "Revenue of stores");
                guiController.addAndDisplayPage(results);
            }
        }));

        panel.addComponent(new Button("9. Back", guiController::closePage));
    }

    @Override
    public Panel getPanel()
    {

        return panel;
    }
}
