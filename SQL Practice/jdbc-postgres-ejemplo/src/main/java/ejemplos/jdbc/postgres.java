package ejemplos.jdbc;

import java.sql.*;

public class postgres {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "USER";
        String password = "PASSWORD";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Conexión exitosa.");

            /* --- Create --- */
            // Try to insert new element as the last id
            int newId = 1;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COALESCE(MAX(id),0)+1 AS next_id FROM city")) {
                if (rs.next()) {
                    newId = rs.getInt("next_id");
                }
            }

            // Create try
            String insertSQL = "INSERT INTO city (id, name, postal_code, population) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSQL)) {
                ps.setInt(1, newId);
                ps.setString(2, "Nueva Ciudad");
                ps.setInt(3, 12345);
                ps.setInt(4, 500000);
                int filasInsertadas = ps.executeUpdate();
                System.out.println("INSERT -> Filas afectadas: " + filasInsertadas);
            }

            /* --- Read --- */
            String selectSQL = "SELECT * FROM city";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectSQL)) {
                System.out.println("\n === Lista de ciudades ===");
                while (rs.next()) {
                    String name = rs.getString("name");
                    int postal_code = rs.getInt("postal_code");
                    int population = rs.getInt("population");
                    System.out.println("City: " + name + " - Postal code: " + postal_code + " - Population: " + population);
                }
            }

            /* --- Update --- */
            String updateSQL = "UPDATE city SET population = ? WHERE name = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateSQL)) {
                ps.setInt(1, 60000);
                ps.setString(2, "Nueva ciudad");
                int filasActualizadas = ps.executeUpdate();
                System.out.println("\nUPDATE -> Filas afectadas: "+filasActualizadas);
            }

            /* --- Delete --- */
            String deleteSQL = "DELETE FROM city WHERE name = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteSQL)) {
                ps.setString(1, "Nueva ciudad");
                int filasEliminadas = ps.executeUpdate();
                System.out.println("\nDELETE -> Filas afectadas: " + filasEliminadas);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
