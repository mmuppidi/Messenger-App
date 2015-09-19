package com.mohan.chatchatmessenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Stack;


public class ChatChatNew extends Activity {

    public EditText InPutText;
    public ImageButton SendButton;
    public ListView chatList;
    public ChatChatClient client;
    public Boolean connected = false ;

    public int defaultPort = 1500;

    public String defaultHost = "23.253.126.103";
    //public String defaultHost = "172.17.108.177";
    //public String defaultHost = "192.168.33.130";

    public String defaultuser = "Mohan";
    private Stack<String> msgReceived = new Stack();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        InPutText = (EditText) findViewById(R.id.ChateditText);
        SendButton = (ImageButton) findViewById(R.id.imageButton);
        chatList = (ListView ) findViewById(R.id.ChatscrollView);
        Intent intent = getIntent();
        defaultPort = intent.getIntExtra("port_no", defaultPort);
        defaultHost = intent.getStringExtra("host_ip");
        defaultuser = intent.getStringExtra("username");


        client = new ChatChatClient(defaultHost,defaultPort,defaultuser ,this);
        client.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connected) {
            client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
        }
    }

    public void chatChatClickListener(View v){
        switch (v.getId()){
            case R.id.imageButton:
                if(connected){
                    client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, InPutText.getText().toString()));
                    InPutText.setText("");

                }

                break;
            case R.id.imageButton2:
                Intent i = new Intent(this,AppPreferences.class);
                startActivity(i);
            default:
                break;
        }
    }

    void append( String str) {

        final String bla = str;

        msgReceived.push(bla);
        final ChatViewAdapter toAdd = new ChatViewAdapter(this.getBaseContext(),getListData(msgReceived));

        ChatChatNew.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatList.setAdapter(toAdd);
                chatList.setSelection(toAdd.getCount() - 1);
            }
        });
    }

    void connectionFailed() {
        msgReceived.push("Oops! Connection Lost ");
        final ChatViewAdapter toAdd = new ChatViewAdapter(this.getBaseContext(),getListData(msgReceived));
        ChatChatNew.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatList.setAdapter(toAdd);
                chatList.setSelection(toAdd.getCount() - 1);
                connected = false;
            }
        });
    }




    private ArrayList<String> getListData(Stack<String> i_c) {
        ArrayList<String> results = new ArrayList<String>();

        for (String i : i_c){

            results.add(i);
        }
        return results;
    }

}
