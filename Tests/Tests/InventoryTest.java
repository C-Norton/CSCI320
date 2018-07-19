package Tests;

import Controllers.DatabaseController;
import Models.Inventory;
import Utilities.StatementTemplate;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

class InventoryTest {
    Connection conn;
    DatabaseController dbController;
    StatementTemplate stmtUtil;
    boolean result;

    @Test
    void retrieveInventory() throws Exception{
        initialize();
        Inventory inventory = Inventory.retrieveInventory(dbController, stmtUtil, 1);
        //assertEquals("ten",inventory.getInventory().get(0).getProduct().getName());
    }

    private void initialize() throws Exception
    {

        conn = DriverManager.getConnection("jdbc:h2:./Tests", "sa", "");

        dbController = new DatabaseController(conn);
        stmtUtil = new StatementTemplate(conn);


        dbController.InitializeNewDatabaseInstance();
    }
}