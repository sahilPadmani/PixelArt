package com.fsd.art.controller;

import com.fsd.art.model.req.UserReq;
import com.fsd.art.model.res.UserRes;
import com.fsd.art.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public UserRes save(@Valid @RequestBody UserReq userReq){
        return userService.saveUser(userReq);
    }

    @GetMapping("/{id}")
    public UserRes getUser(@PathVariable Integer id){
        return userService.getUser(id);
    }
}
