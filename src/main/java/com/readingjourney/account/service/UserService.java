package com.readingjourney.account.service;

import com.readingjourney.account.dto.UserDto;
import com.readingjourney.account.entity.Role;
import com.readingjourney.account.entity.User;
import com.readingjourney.account.exception.UserNotFoundException;
import com.readingjourney.account.mapper.UserMapper;
import com.readingjourney.account.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The UserService. This class provides user-related operations such as finding, saving, updating,
 * and deleting users.
 */
@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public Optional<User> findById(long id) {
    return Optional.ofNullable(userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id)));
  }

  /**
   * Saves the User from the given UserDto.
   *
   * @param userDto the UserDto to be saved
   * @return the saved User
   */
  public User save(UserDto userDto) {
    User user = userMapper.toEntity(userDto);
    user.setCreatedAt(LocalDateTime.now());
    user.setRole(Role.USER);
    return userRepository.save(user);
  }

  /**
   * Update a user with the given ID using the information from the UserDto.
   *
   * @param id      the ID of the user to be updated
   * @param userDto the data transfer object containing the updated user information
   * @return the updated user entity
   */
  public User update(long id, UserDto userDto) {
    return userRepository.findById(id).map(user1 -> {
      user1.setFirstName(userDto.getFirstName());
      user1.setLastName(userDto.getLastName());
      user1.setCountry(userDto.getCountry());
      return userRepository.save(user1);
    }).orElseThrow(() -> new UserNotFoundException(id));
  }

  /**
   * Deletes a user by their ID if it exists, otherwise throws an UserNotFoundException.
   *
   * @param id the ID of the user to be deleted
   */
  public void delete(long id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);
    } else {
      throw new UserNotFoundException(id);
    }
  }

  /**
   * Deletes all records in the repository.
   */
  public void deleteAll() {
    userRepository.deleteAll();
  }

  /**
   * A description of the entire Java function.
   *
   * @param email description of parameter
   * @return description of return value
   */
  public User findUserByEmail(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
        .orElseThrow(
            () -> new UsernameNotFoundException(email));
  }

}
