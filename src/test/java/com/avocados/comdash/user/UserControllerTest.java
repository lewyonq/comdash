package com.avocados.comdash.user;

import com.avocados.comdash.calendar.dto.CalendarEventResponseDTO;
import com.avocados.comdash.config.CurrentUser;
import com.avocados.comdash.exception.ResourceNotFoundException;
import com.avocados.comdash.user.dto.UserRegistrationDto;
import com.avocados.comdash.user.dto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserResponseDto sampleUserResponse;
    private UserRegistrationDto sampleRegistrationDto;
    private List<UserResponseDto> userList;
    private List<CalendarEventResponseDTO> eventList;

    @BeforeEach
    void setUp() {
        sampleRegistrationDto = new UserRegistrationDto();

        sampleUserResponse = new UserResponseDto();
        sampleUserResponse.setId(1L);

        userList = Arrays.asList(sampleUserResponse);

        CalendarEventResponseDTO sampleEvent = new CalendarEventResponseDTO();
        eventList = Arrays.asList(sampleEvent);
    }

    @Test
    void createUser_Success() {
        when(userService.createUser(any(UserRegistrationDto.class))).thenReturn(sampleUserResponse);

        ResponseEntity<UserResponseDto> response = userController.createUser(sampleRegistrationDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(sampleUserResponse);
        assertThat(response.getHeaders().getLocation().toString()).isEqualTo("/api/v1/user/1");
        verify(userService).createUser(sampleRegistrationDto);
    }

    @Test
    void createUser_BadRequest() {
        when(userService.createUser(any(UserRegistrationDto.class)))
                .thenThrow(new IllegalArgumentException("Invalid input"));

        ResponseEntity<UserResponseDto> response = userController.createUser(sampleRegistrationDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNull();
        verify(userService).createUser(sampleRegistrationDto);
    }

    @Test
    void getCurrentUser_Success() {
        when(userService.getCurrentUserDto()).thenReturn(sampleUserResponse);

        ResponseEntity<UserResponseDto> response = userController.getCurrentUser();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(sampleUserResponse);
        verify(userService).getCurrentUserDto();
    }

    @Test
    void getCurrentUser_Unauthorized() {
        when(userService.getCurrentUserDto()).thenThrow(new IllegalStateException("Not authenticated"));

        ResponseEntity<UserResponseDto> response = userController.getCurrentUser();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
        verify(userService).getCurrentUserDto();
    }

    @Test
    void getCurrentUser_NotFound() {
        when(userService.getCurrentUserDto()).thenThrow(new ResourceNotFoundException("User not found"));

        ResponseEntity<UserResponseDto> response = userController.getCurrentUser();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
        verify(userService).getCurrentUserDto();
    }

    @Test
    void getAllUsers_Success() {
        when(userService.getAllUsers()).thenReturn(userList);

        ResponseEntity<List<UserResponseDto>> response = userController.getAllUsers();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userList);
        verify(userService).getAllUsers();
    }

    @Test
    void getAllUsersExceptCurrent_Success() {
        when(userService.getAllUsersExceptCurrent()).thenReturn(userList);

        ResponseEntity<List<UserResponseDto>> response = userController.getAllUsersExceptCurrent();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(userList);
        verify(userService).getAllUsersExceptCurrent();
    }

    @Test
    void getUser_Success() {
        Long userId = 1L;
        when(userService.getUser(userId)).thenReturn(sampleUserResponse);

        ResponseEntity<UserResponseDto> response = userController.getUser(userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(sampleUserResponse);
        verify(userService).getUser(userId);
    }

    @Test
    void getUser_NotFound() {
        Long userId = 1L;
        when(userService.getUser(userId)).thenThrow(new ResourceNotFoundException("User not found"));

        ResponseEntity<UserResponseDto> response = userController.getUser(userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
        verify(userService).getUser(userId);
    }

    @Test
    void updateUser_Success() {
        Long userId = 1L;
        when(userService.updateUser(eq(userId), any(UserRegistrationDto.class))).thenReturn(sampleUserResponse);

        ResponseEntity<UserResponseDto> response = userController.updateUser(userId, sampleRegistrationDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(sampleUserResponse);
        verify(userService).updateUser(userId, sampleRegistrationDto);
    }

    @Test
    void updateUser_NotFound() {
        Long userId = 1L;
        when(userService.updateUser(eq(userId), any(UserRegistrationDto.class)))
                .thenThrow(new ResourceNotFoundException("User not found"));

        ResponseEntity<UserResponseDto> response = userController.updateUser(userId, sampleRegistrationDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
        verify(userService).updateUser(userId, sampleRegistrationDto);
    }

    @Test
    void updateCurrentUser_Success() {
        when(userService.updateCurrentUser(any(UserRegistrationDto.class))).thenReturn(sampleUserResponse);
        ResponseEntity<UserResponseDto> response = userController.updateCurrentUser(sampleRegistrationDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(sampleUserResponse);
        verify(userService).updateCurrentUser(sampleRegistrationDto);
    }

    @Test
    void updateCurrentUser_NotFound() {
        when(userService.updateCurrentUser(any(UserRegistrationDto.class)))
                .thenThrow(new ResourceNotFoundException("User not found"));

        ResponseEntity<UserResponseDto> response = userController.updateCurrentUser(sampleRegistrationDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
        verify(userService).updateCurrentUser(sampleRegistrationDto);
    }

    @Test
    void deleteUser_Success() {
        Long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(userService).deleteUser(userId);
    }

    @Test
    void deleteUser_NotFound() {
        Long userId = 1L;
        doThrow(new ResourceNotFoundException("User not found")).when(userService).deleteUser(userId);

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(userService).deleteUser(userId);
    }

    @Test
    void getUserEvents_Success() {
        when(userService.getUserEvents(1L)).thenReturn(eventList);

        ResponseEntity<List<CalendarEventResponseDTO>> response = userController.getUserEvents(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(eventList);
        verify(userService).getUserEvents(1L);
    }

    @Test
    void getUserEvents_Unauthorized() {
        when(userService.getUserEvents(1L)).thenThrow(new IllegalStateException("Not authenticated"));

        ResponseEntity<List<CalendarEventResponseDTO>> response = userController.getUserEvents(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
        verify(userService).getUserEvents(1L);
    }

    @Test
    void getUserEvents_NotFound() {
        when(userService.getUserEvents(1L)).thenThrow(new ResourceNotFoundException("User not found"));

        ResponseEntity<List<CalendarEventResponseDTO>> response = userController.getUserEvents(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
        verify(userService).getUserEvents(1L);
    }
}
