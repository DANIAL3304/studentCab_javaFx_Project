package sem3_project;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class DriverClient {
    private CabOrderService service;

    public DriverClient() {
        try {
            service = (CabOrderService) Naming.lookup("rmi://localhost/CabOrderService");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> fetchOrders() {
        try {
            return service.getOrders(); // Fetch all orders from the server
        } catch (RemoteException e) {
            e.printStackTrace();
            return List.of(); // Return an empty list if there's an error
        }
    }

    public void takeOrder(int orderIndex) {
        try {
            service.updateOrderStatus(orderIndex, "Successful"); // Mark the order as taken
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
