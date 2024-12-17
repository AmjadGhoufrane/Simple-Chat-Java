package Client;

import Common.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSend implements Runnable {
    private Socket socket;
    private ObjectOutputStream out;

    public ClientSend(Socket socket, ObjectOutputStream out) {
        this.socket = socket;
        this.out = out;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Votre message >> ");
            String m = sc.nextLine();
            if (m.equalsIgnoreCase("bye") || m.equalsIgnoreCase("exit")) {
                break;
            }
            Message mess = new Message(m, 0); // Assuming 0 is the client ID
            try {
                out.writeObject(mess);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sc.close();
    }
}
