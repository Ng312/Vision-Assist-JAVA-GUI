package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class database{
	static Connection connection;
	public database() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			connection = DriverManager.getConnection("jdbc:derby:mydatabase1;create=true");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

    public void action() {
        try {
            createTable();
            insertProdData("Night Blindness","NightVision Optics");
            insertProdData("Short-sightedness","ClearSight Optics");
            insertProdData("Long-sightedness","FarSight Precision Optics");
            insertProdData("Color blind","ChromaCorrect Optics");
            insertProdData("Presbyopia","PresbySculpt Optics");
            queryAndPrintData();

            //connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        }

    private static void createTable() throws Exception {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE customer (email VARCHAR(255) PRIMARY KEY, name VARCHAR(255), disability VARCHAR(255))");
        statement.execute("CREATE TABLE product (target_group VARCHAR(255), product VARCHAR(255))");
        statement.close();
    }

    static void insertCusData( String email, String name, String disability) throws Exception {
        String sql = "INSERT INTO customer (email, name, disability) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, disability);
            preparedStatement.executeUpdate();
        }
    }
    static void insertProdData(String target_group,String product) throws Exception{
    	String sql = "INSERT INTO product (target_group,product) VALUES (?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, target_group);
            preparedStatement.setString(2, product);
            preparedStatement.executeUpdate();
        }
    }
    static String getDisabilityForEmail(String email) throws Exception {
        String query = "SELECT disability FROM customer WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("disability");
                }
            }
        }
        return null; // Return null if no matching email is found
    }
    static String getProductForDisability(String disability) throws Exception {
        String query = "SELECT product FROM product WHERE target_group = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, disability);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("product");
                }
            }
        }
        return null; // Return null if no matching product is found
    }

    static void queryAndPrintData() throws Exception {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");

        System.out.println("Table Contents:");
        System.out.println("ID\tName");
        while (resultSet.next()) {
            String email = resultSet.getString("email");
            String name = resultSet.getString("name");
            String disability = resultSet.getString("disability");
            System.out.println(email + "\t" + name + "\t" + disability);
        }

        //resultSet.close();
        //statement.close();
    }
    
}


