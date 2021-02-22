package com.kwiatek.publisher.controller;


import com.kwiatek.publisher.model.Notification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {


    @Autowired
    private final RabbitTemplate rabbitTemplate;

    public MessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/message")
    public String sendMessage(@RequestParam String message){

        rabbitTemplate.convertAndSend("kurs",message );
        return "Wrzucono wiadomosc na RabbitMQ o treści: " + message;
    }

    @PostMapping("/notification")
    public String sendNotification(@RequestBody Notification notification){
        rabbitTemplate.convertAndSend("kurs", notification);
        return "Notyfikacja wysłana";
    }

}
