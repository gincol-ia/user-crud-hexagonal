package com.example.usercrud.application.service;

import com.example.usercrud.application.port.in.UserServicePort;
import com.example.usercrud.application.port.out.UserRepositoryPort;
import com.example.usercrud.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService implements UserServicePort {
    
    private final UserRepositoryPort userRepository;
    
    public UserService(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public User createUser(String username, String email, String firstName, String lastName) {
        validateNewUser(username, email);
        User user = buildUser(username, email, firstName, lastName);
        return saveUser(user);
    }
    
    /**
     * Validates that the username and email are not already in use.
     * 
     * @param username The username to validate
     * @param email The email to validate
     * @throws IllegalArgumentException if username or email already exists
     */
    private void validateNewUser(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
    }
    
    /**
     * Creates a new User domain object with the provided information.
     * 
     * @param username The username for the new user
     * @param email The email for the new user
     * @param firstName The first name of the user
     * @param lastName The last name of the user
     * @return A new User instance
     */
    private User buildUser(String username, String email, String firstName, String lastName) {
        return new User(username, email, firstName, lastName);
    }
    
    /**
     * Persists the user to the repository.
     * 
     * @param user The user to save
     * @return The saved user
     */
    private User saveUser(User user) {
        return userRepository.save(user);
    }
    
    @Override
    public User updateUser(UUID id, String username, String email, String firstName, String lastName) {
        User user = getUserById(id);
        validateUserUpdate(id, username, email);
        updateUserData(user, username, email, firstName, lastName);
        return saveUser(user);
    }
    
    /**
     * Validates that the username and email can be used for updating a user.
     * 
     * @param userId The ID of the user being updated
     * @param username The new username
     * @param email The new email
     * @throws IllegalArgumentException if username or email is already used by another user
     */
    private void validateUserUpdate(UUID userId, String username, String email) {
        userRepository.findByUsername(username).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(userId)) {
                throw new IllegalArgumentException("Username already exists: " + username);
            }
        });
        
        userRepository.findByEmail(email).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(userId)) {
                throw new IllegalArgumentException("Email already exists: " + email);
            }
        });
    }
    
    /**
     * Updates the user data with new values.
     * 
     * @param user The user to update
     * @param username The new username
     * @param email The new email
     * @param firstName The new first name
     * @param lastName The new last name
     */
    private void updateUserData(User user, String username, String email, String firstName, String lastName) {
        user.update(username, email, firstName, lastName);
    }
    
    @Override
    @Transactional(readOnly = true)
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<User> getAllActiveUsers() {
        return userRepository.findAllActive();
    }
    
    @Override
    public void deactivateUser(UUID id) {
        User user = getUserById(id);
        user.deactivate();
        userRepository.save(user);
    }
    
    @Override
    public void activateUser(UUID id) {
        User user = getUserById(id);
        user.activate();
        userRepository.save(user);
    }
    
    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.findById(id).isPresent()) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}