package votingmachine_V0162;

import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

public class ReceiptPreview extends AnchorPane {

    protected final BorderPane borderPane;
    protected final VBox centerVBox;
    protected final VBox vBox;
    protected final Label voterIDLabel;
    protected final Label locationLabel;
    protected final Label dateLabel;
    protected final Label timeLabel;
    protected final VBox bottomVBox;
    protected final HBox bottomVBox_HBox_top;
    protected final Button printButton;
    protected final Button mailButton;
    protected final Button emailButton;
    protected final HBox bottomVBox_HBox_Bottom;
    protected final Button helpButton;
    protected final Label topLabel;
    private final HelpSocket helpSocket;

    public ReceiptPreview(HelpSocket helpSocket) {

        this.helpSocket = helpSocket;
        
        borderPane = new BorderPane();
        centerVBox = new VBox();
        vBox = new VBox();
        voterIDLabel = new Label();
        locationLabel = new Label();
        dateLabel = new Label();
        timeLabel = new Label();
        bottomVBox = new VBox();
        bottomVBox_HBox_top = new HBox();
        printButton = new Button();
        mailButton = new Button();
        emailButton = new Button();
        bottomVBox_HBox_Bottom = new HBox();
        helpButton = this.setup_helpButton();
        topLabel = new Label();

        //setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        AnchorPane.setBottomAnchor(borderPane, 0.0);
        AnchorPane.setLeftAnchor(borderPane, 0.0);
        AnchorPane.setRightAnchor(borderPane, 0.0);
        AnchorPane.setTopAnchor(borderPane, 0.0);

        BorderPane.setAlignment(centerVBox, javafx.geometry.Pos.CENTER);
        centerVBox.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        vBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        voterIDLabel.setText("            DL/VoterID:");
        voterIDLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        locationLabel.setText("            Location:");
        locationLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        dateLabel.setText("            Date:");
        dateLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        timeLabel.setText("            Time:");
        timeLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        VBox.setMargin(vBox, new Insets(5.0));
        vBox.setPadding(new Insets(5.0));
        borderPane.setCenter(centerVBox);

        BorderPane.setAlignment(bottomVBox, javafx.geometry.Pos.CENTER);

        bottomVBox_HBox_top.setAlignment(javafx.geometry.Pos.CENTER);

        printButton.setAlignment(javafx.geometry.Pos.CENTER);
        printButton.setMnemonicParsing(false);
        printButton.setText("Print");
        HBox.setMargin(printButton, new Insets(10.0));
        mailButton.setText("Mail");
        mailButton.setAlignment(javafx.geometry.Pos.CENTER);
        HBox.setMargin(mailButton, new Insets(10.0));
        emailButton.setMnemonicParsing(false);
        emailButton.setText("Email");
        HBox.setMargin(emailButton, new Insets(10.0));
        VBox.setMargin(bottomVBox_HBox_top, new Insets(0.0));

        bottomVBox_HBox_Bottom.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

//        helpButton.setAlignment(javafx.geometry.Pos.CENTER);
//        helpButton.setMnemonicParsing(false);
//        helpButton.setText("help");
        HBox.setMargin(helpButton, new Insets(10.0));
        borderPane.setBottom(bottomVBox);

        BorderPane.setAlignment(topLabel, javafx.geometry.Pos.CENTER);
        topLabel.setAlignment(javafx.geometry.Pos.CENTER);
        topLabel.setText("Receipt Preview");
        topLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        topLabel.setFont(new Font(18.0));
        BorderPane.setMargin(topLabel, new Insets(10.0));
        borderPane.setTop(topLabel);

        vBox.getChildren().add(voterIDLabel);
        vBox.getChildren().add(locationLabel);
        vBox.getChildren().add(dateLabel);
        vBox.getChildren().add(timeLabel);
        centerVBox.getChildren().add(vBox);
        bottomVBox_HBox_top.getChildren().add(printButton);
        bottomVBox_HBox_top.getChildren().add(mailButton);
        bottomVBox_HBox_top.getChildren().add(emailButton);
        bottomVBox.getChildren().add(bottomVBox_HBox_top);
        bottomVBox_HBox_Bottom.getChildren().add(helpButton);
        bottomVBox.getChildren().add(bottomVBox_HBox_Bottom);
        getChildren().add(borderPane);
    }

    protected void insertBallotResults(Ballot ballots[]) {
        GridPane gPane = new GridPane();
        int x = 0, y = -1;
        gPane.setHgap(10);
        gPane.setVgap(10);
        gPane.setAlignment(javafx.geometry.Pos.CENTER);
        for (int i = 0; i < ballots.length; i++) {
            if (ballots[i].selectedCandidate(false) != null) {
                VBox vb = new VBox();
                //ballot: title
                Label label = new Label(ballots[i].getTitle());
                label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
                label.setFont(new Font(16.0));
                //ballot: selected candidate
                Candidate candidate = ballots[i].selectedCandidate(false);
                VBox.setVgrow(vb, javafx.scene.layout.Priority.NEVER);
                vb.setAlignment(javafx.geometry.Pos.CENTER);
                vb.getChildren().addAll(label, candidate);
                if (i % 2 == 0) {
                    x = 0;
                    y += 1;
                } else {
                    x = 1;
                }
                gPane.add(vb, x, y, 1, 1);
            }
        }

        centerVBox.getChildren().add(gPane);
    }

    private Button setup_helpButton() {
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
