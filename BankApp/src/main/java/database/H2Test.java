package database;

import java.sql.Connection;
import java.sql.DriverManager; //think this is also in org.h2 - so need to figure out how to import for this and the resultset

public class H2Test {
    public static void TestH2() throws Exception {
    	/*
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "christianmeyer", "");
        SimpleResultSet rs = new SimpleResultSet();
        rs.addColumn("NAME", Types.VARCHAR, 255, 0);
        rs.addColumn("EMAIL", Types.VARCHAR, 255, 0);
        rs.addRow("Bob Meier", "bob.meier@abcde.abc");
        rs.addRow("John Jones", "john.jones@abcde.abc");
        //new Csv().write("data/test.csv", rs, null);
        conn.close();
        */
    }
}