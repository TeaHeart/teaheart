package org.example.database;

import org.example.X;
import org.example.function.Func1;
import org.example.function.FuncX;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCX {
    private final String url;
    private final String username;
    private final String password;

    public JDBCX(String driver, String url, String username, String password) {
        FuncX.sneaky(Class::forName, driver);
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public <T> List<T> query(String sql, Func1<ResultSet, T> converter, Object... args) {
        try (Connection conn = getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                setParams(ps, args);
                List<T> list = new ArrayList<>();
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(FuncX.sneaky(converter, rs));
                    }
                }
                return list;
            }
        } catch (SQLException e) {
            throw X.throwrt(e);
        }
    }

    public int update(String sql, Object... args) {
        try (Connection conn = getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                setParams(ps, args);
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw X.throwrt(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    private void setParams(PreparedStatement ps, Object... args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
    }
}