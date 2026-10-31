package sem3_project;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class driverUI extends Application {

    private Scene mainScene;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Cab");
        setupMainScene(primaryStage);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void setupMainScene(Stage primaryStage) {
        // Logo image
        Image logoImage = new Image(getClass().getResourceAsStream("logoAppNew.png"));
        ImageView logoView = new ImageView(logoImage);
        logoView.setFitHeight(100);
        logoView.setFitWidth(100);

        // Center the logo
        HBox logoHBox = new HBox();
        logoHBox.setAlignment(Pos.CENTER);
        logoHBox.getChildren().add(logoView);

        // Orders section
        VBox orderSection = new VBox(10);
        orderSection.setPadding(new Insets(10));
        orderSection.setAlignment(Pos.TOP_CENTER);
        orderSection.setStyle(
            "-fx-background-color: #FFF5F5; " +
            "-fx-border-color: #FFCCCC; " +
            "-fx-border-width: 2px; " +
            "-fx-border-radius: 10; " +
            "-fx-background-radius: 10;"
        );

        // Title for orders
        Text ordersTitle = new Text("Orders Lists");
        ordersTitle.setStyle(
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-fill: #FF4444;"
        );
        TextFlow ordersTitleFlow = new TextFlow(ordersTitle);
        ordersTitleFlow.setTextAlignment(TextAlignment.CENTER);
        orderSection.getChildren().add(ordersTitleFlow);

        // Scrollable pane for orders
        ScrollPane scrollPane = new ScrollPane(orderSection);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        // Taskbar at the bottom
        HBox taskbar = new HBox(10);
        taskbar.setPadding(new Insets(10));
        taskbar.setAlignment(Pos.CENTER);
        taskbar.setStyle("-fx-border-color: #d3d3d3; -fx-border-width: 1px 0 0 0; -fx-background-color: white;");

        Button logOutButton = createTaskbarButton("Log Out", "logout.png");

        logOutButton.setOnAction(e -> logOutDriver.confirmLogout(primaryStage, mainScene));

        taskbar.getChildren().addAll(logOutButton);

     // Refresh orders button
        Button refreshButton = new Button("Refresh Orders");
        refreshButton.setOnAction(e -> {
            DriverClient driverClient = new DriverClient();
            List<String> orders = driverClient.fetchOrders();
            orderSection.getChildren().clear();
            orderSection.getChildren().add(ordersTitleFlow); // Add title back after clearing

            if (orders.isEmpty()) {
                Text noOrdersText = new Text("No orders available.");
                orderSection.getChildren().add(noOrdersText);
            } else {
                for (int i = 0; i < orders.size(); i++) {
                    Text orderText = new Text(orders.get(i));
                    orderText.setStyle("-fx-font-size: 14px;");
                    orderSection.getChildren().add(orderText);

                    Button takeOrderButton = new Button("Take Order");
                    int orderIndex = i; // For capturing the current index

                    takeOrderButton.setOnAction(event -> {
                        // Call the driver's method to take the order
                        driverClient.takeOrder(orderIndex);
                        orderText.setStyle("-fx-font-size: 14px; -fx-strikethrough: true;");
                        takeOrderButton.setDisable(true);

                        // Add Chat button after the order is taken
                        Button chatButton = new Button("Chat with Passenger");
                        chatButton.setOnAction(e2 -> openChatWindow(primaryStage, orderIndex));
                        orderSection.getChildren().add(chatButton);
                    });

                    orderSection.getChildren().add(takeOrderButton);
                }
            }
        });
        orderSection.getChildren().add(refreshButton);


        // Layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(logoHBox);      // Add logo at the top
        mainLayout.setCenter(scrollPane); // Add scrollable orders section in the center
        mainLayout.setBottom(taskbar);    // Add taskbar at the bottom
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #FFE4E1, #FFFFFF);");

        mainScene = new Scene(mainLayout, 360, 640);
    }

    // Method to handle opening a chat window
   

    // Helper method to create taskbar buttons
    private Button createTaskbarButton(String text, String imagePath) {
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        Button button = new Button(text, imageView);
        button.setContentDisplay(ContentDisplay.TOP);
        button.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 12px;");

        return button;
    }
    private void openChatWindow(Stage primaryStage, int orderIndex) {
        try {
            // Launch the DriverChatUI
            DriverChatUI driverChatUI = new DriverChatUI();
            Stage chatStage = new Stage(); // Create a new stage for the chat UI
            driverChatUI.start(chatStage); // Start the chat UI
            chatStage.initOwner(primaryStage); // Set the owner to the main window
            chatStage.setTitle("Chat with Passenger - Order " + (orderIndex + 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
