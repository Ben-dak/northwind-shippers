import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;

public class DataManager {

    // put datasource in this class
    private BasicDataSource createDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername("root");
        dataSource.setPassword("Baldursgate#99");
        return dataSource;
    }
}
