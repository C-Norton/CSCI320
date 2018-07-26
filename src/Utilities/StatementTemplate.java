package Utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Michael on 7/6/2018.
 */
public class StatementTemplate
{

    private static Connection conn;

    public StatementTemplate(Connection connection)
    {

        conn = connection;
    }

    public static Statement connQueryStatement(boolean Updateable) throws SQLException
    {

        return conn.createStatement((Updateable) ? ResultSet.TYPE_SCROLL_SENSITIVE : ResultSet
                .TYPE_SCROLL_INSENSITIVE, (Updateable) ? ResultSet.CONCUR_UPDATABLE : ResultSet.CONCUR_READ_ONLY);

    }

    public static Statement connUpdateStatement() throws SQLException
    {

        return conn.createStatement();
    }

}
