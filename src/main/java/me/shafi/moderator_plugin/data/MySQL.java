package me.shafi.moderator_plugin.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    private final String host;
    private final String port;
    private final String database;
    private final String username;
    private final String password;

    private Connection connection;

    public MySQL(String host, String port, String username , String password, String database){
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }
    public boolean isConnected() {
        return (connection != null);
    }

    public void connect() throws SQLException {
        if(!isConnected()){
            connection = DriverManager.getConnection("jdbc:mysql://" +
                            host + ":" + port + "/" + database + "?useSSL=false",
                    username, password);
        }
    }
    public void disconnect(){
        if(isConnected()){
            try {
                connection.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
