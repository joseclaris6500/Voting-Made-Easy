/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package votingmachine_V0162;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;


public class VotingMachine extends Application {

    private final HelpSocket helpSocket = new HelpSocket(1187, "192.168.0.4", 8080);

    Set<Character> letterSet = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));
    private Set<Character> numberSet = new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
    private Scene langSelectionScene, loginScene, ballotScene, receiptPreviewScene;
    private String ssn, voterID;
    private Ballot ballots[];

    @Override
    public void start(Stage stage) throws Exception {
        this.ballots = this.buildBallots();
        Login login = new Login(this.helpSocket);
        BallotController ballotController = new BallotController(this.ballots, this.helpSocket);
        ReceiptPreview receiptPre = new ReceiptPreview(this.helpSocket);

        ////////////////////////////Login////////////////////////////
        login.loginButton.setOnAction((event) -> {
            //TODO: pass through socket for validation
            String voterID_input = login.voterID_Field.getText();
            String ssn_input = login.ssn_Field.getText();

            if ((this.isValid_DL(voterID_input) || this.isValid_VoterID(voterID_input)) && this.isValid_SSN(ssn_input)) {
                ssn = ssn_input;
                voterID = voterID_input;
                stage.setScene(ballotScene);
                login.ssn_Field.setText("");
                login.voterID_Field.setText("");
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setHeaderText("Login values are incorrect!");
                alert.setContentText("Please enter your voter ID and Social Security Number into the above fields.");
                alert.showAndWait();
            }
        });
        loginScene = new Scene(login);
        /////////////////////////////////////////////////////////////

        ////////////////////////////ballots/////////////////////////////
        ballotController.nextButton.setOnAction((event) -> {
            if (ballotController.ballot_Index == ballotController.ballots.length) {
                ballotController.nextButton.setText("Confirm");
                //this is getting really annoying
                receiptPre.voterIDLabel.setText(receiptPre.voterIDLabel.getText() + " " + this.voterID);
                receiptPre.dateLabel.setText(receiptPre.dateLabel.getText() + " " + new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
                receiptPre.timeLabel.setText(receiptPre.timeLabel.getText() + " " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                receiptPre.insertBallotResults(this.ballots);
                stage.setScene(receiptPreviewScene);
            } else {
                ballotController.nextButton_AddAction();
            }

        });
        ballotScene = new Scene(ballotController);
        /////////////////////////////////////////////////////////////

        ////////////////////////////Summary/////////////////////////////
        receiptPre.printButton.setOnAction((event) -> {
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null) {
                boolean success = job.printPage(receiptPre.centerVBox);
                if (success) {
                    job.endJob();
                }
            }

        });
        receiptPre.mailButton.setOnAction((event) -> {
            TextInputDialog dialog = new TextInputDialog("1234 Hill Dr. Dallas, Texas");
            dialog.setTitle("Mailing Address");
            dialog.setHeaderText("Enter your mailing address.");
            dialog.setContentText("Address:");
            dialog.setWidth(500);
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                //get email and store
                //String email =  result.get();
            }
        });
        receiptPre.emailButton.setOnAction((event) -> {
            TextInputDialog dialog = new TextInputDialog("johndoe@gmail.com");
            dialog.setTitle("Input Email");
            dialog.setHeaderText("Enter your email address");
            dialog.setContentText("Email:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                //get email and store
                //String email =  result.get();
            }
            //stage.setScene(langSelectionScene);
        });
        receiptPreviewScene = new Scene(receiptPre);
        /////////////////////////////////////////////////////////////

        stage.setScene(loginScene);
        stage.show();
    }

    //because i dont understand regex
    private boolean isValid_SSN(String str) {
        if (str.length() == 9) {
            for (int i = 1; i < str.length(); i++) {
                if (!this.numberSet.contains(str.charAt(i))) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean isValid_DL(String str) {
        if (str.length() == 8) {
            for (int i = 1; i < str.length(); i++) {
                if (!this.numberSet.contains(str.charAt(i))) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean isValid_VoterID(String str) {
        str = str.toLowerCase();
        if (letterSet.contains(str.charAt(0)) && str.length() == 9) {
            for (int i = 1; i < str.length(); i++) {
                if (!numberSet.contains(str.charAt(i))) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;

    }

    private Ballot[] buildBallots() {
        Candidate ballot1_Candidates[] = {
            new Candidate("candidate #1"),
            new Candidate("candidate #2"),
            new Candidate("candidate #3")
        };
        Candidate ballot2_Candidates[] = {
            new Candidate("candidate #4"),
            new Candidate("candidate #5")
        };
        Candidate ballot3_Candidates[] = {
            new Candidate("candidate #6"),
            new Candidate("candidate #7"),
            new Candidate("candidate #8"),
            new Candidate("candidate #9")
        };
        Ballot ballots[] = {
            new Ballot("ballot 1", ballot1_Candidates),
            new Ballot("ballot 2", ballot2_Candidates),
            new Ballot("ballot 3", ballot3_Candidates)
        };
        return ballots;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
