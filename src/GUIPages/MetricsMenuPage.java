package GUIPages;

import Controllers.GuiController;
import Models.Products;
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
                iPage Results = new DataTablePage(
                        guiController,
                        Products.getProductsOfBrand(Brandname),
                        "All products offered by " + Brandname);
                guiController.addAndDisplayPage(Results);
            }
        }));
        panel.addComponent(new Button("2. Back", guiController::closePage));
    }

    @Override
    public Panel getPanel()
    {

        return panel;
    }
}
