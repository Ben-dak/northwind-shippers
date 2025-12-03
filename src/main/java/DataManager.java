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
        String query = "INSERT INTO shippers (CompanyName, Phone) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, name); // fills in the first ?
            preparedStatement.setString(2, phone); // fills in the second
            preparedStatement.executeUpdate();

            // Get the result containing primary keys (like I saw in workbook)
            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                // Iterate through the primary keys that were generated
                if (keys.next()) {
                    return keys.getInt(1); // returns the auto increment ID of the shipper I added
                }
            }
        } catch (SQLException e) {
            System.out.println("Error inserting shipper: " + e.getMessage());
        }
        return -1;
    }

    public ArrayList<Shipper> getAllShippers() {
        BasicDataSource dataSource = createDataSource();
        String query = "SELECT ShipperID, CompanyName, Phone FROM shippers ORDER BY ShipperID";
        ArrayList<Shipper> list = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ShipperID");
                String name = resultSet.getString("CompanyName");
                String phone = resultSet.getString("Phone");
                list.add(new Shipper(id, name, phone));
            }
        } catch (SQLException e) {
            System.out.println("Error reading shippers: " + e.getMessage());
        }
        return list;
    }

    public void updateShipperPhone(int shipperId, String newPhone) {
        BasicDataSource dataSource = createDataSource();
        String query = "UPDATE shippers SET Phone = ? WHERE ShipperID = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, newPhone);
            preparedStatement.setInt(2, shipperId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating phone: " + e.getMessage());
        }
    }

    public void deleteShipper(int shipperId) {
        BasicDataSource dataSource = createDataSource();
        String query = "DELETE FROM shippers WHERE ShipperID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, shipperId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error deleting shipper: " + e.getMessage());
        }
    }
}

