package Utilities;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by Michael on 7/6/2018.
 */
public class StatementTemplate {

    private Connection conn;

    public StatementTemplate(Connection conn){
        this.conn = conn;
    }

    public Statement newNullStatement(){
        Statement stmt = null;
        return stmt;
    }

    public Statement connStatement(Statement stmt) throws Exception{
        stmt = this.conn.createStatement();
        return stmt;
    }

}
