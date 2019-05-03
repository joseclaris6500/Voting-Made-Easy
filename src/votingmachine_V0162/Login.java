package votingmachine_V0162;

import javafx.scene.text.*;
import javafx.geometry.*;
import java.lang.*;
import java.util.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

public class Login extends AnchorPane {

    protected final BorderPane borderPane;
    protected final PasswordField voterID_Field;
    protected final PasswordField ssn_Field;
    protected final Button loginButton;
    protected final Button helpButton;
    private final HelpSocket helpSocket;

    public Login(HelpSocket helpSocket) {

        this.helpSocket = helpSocket;
        
        borderPane = new BorderPane();
        

        //setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        AnchorPane.setBottomAnchor(borderPane, 0.0);
        AnchorPane.setLeftAnchor(borderPane, 0.0);
        AnchorPane.setRightAnchor(borderPane, 0.0);
        AnchorPane.setTopAnchor(borderPane, 0.0);
         
        
        Label topLabel = this.setup_TopLabel();
        borderPane.setTop(topLabel);
        BorderPane.setAlignment(topLabel, javafx.geometry.Pos.CENTER);
        BorderPane.setMargin(topLabel, new Insets(10.0));
        
        ssn_Field = new PasswordField();
        voterID_Field = new PasswordField();
        VBox centerVBox = this.setup_CenterVBox(voterID_Field, ssn_Field);
        borderPane.setCenter(this.setup_CenterVBox(voterID_Field, ssn_Field));
        BorderPane.setAlignment(centerVBox, javafx.geometry.Pos.CENTER);
                
        loginButton = this.setup_LoginButton();
        helpButton = this.setup_HelpButton();
        VBox bottomVBox = this.setup_BottomVBox(loginButton, helpButton);
        borderPane.setBottom(bottomVBox);
        BorderPane.setAlignment(bottomVBox, javafx.geometry.Pos.CENTER);

        getChildren().add(borderPane);
    }
    
    private Label setup_TopLabel() {
        Label topLabel = new Label();
        topLabel.setAlignment(javafx.geometry.Pos.CENTER);
        topLabel.setText("Voter Login");
        topLabel.setFont(new Font(18.0));
        return topLabel;
    }
    
    private VBox setup_CenterVBox(PasswordField voterID_Field, PasswordField ssn_Field) {
        VBox centerVBox = new VBox();
        centerVBox.setAlignment(javafx.geometry.Pos.CENTER);
        
        //DL/VoterID label and field 
        Label voterID_Label = new Label("DL/Voter ID");
        HBox centerVBox_HBox_Top = new HBox();
        centerVBox_HBox_Top.setAlignment(javafx.geometry.Pos.CENTER);
        centerVBox_HBox_Top.setFillHeight(false);
        HBox.setMargin(voterID_Label, new Insets(0.0, 20.0, 0.0, 20.0));
        HBox.setMargin(voterID_Field, new Insets(0.0, 20.0, 0.0, 0.0));
        VBox.setMargin(centerVBox_HBox_Top, new Insets(20.0, 0.0, 5.0, 0.0));
        centerVBox_HBox_Top.getChildren().addAll(voterID_Label, voterID_Field);
        centerVBox.getChildren().add(centerVBox_HBox_Top);
        
        //SSN label and field 
        Label ssn_Label = new Label("SSN");
        HBox centerVBox_HBox_Bottom = new HBox();
        centerVBox_HBox_Bottom.setAlignment(javafx.geometry.Pos.CENTER);
        centerVBox_HBox_Bottom.setFillHeight(false);
        HBox.setMargin(ssn_Label, new Insets(0.0, 20.0, 0.0, 60.0));
        HBox.setMargin(ssn_Field, new Insets(0.0, 20.0, 0.0, 0.0));
        VBox.setMargin(centerVBox_HBox_Bottom, new Insets(5.0, 0.0, 20.0, 0.0));
        centerVBox_HBox_Bottom.getChildren().addAll(ssn_Label, ssn_Field);
        centerVBox.getChildren().add(centerVBox_HBox_Bottom);

        return centerVBox;
    }
    
    private VBox setup_BottomVBox(Button loginButton, Button helpButton) {
        VBox bottomVBox = new VBox();
        
        HBox bottomVBox_HBox_top = new HBox();
        bottomVBox_HBox_top.setAlignment(javafx.geometry.Pos.CENTER);
        HBox.setMargin(loginButton, new Insets(10.0));
        VBox.setMargin(bottomVBox_HBox_top, new Insets(0.0));

        HBox bottomVBox_HBox_Bottom = new HBox();
        bottomVBox_HBox_Bottom.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        HBox.setMargin(helpButton, new Insets(10.0));
        
        bottomVBox_HBox_top.getChildren().add(loginButton);
        bottomVBox.getChildren().add(bottomVBox_HBox_top);
        bottomVBox_HBox_Bottom.getChildren().add(helpButton);
        bottomVBox.getChildren().add(bottomVBox_HBox_Bottom);
        
        return bottomVBox;
    }
    
    private Button setup_LoginButton() {
        Button loginButton = new Button();
        loginButton.setAlignment(javafx.geometry.Pos.CENTER);
        loginButton.setMnemonicParsing(false);
        loginButton.setText("Login");
        return loginButton;
    }
    
    private Button setup_HelpButton() {
        double radius = 16;
        Image image = new Image(getClass().getResource("icon_Help.png").toExternalForm());
        IconButton btn = new IconButton(image, new Circle(radius));
        //add event handlers here
        btn.setOnAction((event) -> {
            this.helpSocket.sendRequest();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            //alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("A clerk has be notified and will assist you shortly.");
            alert.showAndWait();
        });
        return btn;
    }
}
