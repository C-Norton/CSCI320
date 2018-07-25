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

import GUIPages.LoginPage;
import GUIPages.iPage;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class GuiController
{
    private final Window window;
    public final WindowBasedTextGUI textGUI;
    private Screen screen;
    private Deque<iPage> PageStack;

    /**
     * Constructor. Creates a terminal, and draws the welcome page.
     */
    public GuiController()
    {

        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        PageStack = new ArrayDeque<>(); // Gives us a screen stack
        window = new BasicWindow("Just put anything, I don't even care");
        try
        {
            screen = terminalFactory.createScreen(); //Populates screen
            screen.startScreen(); //Runs screen
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        textGUI = new MultiWindowTextGUI(screen);

    }

    public int getWidth()
    {

        return screen.getTerminalSize().getColumns();
    }

    public void addAndDisplayPage(iPage page)
    {

        PageStack.addFirst(page);
        updatescreen();
    }

    private void updatescreen()
    {

        window.setComponent(PageStack.peekFirst().getPanel());
        textGUI.addWindowAndWait(window);
    }

    public void showLoginScreen()
    {

        iPage page = new LoginPage(this);
        PageStack.addFirst(page);
        updatescreen();
    }

    public void closePage()
    {

        if (PageStack.size() <= 1)
        {
            close();
        }
        else
        {
            PageStack.removeFirst();
            updatescreen();
        }
    }

    public String textpopup(String prompt, String description)
    {

        return TextInputDialog.showDialog(textGUI, prompt, description, "Enter your " + prompt + " here...");

    }

    public int numPopup(String prompt)
    {

        return TextInputDialog.showNumberDialog(textGUI, prompt, "", "0").intValue();
    }

    /**
     * Close is a cleanup function that MUST be called upon application termination. Should it not be, and references to
     * the GUIController cease, there would be resource loss upon garbage collection. To prevent this, a Finalize method
     * has been created, but best practice is to close your GUIControllers when you're done with them
     */
    public void close()
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

    @Override
    protected void finalize() throws Throwable
    {

        close();
        super.finalize();
    }
}