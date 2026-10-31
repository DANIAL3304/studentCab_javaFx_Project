package sem3_project;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ChatServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(50000); // Start RMI registry on a dynamic port
            ChatInterface service = new ChatServiceImpl();  // Correct implementation class name
            Naming.rebind("rmi://localhost:50000/ChatService", service); // Binding to the new port
            System.out.println("ChatService is running on port 50000...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
