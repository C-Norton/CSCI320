package Utilities;

import java.sql.Connection;
import java.sql.ResultSet;
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

        return null;
    }

    public static Statement connStatement(Statement stmt) throws SQLException
    {

        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        return stmt;
    }

}
