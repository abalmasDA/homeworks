package com.readingjourney.account.mapper;

import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.entity.User;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between User and UserDto objects.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

  User toEntity(UserDto userDto);

  UserDto toDto(User user);

}
