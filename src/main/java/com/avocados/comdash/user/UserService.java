package com.avocados.comdash.user;

import com.avocados.comdash.calendar.CalendarEventMapper;
import com.avocados.comdash.calendar.dto.CalendarEventResponseDTO;
import com.avocados.comdash.config.CurrentUser;
import com.avocados.comdash.exception.ResourceNotFoundException;
import com.avocados.comdash.model.entity.CalendarEvent;
import com.avocados.comdash.model.entity.User;
import com.avocados.comdash.user.dto.UserRegistrationDto;
import com.avocados.comdash.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String USER_NOT_FOUND = "User not found with id: ";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CalendarEventMapper calendarEventMapper;
    private final CurrentUser currentUser;

    public UserResponseDto createUser(@NonNull UserRegistrationDto userRegistrationDto) {
        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and Confirm password do not match");
        }

        User user = userMapper.toEntity(userRegistrationDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    public UserResponseDto updateUser(@NonNull Long id, @NonNull UserRegistrationDto userRegistrationDto) {
        User user = findUserById(id);
        userMapper.updateEntityFromDTO(userRegistrationDto, user);
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    public void deleteUser(@NonNull Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    public UserResponseDto getUser(@NonNull Long id) {
        User user = findUserById(id);
        return userMapper.toDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
            .map(userMapper::toDto)
            .toList();
    }

    public List<UserResponseDto> getAllUsersExceptCurrent() {
        List<UserResponseDto> users = getAllUsers();
        return users.stream()
                .filter(dto -> !dto.getId().equals(currentUser.getCurrentUser().getId()))
                .toList();
    }

    public UserResponseDto getCurrentUserDto() {
        return userMapper.toDto(currentUser.getCurrentUser());
    }

    public List<CalendarEventResponseDTO> getUserEvents(Long id) {
        User user = findUserById(id);

        return Stream.concat(user.getEvents().stream(), user.getOrganizedEvents().stream())
                .sorted(Comparator.comparing(CalendarEvent::getStartTime))
                .map(calendarEventMapper::toResponseDTO)
                .toList();
    }

    private User findUserById(@NonNull Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + id));
    }
}
