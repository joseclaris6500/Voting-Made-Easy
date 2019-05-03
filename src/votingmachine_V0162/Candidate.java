package votingmachine_V0162;

import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;

public class Candidate extends VBox {

    protected final HBox hBoxImage, hBoxLabel;
    protected final ImageView imageView;
    protected final Label labelName;
    protected final String imageDir, name;
    private final Glow glow = new Glow();
    private final double glowValue_Min = 0.0, glowValue_Max = 0.9;
    private boolean selected = false;
    private boolean selectionEnabled = true;

    public Candidate() {
        this("human_avatar_portrait.png", "FirstName LastName");
    }

    public Candidate(String name) {
        this("human_avatar_portrait.png", name);
    }

    public Candidate(String imageDir, String name) {

        this.imageDir = imageDir;
        this.name = name;

        hBoxImage = this.setup_hBoxImage();
        imageView = this.setup_ImageView();
        hBoxImage.getChildren().add(imageView);
        
        hBoxLabel = this.setup_hBoxLabel();
        labelName = this.setup_LabelName();
        hBoxLabel.getChildren().add(labelName);
        
        getChildren().addAll(hBoxImage, hBoxLabel);
    }

    public void select() {
        if(!this.selectionEnabled) return;
        if(this.selected) {
            this.unselect();
        } else {
            this.glow.setLevel(glowValue_Max);
            this.selected = true;
        }
    }
    
    public void unselect() {
        if(!this.selectionEnabled) return;
        this.glow.setLevel(glowValue_Min);
        this.selected = false;
    }
    
    public boolean isSelected () {
        return this.selected;
    }

    private HBox setup_hBoxImage() {
        HBox hBoxImage = new HBox();
        hBoxImage.setAlignment(javafx.geometry.Pos.CENTER);
        return hBoxImage;
    }

    private ImageView setup_ImageView() {
        ImageView imageView = new ImageView();
        imageView = new ImageView();
        VBox.setVgrow(imageView, javafx.scene.layout.Priority.ALWAYS);
        imageView.setFitHeight(120.0);
        imageView.setFitWidth(120.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setImage(new Image(getClass().getResource(this.imageDir).toExternalForm()));
        this.glow.setLevel(this.glowValue_Min);
        imageView.setEffect(glow);

        //add eventhandlers here
        imageView.setOnMouseEntered((event) -> {
            if(this.selectionEnabled){
                this.glow.setLevel(0.3);
            }
        });
        
        imageView.setOnMouseExited((event) -> {
            if(this.selected && this.selectionEnabled) {
                this.glow.setLevel(this.glowValue_Max);
            } else {
                this.glow.setLevel(this.glowValue_Min);
            } 
        });

        return imageView;
    }
    
    private HBox setup_hBoxLabel() {
        HBox hBoxLabel = new HBox();
        hBoxLabel.setAlignment(javafx.geometry.Pos.CENTER);
        return hBoxLabel;
    }
    
    private Label setup_LabelName() {
        Label labelName = new Label();
        VBox.setVgrow(labelName, javafx.scene.layout.Priority.ALWAYS);
        labelName.setAlignment(javafx.geometry.Pos.CENTER);
        labelName.setText(this.name);
        return labelName;
    }
    
    public void setSelectionEnabled(boolean bool) {
        this.selectionEnabled = bool;
        if (this.selectionEnabled && this.isSelected()) {
            this.glow.setLevel(glowValue_Max);
        } else {
            this.glow.setLevel(glowValue_Min);
        }
    }
    
    public Candidate makeClone() {
       return new Candidate(imageDir, name);
    }
}
