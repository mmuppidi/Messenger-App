package com.mohan.chatchatmessenger;

import android.app.Activity;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by robo-ace on 7/27/15.
 */
public class ChatChatClient extends Thread {

    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    private Socket socket;
    private ChatChatNew cg;
    private String server, username;
    private int port;


    ChatChatClient(String server, int port, String username) {
        this(server, port, username, null);
    }

    ChatChatClient(String server, int port, String username, Activity context) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.cg = (ChatChatNew) context;
    }

    public boolean On_start() {

        try {
            socket = new Socket(server, port);
        }

        catch(Exception ec) {
            display("Error connectiong to server:" + ec);
            return false;
        }

        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        display(msg);

        //Creating both Data Stream
        try
        {
            sInput  = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException eIO) {
            display("Exception creating new Input/output Streams: " + eIO);
            return false;
        }

        new NewListenFromServer().start();

        try
        {
            sOutput.writeObject(username);
            sOutput.flush();
        }
        catch (IOException eIO) {
            display("Exception doing login : " + eIO);
            disconnect();
            return false;
        }

        display("Success");
        cg.connected = true;

        return true;
    }

    @Override
    public void run() {
        On_start();
    }

    private void display(String msg) {
        cg.append(msg + "\n");

    }

    void sendMessage(ChatMessage msg) {
        try {
            sOutput.writeObject(msg);
            //display("Writing a message " );
        } catch (EOFException e){
            display("Exception writing to server: " + e);

        } catch(IOException e) {
            display("Exception writing to server: " + e);
        }
    }


    private void disconnect() {
        try {
            if(sInput != null) sInput.close();
        }
        catch(Exception e) {} // not much else I can do
        try {
            if(sOutput != null) sOutput.close();
        }
        catch(Exception e) {} // not much else I can do
        try{
            if(socket != null) socket.close();
        }
        catch(Exception e) {} // not much else I can do

        // inform the GUI
        if(cg != null)
            cg.connectionFailed();

    }



    class NewListenFromServer extends Thread {

        public void run() {
            while (true) {
                try {
                    //cg.append("inside Listener");
                    String msg = (String) sInput.readObject();
                    cg.append(msg);

                } catch (IOException e) {
                    display("Server has close the connection: " + e);
                    if (cg != null)
                        cg.connectionFailed();
                    break;
                }
                // can't happen with a String object but need the catch anyhow
                catch (ClassNotFoundException e2) {
                }
            }
        }
    }


}
