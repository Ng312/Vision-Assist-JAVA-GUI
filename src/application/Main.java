package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;
import org.apache.derby.iapi.sql.Statement;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import javafx.scene.media.*;
import javafx.scene.paint.Color;

public class Main extends Application {
  int width = 350;
  int height = 400;
  private Statement stmt;

  // Declare an array of Strings for glasses titles
  private String[] productTitles = {"NightVision Optics", "ClearSight Optics","FarSight Precision Optics ",
		  "ChromaCorrect Optics","PresbySculpt Optics"};

  // Declare an ImageView array for the glasses
  private ImageView[] glassesImage = {new ImageView("glasses1.jpg"),
      new ImageView("glasses2.jpg"),new ImageView("glasses3.jpg"),new ImageView("glasses4.jpg"),
      new ImageView("glasses5.jpg")};
  
  
  // Declare an array of strings for glasses description
  private String[] glassesDescription = new String[5];

  // Declare and create a description pane
  private DescriptionPane descriptionPane = new DescriptionPane();
  private HeaderPane headerPane = new HeaderPane();
  // Create a combo box for selecting glasses
  private ComboBox<String> cbo = new ComboBox<>(); // 
  
  private void displayLoadingVideo(Stage primaryStage) {
      Group root = new Group();

      Media media = new Media("file:///C:/Users/ngmt3/eclipse-workspace/SmartGlasses/bin/video.mp4");
      MediaPlayer mediaPlayer = new MediaPlayer(media);
      MediaView mediaView = new MediaView(mediaPlayer);

      mediaView.setPreserveRatio(false);
      root.getChildren().add(mediaView);

      // Create a fade transition for a smooth transition
      FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), root);
      fadeTransition.setFromValue(1);
      fadeTransition.setToValue(0);
      fadeTransition.setOnFinished(event -> {
          // Switch to the main application window after the loading video
          Platform.runLater(() -> {
              // Continue with the main application logic
              displayMainApp(primaryStage);
          });
      });

      // Play the video
      mediaPlayer.setOnReady(() -> {
          mediaPlayer.play();
      });

      mediaPlayer.setOnEndOfMedia(() -> {
          fadeTransition.play();
      });
      
      
      mediaView.setFitWidth(800); 
      mediaView.setFitHeight(450); 

      Scene scene = new Scene(root, 800, 450);
      
      primaryStage.setScene(scene);
      primaryStage.setTitle("Vision Assist");
      primaryStage.show();
  }
  
  
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
      displayLoadingVideo(primaryStage);
  }
  
  private void displayMainApp(Stage primaryStage) {    
	  //Set size for each image 
    for (ImageView imageView : glassesImage) {
          imageView.setFitWidth(width);  
          imageView.setFitHeight(height); 
    }
	  
    //Set product description
    try {
		setDescription();
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    // Set the first product for display
    setDisplay(0);

    // Add combo box and description pane to the border pane
    BorderPane pane = new BorderPane();
    BorderPane paneForComboBox = new BorderPane();

    VBox vBox = new VBox(10);
    // Set up the ComboBox
    paneForComboBox.setTop(headerPane);

    paneForComboBox.setCenter(cbo);
    pane.setTop(paneForComboBox);
    cbo.setPrefWidth(300);
    cbo.setTranslateX(25);
    cbo.setTranslateY(2);
    cbo.setValue("NightVision Optics");
    ObservableList<String> items = FXCollections.observableArrayList(productTitles);
    cbo.getItems().addAll(items);
    pane.setCenter(descriptionPane);
    // Display the selected product
    cbo.setOnAction(e -> setDisplay(items.indexOf(cbo.getValue())));

    
    // Create a scene and place it in the stage
    Scene scene = new Scene(pane);
    primaryStage.setTitle("Vision Assist"); 
    primaryStage.setScene(scene); 
    primaryStage.show(); 
  }

  /** Set display information on the description pane */
  public void setDisplay(int index) {
    descriptionPane.setTitle(productTitles[index]);
    descriptionPane.setImageView(glassesImage[index]);
    descriptionPane.setDescription(glassesDescription[index]);
  }

  public void setDescription() throws FileNotFoundException {
	 Scanner read = new Scanner(new File("C:/Users/ngmt3/eclipse-workspace/SmartGlasses/bin/product.txt"));
	 StringBuilder fileContent = new StringBuilder(); 
     while (read.hasNextLine()) {
    	 fileContent.append(read.nextLine()).append("\n");
     }
	 String line = fileContent.toString();
	 String[] desc = line.split("/");
	 glassesDescription[0] = desc[0];
	 glassesDescription[1] = desc[1];
	 glassesDescription[2] = desc[2];
	 glassesDescription[3] = desc[3];
	 glassesDescription[4] = desc[4];
  }

  public static void main(String[] args) {
    launch(args);
  }
}
