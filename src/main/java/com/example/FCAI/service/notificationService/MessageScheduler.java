package com.example.FCAI.service.notificationService;

import com.example.FCAI.api.model.Message;

import java.util.Random;

public class MessageScheduler implements Runnable{
    private Message message;
    private NotificationService notificationService;
    private final Random random = new Random();

    public MessageScheduler(Message message, NotificationService notificationService) {
        this.message = message;
        this.notificationService = notificationService;
    }

    @Override
    public void run() {
        notificationService.addToMessageQueue(message);
        while (notificationService.messageQueueSize() != 0) {
            try {
                System.out.println("inside scheduler");

                Thread.sleep(random.nextInt(1) + 1);

                notificationService.sendMessage(message);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
