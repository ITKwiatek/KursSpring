package com.kwiatek.receiver.controller;

import com.kwiatek.receiver.model.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final RabbitTemplate rabbitTemplate;

    public MessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/message")
    public String receiverMessage() {
        Object message = rabbitTemplate.receiveAndConvert("kurs");
        if (message != null) {
            return "Pobrano wiadomosc z rabbita: " + message.toString();
        }
        return "Nie ma wiadomości do odczytu";
    }

//    @RabbitListener(queues = "kurs")
//    public void listenerMessage(Notification notification) {
//        System.out.println(notification.getEmail() + " " +
//                notification.getTitle() + " "
//                + notification.getBody());
//    }

    @GetMapping("/notification")
    public ResponseEntity<Notification> receiveNotification() {
        Notification notification = rabbitTemplate.
                receiveAndConvert("kurs", ParameterizedTypeReference.
                        forType(Notification.class));
        if (notification != null) {
            return ResponseEntity.ok((Notification) notification);
        }
        return ResponseEntity.noContent().build();
    }


}
