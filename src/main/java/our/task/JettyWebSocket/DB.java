package our.task.JettyWebSocket;

import java.sql.*;


class DB {
    private Connection conn;

    void connectDb() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:./src/main/java/our/task/JettyWebSocket/data/Products.db");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    Product getProduct(String id) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Products WHERE product_id = " + Integer.parseInt(id));
        return new Product(resultSet.getString("ct_publish_date"), resultSet.getString("product_okpd_2"),
                resultSet.getString("product_name"), resultSet.getString("product_measure"),
                resultSet.getDouble("product_price"), resultSet.getInt("product_id"),
                resultSet.getString("region_code"), resultSet.getString("ct_href"),
                resultSet.getString("rev_okpd"));
    }
}
