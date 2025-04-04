package Client;

import java.io.IOException;
import java.net.UnknownHostException;
/**
 * starts a client. Reads the address and port from the command line
 argument
 * @author Remi Watrigant
 *
 */
public class MainClient {
    /**
     * construct a new client
     * @param args
     */
    public static void main(String[] args) {
        String address = "127.0.0.1";
        Integer port = new Integer(2000);
        Client c = new Client(address, port);

    }

    private static void printUsage() {
        System.out.println("java client.Client <address> <port>");
        System.out.println("\t<address>: server's ip address");
        System.out.println("\t<port>: server's port");
    }
}
