package Utilities;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by Michael on 7/6/2018.
 */
public class StatementTemplate {

    public static StatementTemplate Template;
    private final Connection conn;
    public StatementTemplate(Connection conn){
        this.conn = conn;
    }

    public static Statement newNullStatement()
    {
        Statement stmt = null;
        return stmt;
    }

    public static Statement connStatement(Statement stmt) throws Exception
    {

        stmt = Template.conn.createStatement();
        return stmt;
    }

}
