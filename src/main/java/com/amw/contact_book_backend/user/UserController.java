package com.amw.contact_book_backend.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public List<User> showAllUsers() {
        return userService.showAllUsers();
    }

    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable @Valid String email) {
        return userService.getUserByEmail(email);
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable String id, @RequestBody @Valid User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}