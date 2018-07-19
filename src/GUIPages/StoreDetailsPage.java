package GUIPages;

import Controllers.GuiController;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Panel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Channing Helmling-Cornell on 7/18/2018.
 */
public class StoreDetailsPage implements iPage
{
    private Panel panel;

    public StoreDetailsPage(GuiController guiController, ResultSet rs)
    {

        try
        {
            if (rs.next())
            {/*
            panel = new Panel(new LinearLayout(Direction.VERTICAL));
            panel.addComponent(new Label("Store Details - " + store.getName()));
            panel.addComponent(new Separator(Direction.HORIZONTAL));
            panel.addComponent(new Label("Name: " + store.getName()));
            panel.addComponent(new Label("ID: " + store.getId()));
            panel.addComponent(new Label("Location: " + store.getLocation()));
            panel.addComponent(new Label("Hours: " + store.getHours()));
            panel.addComponent(new Label("Phone Number: " + store.getPhone()));*/
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
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public Panel getPanel()
    {

        return panel;
    }
}
