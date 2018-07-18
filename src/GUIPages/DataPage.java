package GUIPages;

import Controllers.GuiController;
import com.googlecode.lanterna.gui2.Panel;

/**
 * Created by Channing Helmling-Cornell on 7/18/2018.
 */
public class DataPage implements iPage
{
    private Panel panel;

    DataPage(GuiController guiController)
    {

    }

    @Override
    public Panel getPanel()
    {

        return panel;
    }
}
