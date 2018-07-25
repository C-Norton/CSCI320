package Tests;

import Controllers.DatabaseController;
import Models.Vendor;
import Utilities.StatementTemplate;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Jeremy on 7/18/2018.
 */
class VendorTest {
    Connection conn;
    DatabaseController dbController;
    StatementTemplate stmtUtil;
    ResultSet resultSet;

    @Test
    void getListOfVendors() throws Exception
    {

        initialize();

        //resultSet = Vendor.getListOfVendors(dbController, stmtUtil);

        ArrayList<String> vendors = Vendor.parseResultSetList(resultSet);
        assertEquals(true, vendors != null);
        assertEquals("lame corp", vendors.get(0));

        //assertNotNull(resultSet); //just checking if its not null rn should be changed in the future
    }

    @Test
    void getSingleVendorQuery() throws Exception{
        initialize();
        //So this test was made outdated by restucturing
    }

    private void initialize() throws Exception
    {

        conn = DriverManager.getConnection("jdbc:h2:./Tests", "sa", "");

        dbController = new DatabaseController(conn);
        stmtUtil = new StatementTemplate(conn);


        dbController.InitializeNewDatabaseInstance();
    }

}
