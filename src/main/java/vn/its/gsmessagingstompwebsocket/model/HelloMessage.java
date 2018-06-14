package vn.its.gsmessagingstompwebsocket.model;

public class HelloMessage {
    private HelloType type;
    private String content;
    private String sender;

    public enum HelloType {
        CHAT, JOIN, LEAVE
    }

    public HelloType getType() {
        return type;
    }

    public void setType(HelloType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
