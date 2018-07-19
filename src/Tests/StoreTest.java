package Tests;

import Controllers.DatabaseController;
import Models.Store;
import Utilities.StatementTemplate;
import org.junit.jupiter.api.Test;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {
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
    void retrieveStores() throws Exception{
        initialize();
        ResultSet rs = Store.retrieveStores(dbController, stmtUtil);
        /* tests for the RS
        ResultSetMetaData rsmd = rs.getMetaData();
        assertEquals(2, rsmd.getColumnCount());
        rs.next();
        assertEquals("NYC", rs.getString("name"));
        assertEquals(1, rs.getInt("STOREID"));
        rs.next();
        assertEquals("LA", rs.getString("name"));
        assertEquals(2, rs.getInt("STOREID"));
        */
        ArrayList<Store> stores = Store.parseStores(rs);
        assertEquals(true, stores != null);
        assertEquals(1, stores.get(0).getId());
        assertEquals("NYC", stores.get(0).getName());
        //assertEquals("NA", stores.get(0).getLocation());
        //assertEquals("420", stores.get(0).getPhone());
        //assertEquals("24hrs", stores.get(0).getHours());
        assertEquals(2, stores.get(1).getId());
        assertEquals("LA", stores.get(1).getName());
    }
}