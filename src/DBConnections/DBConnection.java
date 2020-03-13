package DBConnections;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    public Connection getConnection() throws IOException, SQLException {
        Connection conn;
        Properties props = new Properties();
        try (BufferedReader in = Files.newBufferedReader(Paths.get("app.properties"), Charset.forName("UTF-8"))) {
            props.load(in);
        }
        String dbUrl = props.getProperty("dbUrlAddress");
        String userName = props.getProperty("userName");
        String pass = props.getProperty("password");
        conn = DriverManager.getConnection(dbUrl, userName, pass);
        return conn;

    }
}
