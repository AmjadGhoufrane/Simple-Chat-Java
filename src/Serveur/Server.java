package Serveur;

import Common.Message;

import java.util.ArrayList;
import java.util.List;

public class Server {
    List<ConnectedClient> clients;
    int port;

    public Server(int port){
        this.port = port;
        this.clients = new ArrayList<ConnectedClient>();

        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();

    }

    public void addClient(ConnectedClient newClient){
        this.clients.add(newClient);
        Message m = new Message(newClient.getId()+" vient de se connecter", newClient.getClientId());
        broadcastMessage(m, (int) newClient.getClientId());
    }

    public void broadcastMessage(Message mess, int id){
        for (ConnectedClient client : clients) {
            if (client.getClientId() != id) {
                client.sendMessage(mess);
            }
        }
        System.out.println(mess);
    }

    public void disconnectedClient(ConnectedClient diss){
        diss.closeClient();
        clients.remove(diss);
        Message m = new Message(diss.getId()+" vient de se connecter", diss.getId());
        broadcastMessage(m, (int) diss.getId());
    }

    public int getPort(){
        return this.port;
    }
}
