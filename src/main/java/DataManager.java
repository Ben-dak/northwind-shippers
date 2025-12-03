import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.ArrayList;

public class DataManager {

    // put datasource in this class
    private BasicDataSource createDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword("Baldursgate#99");
        return dataSource;
    }

    public int insertShipper(String name, String phone) {
        BasicDataSource dataSource = createDataSource();
        String sql = "INSERT INTO shippers (CompanyName, Phone) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, phone);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error inserting shipper: " + e.getMessage());
        }
        return -1;
    }

    public ArrayList<Shipper> getAllShippers() {
        BasicDataSource dataSource = createDataSource();
        String sql = "SELECT ShipperID, CompanyName, Phone FROM shippers ORDER BY ShipperID";
        ArrayList<Shipper> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ShipperID");
                String name = rs.getString("CompanyName");
                String phone = rs.getString("Phone");
                list.add(new Shipper(id, name, phone));
            }
        } catch (SQLException e) {
            System.out.println("Error reading shippers: " + e.getMessage());
        }
        return list;
    }

    public void updateShipperPhone(int shipperId, String newPhone) {
        BasicDataSource dataSource = createDataSource();
        String sql = "UPDATE shippers SET Phone = ? WHERE ShipperID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPhone);
            ps.setInt(2, shipperId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating phone: " + e.getMessage());
        }
    }

    public void deleteShipper(int shipperId) {
        BasicDataSource dataSource = createDataSource();
        String sql = "DELETE FROM shippers WHERE ShipperID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, shipperId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error deleting shipper: " + e.getMessage());
        }
    }
}

