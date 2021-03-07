package ru.tikskit.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceException;
import ru.tikskit.ms.FrontendService;

import java.util.List;


@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final DBServiceUser dbServiceUser;
    private final SimpMessagingTemplate template;
    private final FrontendService frontendService;

    public UserController(DBServiceUser dbServiceUser, SimpMessagingTemplate template, FrontendService frontendService) {
        this.dbServiceUser = dbServiceUser;
        this.template = template;
        this.frontendService = frontendService;
    }

    @GetMapping({"/", "/userslist"})
    public String clientsListView(Model model) {
        List<User> users = dbServiceUser.getAll();
        model.addAttribute("users", users);
        return "userslist.html";
    }

    @GetMapping("/createuser")
    public void createUser(@ModelAttribute User user) {
        frontendService.getUserData(1, data -> {
            logger.info("saving user to db: {}", data.getUserId());
            // Получаем пользователя из БД
/*
                    try {
                        User userObj = jsonToUser(data.getData());
                        try {
                            dbServiceUser.saveUser(userObj);
                            sendNewUser(userObj);
                        } catch (DbServiceException e) {
                            logger.error("Error on creation user", e);
                        }
                    } catch (JsonProcessingException e) {
                        logger.error("Error on convertion json to User", e);
                    }
*/
                }
        );
    }

    private User jsonToUser(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, User.class);
    }

    public void sendNewUser(User user) {
        template.convertAndSend("/topic/newUser", user);
    }

}
