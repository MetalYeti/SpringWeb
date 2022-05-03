package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.UserDto;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public Page<UserDto> getUsersByFilter(Integer page, Integer size, String sort) {
        return userRepository.findAll(PageRequest.of(page, size, Sort.by(sort))).map(UserServiceImpl::getMapper);
    }

    @Override
    public Optional<UserDto> findById(long id) {
        return userRepository.findById(id).map(UserServiceImpl::getMapper);
    }

    @Override
    public UserDto save(UserDto user) {
        return getMapper(userRepository.save(new User(user.getId(), user.getName(), user.getEmail(), encoder.encode(user.getPassword()), user.getRoles())));
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    private static UserDto getMapper(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), null, user.getRoles());
    }
}
