package application;

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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class HeaderPane extends BorderPane{
	
	TextField new_emailtf = new TextField();
	TextField new_name = new TextField();
	Button signupbtn = new Button("Sign Up");
	String selected ;
	Label mssg = new Label();
	String[]impairment = {"Night Blindness","Short-sightedness","Long-sightedness","Color Blind","Presbyopia"};
	ComboBox impairmentcb = new ComboBox(FXCollections.observableArrayList(impairment));
	database db = new database();
	Main main;
	
    HeaderPane() {
    	db.action();
    	
        BorderPane pane = new BorderPane();
        
        Label title = new Label("FOCUS POINT");
        title.setFont(Font.font("Josefin Sans", FontWeight.BOLD, 30));
        title.setTranslateX(10);
        title.setTranslateY(10);
        
        Label mssg1 = new Label("Not sure which product to choose?");
        mssg1.setTranslateX(10);
        
        Label mssg2 = new Label("Enter your e-mail, and we'll provide personalized suggestions for you!");
        mssg2.setTranslateX(10);

        TextField emailtf = new TextField();
        emailtf.setPrefWidth(200);

        Label emaillb = new Label("E-mail :        ");
        
        Label suggestlb = new Label("Suggestion :");

        Label signuplb = new Label("Not a member ?");

        TextField suggesttf = new TextField();
        suggesttf.setPrefWidth(200);
        
        Hyperlink link = new Hyperlink("Sign up now !");
        link.setTranslateX(-10);
        link.setTranslateY(-4);
        
        link.setOnAction(e -> {
        	signUp();
        });

        ImageView searchimg = new ImageView("search.png");
        searchimg.setFitWidth(10);
        searchimg.setFitHeight(10);
        
        ImageView icon = new ImageView("icon.png");
        icon.setFitWidth(60);
        icon.setFitHeight(60);
        icon.setTranslateY(5);
        
        Button searchbtn = new Button();
        searchbtn.setGraphic(searchimg);
        
        searchbtn.setOnAction(e->{
        	try {
        		suggesttf.clear();
				String emailsearch = db.getImpairmentForEmail(emailtf.getText());
				String suggestion = db.getProductForImpairment(emailsearch);
				suggesttf.setText(suggestion);
				main.setDisplay(main.items.indexOf(suggestion));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
        });
        
        VBox vBox = new VBox(5);
        
        HBox hBox1 = new HBox(30);
        
        HBox hBox2 = new HBox(10);
        hBox2.setPadding(new Insets(10, 0, 0, 10));

        HBox hBox3 = new HBox(10);
        hBox3.setPadding(new Insets(0, 0, 10, 10));
        
        hBox1.getChildren().addAll(title,icon);
        hBox2.getChildren().addAll(emaillb, emailtf, searchbtn, signuplb, link);
        hBox3.getChildren().addAll(suggestlb, suggesttf);
        vBox.getChildren().addAll(hBox1,mssg1,mssg2,hBox2,hBox3);

        setTop(vBox);
        setStyle("-fx-background-color: #DED0B6;");
    }
    
    public void signUp() {
    	StackPane sPane = new StackPane();
    	
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

	    sPane.getChildren().addAll(signuplb,icon);
	    sPane.setAlignment(icon,Pos.TOP_RIGHT);
	    sPane.setAlignment(signuplb,Pos.TOP_LEFT);

  
        Label emailLabel = new Label("E-mail : ");
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
        	if(!new_emailtf.getText().isBlank()) {
	            try {
	                pane.getChildren().remove(mssg);
	                selected = String.valueOf(impairmentcb.getValue());
	                boolean success = db.insertCusData(new_emailtf.getText(), new_name.getText(), selected);
	                
	                if (success) {
	                    db.queryAndPrintData(new_emailtf.getText());
	                    mssg.setTextFill(Color.GREEN); 
	                    mssg.setText("Sign up successful.");
	                } else {
	                	mssg.setTextFill(Color.RED); 
	                    mssg.setText("User already exists.");;
	                }
	                
	                pane.add(mssg, 0, 4);
	                impairmentcb.valueProperty().set(null);
	                new_emailtf.clear();
	                new_name.clear();
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
        	} else {
        		mssg.setText("Invalid e-mail.");;
        	}
        });

        pane.setVgap(10);
        pane.setAlignment(Pos.BOTTOM_CENTER);
        sPane.getChildren().add(pane);
        StackPane.setAlignment(pane, Pos.BOTTOM_CENTER);
        
        Scene scene = new Scene(sPane,400,280);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Customer Sign Up");
        stage.show();
    }
}


