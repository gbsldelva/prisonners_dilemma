package fr.uga.m1miage.pc.model;

import java.util.Objects;

public class Message {
    private String type;
    private Object payload;

    public Message(String type, Object payload) {
        this.type = type;
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(type, message.type) && 
               Objects.equals(payload, message.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, payload);
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", payload=" + payload +
                '}';
    }
}
