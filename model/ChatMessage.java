package model;

public class ChatMessage {
    private String senderName;
    private String message;

    public ChatMessage(String senderName, String message) {
        this.senderName = senderName;
        this.message = message;
    }

    //Getters
    public String getSenderName() {
        return senderName;
    }
    
    public String getMessage() {
        return message;
    }

    //Setters
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        return "Sender: " + senderName + ", Message: " + message;
    }

}
