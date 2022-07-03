package com.remote.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


public interface InterfaceClient extends Remote{
    //retrieve messages from the server
    void retrieveMessage(String message) throws RemoteException;
    
    //retrieve the shared files of the discussion from server
    void retrieveMessage(String filename,ArrayList<Integer> inc) throws RemoteException;
    
    //send a message from a client to the server
    void sendMessage(List<String> list) throws RemoteException;
    
    //retrieve the name of the connected clients (client identifier)
    String getName()throws RemoteException;
    
}
