package net.SpringProject.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.SpringProject.journalApp.entity.JournalEntry;
import net.SpringProject.journalApp.entity.User;
import net.SpringProject.journalApp.repository.UserRepository;
import net.SpringProject.journalApp.service.JournalEntryService;
import net.SpringProject.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) { // user: the data provided by client(postman ki body:
                                                                  // username password)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDB = userService.findByUserName(userName); // userInDb: pura dataset from user repo by searching
                                                              // username
        if (userName != null) {
            userInDB.setUserName(user.getUserName());
            userInDB.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(userInDB);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
