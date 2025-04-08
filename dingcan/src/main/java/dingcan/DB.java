package dingcan;

import java.util.*;
import java.util.function.*;
import java.util.logging.Logger;
import java.sql.*;

public class DB {
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    
    private static final Logger log = Logger.getLogger(DB.class.getName());

    static {
        try {
            DRIVER = System.getProperty("db.driver", "com.mysql.cj.jdbc.Driver");
            URL = System.getProperty("db.url", "jdbc:mysql://localhost:3306/dingcan");
            USERNAME = System.getProperty("db.username", "root");
            PASSWORD = System.getProperty("db.password", "123123");
            Class.forName(DRIVER);
            log.info(String.format("%s %s %s %s", DRIVER, URL, USERNAME, PASSWORD));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    private static void setParam(PreparedStatement ps, Object... args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
    }

    public static <T> List<T> query(String sql, Function<ResultSet, T> converter, Object... args) {
        try (Connection conn = getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                setParam(ps, args);
                List<T> list = new ArrayList<>();
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(converter.apply(rs));
                    }
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int update(String sql, Object... args) {
        try (Connection conn = getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                setParam(ps, args);
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
