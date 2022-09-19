package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserWithBooks;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    UserDto getUserById(Long id);
    UserDto getUser(UserDto userDto);

    UserWithBooks getUserWithBook(Long id);

    void deleteUserById(Long id);

    UserWithBooks createJoinUserAndBooks(Long idUser, List<Long> idBooks);
    UserWithBooks updateJoinUserAndBooks(Long idUser, Long idBook);
    void deleteJoinUserAndBooks(UserWithBooks userWithBooks);
}
