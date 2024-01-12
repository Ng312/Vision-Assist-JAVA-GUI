package application;

import java.sql.*;

import org.apache.derby.iapi.sql.Statement;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Main extends Application {
  int width = 350;
  int height = 210;
  private Statement stmt;
  // Declare an array of Strings for flag titles
  private String[] productTitles = {"SmartScript Spectacle", "CaptureLens Spectacle","LinguaAudio Translens",
		  "AndroidGaze SmartFrames","ChromaCorrect Glasses"};

  // Declare an ImageView array for the glasses
  private ImageView[] glassesImage = {new ImageView("glasses1.jpg"),
      new ImageView("glasses2.jpeg"),new ImageView("glasses3.jpg"),new ImageView("glasses4.jpg"),
      new ImageView("glasses5.jpg")};
  
  
  // Declare an array of strings for flag descriptions
  private String[] glassesDescription = new String[5];

  // Declare and create a description pane
  private DescriptionPane descriptionPane = new DescriptionPane();
  private HeaderPane headerPane = new HeaderPane();
  // Create a combo box for selecting countries
  private ComboBox<String> cbo = new ComboBox<>(); // 
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    //Set size for each image 
    for (ImageView imageView : glassesImage) {
          imageView.setFitWidth(width);  // Set your preferred width
          imageView.setFitHeight(height); // Set your preferred height
    }
	  
	// Set text description
    glassesDescription[0] = "Introducing the SmartScript Spectacle – Your Gateway to Multilingual Excellence! "
    		+ "\n\n" + "Unlock the power of seamless communication with the revolutionary SmartScript "
    		+ "\n" + "Spectacle,a cutting-edge device that transcends language barriers effortlessly. "
    		+ "\n" + "This sleek and compact device is your personal translator, designed to "
    		+ "\n" + "make communication a breeze no matter where you are in the world.";
    glassesDescription[1] = "Introducing CaptureLens Spectacles – Your Vision, Your Memories, Captured! "
    		+ "\n\n" + "Elevate your visual storytelling with the groundbreaking CaptureLens Spectacles"
    		+ "\n" + "a cutting-edge wearable that seamlessly integrates photography and videography"
    		+ "\n" + "into your everyday experiences. With a sleek and stylish design, these spectacles"
    		+ "\n" + "go beyond mere eyewear – they are your personal lens to a world of limitless creativity.";
    glassesDescription[2] = "Introducing LinguaAudio Translens – Where Languages Unite in Harmony!"
    		+ "\n\n" + "Step into a world of seamless communication with the LinguaAudio Translens,"
    		+ "\n" + "a groundbreaking pair of smart spectacles designed to transcend linguistic"
    		+ "\n" + "boundaries. Immerse yourself in the art of conversation without missing a beat – "
    		+ "\n" + "these intelligent spectacles not only enhance your vision but" 
    		+ "\n" +"also serve as your personal audio translator.";
    glassesDescription[3] = "Introducing AndroidGaze SmartFrames – Your Portal to the Digital Universe, "
    		+ "\n" + "Right on Your Nose!" + "\n\n" + "Unleash the power of technology with the AndroidGaze SmartFrames, a revolutionary"
    		+ "\n"+ "pair of smart spectacles that redefine what eyewear can be. Featuring a built-in" 
    		+ "\n" + "Android system, these frames seamlessly transform into a compact yet powerful " 
    		+ "\n" + "martphone, bringing the world of apps, connectivity, and innovation right to your gaze.";
    glassesDescription[4] = "Introducing ChromaCorrect Glasses – See the World in True Color, Naturally!"
    		+ "\n\n" + "Experience the world through a lens of vibrant clarity with ChromaCorrect Glasses"
    		+ "\n" + "a revolutionary eyewear innovation designed to enhance and color-correct your vision."
    		+ "\n" + "These spectacles go beyond ordinary eyewear, providing a unique and" 
    		+ "\n" + "natural way to see the world in its true, vivid colors.";
    		
    
    // Set the first product for display
    setDisplay(0);

    // Add combo box and description pane to the border pane
    BorderPane pane = new BorderPane();
    //pane.setStyle("-fx-background : #FFE194;");
    BorderPane paneForComboBox = new BorderPane();

    //User type email
    //initializeDB();

    
    VBox vBox = new VBox(10);
 
    //searchbtn.setOnAction(e -> showGrade());
    Label productlb = new Label("Select a product");
    productlb.setTranslateX(260);
    productlb.setTranslateY(5);

    // Set up the ComboBox
    paneForComboBox.setTop(headerPane);
    paneForComboBox.setLeft(productlb);
    paneForComboBox.setCenter(cbo);
    pane.setTop(paneForComboBox);
    cbo.setPrefWidth(300);
    cbo.setTranslateX(2);
    cbo.setTranslateY(2);
    cbo.setValue("SmartScript Spectacle");
    ObservableList<String> items = FXCollections.observableArrayList(productTitles);
    cbo.getItems().addAll(items);
    pane.setCenter(descriptionPane);
    // Display the selected product
    cbo.setOnAction(e -> setDisplay(items.indexOf(cbo.getValue())));

    
    // Create a scene and place it in the stage
    Scene scene = new Scene(pane);
    primaryStage.setTitle("Smart Glasses GUI"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }

  /** Set display information on the description pane */
  public void setDisplay(int index) {
    descriptionPane.setTitle(productTitles[index]);
    descriptionPane.setImageView(glassesImage[index]);
    descriptionPane.setDescription(glassesDescription[index]);
  }

  
  /*private void initializeDB() {
	    try {
	      // Load the JDBC driver
	      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//	      Class.forName("oracle.jdbc.driver.OracleDriver");
	      System.out.println("Driver loaded");

	      // Establish a connection
	      Connection connection = DriverManager.getConnection
	        ("jdbc:derby:javabook;user=scott;password=tiger");
//	    ("jdbc:oracle:thin:@liang.armstrong.edu:1521:orcl",
//	     "scott", "tiger");
	      System.out.println("Database connected");

	      // Create a statement
	      stmt = connection.createStatement();
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }
  
  private void showGrade() {
	    String ssn = tfSSN.getText();
	    String courseId = tfCourseId.getText();
	    try {
	      String queryString = "select firstName, mi, " +
	        "lastName, title, grade from Student, Enrollment, Course " +
	        "where Student.ssn = '" + ssn + "' and Enrollment.courseId "
	        + "= '" + courseId +
	        "' and Enrollment.courseId = Course.courseId " +
	        " and Enrollment.ssn = Student.ssn";

	      ResultSet rset = stmt.executeQuery(queryString);

	      if (rset.next()) {
	        String lastName = rset.getString(1);
	        String mi = rset.getString(2);
	        String firstName = rset.getString(3);
	        String title = rset.getString(4);
	        String grade = rset.getString(5);

	        // Display result in a label
	        lblStatus.setText(firstName + " " + mi +
	          " " + lastName + "'s grade on course " + title + " is " +
	          grade);
	      } else {
	        lblStatus.setText("Not found");
	      }
	    }
	    catch (SQLException ex) {
	      ex.printStackTrace();
	    }
	  }*/
  public static void main(String[] args) {
    launch(args);
  }
}
