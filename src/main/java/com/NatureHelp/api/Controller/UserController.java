package com.NatureHelp.api.Controller;

import com.NatureHelp.api.Dto.UserLoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import com.NatureHelp.api.Service.UserService;
import com.NatureHelp.api.Service.UserService.AuthenticationResponse;
import com.NatureHelp.api.Model.User;

@RestController
@RequestMapping("/api")
@Api(tags = "Users", description = "Operations related to users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;
    

    @GetMapping("/users")
    @ApiOperation(value = "Get all users", notes = "Returns a list of all users.")
    public Iterable<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "Get user by ID", notes = "Returns a user by its ID.")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.GetUserById(id);

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("auth/register")
    @ApiOperation(value = "Create a new user", notes = "Creates a new user.")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
    
   
    @PostMapping("/auth/login")
    @ApiOperation(value = "Authenticate user", notes = "Authenticate a user and return a JWT token.")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody UserLoginRequest loginRequest) {
        AuthenticationResponse response = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    // @GetMapping("/auth/me")
    // @ApiOperation(value = "Get current user", notes = "Returns the current user.")
    // public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
    //     String token = authorizationHeader.substring(7);
    //     String email = userService.getEmailFromToken(token);
    //     Optional<User> user = userService.GetUserByEmail(email);

    //     if (user.isPresent()) {
    //         return new ResponseEntity<>(user.get(), HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }
}

