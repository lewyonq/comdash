package com.avocados.comdash.user;

import java.net.URI;
import java.util.List;

import com.avocados.comdash.calendar.dto.CalendarEventResponseDTO;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avocados.comdash.exception.ResourceNotFoundException;
import com.avocados.comdash.user.dto.UserRegistrationDto;
import com.avocados.comdash.user.dto.UserResponseDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    
    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            UserResponseDto responseDto = userService.createUser(userRegistrationDto);
            return ResponseEntity
                    .created(URI.create("/api/v1/user/" + responseDto.getId()))
                    .body(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        try {
            return ResponseEntity.ok(userService.getCurrentUserDto());
        } catch (IllegalStateException e)  {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/all-except-current")
    public ResponseEntity<List<UserResponseDto>> getAllUsersExceptCurrent() {
        return ResponseEntity.ok(userService.getAllUsersExceptCurrent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUser(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
        @PathVariable Long id, @RequestBody UserRegistrationDto userRegistrationDto
    ) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, userRegistrationDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping()
    public ResponseEntity<UserResponseDto> updateCurrentUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            return ResponseEntity.ok(userService.updateCurrentUser(userRegistrationDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/get-events")
    public ResponseEntity<List<CalendarEventResponseDTO>> getUserEvents(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserEvents(id));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
