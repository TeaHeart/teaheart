package dingcan;

import java.sql.*;

public class User {
    public Integer id;
    public String username;
    public String password;
    public Boolean isAdmin;

    public static User converter(ResultSet rs) {
        try {
            User user = new User();
            user.id = rs.getInt(1);
            user.username = rs.getString(2);
            user.password = rs.getString(3);
            user.isAdmin = rs.getBoolean(4);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
