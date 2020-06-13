package ru.fivestarter.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.fivestarter.user.dao.User;
import ru.fivestarter.user.dao.UserRepository;

@RestController
@RequestMapping("/api/v1/batch/users")
public class UserController {

    Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserJson> getUser() throws org.springframework.batch.core.JobExecutionException {
        userService.process();

        UserJson user = new UserJson();
        user.setFirstName("Mick");
        user.setLastName("Jagger");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserJson> postUser(@RequestBody UserJson user) {
        User savedUser = userRepository.save(user.toUser());
        return new ResponseEntity<>(new UserJson(savedUser), HttpStatus.OK);
    }
}
