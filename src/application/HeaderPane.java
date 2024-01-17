package application;

import java.sql.SQLException;

import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HeaderPane extends BorderPane{
	
	TextField new_emailtf = new TextField();
	TextField new_name = new TextField();
	Button signupbtn = new Button("Sign Up");
	String selected ;
	Label mssg = new Label();
	String[]impairment = {"Night Blindness","Short-sightedness","Long-sightedness","Color Blind","Presbyopia"};
	ComboBox impairmentcb = new ComboBox(FXCollections.observableArrayList(impairment));
	database db = new database();
    HeaderPane() {
    	db.action();
        BorderPane pane = new BorderPane();
        ImageView searchimg = new ImageView("search.png");
        ImageView icon = new ImageView("icon.png");
        icon.setFitWidth(60);
        icon.setFitHeight(60);
        icon.setTranslateY(5);
        searchimg.setFitWidth(10);
        searchimg.setFitHeight(10);
        Button searchbtn = new Button();

        searchbtn.setGraphic(searchimg);

        Label title = new Label("FOCUS POINT");
        title.setFont(Font.font("Josefin Sans", FontWeight.BOLD, 30));
        Label mssg1 = new Label("Not sure which product to choose?");
        Label mssg2 = new Label("Enter your e-mail, and we'll provide personalized suggestions for you!");
        TextField emailtf = new TextField();
        Label emaillb = new Label("E-mail :        ");
        Label suggestlb = new Label("Suggestion :");
        TextField suggesttf = new TextField();
        Hyperlink link = new Hyperlink("Sign up now !");
        Label signuplb = new Label("Not a member ?");
        title.setTranslateX(10);
        title.setTranslateY(10);
        mssg1.setTranslateX(10);
        mssg2.setTranslateX(10);
        link.setTranslateX(-10);
        link.setTranslateY(-4);
        emailtf.setPrefWidth(200);
        suggesttf.setPrefWidth(200);
        
        link.setOnAction(e -> {
        	signUp();
        });
        //search for suggestion
        searchbtn.setOnAction(e->{
        	try {
        		suggesttf.clear();
				String emailsearch = db.getImpairmentForEmail(emailtf.getText());
				String suggestion = db.getProductForImpairment(emailsearch);
				suggesttf.setText(suggestion);
				

			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
        });
        VBox vBox = new VBox(5);
        HBox hBox1 = new HBox(30);
        HBox hBox2 = new HBox(10);
        HBox hBox3 = new HBox(10);
        hBox2.setPadding(new Insets(10, 0, 0, 10));
        hBox3.setPadding(new Insets(0, 0, 10, 10));
        setStyle("-fx-background-color: #DED0B6;");
        hBox1.getChildren().addAll(title,icon);
        hBox2.getChildren().addAll(emaillb, emailtf, searchbtn, signuplb, link);
        hBox3.getChildren().addAll(suggestlb, suggesttf);
        vBox.getChildren().addAll(hBox1,mssg1,mssg2,hBox2,hBox3);
        setTop(vBox);

    }
    //sign up
    public void signUp() {
    	StackPane Spane = new StackPane();
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(50));
        
        ImageView icon = new ImageView("icon.png");
        icon.setFitWidth(100);
        icon.setFitHeight(100);
        icon.setTranslateY(-10);
        icon.setTranslateX(-10);
        Label signuplb = new Label("Sign Up");
        signuplb.setTranslateY(30);
        signuplb.setFont(Font.font("Josefin Sans", FontWeight.BOLD, 20));
        signuplb.setPadding(new Insets(20,0,0,70));

	    Spane.getChildren().addAll(signuplb,icon);
	    Spane.setAlignment(icon,Pos.TOP_RIGHT);
	    Spane.setAlignment(signuplb,Pos.TOP_LEFT);

  
        Label emailLabel = new Label("E-Mail : ");
        pane.add(emailLabel, 0, 1);
        pane.add(new_emailtf, 1, 1);

        Label nameLabel = new Label("Name : ");
        pane.add(nameLabel, 0, 2);
        pane.add(new_name, 1, 2);

        Label impairmentLabel = new Label("Vision Impairment : ");
        pane.add(impairmentLabel, 0, 3);
        pane.add(impairmentcb, 1, 3);

        Button signupbtn = new Button("Sign Up");
        signupbtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Set button color
        pane.add(signupbtn, 1, 4);
        GridPane.setHalignment(signupbtn, HPos.RIGHT);

        signupbtn.setOnAction(e -> {
            try {
                pane.getChildren().remove(mssg);
                selected = String.valueOf(impairmentcb.getValue());
                boolean success = db.insertCusData(new_emailtf.getText(), new_name.getText(), selected);
                if (success) {
                    db.queryAndPrintData(new_emailtf.getText());
                    mssg.setTextFill(Color.GREEN); // Set text color
                    mssg.setText("Sign up successful.");
                } else {
                	mssg.setTextFill(Color.RED); // Set text color
                    mssg.setText("User already exists.");;
                }
                pane.add(mssg, 0, 4);
                impairmentcb.valueProperty().set(null);
                new_emailtf.clear();
                new_name.clear();
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        });

        pane.setVgap(10);
        pane.setAlignment(Pos.BOTTOM_CENTER);
        Spane.getChildren().add(pane);
        StackPane.setAlignment(pane, Pos.BOTTOM_CENTER);
        Scene scene = new Scene(Spane,400,280);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Customer Sign Up");
        stage.show();
    }
    
 

}


