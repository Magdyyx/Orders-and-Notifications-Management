package com.example.FCAI.api.model.Notification;

import java.time.LocalDateTime;

public class Message {
    private String header;
    private String body;

    private LocalDateTime timeSent;

    public Message(String header, String body) {
        this.header = header;
        this.body = body;
        this.timeSent = LocalDateTime.now();
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(LocalDateTime timeSent) {
        this.timeSent = timeSent;
    }

    @Override
    public String toString() {
        return "Message{" +
                "header='" + header + '\'' +
                ", body='" + body + '\'' +
                ", timeSent=" + timeSent +
                '}';
    }
}
