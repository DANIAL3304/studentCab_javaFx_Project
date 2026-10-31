package sem3_project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class CabOrderServiceImpl extends UnicastRemoteObject implements CabOrderService {
    private final List<Order> orders = new ArrayList<>();

    protected CabOrderServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized void addOrder(String orderDetails) throws RemoteException {
        orders.add(new Order(orderDetails, "Pending")); // Add a new order with status "Pending"
    }

    @Override
    public synchronized List<String> getOrders() throws RemoteException {
        List<String> orderDescriptions = new ArrayList<>();
        for (Order order : orders) {
            orderDescriptions.add(order.getDetails() + " - Status: " + order.getStatus());
        }
        return orderDescriptions;
    }

    @Override
    public synchronized void updateOrderStatus(int orderIndex, String status) throws RemoteException {
        if (orderIndex >= 0 && orderIndex < orders.size()) {
            orders.get(orderIndex).setStatus(status); // Update the order status
        }
    }

    private static class Order {
        private String details;
        private String status;

        public Order(String details, String status) {
            this.details = details;
            this.status = status;
        }

        public String getDetails() {
            return details;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
