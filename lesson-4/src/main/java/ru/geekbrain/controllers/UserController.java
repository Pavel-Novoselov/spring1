package ru.geekbrain.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrain.entities.User;
import ru.geekbrain.repositories.UserRepository;
import ru.geekbrain.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model,
                           @RequestParam(name = "minAge", required = false, defaultValue = "1") Integer minAge,
                           @RequestParam(name = "maxAge", required = false, defaultValue = "100") Integer maxAge) {
        logger.info("User list. With minAge = {} and maxAge = {}", minAge, maxAge);

        model.addAttribute("users", userService.filterByAge(minAge, maxAge));
        return "users";
    }

    @GetMapping("new")
    public String createUser(Model model) {
        logger.info("Create user form");

        model.addAttribute("user", new User());
        return "user";
    }

    @PostMapping
    public String saveUser(User user) {
        logger.info("Save user method");

        userService.save(user);
        return "redirect:/user";
    }
}
