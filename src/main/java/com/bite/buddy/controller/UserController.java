package com.bite.buddy.controller;

import com.bite.buddy.model.ApiResponse;
import com.bite.buddy.model.UserDto;
import com.bite.buddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biteBuddy")
public class UserController {

    @Autowired
    private UserService userService;

    // POST
    @PostMapping("/user/register")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUserDto = this.userService.registerUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    // PUT
    @PutMapping("/user/modify/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
        UserDto updatedUserDto = this.userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        this.userService.deleteUser(userId);
        return ResponseEntity.ok(new ApiResponse("User deleted successfully", true));
    }

    // GET
    @GetMapping("/user/search")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    // GET
    @GetMapping("/user/search/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") String uid) {
        return ResponseEntity.ok(this.userService.getUser(uid));
    }
}

