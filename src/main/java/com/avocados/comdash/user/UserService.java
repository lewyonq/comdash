package com.avocados.comdash.user;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.avocados.comdash.exception.ResourceNotFoundException;
import com.avocados.comdash.model.entity.User;
import com.avocados.comdash.user.dto.UserRegistrationDto;
import com.avocados.comdash.user.dto.UserResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String USER_NOT_FOUND = "User not found with id: %d";

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto createUser(@NonNull UserRegistrationDto userRegistrationDto) {
        User user = userMapper.toEntity(userRegistrationDto);
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

    private User findUserById(@NonNull Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
    }
}
