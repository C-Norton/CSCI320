package Tests;

import Controllers.DatabaseController;
import Models.Inventory;
import Utilities.StatementTemplate;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    Connection conn;
    DatabaseController dbController;
    StatementTemplate stmtUtil;
    boolean result;

    private void initialize() throws Exception {
        conn = DriverManager.getConnection("jdbc:h2:./test", "sa", "");

        dbController = new DatabaseController(conn);
        stmtUtil = new StatementTemplate(conn);


        dbController.InitializeNewDatabaseInstance();
    }

    @Test
    void retrieveInventory() throws Exception{
        initialize();
        Inventory inventory = Inventory.retrieveInventory(dbController, stmtUtil, 1);
        System.out.println(inventory.getInventory().get(0).getProduct().getName());
    }
}