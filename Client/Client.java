/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;
import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class Client extends javax.swing.JFrame {
    String username;
    Socket sock;
    BufferedReader reader;
    PrintWriter writer;
    ArrayList<String> userList = new ArrayList();
    Boolean isConnected = false;

    public Client() {
        initComponents();
    }

    public class IncomingReader implements Runnable{

        public void run() {
            String[] data;
            String stream, done = "Done", connect = "Подключить", disconnect = "Отключить", chat = "Чат";

            try {
                while ((stream = reader.readLine()) != null) {

                    data = stream.split(":");

                    if (data[2].equals(chat)) {

                        chatTextArea.append(data[0] + ": " + data[1] + "\n");
                        chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());

                    } else if (data[2].equals(connect)){

                        chatTextArea.removeAll();
                        userAdd(data[0]);

                    } else if (data[2].equals(disconnect)) {


                        userRemove(data[0]);

                    } else if (data[2].equals(done)) {


                        usersList.setText("");
                        writeUsers();
                        userList.clear();

                    }

                }
            }catch(Exception ex) {
            }
        }
    }

    public void ListenThread() {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }

    public void userAdd(String data) {
        userList.add(data);

    }

    public void userRemove(String data) {
        chatTextArea.append(data + " has disconnected.\n");

    }

    public void writeUsers() {
        String[] tempList = new String[(userList.size())];
        userList.toArray(tempList);
        for (String token:tempList) {

            usersList.append(token + "\n");

        }
    }

    public void sendDisconnect() {

        String bye = (username + ": :Disconnect");
        try{
            writer.println(bye);
            writer.flush();
        } catch (Exception e) {
            chatTextArea.append("Could not send Disconnect message.\n");
        }
    }

    public void Disconnect() {

        try {
            chatTextArea.append("Disconnected.\n");
            sock.close();
        } catch(Exception ex) {
            chatTextArea.append("Failed to disconnect. \n");
        }
        isConnected = false;
        usernameField.setEditable(true);
        usersList.setText("");

    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setBackground(Color.PINK);
        inputTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane2.setBackground(Color.PINK);
        chatTextArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel1.setBackground(Color.PINK);
        usernameField = new javax.swing.JTextField();
        connectButton = new javax.swing.JButton();
        connectButton.setBackground(Color.PINK);
        disconnectButton = new javax.swing.JButton();
        disconnectButton.setBackground(Color.PINK);
        sendButton = new javax.swing.JButton(new ImageIcon("1.png"));
        sendButton.setBackground(Color.PINK);
        jScrollPane3 = new javax.swing.JScrollPane();
        usersList = new javax.swing.JTextArea();
        usersList.setBackground(Color.PINK);
        jLabel2 = new javax.swing.JLabel();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Клиентский чат");
        inputTextArea.setColumns(20);
        inputTextArea.setLineWrap(true);
        inputTextArea.setRows(5);
        jScrollPane1.setViewportView(inputTextArea);
        chatTextArea.setColumns(20);
        chatTextArea.setEditable(false);
        chatTextArea.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        chatTextArea.setLineWrap(true);
        chatTextArea.setRows(5);
        jScrollPane2.setViewportView(chatTextArea);
        usernameField.setBackground(Color.PINK);
        chatTextArea.setBackground(Color.PINK);
        jScrollPane3.setBackground(Color.PINK);
        inputTextArea.setBackground(Color.PINK);


        jLabel1.setText("Логин:");
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 16));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 16));
        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        connectButton.setText("Connect");
        connectButton.setFont(new java.awt.Font("Times New Roman", 1, 16));
        disconnectButton.setFont(new java.awt.Font("Times New Roman", 1, 16));
        sendButton.setFont(new java.awt.Font("Times New Roman", 1, 16));
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        disconnectButton.setText("Disconnect");
        disconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectButtonActionPerformed(evt);
            }
        });

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        usersList.setEditable(false);
        usersList.setColumns(20);
        usersList.setRows(5);
        jScrollPane3.setViewportView(usersList);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Online Users");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(connectButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(disconnectButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane3))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(disconnectButton)
                                        .addComponent(connectButton)
                                        .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                                        .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)))
                                        .addComponent(jScrollPane3))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        if (isConnected == false) {
            username = usernameField.getText();
            usernameField.setEditable(false);

            try {
                sock = new Socket("localhost", 4004);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":has connected.:Connect"); // Displays to everyone that user connected.
                writer.flush(); // flushes the buffer
                isConnected = true; // Used to see if the client is connected.
            } catch (Exception ex) {
                chatTextArea.append("Cannot Connect! Try Again. \n");
                usernameField.setEditable(true);
            }
            ListenThread();
        } else if (isConnected == true) {
            chatTextArea.append("You are already connected. \n");
        }
    }

    private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        sendDisconnect();
        Disconnect();
    }

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        String nothing = "";
        if ((inputTextArea.getText()).equals(nothing)) {
            inputTextArea.setText("");
            inputTextArea.requestFocus();
        } else {
            try {
                writer.println(username + ":" + inputTextArea.getText() + ":" + "Chat");
                writer.flush(); // flushes the buffer
            } catch (Exception ex) {
                chatTextArea.append("Message was not sent. \n");
            }
            inputTextArea.setText("");
            inputTextArea.requestFocus();
        }

        inputTextArea.setText("");
        inputTextArea.requestFocus();
    }

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JTextArea chatTextArea;
    private javax.swing.JButton connectButton;
    private javax.swing.JButton disconnectButton;
    private javax.swing.JTextArea inputTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextField usernameField;
    private javax.swing.JTextArea usersList;
    // End of variables declaration

}
