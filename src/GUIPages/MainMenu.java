package GUIPages;

import com.googlecode.lanterna.gui2.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Channing Helmling-Cornell on 7/14/2018.
 */
public class MainMenu
{
    public static void DrawMainMenu(final WindowBasedTextGUI textGUI, final Window window)
    {

        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new Label("Welcome to FastMart! Where it's fast and easy to shop!\nUse the arrow Keys "
                                     + "to highlight desired option. Press ENTER to select"));
        panel.addComponent(new Button("1. Select Store", new Runnable()
        {
            public void run()
            {

                throw new NotImplementedException();
            }
        }));
        //I'd like to have a good way to remember where you were and add a "back" button, but I can't pass in the old
        //panel, or have a runnable break to go back, so I'm not sure how to handle this yet. More research.
        window.setComponent(panel);
        textGUI.addWindowAndWait(window);
    }
}
