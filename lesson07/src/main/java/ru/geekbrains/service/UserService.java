package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.dto.UserDto;

import java.util.Optional;

public interface UserService {

    Page<UserDto> getUsersByFilter(Integer page, Integer size, String sort);

    Optional<UserDto> findById(long id);

    UserDto save(UserDto user);

    void deleteById(long id);
}
