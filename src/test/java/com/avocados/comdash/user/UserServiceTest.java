package com.avocados.comdash.user;

import com.avocados.comdash.calendar.CalendarEventMapper;
import com.avocados.comdash.calendar.dto.CalendarEventResponseDTO;
import com.avocados.comdash.config.CurrentUser;
import com.avocados.comdash.exception.ResourceNotFoundException;
import com.avocados.comdash.model.entity.CalendarEvent;
import com.avocados.comdash.model.entity.User;
import com.avocados.comdash.user.dto.UserRegistrationDto;
import com.avocados.comdash.user.dto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CalendarEventMapper calendarEventMapper;

    @Mock
    private CurrentUser currentUser;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserRegistrationDto testRegistrationDto;
    private UserResponseDto testResponseDto;
    private List<User> testUsers;
    private List<UserResponseDto> testResponseDtos;
    private List<CalendarEvent> testEvents;
    private List<CalendarEventResponseDTO> testEventResponseDtos;

    @BeforeEach
    void setUp() {
        // Initialize test user
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setPassword("encodedPassword");
        testUser.setEvents(new ArrayList<>());
        testUser.setOrganizedEvents(new ArrayList<>());

        // Initialize test registration DTO
        testRegistrationDto = new UserRegistrationDto();
        testRegistrationDto.setEmail("test@example.com");
        testRegistrationDto.setPassword("password");
        testRegistrationDto.setConfirmPassword("password");

        // Initialize test response DTO
        testResponseDto = new UserResponseDto();
        testResponseDto.setId(1L);
        testResponseDto.setEmail("test@example.com");

        // Initialize test user list
        User secondUser = new User();
        secondUser.setId(2L);
        secondUser.setEmail("second@example.com");
        testUsers = Arrays.asList(testUser, secondUser);

        // Initialize test response DTO list
        UserResponseDto secondResponseDto = new UserResponseDto();
        secondResponseDto.setId(2L);
        secondResponseDto.setEmail("second@example.com");
        testResponseDtos = Arrays.asList(testResponseDto, secondResponseDto);

        // Setup events
        CalendarEvent event1 = new CalendarEvent();
        event1.setId(1L);
        event1.setStartTime(LocalDateTime.now());

        CalendarEvent event2 = new CalendarEvent();
        event2.setId(2L);
        event2.setStartTime(LocalDateTime.now().plusDays(1));

        testEvents = Arrays.asList(event1, event2);

        // Setup event response DTOs
        CalendarEventResponseDTO eventResponse1 = new CalendarEventResponseDTO();
        eventResponse1.setId(1L);

        CalendarEventResponseDTO eventResponse2 = new CalendarEventResponseDTO();
        eventResponse2.setId(2L);

        testEventResponseDtos = Arrays.asList(eventResponse1, eventResponse2);
    }

    @Test
    void createUser_PasswordsMatch_ReturnsUserResponseDto() {
        // Arrange
        when(userMapper.toEntity(any(UserRegistrationDto.class))).thenReturn(testUser);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toDto(any(User.class))).thenReturn(testResponseDto);

        // Act
        UserResponseDto result = userService.createUser(testRegistrationDto);

        // Assert
        assertNotNull(result);
        assertEquals(testResponseDto, result);
        verify(userMapper).toEntity(testRegistrationDto);
        verify(passwordEncoder).encode(testUser.getPassword());
        verify(userRepository).save(testUser);
        verify(userMapper).toDto(testUser);
    }

    @Test
    void createUser_PasswordsDoNotMatch_ThrowsIllegalArgumentException() {
        // Arrange
        testRegistrationDto.setConfirmPassword("differentPassword");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.createUser(testRegistrationDto)
        );

        assertEquals("Password and Confirm password do not match", exception.getMessage());
        verifyNoInteractions(userRepository, passwordEncoder);
    }

    @Test
    void updateUser_ExistingUser_ReturnsUpdatedUserResponseDto() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        doNothing().when(userMapper).updateEntityFromDTO(any(UserRegistrationDto.class), any(User.class));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toDto(any(User.class))).thenReturn(testResponseDto);

        // Act
        UserResponseDto result = userService.updateUser(1L, testRegistrationDto);

        // Assert
        assertNotNull(result);
        assertEquals(testResponseDto, result);
        verify(userRepository).findById(1L);
        verify(userMapper).updateEntityFromDTO(testRegistrationDto, testUser);
        verify(userRepository).save(testUser);
        verify(userMapper).toDto(testUser);
    }

    @Test
    void updateUser_NonExistingUser_ThrowsResourceNotFoundException() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.updateUser(1L, testRegistrationDto)
        );

        assertEquals("User not found with id: 1", exception.getMessage());
        verify(userRepository).findById(1L);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userMapper);
    }

    @Test
    void deleteUser_ExistingUser_DeletesUser() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).delete(any(User.class));

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository).findById(1L);
        verify(userRepository).delete(testUser);
    }

    @Test
    void deleteUser_NonExistingUser_ThrowsResourceNotFoundException() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.deleteUser(1L)
        );

        assertEquals("User not found with id: 1", exception.getMessage());
        verify(userRepository).findById(1L);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void getUser_ExistingUser_ReturnsUserResponseDto() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(userMapper.toDto(any(User.class))).thenReturn(testResponseDto);

        // Act
        UserResponseDto result = userService.getUser(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testResponseDto, result);
        verify(userRepository).findById(1L);
        verify(userMapper).toDto(testUser);
    }

    @Test
    void getUser_NonExistingUser_ThrowsResourceNotFoundException() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.getUser(1L)
        );

        assertEquals("User not found with id: 1", exception.getMessage());
        verify(userRepository).findById(1L);
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(userMapper);
    }

    @Test
    void getAllUsers_ReturnsAllUserResponseDtos() {
        // Arrange
        when(userRepository.findAll()).thenReturn(testUsers);
        when(userMapper.toDto(testUsers.get(0))).thenReturn(testResponseDtos.get(0));
        when(userMapper.toDto(testUsers.get(1))).thenReturn(testResponseDtos.get(1));

        // Act
        List<UserResponseDto> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testResponseDtos, result);
        verify(userRepository).findAll();
        verify(userMapper, times(2)).toDto(any(User.class));
    }

    @Test
    void getAllUsers_NoUsers_ReturnsEmptyList() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<UserResponseDto> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository).findAll();
        verifyNoInteractions(userMapper);
    }

    @Test
    void getCurrentUserDto_ReturnsCurrentUserResponseDto() {
        // Arrange
        when(currentUser.getCurrentUser()).thenReturn(testUser);
        when(userMapper.toDto(any(User.class))).thenReturn(testResponseDto);

        // Act
        UserResponseDto result = userService.getCurrentUserDto();

        // Assert
        assertNotNull(result);
        assertEquals(testResponseDto, result);
        verify(currentUser).getCurrentUser();
        verify(userMapper).toDto(testUser);
    }

    @Test
    void getUserEvents_ReturnsUserEventsSorted() {
        // Arrange
        testUser.setEvents(Collections.singletonList(testEvents.get(0)));
        testUser.setOrganizedEvents(Collections.singletonList(testEvents.get(1)));

        when(currentUser.getCurrentUser()).thenReturn(testUser);
        when(calendarEventMapper.toResponseDTO(testEvents.get(0))).thenReturn(testEventResponseDtos.get(0));
        when(calendarEventMapper.toResponseDTO(testEvents.get(1))).thenReturn(testEventResponseDtos.get(1));

        // Act
        List<CalendarEventResponseDTO> result = userService.getUserEvents();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(currentUser).getCurrentUser();
        verify(calendarEventMapper, times(2)).toResponseDTO(any(CalendarEvent.class));
    }

    @Test
    void getUserEvents_NoEvents_ReturnsEmptyList() {
        // Arrange
        testUser.setEvents(Collections.emptyList());
        testUser.setOrganizedEvents(Collections.emptyList());
        when(currentUser.getCurrentUser()).thenReturn(testUser);

        // Act
        List<CalendarEventResponseDTO> result = userService.getUserEvents();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(currentUser).getCurrentUser();
        verifyNoInteractions(calendarEventMapper);
    }
}
