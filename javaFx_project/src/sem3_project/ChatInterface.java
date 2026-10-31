package sem3_project;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatInterface extends Remote {
    void sendMessage(String message) throws RemoteException;
    List<String> getAllMessages() throws RemoteException;
}
