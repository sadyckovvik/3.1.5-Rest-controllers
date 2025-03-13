package ru.sadykov.katacourse.PP3_1_2_Security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sadykov.katacourse.PP3_1_2_Security.models.User;
import ru.sadykov.katacourse.PP3_1_2_Security.services.RoleService;
import ru.sadykov.katacourse.PP3_1_2_Security.services.UserService;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public ModelAndView showAllUsers(@ModelAttribute("newUser") User user,
                               Principal principal,
                               Model model) {
        model.addAttribute("admin", userService.findByUsername(principal.getName()).get());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("activeTable", "usersTable");
        return new ModelAndView("admin-page");
    }

    @PostMapping
    public ModelAndView saveNewUser(@ModelAttribute("newUser") User user,
                              @RequestParam("roleIds") List<Long> roleIds) {
        user.setRoles(roleService.findsRolesByIds(roleIds)); // Устанавливаем роли для пользователя
        userService.saveUser(user);
        return new ModelAndView("redirect:/admin");
    }

    @PatchMapping("/{id}")
    public ModelAndView edit(@PathVariable("id") Long id,
                       @ModelAttribute("user") User user,
                       @RequestParam("roleIds") List<Long> roleIds) {
        user.setRoles(roleService.findsRolesByIds(roleIds));
        userService.updateUser(id, user);
        return new ModelAndView("redirect:/admin");
    }

    @DeleteMapping("/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        userService.removeUser(id);
        return new ModelAndView("redirect:/admin");
    }
}
