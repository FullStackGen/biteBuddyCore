package com.bite.buddy.controller;

import com.bite.buddy.entity.User;
import com.bite.buddy.model.ApiResponse;
import com.bite.buddy.model.AuthRequest;
import com.bite.buddy.model.UserDto;
import com.bite.buddy.repository.UserRepo;
import com.bite.buddy.security.JwtUtil;
import com.bite.buddy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/biteBuddy")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        User user = userRepo.findByEmail(authRequest.getEmail()).get();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails, user.getRole());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", user.getUserId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("user", userDto);
        UserDto createdUserDto = this.userService.registerUser(requestMap);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("user", userDto);
        requestMap.put("userId", userId);
        UserDto updatedUserDto = this.userService.updateUser(requestMap);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userId", userId);
        this.userService.deleteUser(requestMap);
        return ResponseEntity.ok(new ApiResponse("User deleted successfully", true));
    }

    // GET
//    @GetMapping("/admin/user/search")
//    public ResponseEntity<List<UserDto>> getAllUsers() {
//        return ResponseEntity.ok(this.userService.getAllUsers());
//    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") String uid) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("userId", uid);
        return ResponseEntity.ok(this.userService.getUser(requestMap));
    }
}

