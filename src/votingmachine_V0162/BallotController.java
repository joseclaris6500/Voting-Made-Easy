package votingmachine_V0162;

import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

public class BallotController extends AnchorPane {

    protected final Label titleLabel;
    protected final Ballot ballots[];
    protected int ballot_Index = 0;
    protected final BorderPane borderPane;
    protected final Button backButton, nextButton, helpButton, confirmButton;
    //protected final Button changeButtons[];
    protected final HBox center_HBox;
    protected final HBox button_HBox;
    private final HelpSocket helpSocket;

    public BallotController(Ballot ballots[],HelpSocket helpSocket) {

        this.helpSocket = helpSocket;
        
        //setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);
        this.ballots = ballots;
        this.titleLabel = new Label();
        this.button_HBox = new HBox();
        this.borderPane = new BorderPane();
        AnchorPane.setBottomAnchor(this.borderPane, 0.0);
        AnchorPane.setLeftAnchor(this.borderPane, 0.0);
        AnchorPane.setRightAnchor(this.borderPane, 0.0);
        AnchorPane.setTopAnchor(this.borderPane, 0.0);
        this.backButton = this.setup_BackButton();
        this.nextButton = this.setup_NextButton();
        this.helpButton = this.setup_helpButton();
        this.confirmButton = this.setup_confirmButton();
        //this.changeButtons = this.setup_changeButtons();
        this.borderPane.setTop(this.setup_TitleLabel());
        this.center_HBox = this.setup_CenterHBox();
        this.borderPane.setCenter(this.center_HBox);
        this.borderPane.setBottom(this.setup_BottomHBox(this.backButton, this.nextButton, this.helpButton));
        this.load_Ballot(this.ballots[this.ballot_Index]);
        getChildren().add(borderPane);
    }

    private Label setup_TitleLabel() {
        titleLabel.setText(ballots[ballot_Index].getTitle());
        titleLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        titleLabel.setFont(new Font(20.0));
        BorderPane.setAlignment(titleLabel, javafx.geometry.Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(20.0));
        return titleLabel;
    }

    private HBox setup_CenterHBox() {
        HBox centerHBox = new HBox();
        centerHBox.setAlignment(javafx.geometry.Pos.CENTER);
        centerHBox.setSpacing(20.0);
        BorderPane.setAlignment(centerHBox, javafx.geometry.Pos.CENTER);
        BorderPane.setMargin(centerHBox, new Insets(20.0));
        return centerHBox;
    }

    private HBox setup_BottomHBox(Button backButton, Button nextButton, Button helpButton) {
        HBox bottomHBox = new HBox();
        VBox bottomHBox_VBox = new VBox();
        HBox bottomHBox_VBox_HBox_Bottom = new HBox();

        BorderPane.setAlignment(bottomHBox, javafx.geometry.Pos.CENTER);
        bottomHBox.setAlignment(javafx.geometry.Pos.CENTER);
        HBox.setHgrow(bottomHBox_VBox, javafx.scene.layout.Priority.ALWAYS);
        bottomHBox_VBox.setAlignment(javafx.geometry.Pos.CENTER);
        button_HBox.setAlignment(javafx.geometry.Pos.CENTER);
        bottomHBox_VBox_HBox_Bottom.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

        button_HBox.getChildren().add(backButton);
        HBox.setMargin(backButton, new Insets(5.0));
        button_HBox.getChildren().add(nextButton);
        HBox.setMargin(nextButton, new Insets(5.0));
        bottomHBox_VBox_HBox_Bottom.getChildren().add(helpButton);
        HBox.setMargin(helpButton, new Insets(5.0));

        bottomHBox_VBox.getChildren().add(button_HBox);
        bottomHBox_VBox.getChildren().add(bottomHBox_VBox_HBox_Bottom);
        bottomHBox.getChildren().add(bottomHBox_VBox);
        return bottomHBox;
    }

