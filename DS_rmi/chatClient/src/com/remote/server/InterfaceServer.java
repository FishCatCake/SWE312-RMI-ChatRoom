package com.remote.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import com.remote.client.InterfaceClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public interface InterfaceServer extends Remote{    
    //distribute a message to all connected clients
    void broadcastMessage(String message,List<String> list) throws RemoteException;
    
    //distribute a shared file to all connected clients
    void broadcastMessage(ArrayList<Integer> inc,List<String> list,String filename) throws RemoteException;
    
    //retrieve the name of the connected clients
    Vector<String> getListClientByName(String name) throws RemoteException;
    
    //add a connected client to the list of connected clients
    void addClient(InterfaceClient client) throws RemoteException;
    
    // block a client from sending a message, but it can receive the messages
    void blockClient(List<String> clients) throws RemoteException;
    
    //completely remove a list of chat clients (kick-out)
    void removeClient(List<String> clients) throws RemoteException;
    
    //completely remove a single chat client (kick-out)
    void removeClient(String clients) throws RemoteException;
    
    //activate a client in chat, according to be in the case of "block"
    void reactiveClient(List<String> clients) throws RemoteException;
    
    //check if a username already exists in the server or not, because username is the identifier on chat
    boolean checkUsername(String username) throws RemoteException;
}