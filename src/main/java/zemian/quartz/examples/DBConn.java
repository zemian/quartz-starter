package zemian.quartz.examples;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by zemian on 7/4/17.
 */
public class DBConn {
    public static void main(String[] args) throws Exception {
        Class.forName("org.hsqldb.jdbc.JDBCDriver" );
        Connection c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "");
        System.out.println(c);
        c.close();
    }
}
