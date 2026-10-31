package sem3_project;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class CabOrderServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099); // Start RMI registry
            CabOrderService service = new CabOrderServiceImpl();
            Naming.rebind("rmi://localhost/CabOrderService", service);
            System.out.println("CabOrderService is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
