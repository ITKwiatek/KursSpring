package com.kwiatek.publisher.controller;


import com.kwiatek.publisher.service.NotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {


    private final NotificationService notificationService;


    public MessageController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public String sendStudentNotofication(@RequestParam Long studentId){
        notificationService.sendStudentNotification(studentId);
        return "Wiadomość została wysłana do studenta o id :" +studentId;
    }

}
