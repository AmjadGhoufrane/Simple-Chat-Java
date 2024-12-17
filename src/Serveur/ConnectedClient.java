package Serveur;

import Common.Message;

import java.io.*;
import java.net.*;

public class ConnectedClient extends Thread {

    private static int idCounter = 0;

    private int idc;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Server server;

    public ConnectedClient(Server server, Socket socket) {
        this.server = server;
        this.socket =socket;
        this.idc = idCounter;
        idCounter++;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Nouvelle connexion, id = " + idc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(Message mess) {
        try {
            this.out.writeObject(mess);
            this.out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getClientId(){
        return this.idc;
    }

    public void closeClient() {

        try {
            this.out.close();
            this.in.close();
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        boolean isActive;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            isActive = true;
            while (isActive) {
                Message mess = (Message) in.readObject();
                if (mess == null) {
                    throw new IOException("Client déconnecté");
                } else{
                    mess.setSender(Long.parseLong(String.valueOf(idc)));
                    server.broadcastMessage(mess, idc);
                }
            }
            server.disconnectedClient(this);
            isActive = false;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
