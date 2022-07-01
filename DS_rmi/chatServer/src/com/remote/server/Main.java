package com.remote.server;

import java.awt.*;
import javax.swing.*;
import java.util.Date;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class Main extends JFrame {
    
    public JTextArea jta = new JTextArea();
     // constructor
    public Main()
    {

    }
        public static void main (String[] args) {
        Main main= new Main();
        main.build();
    }
  
//    public static void main(String []args){
//        try {
//            LocateRegistry.createRegistry(4321);
//            Naming.rebind("rmi://localhost:4321/remote",new ChatServer());
//            System.out.println("Server started at " + new Date() + '\n'");
//        } catch (MalformedURLException | RemoteException ex) {
//            System.out.println("Error: " + ex.getMessage());
//        }
//    }
    
         public void build(){
        Main s1 = new Main();
        try{
           LocateRegistry.createRegistry(4321);
            Naming.rebind("rmi://localhost:4321/remote",new ChatServer());

            System.out.println ("Server is ready.");
            s1.jta.append("Server started at " + new Date() + '\n');

            s1.setLayout(new BorderLayout());
            s1.add(new JScrollPane(s1.jta), BorderLayout.CENTER);
            s1.setTitle("Server");
            s1.setSize(500,600);

            s1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            s1.setVisible(true);
            s1.jta.setEditable(false);
        }catch(MalformedURLException | RemoteException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void displayMsg(Main obj, String s){
        obj.jta.append(s + ".\n");
    }
        
    
    
}