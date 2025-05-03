package edu.duan.app.usersservice.controller;

import edu.duan.app.api.User;
import edu.duan.app.usersservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return createdUser;
    }
}
