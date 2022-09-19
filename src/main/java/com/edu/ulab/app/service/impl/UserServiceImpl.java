package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.entity.UserWithBooks;
import com.edu.ulab.app.facade.UserDataFacade;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.impl.RepositoryImpl;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    RepositoryImpl repo = UserDataFacade.repository;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setId(idGen());
        UserEntity user = userMapper.userDtoToUserEntity(userDto);
        log.info("Mapped user dto in method createUser: {}", user);
        repo.saveUser(user);
        log.info("Save user");
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        UserEntity user = userMapper.userDtoToUserEntity(userDto);
        log.info("Mapped user dto in method updateUser: {}", user);
        UserEntity userRepo = repo.findUser(user);
        if (!userRepo.equals(null)) {
            repo.updateUser(user);
            log.info("Update user");
        } else {
            System.out.println("User not find in BD. Add user");

            repo.saveUser(user);
            log.info("Add user");
        }
        return userDto;
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity user = repo.findUserById(id);
        log.info("Get userEntity: {}", user);
        UserDto userDto = userMapper.userEntityToUserDto(user);
        log.info("Mapped user in method getUserByID: {}", userDto);
        return userDto;
    }

    @Override
    public UserDto getUser(UserDto userDto) {
        UserEntity userEntity = repo.findUser(userMapper.userDtoToUserEntity(userDto));

        if (userEntity.equals(null)) {
            log.info("object not find");
        }
        return userMapper.userEntityToUserDto(userEntity);

    }

    @Override
    public UserWithBooks getUserWithBook(Long id) {
        UserWithBooks userWithBooks = repo.findUserWithBooks(id);
        log.info("Get userWithBooks: {}", userWithBooks);
        return userWithBooks;
    }

    @Override
    public void deleteUserById(Long id) {
        UserEntity user = repo.findUserById(id);
        log.info("Get user: {}", user);
        repo.removeUser(user);
        log.info("Delete user: {}", user);
    }

    public UserWithBooks createJoinUserAndBooks(Long idUser, List<Long> idListBooks) {
        UserWithBooks userWithBooks = new UserWithBooks(idUser, idListBooks);
        log.info("Create userWithBooks: {}", userWithBooks);
        repo.saveUserWithBooks(userWithBooks);
        log.info("Save in repo userWithBooks: {}", userWithBooks);
        return userWithBooks;
    }

    @Override
    public UserWithBooks updateJoinUserAndBooks(Long idUser, Long idBookDto) {
        UserWithBooks userWithBooks = repo.findUserWithBooks(idUser);
        List<Long> listBook = new ArrayList<>();
        listBook.addAll(userWithBooks.getBooksIdList());
        listBook.add(idBookDto);
        repo.removeUserWithBooks(userWithBooks);
        repo.saveUserWithBooks(idUser, listBook);
        userWithBooks = repo.findUserWithBooks(idUser);
        return userWithBooks;
    }

    @Override
    public void deleteJoinUserAndBooks(UserWithBooks userWithBooks) {
        repo.removeUserWithBooks(userWithBooks);
    }


    private long idGen() {

        return new Date().getTime();
    }
}
