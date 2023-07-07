package me.kandangalwi.kahub.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private final String host;
    private final String port;
    private final String database;
    private final String username;
    private final String password;
    private Connection connection;

    public DatabaseManager(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public void connect() throws SQLException {
        if (!isConnected()) {
            String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";
            connection = DriverManager.getConnection(url, username, password);
        }
    }

    public void disconnect() throws SQLException {
        if (isConnected()) {
            connection.close();
            connection = null;
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void createTables() throws SQLException {
        Statement statement = connection.createStatement();

        // Create the commands table
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS commands ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "player_uuid VARCHAR(36) NOT NULL,"
                + "command VARCHAR(255) NOT NULL,"
                + "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ")");

        statement.close();
    }

    // Example method to insert a command log into the database
    public void insertCommandLog(String playerUUID, String command) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "INSERT INTO commands (player_uuid, command) VALUES ('" + playerUUID + "', '" + command + "')";
        statement.executeUpdate(query);
        statement.close();
    }
}
