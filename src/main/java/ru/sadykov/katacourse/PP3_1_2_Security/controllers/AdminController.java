package ru.sadykov.katacourse.PP3_1_2_Security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sadykov.katacourse.PP3_1_2_Security.models.Role;
import ru.sadykov.katacourse.PP3_1_2_Security.models.User;
import ru.sadykov.katacourse.PP3_1_2_Security.services.RoleService;
import ru.sadykov.katacourse.PP3_1_2_Security.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userDetailsService;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserService userDetailsService, RoleService roleService) {
        this.userDetailsService = userDetailsService;
        this.roleService = roleService;
    }
    @GetMapping
    public String adminPage(Principal principal, Model model) {
        model.addAttribute("allUsers", userDetailsService.getAllUsers()); // Список всех пользователей
        model.addAttribute("allRoles", roleService.getAllRoles()); // Список всех ролей
        model.addAttribute("authUser", userDetailsService.findByUsername(principal.getName()).get());
        model.addAttribute("newUser", new User()); // Объект для добавления нового пользователя
        return "admin-panel"; // Имя вашего шаблона (admin-panel.html)
    }

    // Добавление нового пользователя
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("newUser") User user, @RequestParam("roleIds") List<Long> roleIds) {
        user.setRoles(roleService.findsRolesByIds(roleIds)); // Устанавливаем роли для пользователя
        userDetailsService.saveOrUpdateUser(user); // Сохраняем пользователя
        return "redirect:/admin"; // Перенаправляем на страницу администратора
    }

    // Открытие формы редактирования пользователя
    @GetMapping("/editUser")
    public String editUserForm(@RequestParam("userId") long id, Model model) {
        Optional<User> userOptional = userDetailsService.getUser(id);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get()); // Передаем пользователя в модель
            model.addAttribute("allRoles", roleService.getAllRoles()); // Список всех ролей
            return "edit-user"; // Имя вашего шаблона для редактирования (edit-user.html)
        } else {
            return "redirect:/admin"; // Если пользователь не найден, перенаправляем на страницу администратора
        }
    }

    // Сохранение изменений после редактирования пользователя
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user, @RequestParam("roleIds") List<Long> roleIds) {
        user.setRoles(roleService.findsRolesByIds(roleIds)); // Устанавливаем роли для пользователя
        userDetailsService.saveOrUpdateUser(user); // Сохраняем изменения
        return "redirect:/admin"; // Перенаправляем на страницу администратора
    }

    // Удаление пользователя
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") long id) {
        userDetailsService.removeUser(id); // Удаляем пользователя
        return "redirect:/admin"; // Перенаправляем на страницу администратора
    }
}









//    @GetMapping
//    public String getAllUsers(Model model) {
//        model.addAttribute("allUsers", userDetailsService.getAllUsers());
//        return "all-users";
//    }
//
//    @GetMapping("/addNewUser")
//    public String addNewUser(Model model) {
//        model.addAttribute("user", new User());
//        model.addAttribute("allRoles", roleService.getAllRoles());
//        return "user-info";
//    }
//
//    @PostMapping("/saveUser")
//    public String saveNewUser(@ModelAttribute("user") User user, @RequestParam("roleIds") List<Long> roles) {
//        user.setRoles(roleService.findsRolesByIds(roles));
//        userDetailsService.saveOrUpdateUser(user);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/updateInfo")
//    public String updateUser(@RequestParam("userId") long id, Model model) {
//        model.addAttribute("user", userDetailsService.getUser(id).get());
//        model.addAttribute("allRoles", roleService.getAllRoles());
//        return "user-info";
//    }
//
//    @GetMapping("/removeUser")
//    public String removeUser(@RequestParam("userId") long id) {
//        userDetailsService.removeUser(id);
//        return "redirect:/admin";
//    }

