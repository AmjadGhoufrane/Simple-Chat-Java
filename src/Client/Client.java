package Client;

import Common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private String address;
    private int port;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    ArrayList<Message> messages = new ArrayList<>();
    private int pending = 0;

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
        new Thread(new ClientSend(this.socket, out,this)).start();
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

    public boolean isPending(){
        return pending>0;
    }

    public Message[] getPendingMessages(){
        Message[] messages1 = new Message[pending];
        int i = messages.size()-1-pending;
        for (int j = i; j < messages.size(); j++) {
            messages1[j] = messages.get(j);
        }
        pending = 0;
        return messages1;
    }

    public void messageReceived(Message mess) {
        this.messages.add(mess);
        pending++;
//        System.out.println(mess);
    }
}
