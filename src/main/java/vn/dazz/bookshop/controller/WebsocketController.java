package vn.dazz.bookshop.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.dazz.bookshop.services.user.UserService;
import vn.dazz.bookshop.util.Utils;

import java.io.Serializable;

@Controller
public class WebsocketController {

    @Autowired
    SimpMessageSendingOperations messageSendingOperations;

    @Autowired
    UserService userService;

    @GetMapping(value = "contact")
    public String chat(Model model) {
        model.addAttribute("user", userService.findUserById(Utils.getUserHadLogin().getId()));
        // Lưu lịch sử chat: 3 thông tin (userId, nội dung chat, thời gian, sở hữu(client, admin)))
        return "contact";
    }

    @MessageMapping("contact")
    public ChatContent chat(@Payload ChatContent chatContent) {
        ChatContent response = new ChatContent();
        response.setFrom(chatContent.getFrom()); // Đặt người gửi
        response.setTo(chatContent.getTo());     // Đặt người nhận
        response.setContent(chatContent.getContent()); // Đặt nội dung tin nhắn

        // Gửi tin nhắn tới người nhận qua kênh riêng
        messageSendingOperations.convertAndSend("/topic/" + chatContent.getTo(), response);
        return response;
    }

    @Data
    static class ChatContent implements Serializable {
        String from;
        String to;
        String content;
    }
}
