package com.avocados.comdash.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.avocados.comdash.model.entity.User;
import com.avocados.comdash.user.dto.UserRegistrationDto;
import com.avocados.comdash.user.dto.UserResponseDto;

@Mapper(componentModel="spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    
    UserResponseDto toDto(User user);

    User toEntity(UserRegistrationDto userDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(UserRegistrationDto dto, @MappingTarget User entity);
}