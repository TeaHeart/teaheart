package dingcan;

import java.sql.*;

public class Order {
    public Integer id;
    public Integer userId;
    public Integer dishId;

    public static Order converter(ResultSet rs) {
        try {
            Order order = new Order();
            order.id = rs.getInt(1);
            order.userId = rs.getInt(2);
            order.dishId = rs.getInt(3);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
