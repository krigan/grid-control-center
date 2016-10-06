package lv.ctco.adapters;

import lv.ctco.beans.Hub;
import lv.ctco.beans.LogItem;
import lv.ctco.beans.Node;
import lv.ctco.enums.LogType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class SqliteAdapter {

    private static Connection connection;

    public SqliteAdapter() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:grid-control-center.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createDbIfNotExist();
    }

    private Statement getStatement() throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(10);
        return statement;
    }

    private void createDbIfNotExist() {
        try {
            getStatement().executeUpdate("create table if not EXISTS HUB (" +
                    "id INTEGER PRIMARY KEY, " +
                    "start_command TEXT," +
                    "is_running INTEGER," +
                    "host TEXT," +
                    "port INTEGER);");

            getStatement().executeUpdate("create table if not EXISTS NODES (" +
                    "id INTEGER PRIMARY KEY, " +
                    "start_command TEXT," +
                    "is_running INTEGER," +
                    "host TEXT," +
                    "port INTEGER);");

            getStatement().executeUpdate("create table if not EXISTS LOGS (" +
                    "id INTEGER PRIMARY KEY, " +
                    "log_date INTEGER," +
                    "log_type TEXT," +
                    "message TEXT);");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void addInfoMessage(String message) {
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("INSERT INTO LOGS (log_date, log_type, message) VALUES (?,?,?)");
            ps.setLong(1, new Date().getTime());
            ps.setString(2, LogType.INFO.name());
            ps.setString(3, message);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void addErrorMessage(String message) {
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("INSERT INTO LOGS (log_date, log_type, message) VALUES (?,?,?)");
            ps.setLong(1, new Date().getTime());
            ps.setString(2, LogType.ERROR.name());
            ps.setString(3, message);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<LogItem> getLogs() {
        List<LogItem> logsList = new ArrayList<>();
        try {
            ResultSet rs = getStatement().executeQuery("SELECT * FROM LOGS");
            while (rs.next()) {
                logsList.add(new LogItem(rs.getLong("log_date"), LogType.valueOf(rs.getString("log_type")), rs.getString("message")));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return logsList;
    }

    public void updateHub(Hub hub) {
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("INSERT OR REPLACE INTO HUB (id, start_command, is_running,host,port) VALUES (?,?,?,?,?)");
            ps.setInt(1, 1);
            ps.setString(2, hub.getStartCommand());
            ps.setInt(3, getIsRunning(hub.isRunning()));
            ps.setString(4, hub.getHost());
            ps.setInt(5, hub.getPort());
            int result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void addNode(Node node) {
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("INSERT INTO NODES (start_command, is_running,host,port) VALUES (?,?,?,?)");
            ps.setString(1, node.getStartCommand());
            ps.setInt(2, getIsRunning(node.isRunning()));
            ps.setString(3, node.getHost());
            ps.setInt(4, node.getPort());
            int result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateNode(Node node) {
        try {
            PreparedStatement ps;
            ps = connection.prepareStatement("UPDATE NODES SET start_command=?, is_running=? WHERE host='" + node.getHost() + "' and port='" + node.getPort() + "'");
            ps.setString(1, node.getStartCommand());
            ps.setInt(2, getIsRunning(node.isRunning()));
            int result = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void removeNode(Node node) {
        try {
            String sql = "DELETE FROM NODES where host='" + node.getHost() + "' and port='" + node.getPort() + "'";
            int result = getStatement().executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Hub getHubInfo() {
        Hub hub = new Hub(new HashSet<>());
        try {
            ResultSet rs = getStatement().executeQuery("SELECT * FROM HUB");
            while (rs.next()) {
                hub.setStartCommand(rs.getString("start_command"));
                hub.setRunning(rs.getBoolean("is_running"));
            }

            rs = getStatement().executeQuery("SELECT * FROM NODES");
            while (rs.next()) {
                String host = rs.getString("host");
                int port = rs.getInt("port");
                boolean isRunning = rs.getBoolean("is_running");
                String startCommand = rs.getString("start_command");
                hub.addNode(new Node(host, port, isRunning, startCommand));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return hub;
    }

    private int getIsRunning(boolean isRunning) {
        if (isRunning) {
            return 1;
        } else {
            return 0;
        }
    }
}
