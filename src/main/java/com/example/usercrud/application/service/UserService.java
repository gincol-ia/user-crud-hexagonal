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
     * Validates that a new user can be created with the given username and email.
     * 
     * @param username the username to validate
     * @param email the email to validate
     * @throws IllegalArgumentException if username or email already exists
     */
    private void validateNewUser(String username, String email) {
        validateUsernameDoesNotExist(username);
        validateEmailDoesNotExist(email);
    }
    
    /**
     * Validates that the username does not already exist in the system.
     * 
     * @param username the username to check
     * @throws IllegalArgumentException if username already exists
     */
    private void validateUsernameDoesNotExist(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
    }
    
    /**
     * Validates that the email does not already exist in the system.
     * 
     * @param email the email to check
     * @throws IllegalArgumentException if email already exists
     */
    private void validateEmailDoesNotExist(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
    }
    
    /**
     * Builds a new User domain object with the provided details.
     * 
     * @param username the user's username
     * @param email the user's email
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @return a new User instance
     */
    private User buildUser(String username, String email, String firstName, String lastName) {
        return new User(username, email, firstName, lastName);
    }
    
    /**
     * Saves the user to the repository.
     * 
     * @param user the user to save
     * @return the saved user
     */
    private User saveUser(User user) {
        return userRepository.save(user);
    }
    
    @Override
    public User updateUser(UUID id, String username, String email, String firstName, String lastName) {
        User user = getUserById(id);
        
        // Validar que no exista otro usuario con el mismo username o email
        userRepository.findByUsername(username).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(id)) {
                throw new IllegalArgumentException("Username already exists: " + username);
            }
        });
        
        userRepository.findByEmail(email).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(id)) {
                throw new IllegalArgumentException("Email already exists: " + email);
            }
        });
        
        user.update(username, email, firstName, lastName);
        return userRepository.save(user);
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