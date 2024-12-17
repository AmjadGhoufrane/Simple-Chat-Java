package Client;

import Common.Message;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientSend extends JFrame implements Runnable  {
    private Socket socket;
    private ObjectOutputStream out;
    private Client client;

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    public ClientSend(Socket socket, ObjectOutputStream out, Client client) {
        this.socket = socket;
        this.out = out;
        this.client = client;

        setTitle("Chat Window");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField(25);
        sendButton = new JButton("Send");

        JPanel panel = new JPanel();
        panel.add(inputField);
        panel.add(sendButton);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if (inputField.getText().equalsIgnoreCase("bye") || inputField.getText().equalsIgnoreCase("exit")) {
//                    break;
//                }
                Message mess = new Message(inputField.getText(), 0); // Assuming 0 is the client ID
                try {
                    out.writeObject(mess);
                    out.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void messageReceived(){
        if (this.client.isPending()){
            Message[] messages = this.client.getPendingMessages();
            for(Message mess : messages){
                this.chatArea.setText(this.chatArea.getText()+'\n'+mess);
            }
        }

    }

    @Override
    public void run() {
        this.setVisible(true);
        while (true) {
            this.messageReceived();
        }
    }
}


