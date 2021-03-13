package ru.tikskit.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.tikskit.ms.FrontendService;
import ru.tikskit.ms.UserData;
import ru.tikskit.utils.UserUtils;

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
    public RedirectView createUser(@ModelAttribute User user) throws JsonProcessingException {

        UserData userData = new UserData(0, UserUtils.userToJson(user));

        frontendService.saveUser(userData, data -> {

            try {
                User userObj = UserUtils.jsonToUser(data.getData());
                logger.info("User to response: {}", user);
                returnUserWS(userObj);
            } catch (JsonProcessingException e) {
                logger.error("Error on convertion json to User", e);
            }

        }
        );
        return new RedirectView("/");
    }

    public void returnUserWS(User user) {
        template.convertAndSend("/topic/newUser", user);
        logger.info("User was sent to client: {}", user);
    }

}
