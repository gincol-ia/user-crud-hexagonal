package com.example.usercrud.application.port.in;

import com.example.usercrud.domain.User;
import java.util.List;
import java.util.UUID;

public interface UserServicePort {
    User createUser(String username, String email, String firstName, String lastName);
    User updateUser(UUID id, String username, String email, String firstName, String lastName);
    User getUserById(UUID id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    List<User> getAllActiveUsers();
    void deactivateUser(UUID id);
    void activateUser(UUID id);
    void deleteUser(UUID id);
}