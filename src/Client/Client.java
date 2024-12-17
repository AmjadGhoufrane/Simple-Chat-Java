package Client;

import Common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private String address;
    private int port;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Client(String address, int port){
        this.address = address;
        this.port = port;

        try {
            this.socket = new Socket(address,port);
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Création des threads
        new Thread(new ClientReceive(this, socket)).start();
        new Thread(new ClientSend(this.socket, out)).start();
    }

    public void disconnectedServer() {
        try {
            if (out != null) out.close();
            if (socket != null) socket.close();
            if (in != null) in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public void messageReceived(Message mess) {
        System.out.println(mess);
    }
}
