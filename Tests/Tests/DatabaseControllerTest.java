package Tests;

import Controllers.DatabaseController;
import Models.Cart;
import Utilities.StatementTemplate;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseControllerTest {
    private void initialize() throws Exception{

        Connection conn = DriverManager.getConnection("jdbc:h2:./Tests", "sa", "");

        DatabaseController dbController = new DatabaseController(conn);
        StatementTemplate stmtUtil = new StatementTemplate(conn);

        dbController.InitializeNewDatabaseInstance();

        //Restock restock = new Restock();
        //restock.init(conn, "RestockTrigger", "autorestock", "inventory",false, 2);
    }

    @Test
    void enableRestockingTrigger() throws Exception{
        initialize();
        assertEquals(true, DatabaseController.enableRestockingTrigger());
        Cart cart = new Cart(1);
        cart.addItem("608630039789", 19);
        cart.addItem("608263619879", 62);
        cart.CheckOut();
        cart = new Cart(1);
        assertEquals(true, cart.addItem("608630039789", 1));
        assertEquals(true, cart.addItem("608630039789", 49));
        assertEquals(false, cart.addItem("608630039789", 1));
        assertEquals(true, cart.addItem("608263619879", 1));
        assertEquals(true, cart.addItem("608263619879", 49));
        assertEquals(false, cart.addItem("608263619879", 1));
    }
}