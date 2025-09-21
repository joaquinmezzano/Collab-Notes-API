package ejemplos.jdbc;

import java.sql.*;

public class postgres {
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "USERNAME","PASSWORD");
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM city");
            while (rs.next()) {
                String name = rs.getString("name");
                int postal_code = rs.getInt("postal_code");
                int population = rs.getInt("population");
                System.out.println("City: "+name+" - Postal code: "+postal_code+" - Population: "+population);
            }
        }
    }
}
