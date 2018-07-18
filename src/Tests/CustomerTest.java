package Tests;

import Controllers.DatabaseController;
import Models.Customer;
import Utilities.StatementTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
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

    @org.junit.jupiter.api.Test
    void logIn() throws Exception {
        initialize();

        result = Customer.logIn(dbController, stmtUtil, "bobby", "password123");
        assertEquals(true, result);
    }

}