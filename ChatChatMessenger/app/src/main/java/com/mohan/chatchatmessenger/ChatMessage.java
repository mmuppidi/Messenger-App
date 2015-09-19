package com.mohan.chatchatmessenger;

/**
 * Created by robo-ace on 7/26/15.
 */
import java.io.Serializable;

public class ChatMessage implements Serializable {
    protected static final long serialVersionUID = 1112122200L;
    static final int WHOISIN = 0;
    static final int MESSAGE = 1;
    static final int LOGOUT = 2;
    private int type;
    private String message;

    ChatMessage(int var1, String var2) {
        this.type = var1;
        this.message = var2;
    }

    int getType() {
        return this.type;
    }

    String getMessage() {
        return this.message;
    }
}
