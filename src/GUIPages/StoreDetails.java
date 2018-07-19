package GUIPages;

import Controllers.GuiController;
import Models.Store;
import com.googlecode.lanterna.gui2.*;

/**
 * Created by Channing Helmling-Cornell on 7/18/2018.
 */
public class StoreDetails implements iPage
{
    private Panel panel;

    public StoreDetails(GuiController guiController, Store store)
    {

        if (store != null)
        {
            panel = new Panel(new LinearLayout(Direction.VERTICAL));
            panel.addComponent(new Label("Store Details - " + store.getName()));
            panel.addComponent(new Separator(Direction.HORIZONTAL));
            panel.addComponent(new Label("Name: " + store.getName()));
            panel.addComponent(new Label("ID: " + store.getId()));
            panel.addComponent(new Label("Location: " + store.getLocation()));
            panel.addComponent(new Label("Hours: " + store.getHours()));
            panel.addComponent(new Label("Phone Number: " + store.getPhone()));
        }
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
