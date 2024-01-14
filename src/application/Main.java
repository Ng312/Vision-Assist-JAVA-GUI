package application;

import java.sql.*;


import org.apache.derby.iapi.sql.Statement;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Main extends Application {
  int width = 350;
  int height = 210;
  private Statement stmt;

  // Declare an array of Strings for flag titles
  private String[] productTitles = {"NightVision Optics", "ClearSight Optics","FarSight Precision Optics ",
		  "ChromaCorrect Opticss","PresbySculpt Optics"};

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
    glassesDescription[0] = "Introducing our revolutionary eyewear solution, NightVision Optics, specially designed to provide enhanced "
    		+ "visibility and combat night blindness. Engineered with cutting-edge technology,"
    		+ " these glasses are crafted for those seeking improved vision in low-light conditions.";
    glassesDescription[1] = "Discover a new realm of visual clarity with our ClearSight Optics, meticulously designed to address "
    		+ "short-sightedness and provide unparalleled visual correction. Engineered with precision and comfort in mind, "
    		+ "these glasses offer a holistic approach to managing myopia.";
    glassesDescription[2] = "Experience the world with unparalleled clarity and precision using our FarSight Precision Optics ,"
    		+ " expertly crafted to address long-sightedness and provide a crystal-clear view of both near and distant objects. "
    		+ "Designed for comfort and style, these glasses redefine the way you perceive the world.";
    glassesDescription[3] = "Introducing ChromaCorrect Optic, a groundbreaking solution designed to transform the way individuals "
    		+ "with color blindness experience the world. These cutting-edge glasses are engineered to enhance color perception,"
    		+ " providing a vivid and vibrant visual experience like never before.";
    glassesDescription[4] = "Empower your vision and rediscover the joy of reading with PresbySculpt Optics. "
    		+ "Crafted specifically for individuals experiencing presbyopia, these innovative glasses seamlessly"
    		+ "blend advanced optics with elegant design, offering a sophisticated solution for near-vision clarity.";
    		
    
    // Set the first product for display
    setDisplay(0);

    // Add combo box and description pane to the border pane
    BorderPane pane = new BorderPane();
    //pane.setStyle("-fx-background : #FFE194;");
    BorderPane paneForComboBox = new BorderPane();

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
    cbo.setValue("NightVision Optics");
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


  public static void main(String[] args) {
    launch(args);
  }
}
