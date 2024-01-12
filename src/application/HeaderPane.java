package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HeaderPane extends BorderPane{

	HeaderPane(){
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
	    title.setTranslateX(10);
	    title.setTranslateY(10);
	    mssg1.setTranslateX(10);
	    mssg1.setTranslateY(10);
	    mssg2.setTranslateX(10);
	    mssg2.setTranslateY(10);
	    emailtf.setPrefWidth(200);
	    suggesttf.setPrefWidth(200);
	    
	    VBox vBox = new VBox(5);
	    HBox hBox1 = new HBox(10);
	    HBox hBox2 = new HBox(10);
	    hBox1.setPadding(new Insets(10,0,0,10));
	    hBox2.setPadding(new Insets(0,0,10,10));
	    setStyle("-fx-background-color: #AAD7D9;");

	    hBox1.getChildren().addAll(emaillb, emailtf, searchbtn);
	    hBox2.getChildren().addAll(suggestlb,suggesttf);


	    vBox.getChildren().addAll(title,mssg1,mssg2,hBox1,hBox2);
	    

	    setTop(vBox);
	}
}
