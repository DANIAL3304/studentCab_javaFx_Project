package sem3_project;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos; // For Pos.CENTER alignment
import javafx.stage.Stage;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DriverChatUI extends Application {
    private ChatInterface chatService;
    private TextArea chatArea;
    private String lastMessage = ""; // To track the last message and avoid redundant updates

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Lookup the remote object from the registry using the new port
            chatService = (ChatInterface) Naming.lookup("rmi://localhost:50000/ChatService");

            // UI components
            chatArea = new TextArea();
            chatArea.setEditable(false);
            chatArea.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial Rounded MT Bold'; -fx-background-color: #fff4f4; -fx-text-fill: #660000;");
            Button sendButton = new Button("Send");
            sendButton.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #660000;" +
                "-fx-background-color: #FFCCCC;" +
                "-fx-border-color: #cccccc;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 10;" // Explicit padding
            );
            TextArea messageArea = new TextArea();
            messageArea.setPromptText("Type your message here...");
            messageArea.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial Rounded MT Bold'; -fx-background-color: #ffffff; -fx-text-fill: #660000;");

            // Send button click event
            sendButton.setOnAction(event -> {
                try {
                    String message = messageArea.getText();
                    if (!message.isEmpty()) {
                        chatService.sendMessage("Driver: " + message);
                        messageArea.clear();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });

            // Layout
            VBox layout = new VBox(10);
            layout.getChildren().addAll(chatArea, messageArea, sendButton);
            layout.setStyle("-fx-background-color: linear-gradient(to bottom, #FFCCCC, #FFFFFF); -fx-padding: 30;");
            layout.setAlignment(Pos.CENTER);

            // Scene
            Scene scene = new Scene(layout, 400, 300);
            primaryStage.setTitle("Driver Chat");
            primaryStage.setScene(scene);
            primaryStage.show();

            // Polling for messages
            startMessagePolling();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startMessagePolling() {
        Timer timer = new Timer(true); // Daemon thread for periodic task
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    List<String> messages = chatService.getAllMessages();
                    if (!messages.isEmpty()) {
                        String latestMessage = messages.get(messages.size() - 1);
                        if (!latestMessage.equals(lastMessage)) {
                            lastMessage = latestMessage;

                            Platform.runLater(() -> {
                                chatArea.clear();
                                for (String message : messages) {
                                    chatArea.appendText(message + "\n");
                                }
                            });
                        }
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000); // Poll every 1 second
    }
}
