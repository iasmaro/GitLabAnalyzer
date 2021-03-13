package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.model.User;
import com.haumea.gitanalyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(path = "/api/v1/users")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void saveUser(@Valid @RequestBody User user){

        userService.saveUser(user);
    }

    @PutMapping
    public void updateUser(@Valid @RequestBody User user){

        userService.updateUser(user);

    }

    @GetMapping("/token")
    public String getPersonalAccessToken(@RequestParam @NotBlank String userId){

      return userService.getPersonalAccessToken(userId);

    }

    @GetMapping("/server")
    public String getGitlabServer(@RequestParam @NotBlank String userId){

        return userService.getGitlabServer(userId);

    }

    @GetMapping("/userId")
    public String getUserId(@RequestParam @NotBlank String url, @RequestParam @NotBlank String ticket) {

        return userService.getUserId(url, ticket);

    }
}
