package flapkap.demo.controller;

import flapkap.demo.model.User;
import flapkap.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(User user) {
        return ResponseEntity.status(200).body(userService.saveUser(user));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(Long id) {
        Optional<User> user = userService.getUser(id);
        if (user.isPresent())
            return ResponseEntity.status(200).body(user.get());
        else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(200).build();
    }

    @PreAuthorize("hasRole('BUYER')")
    @PutMapping("/user/reset")
    public ResponseEntity<User> resetUser() {
        userService.resetUser();
        return ResponseEntity.status(200).build();
    }

}