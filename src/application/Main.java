package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.*;

public class Main extends Application {
  int width = 350;
  int height = 400;

  private String[] productTitles = {"NightVision Optics", "ClearSight Optics","FarSight Precision Optics ",
		  "ChromaCorrect Optics","PresbySculpt Optics"};

  private ImageView[] glassesImage = {new ImageView("glasses1.jpg"),
      new ImageView("glasses2.jpg"),new ImageView("glasses3.jpg"),new ImageView("glasses4.jpg"),
      new ImageView("glasses5.jpg")};
  
  private String[] glassesDescription = new String[5];

  private DescriptionPane descriptionPane = new DescriptionPane();
  private HeaderPane headerPane = new HeaderPane();
  private ComboBox<String> cbo = new ComboBox<>(); 
  ObservableList<String> items = FXCollections.observableArrayList(productTitles);

  private void displayLoadingVideo(Stage primaryStage) {
      Group root = new Group();

      String videoFile = "loading.mp4";
      Media media = new Media(new File(videoFile).toURI().toString());
      MediaPlayer mediaPlayer = new MediaPlayer(media);
      MediaView mediaView = new MediaView(mediaPlayer);

      mediaView.setPreserveRatio(false);
      root.getChildren().add(mediaView);

      FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), root);
      fadeTransition.setFromValue(1);
      fadeTransition.setToValue(0);
      fadeTransition.setOnFinished(event -> {
          Platform.runLater(() -> {
              displayMainApp(primaryStage);
          });
      });

      mediaPlayer.setOnReady(() -> {
          mediaPlayer.play();
      });

      mediaView.setOnMouseClicked(e -> {
    	  fadeTransition.play();
    	  mediaPlayer.stop();
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
  
  private void displayMainApp(Stage primaryStage) {    
    for (ImageView imageView : glassesImage) {
          imageView.setFitWidth(width);  
          imageView.setFitHeight(height); 
    }
	  
    try {
		setDescription();
	} catch (FileNotFoundException e1) {
		e1.printStackTrace();
	}
    setDisplay(0);

    BorderPane pane = new BorderPane();
    BorderPane paneForComboBox = new BorderPane();

    paneForComboBox.setTop(headerPane);
    paneForComboBox.setCenter(cbo);
    pane.setTop(paneForComboBox);
    
    cbo.setPrefWidth(300);
    cbo.setTranslateX(25);
    cbo.setTranslateY(2);
    cbo.setValue("NightVision Optics");

    cbo.setOnAction(e -> setDisplay(items.indexOf(cbo.getValue())));
    cbo.getItems().addAll(items);
    pane.setCenter(descriptionPane);

    Scene scene = new Scene(pane);
    primaryStage.setTitle("Vision Assist"); 
    primaryStage.setScene(scene); 
    primaryStage.show(); 
  }

  public void setDisplay(int index) {
    descriptionPane.setTitle(productTitles[index]);
    descriptionPane.setImageView(glassesImage[index]);
    descriptionPane.setDescription(glassesDescription[index]);
  }

  public void setDescription() throws FileNotFoundException {
	 Scanner read = new Scanner(new File("productDesc.txt")); 
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

  @Override 
  public void start(Stage primaryStage) {
      displayLoadingVideo(primaryStage);
      headerPane.main = this;
  }
  
  public static void main(String[] args) {
    launch(args);
  }
}
