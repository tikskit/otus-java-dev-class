package ru.tikskit.controllers;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

import java.util.List;


@Controller
public class UserController {
    private final DBServiceUser dbServiceUser;
    private final SimpMessagingTemplate template;

    public UserController(DBServiceUser dbServiceUser, SimpMessagingTemplate template) {
        this.dbServiceUser = dbServiceUser;
        this.template = template;
    }

    @GetMapping({"/", "/userslist"})
    public String clientsListView(Model model) {
        List<User> users = dbServiceUser.getAll();
        model.addAttribute("users", users);
        return "userslist.html";
    }

    @GetMapping("/createuser")
    public void createUser(@ModelAttribute User user) {
        dbServiceUser.saveUser(user);
        sendNewUser(user);
    }

    public void sendNewUser(User user) {
        template.convertAndSend("/topic/newUser", user);
    }

}
