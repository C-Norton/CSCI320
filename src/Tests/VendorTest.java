package Tests;

import Controllers.DatabaseController;
import Models.Vendor;
import Utilities.StatementTemplate;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Jeremy on 7/18/2018.
 */
public class VendorTest {
    Connection conn;
    DatabaseController dbController;
    StatementTemplate stmtUtil;
    ResultSet resultSet;

    private void initialize() throws Exception {
        conn = DriverManager.getConnection("jdbc:h2:./test", "sa", "");

        dbController = new DatabaseController(conn);
        stmtUtil = new StatementTemplate(conn);


        dbController.InitializeNewDatabaseInstance();
    }

    @Test
    void getSingleVendorQuery() throws Exception{
        initialize();

        resultSet = Vendor.getSingleVendorQuery(dbController, stmtUtil, 123);
        assertNotNull(resultSet); //just checking if its not null rn should be changed in the future
    }
    @Test
    void getListOfVendors() throws Exception{
        initialize();

        resultSet = Vendor.getListOfVendors(dbController, stmtUtil);
        assertNotNull(resultSet); //just checking if its not null rn should be changed in the future
    }

}
