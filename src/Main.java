import Controllers.DatabaseController;
import Controllers.GuiController;
import Utilities.StatementTemplate;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static DatabaseController dbController;
    public static StatementTemplate stmtUtil;

    public static void main(String[] a) throws Exception {

        Connection conn = DriverManager.getConnection("jdbc:h2:./Tests", "sa", "");

        dbController = new DatabaseController(conn);
        stmtUtil = new StatementTemplate(conn);

        dbController.InitializeNewDatabaseInstance();

        GuiController gui = new GuiController();
        gui.showLoginScreen();
        conn.close();
    }

}
