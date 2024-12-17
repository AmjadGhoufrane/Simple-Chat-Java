package Client;
import Common.Message;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceive implements Runnable {
    private Client client;
    private Socket socket;
    private ObjectInputStream in;

    public ClientReceive(Client client, Socket socket) {
        this.client = client;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            boolean isActive = true;
            while (isActive) {
                try {
                    Message mess = (Message) in.readObject();
                    if (mess != null) {
                        this.client.messageReceived(mess);
                    } else {
                        isActive = false;
                    }
                } catch (EOFException e) {
                    isActive = false;
                }
            }
            client.disconnectedServer();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}