    private Button setup_BackButton() {
        Button btn = new Button();
        btn.setMnemonicParsing(false);
        btn.setText("Back");
        btn.setDisable(true);

        //add event handlers here
        btn.setOnAction((event) -> {
            if (this.ballot_Index > 0) {
                this.ballot_Index = this.ballot_Index - 1;
                this.load_Ballot(ballots[this.ballot_Index]);
            }
            if (this.ballot_Index == this.ballots.length) {
                for (Ballot ballot : this.ballots) {
                    for (Candidate candidate : ballot.getCandidates()) {
                        candidate.setSelectionEnabled(true);
                    }
                }
                this.ballot_Index = this.ballot_Index - 1;
            }
            if (this.ballot_Index == 0) {
                btn.setDisable(true);
            }
            this.nextButton.setText("Next");
        });
        return btn;
    }

    private Button setup_NextButton() {
        Button btn = new Button();
        btn.setMnemonicParsing(false);
        btn.setText("Next");

//        //add event handlers here
//        btn.setOnAction((event) -> {
//            if (this.ballot_Index < (this.ballots.length - 1)) {
//                this.ballot_Index = this.ballot_Index + 1;
//                this.load_Ballot(ballots[this.ballot_Index]);
//            } else if (this.ballot_Index < this.ballots.length) {
//                //goto -> selection summary
//                this.ballot_Index = this.ballot_Index + 1;
//                this.load_SelectionSummary();
//            }
//            if (this.ballot_Index != 0) {
//                this.backButton.setDisable(false);
//            }
//        });
        return btn;
    }
    
    //untill i find a better solution
    public void nextButton_AddAction() {
            if (this.ballot_Index < (this.ballots.length - 1)) {
                this.ballot_Index = this.ballot_Index + 1;
                this.load_Ballot(ballots[this.ballot_Index]);
            } else if (this.ballot_Index < this.ballots.length) {
                //goto -> selection summary
                this.ballot_Index = this.ballot_Index + 1;
                this.load_SelectionSummary();
            }
            if (this.ballot_Index != 0) {
                this.backButton.setDisable(false);
            }
            if(this.ballot_Index == this.ballots.length) {
                this.nextButton.setText("Confirm");
            }
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

    private Button setup_confirmButton() {
        Button btn = new Button("Confirm");
        btn.setOnAction((event) -> {
            this.button_HBox.getChildren().clear();
            this.button_HBox.getChildren().addAll(this.backButton, this.nextButton);
            this.load_SelectionSummary();
        });
        return btn;
    }

    private Button setup_changeButton(final int i, final Ballot ballot) {
        Button btn = new Button("Change");
        final Candidate candidate = this.ballots[i].selectedCandidate(false);
        btn.setOnAction((event) -> {
            this.button_HBox.getChildren().clear();
            this.button_HBox.getChildren().add(this.confirmButton);
            candidate.setSelectionEnabled(true);
            this.load_Ballot(ballot);
        }); 
        return btn;
    }

    private void load_Ballot(Ballot ballot) {
        this.center_HBox.getChildren().clear();
        this.titleLabel.setText(ballot.getTitle());
        Candidate candidates[] = ballot.getCandidates();
        for (Candidate candidate : candidates) {
            //add eventhandler to each candidate
            candidate.setSelectionEnabled(true);
            candidate.setOnMousePressed((event) -> {
                for (Candidate cand : candidates) {
                    if (candidate == cand) {
                        candidate.select();
                    } else {
                        cand.unselect();
                    }
                }
            });
            //add all candidates to hbox
            this.center_HBox.getChildren().add(candidate);
        }
    }

    private void load_SelectionSummary() {
        this.center_HBox.getChildren().clear();
        this.titleLabel.setText("Selection Summary");

        for (int i = 0; i < ballots.length; i++) {
            if (ballots[i].selectedCandidate(false) != null) {
                VBox vb = new VBox();
                //ballot: title
                Label label = new Label(ballots[i].getTitle());
                label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
                label.setFont(new Font(16.0));
                
                //ballot: selected candidate
                Candidate candidate = ballots[i].selectedCandidate(false);
                candidate.setSelectionEnabled(false);
                Button changeButton = this.setup_changeButton(i, ballots[i]);
                VBox.setVgrow(vb, javafx.scene.layout.Priority.NEVER);
                vb.setAlignment(javafx.geometry.Pos.CENTER);
                vb.getChildren().addAll(label,candidate,changeButton);
                this.center_HBox.getChildren().add(vb);
            }
        }

    }
}
