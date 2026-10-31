package sem3_project;

public class updateStatus {

public static String orderStatus = "Pending"; // Default order status
public static boolean hasOrder = false; // Track if the passenger has an order
public static String orderDetails; // Store order details
public static String orderTimeSaved; // Store the order time

// Method to clear the order
public static void clearOrder() {
    orderDetails = null;
    orderTimeSaved = null;
    orderStatus = "No Order"; // Reset status to "No Order"
    hasOrder = false; // Mark as no order
}

// Method to update order status
public static void updateOrderStatus(String newStatus) {
    orderStatus = newStatus;
}

// Method to retrieve the order status
public static String getOrderStatus() {
    return orderStatus;
}
}