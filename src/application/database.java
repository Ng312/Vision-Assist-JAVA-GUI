package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class database{
	static Connection connection;
	private static String lastProcessed = "";
	
	public database() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			connection = DriverManager.getConnection("jdbc:derby:mydatabase;create=true");
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
            insertProdData("Color Blind","ChromaCorrect Optics");
            insertProdData("Presbyopia","PresbySculpt Optics");


        } catch (Exception e) {
            e.printStackTrace();
        }
        }

    private static void createTable(){
        Statement statement;
		try {
			statement = connection.createStatement();
			statement.execute("CREATE TABLE customer (email VARCHAR(255) PRIMARY KEY, name VARCHAR(255), vision_impairment VARCHAR(255))");
		    statement.execute("CREATE TABLE product (target_group VARCHAR(255), product VARCHAR(255))");
	        System.out.println("Customer Table Contents:");
	        System.out.printf("%-20s %-20s %-20s","Email","Name","Vision Impairment");
		    statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
       
    }

    static boolean insertCusData( String email, String name, String vision_impairment){
        String sql = "INSERT INTO customer (email, name, vision_impairment) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, vision_impairment);
            preparedStatement.executeUpdate();
            return true;
        }catch(Exception e) {
        	return false;
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
    static String getImpairmentForEmail(String email) throws Exception {
        String query = "SELECT vision_impairment FROM customer WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("vision_impairment");
                }
            }
        }
        return null; // Return null if no matching email is found
    }
    static String getProductForImpairment(String vision_impairment) throws Exception {
        String query = "SELECT product FROM product WHERE target_group = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, vision_impairment);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("product");
                }
            }
        }
        return null; // Return null if no matching product is found
    }

    static void queryAndPrintData(String new_email) throws Exception {
        String query = "SELECT * FROM customer WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        	preparedStatement.setString(1, new_email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String email = resultSet.getString("email");
                    String name = resultSet.getString("name");
                    String vision_impairment = resultSet.getString("vision_impairment");
                    System.out.printf("\n%-20s %-20s %-20s",email,name,vision_impairment);

                }
            }
       }
    }


}
    



