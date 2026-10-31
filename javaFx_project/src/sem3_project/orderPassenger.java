package sem3_project;

import java.rmi.RemoteException;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class orderPassenger {
    static String orderStatus = "Refrash"; // Default order status

    public static void showMyOrdersPage(Stage primaryStage, Scene mainScene) {
        // Add the order time label (retrieved from orderCabMethod)
        Label orderTimeLabel = new Label("Order Time: " + 
            (orderCabMethodPassenger.orderTimeSaved != null ? orderCabMethodPassenger.orderTimeSaved : "N/A"));  
        orderTimeLabel.setFont(new Font("Arial", 12));
        orderTimeLabel.setStyle("-fx-text-fill: #555555;");

        // Create a VBox for the layout
        VBox myOrdersBox = new VBox(20);
        myOrdersBox.setPadding(new Insets(20));
        myOrdersBox.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: linear-gradient(to bottom, #FFCCCC, #FFFFFF);");
        myOrdersBox.setAlignment(Pos.CENTER);

        // Title label
        Label titleLabel = new Label(orderCabMethodPassenger.hasOrder ? "Order Successful" : "No Orders Yet");
        titleLabel.setFont(new Font(20));
        titleLabel.setStyle("-fx-alignment: center; -fx-text-fill: #2a3d66;");

        // Order details label
        Label orderDetailsLabel = new Label(orderCabMethodPassenger.hasOrder ? orderCabMethodPassenger.orderDetails : "No details available.");
        orderDetailsLabel.setWrapText(true);
        orderDetailsLabel.setFont(new Font(12));
        orderDetailsLabel.setPadding(new Insets(10));
        orderDetailsLabel.setStyle("-fx-border-color: #4CAF50; -fx-border-width: 2; -fx-background-color: white;");

     // Status button
        Button statusButton = new Button();
        statusButton.setDisable(!orderCabMethodPassenger.hasOrder); // Disable if no order
        statusButton.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white; -fx-border-radius: 20; -fx-padding: 10 20;");

        // Dynamically set the button text based on the current order status
        if (orderCabMethodPassenger.hasOrder) {
            statusButton.setText("Status: " + orderStatus);
        } else {
            statusButton.setText("No Order");
        }

     // Update status dynamically on button click
        statusButton.setOnAction(e -> {
            if (orderCabMethodPassenger.hasOrder) {
                DriverClient driverClient = new DriverClient();
				List<String> orders = driverClient.fetchOrders();
				// Update the order status based on the driver’s actions
				String newStatus = orders.get(0); // Assuming the first order is the one the passenger is concerned with
				updateOrderStatus(newStatus);
				statusButton.setText(newStatus);
            }
        });

        
        // Chat button
        Button chatButton = new Button("Chat Driver");
        chatButton.setDisable(!orderCabMethodPassenger.hasOrder); // Disable if no order
        chatButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 20; -fx-padding: 10 20;");
        chatButton.setOnAction(e -> openChatWindow(primaryStage));

        // Back button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(mainScene));
        backButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-size: 14px;");

        // Add components to VBox
        myOrdersBox.getChildren().addAll(titleLabel, orderDetailsLabel, orderTimeLabel, statusButton, chatButton, backButton);

        // Create the scene
        Scene myOrdersScene = new Scene(myOrdersBox, 360, 640);
        primaryStage.setScene(myOrdersScene);
    }
    
 // Update order status (called by both Driver and Passenger)
    public static void updateOrderStatus(String newStatus) {
        orderStatus = newStatus;
        // If status changes to successful, the order is complete
        if (orderStatus.equals("Successful")) {
            orderCabMethodPassenger.hasOrder = false;
        }
    }

    private static void openChatWindow(Stage ownerStage) {
        try {
            // Create a new stage
            Stage chatStage = new Stage();

            // Set the owner before the stage is shown
            chatStage.initOwner(ownerStage);

            // Launch PassengerChatUI on this stage
            PassengerChatUI passengerChatUI = new PassengerChatUI();
            passengerChatUI.start(chatStage); // Start the PassengerChatUI
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // Method to set order status and mark order presence
    public static void setOrderDetails(String details, String time) {
        orderCabMethodPassenger.orderDetails = details;
        orderCabMethodPassenger.orderTimeSaved = time;
        orderStatus = "Pending"; // Reset to Pending when a new order is made
        orderCabMethodPassenger.hasOrder = true; // Mark as having an order
    }

    public static void clearOrder() {
        orderCabMethodPassenger.orderDetails = null;
        orderCabMethodPassenger.orderTimeSaved = null;
        orderStatus = "No Order";
        orderCabMethodPassenger.hasOrder = false; // Mark as no order
    }
}