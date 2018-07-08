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

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class GuiController
{
    private DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
    private Terminal terminal = null;
    private final TextGraphics textGraphics;

    /**
     * Constructor. Creates a terminal, and draws the welcome page.
     */
    public GuiController()
    {

        TextGraphics tmp = null;
        try
        {
            this.terminal = terminalFactory.createTerminal();
            Thread.sleep(500); //On windows, inconsistent behavior was occurring without a sleep here, as a flush
            //was being called before windows had finished initializing the terminal window. While I would wait on that
            //thread, this is also problematic due to OS differences in how this terminal is managed.
            this.terminal.enterPrivateMode();
            this.terminal.clearScreen();
            this.terminal.flush();
            tmp = terminal.newTextGraphics();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            if (this.terminal != null)
            {
                try
                {
                    this.terminal.close();
                }
                catch (IOException f)
                {
                    f.printStackTrace();
                }
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            if (this.terminal != null)
            {
                try
                {
                    this.terminal.close();
                }
                catch (IOException f)
                {
                    f.printStackTrace();
                }
            }
        }
        finally
        {
            textGraphics = tmp;
        }
    }

    /**
     * Close is a cleanup function that MUST be called upon application termination. Should it not be, and references to
     * the GUIController cease, there would be resource loss upon garbage collection. To prevent this, a Finalize method
     * has been created, but best practice is to close your GUIControllers when you're done with them
     */
    public void close()
    {

        if (terminal != null)
        {
            try
            {
                terminal.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();

            }
        }
    }

    @Override
    protected void finalize() throws Throwable
    {

        close();
        super.finalize();
    }

}