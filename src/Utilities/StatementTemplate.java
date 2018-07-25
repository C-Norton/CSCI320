package Utilities;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Michael on 7/6/2018.
 */
public class StatementTemplate {

    private static Connection conn;

    public StatementTemplate(Connection connection)
    {

        conn = connection;
    }

    public static Statement newNullStatement()
    {
        Statement stmt = null;
        return stmt;
    }

    public static Statement connStatement(Statement stmt) throws SQLException
    {

        stmt = conn.createStatement();
        return stmt;
    }

}
