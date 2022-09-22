package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.entity.UserWithBooks;
import com.edu.ulab.app.repository.impl.UserWithBooksRepositoryImpl;
import com.edu.ulab.app.service.UserWithBooksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class UserWithBooksServiceImpl implements UserWithBooksService {

    UserWithBooksRepositoryImpl repository = new UserWithBooksRepositoryImpl();

    @Override
    public UserWithBooks createJoinUserAndBooks(Long idUser, List<Long> idBooks) {
        UserWithBooks userWithBooks = new UserWithBooks();
        userWithBooks.setUserId(idUser);
        userWithBooks.setBooksIdList(idBooks);
        log.info("Create userWithBooks: {}", userWithBooks);
        repository.createUserWithBooks(userWithBooks);
        log.info("Save in repo userWithBooks: {}", userWithBooks);
        return userWithBooks;
    }

    @Override
    public UserWithBooks updateJoinUserAndBooks(Long idUser, List<Long> idBooks) {
        log.info("Update userWithBooks");
        return repository.updateUserWithBooks(idUser, idBooks);

    }

    @Override
    public void deleteJoinUserAndBooks(UserWithBooks userWithBooks) {
        log.info("Delete userWithBooks: {}", userWithBooks);
        repository.removeUserWithBooks(userWithBooks);
    }

    @Override
    public UserWithBooks getUserWithBook(Long id) {
        UserWithBooks userWithBooks = repository.findUserWithBooks(id);
        log.info("Get userWithBooks: {}", userWithBooks);
        return userWithBooks;
    }


}
