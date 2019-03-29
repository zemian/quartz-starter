package zemian;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/*
TODO: Why can we use ApplicationName and assumeMinServerVersion
at the same time?
*/
public class TestDB {
  public static void main(String[] args) throws Exception {
    System.out.println("Hello");
    String url = "jdbc:postgresql://localhost/postgres";
    Properties props = new Properties();
    props.setProperty("user", "postgres");
    props.setProperty("ApplicationName", "webdemo");

    // This will prevent 'SET extra_float_digits = 3'
    props.setProperty("assumeMinServerVersion", "11");
    try (Connection conn = DriverManager.getConnection(url, props)) {
      try (Statement st = conn.createStatement()) {
        try (ResultSet rs = st.executeQuery("select version()")) {
          rs.next();
          System.out.println(rs.getString(1));
        }
      }
    }
  }
}
