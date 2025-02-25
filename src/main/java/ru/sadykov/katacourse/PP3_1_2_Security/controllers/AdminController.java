package ru.sadykov.katacourse.PP3_1_2_Security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sadykov.katacourse.PP3_1_2_Security.models.User;
import ru.sadykov.katacourse.PP3_1_2_Security.services.CustomUserDetailsService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private CustomUserDetailsService userDetailsService;

    @Autowired
    public AdminController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("allUsers",userDetailsService.getAllUsers());
        return "all-users";
    }

    @GetMapping("/addNewUser")
    public String addNewUser(Model model) {
        model.addAttribute("user",new User());
        return "user-info";
    }

    @PostMapping("/saveUser")
    public String saveNewUser(@ModelAttribute("user") User user) {
        userDetailsService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/updateInfo")
    public String updateUser(@RequestParam("userId") long id, Model model) {
        model.addAttribute("user",userDetailsService.findUserById(id));
        return "user-info";
    }

    @GetMapping("/removeUser")
    public String removeUser(@RequestParam("userId") long id) {
        userDetailsService.deleteUserById(id);
        return "redirect:/admin";
    }
}
