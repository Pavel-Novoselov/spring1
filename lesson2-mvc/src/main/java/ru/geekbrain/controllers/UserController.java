package ru.geekbrain.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrain.entities.User;
import ru.geekbrain.repositories.UserRepository;

@RequestMapping("/user")
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(ru.geekbrain.controllers.UserController.class);

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userList(Model model) {
        logger.info("User list");

        model.addAttribute("users", userRepository.findAll());
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

        userRepository.save(user);
        return "redirect:/user";
    }
}
