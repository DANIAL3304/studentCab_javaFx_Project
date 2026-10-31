package sem3_project;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CabOrderService extends Remote {
    void addOrder(String orderDetails) throws RemoteException; // Add new order
    List<String> getOrders() throws RemoteException; // Get list of orders
    void updateOrderStatus(int orderIndex, String status) throws RemoteException; // Update order status
}

