package vn.its.gsmessagingstompwebsocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.its.gsmessagingstompwebsocket.model.HelloMessage;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GreetingController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/publicChatRoom")
    public HelloMessage sendMessage(@Payload HelloMessage helloMessage) {
        return helloMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/publicChatRoom")
    public HelloMessage addUser(@Payload HelloMessage helloMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", helloMessage.getSender());
        return helloMessage;
    }

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("username");

        if (username == null || username.isEmpty()) {
            return "redirect:/login";
        }
        model.addAttribute("username", username);

        return "chat";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(HttpServletRequest request, @RequestParam(defaultValue = "") String username) {
        username = username.trim();

        if (username.isEmpty()) {
            return "login";
        }
        request.getSession().setAttribute("username", username);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession(true).invalidate();

        return "redirect:/login";
    }
}
