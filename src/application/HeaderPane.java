package application;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
public class HeaderPane extends BorderPane{
	
	TextField new_emailtf = new TextField();
	TextField new_name = new TextField();
	TextField new_disability = new TextField();
	Button signupbtn = new Button("Sign Up");
	database db = new database();
    HeaderPane() {
    	db.action();
        BorderPane pane = new BorderPane();
        ImageView searchimg = new ImageView("search.png");
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
        mssg1.setTranslateY(10);
        mssg2.setTranslateX(10);
        mssg2.setTranslateY(10);
        link.setTranslateX(-10);
        link.setTranslateY(-4);
        emailtf.setPrefWidth(200);
        suggesttf.setPrefWidth(200);

        link.setOnAction(e -> {
            signUp();
        });
        searchbtn.setOnAction(e->{
        	try {
        		suggesttf.clear();
				String emailsearch = db.getDisabilityForEmail(emailtf.getText());
				String suggestion = db.getProductForDisability(emailsearch);
				suggesttf.setText(suggestion);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        VBox vBox = new VBox(5);
        HBox hBox1 = new HBox(10);
        HBox hBox2 = new HBox(10);
        hBox1.setPadding(new Insets(10, 0, 0, 10));
        hBox2.setPadding(new Insets(0, 0, 10, 10));
        setStyle("-fx-background-color: #AAD7D9;");

        hBox1.getChildren().addAll(emaillb, emailtf, searchbtn, signuplb, link);
        hBox2.getChildren().addAll(suggestlb, suggesttf);

        vBox.getChildren().addAll(title, mssg1, mssg2, hBox1, hBox2);

        setTop(vBox);
    }
	public void signUp() {
		GridPane pane = new GridPane();
		pane.setPadding(new Insets(50));

		pane.add(new Label("E-Mail :   "), 0, 0);
		pane.add(new_emailtf, 1, 0);
		pane.add(new Label("Name :   "), 0, 1);
		pane.add(new_name, 1, 1);
		pane.add(new Label("Disability (If any) :   "), 0, 2);
		pane.add(new_disability, 1, 2);
		pane.add(signupbtn, 1, 3);
		GridPane.setHalignment(signupbtn, HPos.RIGHT);
		pane.setVgap(10);
		
		signupbtn.setOnAction(e->{
			try {
				db.insertCusData(new_emailtf.getText(), new_name.getText(), new_disability.getText());
				db.queryAndPrintData();
				Label mssg = new Label("Sign up successful.");
				new_emailtf.clear();
				new_name.clear();
				new_disability.clear();
				pane.add(mssg, 0, 3);
			} catch (Exception exp) {
			// TODO Auto-generated catch block
				exp.printStackTrace();
			}
		});

		Scene scene = new Scene(pane);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Customer Sign Up");
		stage.show();
		
	}

}
