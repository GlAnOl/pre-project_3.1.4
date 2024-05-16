package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;

import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }


    @GetMapping("")
    public String adminPage(Model model, @CurrentSecurityContext(expression = "authentication.principal") User principal) {
        model.addAttribute("listUser", userService.findAll());
        model.addAttribute("user", principal);
        model.addAttribute("role", roleService.getRoles());
        return "admin/admin";
    }


    @PostMapping("/delete")
    public String delete(@RequestParam Integer id) {
        userService.delete(id);
        return "redirect:/admin/";
    }


    @PostMapping("/new")
    public String add(@ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "newError";
        } else {
            userService.save(user);
            return "redirect:/admin/";
        }
    }

    @PostMapping("/edit")
    public String update(@RequestParam int id, User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }

        userService.update(user);
        return "redirect:/admin";
    }

}
