package Tests;

import Controllers.DatabaseController;
import Utilities.StatementTemplate;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

class StoreTest {
    Connection conn;
    DatabaseController dbController;
    StatementTemplate stmtUtil;
    boolean result;

    @Test
    void retrieveStoreById() throws Exception{
        initialize();
        //Store store = Store.retrieveStoreById(1);
        //assertEquals("NYC", store.getName());
    }

    private void initialize() throws Exception
    {

        conn = DriverManager.getConnection("jdbc:h2:./Tests", "sa", "");

        dbController = new DatabaseController(conn);
        stmtUtil = new StatementTemplate(conn);


        dbController.InitializeNewDatabaseInstance();
    }

    @Test //passed
    void retrieveStores() throws Exception{
        initialize();

    }
}