package GUIPages;

import Controllers.GuiController;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

/**
 * Created by Channing Helmling-Cornell on 7/18/2018.
 */
public class LoginPage implements iPage
{
    private Panel panel;

    public LoginPage(GuiController guiController)
    {

        panel = new Panel(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Button("Begin", new Runnable()
        {
            @Override
            public void run()
            {

                guiController.addAndDisplayPage(new MainMenu(guiController));
            }
        }));
        panel.addComponent(new Button("Exit", guiController::close));
    }

    @Override
    public Panel getPanel()
    {

        return panel;
    }
}
