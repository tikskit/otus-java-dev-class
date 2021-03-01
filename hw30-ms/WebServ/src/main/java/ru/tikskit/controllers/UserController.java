package ru.tikskit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

import java.util.List;


@Controller
public class UserController {
    private final DBServiceUser dbServiceUser;

    public UserController(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

//    @RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping({"/", "/userslist"})
    public String clientsListView(Model model) {
        List<User> users = dbServiceUser.getAll();
        model.addAttribute("users", users);
        return "/WEB-INF/templates/userslist.html";
    }

    @GetMapping({"/createuser"})
    public String createUserView(Model model) {
        model.addAttribute(new User());
        return "/WEB-INF/templates/createuser.html";
    }

    @PostMapping("/createuser")
    public RedirectView createUser(@ModelAttribute User user) {
        dbServiceUser.saveUser(user);
        return new RedirectView("/userslist", true);
    }

}
