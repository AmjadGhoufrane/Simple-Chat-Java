package Common;

import java.io.Serializable;

public class Message implements Serializable {
    private long sender;
    private String message;

    public Message(String message,long sender){
        this.sender = sender;
        this.message = message;
    }

    @Override
    public String toString() {
        return '\n'+sender+" : "+message;
    }

    public long getSender() {
        return sender;
    }

    public void setSender(long sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
