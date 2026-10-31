package sem3_project;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class firstPage extends Application {

    @Override
    public void start(Stage stage) {
        
        Image logoImage = new Image(getClass().getResourceAsStream("/logo.png"));
        ImageView logoIV = new ImageView(logoImage);
        logoIV.setFitHeight(120);
        logoIV.setFitWidth(120);
        
        Label appTitleLine1 = new Label("Welcome to");
        appTitleLine1.setAlignment(Pos.CENTER);
        Label appTitleLine2 = new Label("UPM Student Cab App");
        appTitleLine2.setAlignment(Pos.CENTER);

        appTitleLine1.setStyle(
            "-fx-font-family: 'Arial Rounded MT Bold'; " +
            "-fx-font-size: 26px; " +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #FF4444;"
        );

        appTitleLine2.setStyle(
            "-fx-font-family: 'Arial Rounded MT Bold'; " +
            "-fx-font-size: 26px; " +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #FF4444;"
        );
        
        VBox appTitleBox = new VBox(-5, logoIV, appTitleLine1, appTitleLine2); // Negative or zero spacing
        VBox.setMargin(appTitleLine1, new Insets(-20, 0, 0, 0));
        VBox.setMargin(logoIV, new Insets(-20, 0, 0, 0));
        appTitleBox.setAlignment(Pos.CENTER); // Center-align the content
        
        // Top Label
        Label topLabel = new Label("Which One Are You?");
        topLabel.setStyle(
            "-fx-font-family: 'Arial Rounded MT Bold'; " +
            "-fx-font-size: 16px; " + // Larger font for mobile readability
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #FF4444;"
        );

        // Images
        Image driverImage = new Image(getClass().getResourceAsStream("/driverLOGO.png"));
        Image studentImage = new Image(getClass().getResourceAsStream("/studentLOGO.png"));

        ImageView driverIV = new ImageView(driverImage);
        driverIV.setFitWidth(170); // Suitable size for mobile
        driverIV.setFitHeight(170);

        ImageView studentIV = new ImageView(studentImage);
        studentIV.setFitWidth(170);
        studentIV.setFitHeight(170);

        // Driver Button
        Button driverButton = new Button("Driver", driverIV);
        driverButton.setContentDisplay(ContentDisplay.TOP);
        driverButton.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #660000;" +
            "-fx-background-color: #FFCCCC;" +
            "-fx-border-color: #cccccc;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;" +
            "-fx-padding: 10;" // Explicit padding
        );
        driverButton.setOnAction(e -> {
            driverPage driverPage = new driverPage();
            driverPage.start(stage);
        });

        // Student Button
        Button studentButton = new Button("Student", studentIV);
        studentButton.setContentDisplay(ContentDisplay.TOP);
        studentButton.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #660000;" +
            "-fx-background-color: #FFCCCC;" +
            "-fx-border-color: #cccccc;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;" +
            "-fx-padding: 10;"
        );
        
        studentButton.setOnAction(e -> {
            studentPage studentPage = new studentPage();
            studentPage.start(stage);
        });

        // Layout
        VBox layout = new VBox(10, appTitleBox, topLabel, driverButton, studentButton); // Vertical layout with spacing
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 30;"); // Padding around the edges
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #FFCCCC, #FFFFFF);");
        
        // Scene (Portrait Dimensions for Mobile)
        Scene scene = new Scene(layout, 360, 640); // Mobile portrait dimensions
        stage.setScene(scene);
        stage.setTitle("First Page");
        stage.show();
    }

    public static Scene createScene(Stage stage) {
        firstPage app = new firstPage();
        app.start(stage);
        return stage.getScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

