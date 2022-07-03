package com.remote.client;

import com.remote.server.InterfaceServer;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;



public class ChatClient extends UnicastRemoteObject implements InterfaceClient{
    private final InterfaceServer server;
    private final String name;
    private final JTextArea input;
    private final JTextArea output;
    private final JPanel jpanel;
    
    //constructor
    public ChatClient(String name , InterfaceServer server,JTextArea jtext1,JTextArea jtext2,JPanel jpanel) throws RemoteException{
        this.name = name;
        this.server = server;
        this.input = jtext1;
        this.output = jtext2;
        this.jpanel = jpanel;
        server.addClient(this);
    }
    
    //retrieve messages from the server
    @Override
    public void retrieveMessage(String message) throws RemoteException {
        output.setText(output.getText() + "\n" + message);
     
    }
    
    //retrieve the shared files of the discussion from server
    @Override
    public void retrieveMessage(String filename, ArrayList<Integer> inc) throws RemoteException {
        JLabel label = new JLabel("<HTML><U><font size=\"4\" color=\"#365899\">" + filename + "</font></U></HTML>");
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    FileOutputStream out;
                    String separator;
                    if(System.getProperty("os.name").startsWith("Linux") || System.getProperty("os.name").startsWith("MacOS")) separator = "/";
                    else separator = "\\";
                    out = new FileOutputStream(System.getProperty("user.home") + separator + filename);
                    String[] extension = filename.split("\\.");
                    for (int i = 0; i<inc.size(); i++) {
                        int cc = inc.get(i);
                        if(extension[extension.length - 1].equals("txt")||
                                extension[extension.length - 1].equals("java")||
                                extension[extension.length - 1].equals("php")||
                                extension[extension.length - 1].equals("c")||
                                extension[extension.length - 1].equals("cpp")||
                                extension[extension.length - 1].equals("xml")
                                )
                        out.write((char)cc);
                        else{
                            out.write((byte)cc);
                        }
                    }
                    out.flush();
                    out.close();
                    JOptionPane.showMessageDialog(new JFrame(),"your file saved at " + System.getProperty("user.home") + separator + filename,"File Saved",JOptionPane.INFORMATION_MESSAGE);
                } catch (FileNotFoundException ex) {
                    System.out.println("Error: " + ex.getMessage());
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }             
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        jpanel.add(label);
        jpanel.repaint();
        jpanel.revalidate();
    }
    
    //send message to the server and replicate
    @Override
    public void sendMessage(List<String> list) {
        try {
            server.broadcastMessage(name + " : " + input.getText(),list);         
            
            String msg = name + " : " + input.getText();
            Date tdate =  new Date();
            // file path of chatLog.txt
            //try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("/users/apple/Repo/DS/DS_rmi/chatClient/src/com/remote/client/chatLog.txt", true)))) {
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("/users/apple/Repo/DS/DS_rmi/chatClient/src/com/remote/client/chatLog.txt", true)))) {
                    writer.append(tdate.toString() +"  "+ msg + "\n");
                    writer.close();
                    System.out.println("Successfully wrote to the file.");
            }catch (IOException e) {
                System.out.println("An error occurred; " + e.getMessage());
            }
            
        }catch (RemoteException ex) {
            System.out.println("Error: " + ex.getMessage());    
        }
    }
    
    // retrieve the name of a connected client
    @Override
    public String getName() {
        return name;
    }

}