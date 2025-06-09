package com.example.usercrud.infrastructure.adapter.in.web.controller;

import com.example.usercrud.application.port.in.UserServicePort;
import com.example.usercrud.domain.User;
import com.example.usercrud.infrastructure.adapter.in.web.dto.CreateUserDto;
import com.example.usercrud.infrastructure.adapter.in.web.dto.UpdateUserDto;
import com.example.usercrud.infrastructure.adapter.in.web.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management API")
public class UserController {
    
    private final UserServicePort userService;
    
    public UserController(UserServicePort userService) {
        this.userService = userService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or user already exists")
    })
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto createDto) {
        User user = userService.createUser(
            createDto.getUsername(),
            createDto.getEmail(),
            createDto.getFirstName(),
            createDto.getLastName()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(user));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", 
               parameters = @Parameter(name = "id", description = "User ID", required = true, in = ParameterIn.PATH))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") UUID id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(toDto(user));
    }
    
    @GetMapping("/username/{username}")
    @Operation(summary = "Get user by username",
               parameters = @Parameter(name = "username", description = "Username", required = true, in = ParameterIn.PATH))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable("username") String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(toDto(user));
    }
    
    @GetMapping
    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "List of all users")
    public ResponseEntity<List<UserDto>> getAllUsers(
            @Parameter(description = "Filter only active users", name = "activeOnly") 
            @RequestParam(name = "activeOnly", required = false, defaultValue = "false") Boolean activeOnly) {
        List<User> users = activeOnly ? 
            userService.getAllActiveUsers() : 
            userService.getAllUsers();
        
        List<UserDto> dtos = users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(dtos);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update user",
               parameters = @Parameter(name = "id", description = "User ID", required = true, in = ParameterIn.PATH))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateUserDto updateDto) {
        User user = userService.updateUser(
            id,
            updateDto.getUsername(),
            updateDto.getEmail(),
            updateDto.getFirstName(),
            updateDto.getLastName()
        );
        return ResponseEntity.ok(toDto(user));
    }
    
    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate user",
               parameters = @Parameter(name = "id", description = "User ID", required = true, in = ParameterIn.PATH))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deactivated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deactivateUser(@PathVariable("id") UUID id) {
        userService.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate user",
               parameters = @Parameter(name = "id", description = "User ID", required = true, in = ParameterIn.PATH))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User activated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> activateUser(@PathVariable("id") UUID id) {
        userService.activateUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user",
               parameters = @Parameter(name = "id", description = "User ID", required = true, in = ParameterIn.PATH))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    private UserDto toDto(User user) {
        return new UserDto(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getFullName(),
            user.getCreatedAt(),
            user.getUpdatedAt(),
            user.isActive()
        );
    }
}