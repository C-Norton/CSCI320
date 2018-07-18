package Controllers;

/**
 * Created by Channing Helmling-Cornell on 7/8/2018.
 * GUIController is the root of the GUI system. As the GUI is simple, this is planned to be a single file, with only
 * one public class (though other private classes may exist as needed). While not an ideal architecture to scale
 * from, having one class makes it easy to hook the GUI, once complete, up to the rest of the system, and will not
 * clutter the source tree. While these concerns are normally trivial, as this project will not be extended in the
 * future, and requires only minimal GUI functionality, here it makes sense to reduce burden.
 * <p>
 * The GUI is constructed with the "Lanterna" GUI library, found here https://github.com/mabe02/lanterna
 */

import GUIPages.MainMenu;
import GUIPages.iPage;
import com.googlecode.lanterna.*;
import Utilities.StatementTemplate;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.Stack;

public class GuiController
{
    private DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
    public static Screen screen = null;
    public static final Window window = null;
    Stack<iPage> Screens;
    final public static WindowBasedTextGUI textGUI;//TODO
    private DatabaseController dbController;
    private StatementTemplate stmtUtil;

    /**
     * Constructor. Creates a terminal, and draws the welcome page.
     */
    public GuiController(DatabaseController dbController, StatementTemplate stmtUtil)
    {
        this.dbController = dbController;
        this.stmtUtil = stmtUtil;
        Screen screen = null;
        TextGraphics tmp = null;
        try
        {
            screen = terminalFactory.createScreen();
            screen.startScreen();
            textGUI = new MultiWindowTextGUI(screen);
            final Window window = new BasicWindow("Just put anything, I don't even care");
            Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));
            panel.addComponent(new Button("Begin", new Runnable()
            {
                public void run()
                {

                    new MainMenu();
                }
            }));
            panel.addComponent(new Button("Exit", new Runnable()
            {
                public void run()
                {

                    window.close();
                    try
                    {
                        screen.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }));
            window.setComponent(panel);
            textGUI.addWindowAndWait(window);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Close is a cleanup function that MUST be called upon application termination. Should it not be, and references to
     * the GUIController cease, there would be resource loss upon garbage collection. To prevent this, a Finalize method
     * has been created, but best practice is to close your GUIControllers when you're done with them
     */
    public void close()
    {

    }

    @Override
    protected void finalize() throws Throwable
    {

        close();
        super.finalize();
    }

}