package com.bite.buddy.service.impl;

import com.bite.buddy.entity.User;
import com.bite.buddy.exceptions.ResourceNotFoundException;
import com.bite.buddy.exceptions.ResourceNotUniqueException;
import com.bite.buddy.kafka.NotificationEvent;
import com.bite.buddy.kafka.NotificationProducer;
import com.bite.buddy.model.UserDto;
import com.bite.buddy.repository.UserRepo;
import com.bite.buddy.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private NotificationProducer notificationProducer;

    @Value("${notification.enable}")
    private String notificationFlag;

    public UserServiceImpl(ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto registerUser(Map<String, Object> requestMap) {
        UserDto userDto = (UserDto) requestMap.get("user");
        Optional<User> uniqueUser = this.userRepo.findByEmail(userDto.getEmail());
        if (uniqueUser.isPresent()) throw new ResourceNotUniqueException("User", "email", userDto.getEmail());
        User user = this.dtoToUser(userDto);
        UUID id = UUID.randomUUID();
        long n = (id.getLeastSignificantBits() ^ id.getMostSignificantBits()) & Long.MAX_VALUE;
        user.setUserId("US." + n);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Date date = new Date();
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        user.setCreatedOn(localDateTime);
        User savedUser = userRepo.save(user);
        if (notificationFlag.equals("true")) {
            NotificationEvent event = NotificationEvent.builder()
                    .eventType("USER_REGISTERED")
                    .name(user.getName())
                    .email(user.getEmail())
                    .mobileNumber(user.getPhoneNumber())
                    .message("User #" + user.getName() + " is registered successfully!")
                    .build();

            notificationProducer.sendNotification("users-notifications", event);
        }
        return this.modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(Map<String, Object> requestMap) {
        UserDto userDto = (UserDto) requestMap.get("user");
        String userId = (String) requestMap.get("userId");
        User user = this.userRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        modelMapper.map(userDto, user);
        Date date = new Date();
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        user.setUpdatedOn(localDateTime);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto getUser(Map<String, Object> requestMap) {
        String userId = (String) requestMap.get("userId");
        User user = this.userRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        return users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Map<String, Object> requestMap) {
        String userId = (String) requestMap.get("userId");
        User user = this.userRepo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        this.userRepo.delete(user);

    }

    private User dtoToUser(UserDto userDto) {
        return this.modelMapper.map(userDto, User.class);
    }

    private UserDto userToDto(User user) {
        return this.modelMapper.map(user, UserDto.class);
    }

}