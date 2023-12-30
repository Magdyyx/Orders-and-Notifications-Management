package com.example.FCAI.api.Repositories;

import com.example.FCAI.api.model.Message;
import com.example.FCAI.service.RepositoryService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class MessageRepo implements RepositoryService<Message> {
    Queue<Message> messagesQueue;
    Queue<Message> sentMessages;

    public MessageRepo() {
        messagesQueue = new LinkedList<>();
        sentMessages = new LinkedList<>();
    }
    @Override
    public Message create(Message message) {
        Message message1 = new Message(message);
        messagesQueue.add(message);
        return message1;
    }
    public Message setSent(Message message) {
        Message message1 = new Message(message);
        messagesQueue.remove(message);
        sentMessages.add(message);
        return message1;
    }

    public Queue<Message> getMessagesQueue(){
        Queue<Message> messageQueue1 = new LinkedList<>(messagesQueue);
        return messageQueue1;
    }

    public Queue<Message> getSentMessages(){
        Queue<Message> messageQueue1 = new LinkedList<>(sentMessages);
        return messageQueue1;
    }

    //TODO: move them to the service
//    public int getSentCount(){
//        return sentMessages.size();
//    }
//
//    public int getQueueSize(){
//        return messagesQueue.size();
//    }

    @Override
    public Message update(Message message, Message t1) {
        return null;
    }

    @Override
    public Message delete(Message message) {
        messagesQueue.remove(message);
        return message;
    }

    @Override
    public Message findById(int id) {
        return null;
    }

    @Override
    public List<Message> findAll() {
        return null;
    }
}
