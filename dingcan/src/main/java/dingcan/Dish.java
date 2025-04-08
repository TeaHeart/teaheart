package dingcan;

import java.sql.*;

public class Dish {
    public Integer id;
    public String name;
    public Double price;

    public static Dish converter(ResultSet rs) {
        try {
            Dish dish = new Dish();
            dish.id = rs.getInt(1);
            dish.name = rs.getString(2);
            dish.price = rs.getDouble(3);
            return dish;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}