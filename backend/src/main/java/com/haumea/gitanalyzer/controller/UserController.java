package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.model.User;
import com.haumea.gitanalyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

        if(user.getPersonalAccessToken() == null || user.getPersonalAccessToken().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "getPersonalAccessToken cannot be null, empty or blank.");
        }

        userService.updateUser(user);

    }

    @GetMapping("/token")
    public String getPersonalAccessToken(@RequestParam @NotBlank String userId){
      return userService.getPersonalAccessToken(userId);
    }


    @GetMapping("/userId")
    public String getUserId(@RequestParam @NotBlank String url, @RequestParam @NotBlank String ticket) {
        return userService.getUserId(url, ticket);
    }
}
