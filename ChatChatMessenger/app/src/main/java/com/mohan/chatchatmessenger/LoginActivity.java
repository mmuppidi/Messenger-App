package com.mohan.chatchatmessenger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;


public class LoginActivity extends ActionBarActivity {

    EditText host_ip;
    EditText port_no;
    EditText pUsername;
    CheckBox checkIt;

    private String default_ip,default_username;
    private String cur_ip,cur_username;

    private int cur_port,default_port;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        host_ip =(EditText) findViewById(R.id.login_host_name);
        port_no =(EditText) findViewById(R.id.login_port);
        pUsername =(EditText) findViewById(R.id.login_username);
        checkIt = (CheckBox) findViewById(R.id.login_checkBox);





    }


    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public void onClickSignIn(View v){

        switch (v.getId()){
            case R.id.SignInbutton:
                cur_ip = host_ip.getText().toString();
                //
                cur_username = pUsername.getText().toString();

                if (cur_ip.isEmpty() || port_no.getText().toString().isEmpty() || pUsername.getText().toString().isEmpty()){
                    break;
                }
                else {
                    cur_port = Integer.parseInt(port_no.getText().toString());

                }


                Intent intent = new Intent(this,ChatChatNew.class);
                intent.putExtra("host_ip",cur_ip);
                intent.putExtra("port_no",cur_port);
                intent.putExtra("username",cur_username);
                setDefaults("ipAddress", cur_ip, this);
                setDefaults("port",port_no.getText().toString()  ,this);
                setDefaults("username", cur_username ,this);
                startActivity(intent);



                break;
            case R.id.login_checkBox:

                if (checkIt.isChecked()){
                    cur_ip = getDefaults("ipAddress", this);

                    cur_username = getDefaults("username", this);
                    host_ip.setText(cur_ip);
                    port_no.setText(getDefaults("port", this));
                    pUsername.setText(cur_username);

                }

                else {

                    host_ip.setText("");
                    port_no.setText("");
                    pUsername.setText("");

                }


                break;
            default:
                break;
        }




    }


}
