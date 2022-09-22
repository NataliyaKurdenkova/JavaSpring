package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.impl.UserWithBooksRepositoryImpl;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class UserServiceNewImpl implements UserService {

    private final UserMapper userMapper;

    UserWithBooksRepositoryImpl repository = new UserWithBooksRepositoryImpl();

    public UserServiceNewImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity user = userMapper.userDtoToUserEntity(userDto);
        log.info("Mapped user dto in method createUser: {}", user);
        repository.saveUser(user);
        log.info("Save user");
        return userMapper.userEntityToUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {

        log.info("Mapped user dto in method updateUser: {}", userDto);
        UserEntity userEntity = userMapper.userDtoToUserEntity(userDto);
        log.info("Update user: {}", userEntity);
        return userMapper.userEntityToUserDto(repository.updateUser(userEntity));

    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Get user by id: {}", id);
        return userMapper.userEntityToUserDto(repository.findUserById(id));
    }


    @Override
    public void deleteUserById(Long id) {
        UserEntity user = repository.findUserById(id);
        log.info("Get user: {}", user);
        repository.removeUser(user);
        log.info("Delete user: {}", user);
    }

    @Override
    public UserDto getUser(UserDto userDto) {
        log.info("Update user: {}", userDto);
        return userMapper.userEntityToUserDto(repository.findUser(userMapper.userDtoToUserEntity(userDto)));
    }

}



