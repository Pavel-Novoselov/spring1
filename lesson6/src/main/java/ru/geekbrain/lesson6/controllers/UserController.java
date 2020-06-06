package ru.geekbrain.lesson6.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrain.lesson6.entities.User;
import ru.geekbrain.lesson6.service.UserService;


import java.util.Optional;

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
                           @RequestParam(name = "maxAge", required = false, defaultValue = "100") Integer maxAge,
                           @RequestParam(name = "page") Optional<Integer> page,
                           @RequestParam(name = "size", defaultValue = "5") Optional<Integer> size) {
        logger.info("User list. With minAge = {} and maxAge = {}", minAge, maxAge);

        //model.addAttribute("usersPage", userService.filterByAge(minAge, maxAge, PageRequest.of(page.orElse(1)-1, size.orElse(3))));
        Page<User> userPage = userService.filterByAge(minAge, maxAge,
                PageRequest.of(page.orElse(1) - 1, size.orElse(5)));
        model.addAttribute("usersPage", userPage);
        model.addAttribute("prevPageNumber", userPage.hasPrevious() ? userPage.previousPageable().getPageNumber() + 1 : -1);
        model.addAttribute("nextPageNumber", userPage.hasNext() ? userPage.nextPageable().getPageNumber() + 1 : -1);
        return "users";
    }

    @GetMapping("new")
    public String createUser(Model model) {
        logger.info("Create user form");

        model.addAttribute("user", new User());
        return "user";
    }

    @PostMapping
    public String saveUser(User user, BindingResult bindingResult) {
        logger.info("Save user method");


        if (bindingResult.hasErrors()) {
            return "user";
        }
        logger.info("password {} repeat {}", user.getPassword(), user.getRepeatPassword());
        if (!user.getPassword().equals(user.getRepeatPassword())) {
            bindingResult.rejectValue("repeatPassword", "", "пароли не совпадают");
            return "user";
        }

        if (user.getAge()<16){
            bindingResult.rejectValue("age", "", "вы еще слишком молоды!");
            return "user";
        }

        userService.save(user);
        return "redirect:/user";
    }

    @GetMapping("edit")
    public String editUser(Model model) {
        logger.info("Edit user form");

        model.addAttribute("user", new User());
        return "userEdit";
    }

    @PostMapping ("userEdit")
    public String editUser(User user, BindingResult bindingResult) {
        logger.info("Edit user method");

        if (bindingResult.hasErrors()) {
            return "user";
        }
        logger.info("password {} repeat {}", user.getPassword(), user.getRepeatPassword());
        if (!user.getPassword().equals(user.getRepeatPassword())) {
            bindingResult.rejectValue("repeatPassword", "", "пароли не совпадают");
            return "user";
        }

        if (user.getAge()<16){
            bindingResult.rejectValue("age", "", "вы еще слишком молоды!");
            return "user";
        }
        userService.editUser(user);
        return "redirect:/user";
    }
}
