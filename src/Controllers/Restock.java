package Controllers;

import Utilities.StatementTemplate;
import org.h2.api.Trigger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Restock implements Trigger {

    @Override
    public void init(Connection conn, String schemaName,
                     String triggerName, String tableName, boolean before, int type)
    throws SQLException {}

    @Override
    public void fire(Connection conn, Object[] oldRow, Object[] newRow)
    throws SQLException {
        if ((int)newRow[2] < 1){
            String upc = newRow[1].toString();
            System.out.println(upc);
            System.out.println((int)newRow[2]);
            String statement = "UPDATE Inventory SET quantity = 50 WHERE productUPC = " + upc;
            StatementTemplate stmtUtil = new StatementTemplate(conn);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(statement);
        }
    }

    @Override
    public void close() throws SQLException {}

    @Override
    public void remove() throws SQLException {}
}