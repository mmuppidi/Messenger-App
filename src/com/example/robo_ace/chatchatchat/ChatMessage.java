package com.example.robo_ace.chatchatchat;

/**
 * Created by robo-ace on 7/26/15.
 */
import java.io.Serializable;

public class ChatMessage implements Serializable {
    protected static final long serialVersionUID = 1112122200L;
    public static final int WHOISIN = 0;
    public static final int MESSAGE = 1;
    public static final int LOGOUT = 2;
    private int type;
    private String message;

    public ChatMessage(int var1, String var2) {
        this.type = var1;
        this.message = var2;
    }

    public int getType() {
        return this.type;
    }

    public String getMessage() {
        return this.message;
    }
}
