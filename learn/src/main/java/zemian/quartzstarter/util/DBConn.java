package zemian.quartzstarter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Just a DB conn test program.
 *
 * Created by zemian on 7/4/17.
 */
public class DBConn {
    private static Logger LOG = LoggerFactory.getLogger(DBConn.class);

    public static void main(String[] args) throws Exception {
        postgres(args);
        //hsqldb(args);
    }

    public static void postgres(String[] args) throws Exception {
        Class.forName("org.postgresql.Driver" );
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/quartz", "postgres", "");
        try {
            LOG.info("DB Connection={}", conn);
        } finally {
            conn.close();
        }
    }

    public static void hsqldb(String[] args) throws Exception {
        Class.forName("org.hsqldb.jdbc.JDBCDriver" );
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "");
        try {
            LOG.info("DB Connection={}", conn);
        } finally {
            conn.close();
        }
    }
}
