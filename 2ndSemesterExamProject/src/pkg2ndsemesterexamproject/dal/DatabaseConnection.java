package pkg2ndsemesterexamproject.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class DatabaseConnection {

    private static final String PROP_FILE = "database/database.settings";
    private final SQLServerDataSource ds;

    /**
     * Sørger for at oprette forbindelse til databasen.
     *
     * @throws IOException
     */
    public DatabaseConnection() throws IOException {
        Properties databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream(PROP_FILE));
        ds = new SQLServerDataSource();
        ds.setServerName(databaseProperties.getProperty("servername"));
        ds.setDatabaseName(databaseProperties.getProperty("databasename"));
        ds.setUser(databaseProperties.getProperty("user"));
        ds.setPassword(databaseProperties.getProperty("pw"));
    }

    /**
     * returner connection.
     *
     * @return Connection
     * @throws SQLServerException
     */
    public Connection getConnection() throws SQLServerException {
        return ds.getConnection();
    }
}
