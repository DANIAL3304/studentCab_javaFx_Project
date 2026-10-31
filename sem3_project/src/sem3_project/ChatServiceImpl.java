package sem3_project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatInterface {
    private static final long serialVersionUID = 1L;
    
    // List to store messages
    private List<String> messages = new ArrayList<>();
    
    public ChatServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized void sendMessage(String message) throws RemoteException {
        // Store the message and print it to server log
        messages.add(message);
        System.out.println("Received message: " + message);
    }

    @Override
    public synchronized List<String> getAllMessages() throws RemoteException {
        return new ArrayList<>(messages);
    }
}
