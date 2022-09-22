package com.edu.ulab.app.repository;

import com.edu.ulab.app.entity.UserWithBooks;

import java.util.List;

public interface UserWithBooksRepository {

    UserWithBooks createUserWithBooks(Long id, List<Long> idBooks);
    UserWithBooks createUserWithBooks(UserWithBooks userWithBooks);

    UserWithBooks findUserWithBooks(Long id);
    UserWithBooks updateUserWithBooks(Long id, List<Long> idBooks);

    void removeUserWithBooks(UserWithBooks userWithBooks);

}
