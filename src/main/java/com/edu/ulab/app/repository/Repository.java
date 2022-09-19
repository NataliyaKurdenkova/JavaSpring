package com.edu.ulab.app.repository;

import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.entity.UserWithBooks;

import java.util.List;


public interface Repository {
    UserEntity findUserById(Long id);

    UserEntity findUser(UserEntity userEntity);

    UserWithBooks findUserWithBooks(Long id);

    BookEntity findBookById(Long id);

    BookEntity findBook(BookEntity book);

    Boolean saveUser(UserEntity user);

    Boolean updateUser(UserEntity user);

    Boolean removeUser(UserEntity user);


    Boolean saveBook(BookEntity book);

    Boolean removeBook(BookEntity book);

    Boolean saveUserWithBooks(UserWithBooks userWithBooks);
    Boolean saveUserWithBooks(Long idUser,List<Long> idListBooks);

    Boolean removeUserWithBooks(UserWithBooks userWithBooks);

    List<Long> findBookIdForUserById(Long idUser);

    List<BookEntity> findBookForUserById(Long idUser);
}
