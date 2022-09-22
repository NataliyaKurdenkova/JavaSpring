package com.edu.ulab.app.repository.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.entity.UserWithBooks;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.repository.UserWithBooksRepository;
import com.edu.ulab.app.storage.StorageNew;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class UserWithBooksRepositoryImpl implements UserRepository, BookRepository, UserWithBooksRepository {

    StorageNew storage = new StorageNew();

    @Override
    public BookEntity findBookById(Long id) {
        return storage.books.get(id);
    }

    @Override
    public UserEntity findUser(UserEntity userEntity) {
        log.info("Find User");
        for (UserEntity user : storage.users.values()) {
            if (user.equals(userEntity))
                return user;
        }
        return null;
    }

    @Override
    public BookEntity saveBook(BookEntity book) {
        log.info("save book");
        book.setId(idGen());
        log.info("Generate bookId: {}", book.getId());
        BookEntity bookEntity = createBookEntity(book);
        log.info("Created book: {}", bookEntity);
        storage.books.put(book.getId(), book);
        log.info("Save user by entity: {}", book);
        return bookEntity;
    }

    @Override
    public BookEntity removeBook(BookEntity book) {
        log.info("remove book");
        return storage.books.remove(book);
    }

    @Override
    public BookEntity updateBook(BookEntity book) {
        log.info("Method update");
        return saveBook(book);
    }

    @Override
    public UserEntity findUserById(Long id) {
        log.info("find user by id");
        return storage.users.get(id);
    }


    @Override
    public UserEntity saveUser(UserEntity user) {
        log.info("save user");
        user.setId(idGen());
        log.info("Generate userId: {}", user.getId());
        UserEntity userEntity = createUserEntity(user);
        log.info("Created user: {}", userEntity);
        storage.users.put(user.getId(), user);
        log.info("Save user by entity: {}", user);
        return userEntity;
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        log.info("update user: {}", user);
        return storage.users.put(user.getId(), user);
    }

    @Override
    public UserEntity removeUser(UserEntity user) {
        log.info("remove user: {}", user);
        return storage.users.remove(user);
    }

    public Long idGen() {
        //return new Date().getTime();
        return (long) (Math.random() * 1000000);
    }

    private UserEntity createUserEntity(UserEntity entity) {
        log.info("create userEntity: {}", entity);
        if (entity.equals(null)) return null;
        return UserEntity.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .title(entity.getTitle())
                .age(entity.getAge())
                .build();
    }

    private BookEntity createBookEntity(BookEntity entity) {
        log.info("create bookEntity: {}", entity);
        if (entity.equals(null)) return null;

        return BookEntity.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .pageCount(entity.getPageCount())
                .build();
    }

    @Override
    public UserWithBooks createUserWithBooks(UserWithBooks userWithBooks) {
        storage.userWithBooks.put(userWithBooks.getUserId(), userWithBooks);
        log.info("Create userWithBooks: {}", userWithBooks);
        return userWithBooks;
    }

    public List<BookEntity> getAllBookIdByUserId(Long idUser) {
        log.info("Get all book with idUser: {}", idUser);
        List<BookEntity> list = new ArrayList<>();
        if (idUser != null) {

            for (BookEntity book : storage.books.values()) {
                if (book.getUserId() == idUser) {
                    list.add(book);
                }
            }
            return list;
        }
        log.info("All book is null");
        return null;
    }

    @Override
    public UserWithBooks createUserWithBooks(Long id, List<Long> idBooks) {
        log.info("Create userWithBooks");
//        UserWithBooks userWithBooks = new UserWithBooks(id, idBooks);
//        return storage.userWithBooks.put(id, userWithBooks);
        return createUserWithBooks(id, idBooks);
    }

    @Override
    public UserWithBooks findUserWithBooks(Long id) {
        log.info("Find userWithBooks by id");
        UserWithBooks userWithBooks;
        if (storage.userWithBooks.get(id) != null) {
            userWithBooks = storage.userWithBooks.get(id);
            return userWithBooks;
        }
        return null;
    }

    @Override
    public UserWithBooks updateUserWithBooks(Long id, List<Long> idBooks) {
        log.info("Delete userWithBooks by id");
        storage.userWithBooks.remove(id);

        log.info("Create new userWithBooks");
        UserWithBooks userWithBooks = new UserWithBooks(id, idBooks);

        storage.userWithBooks.put(id, userWithBooks);

        return userWithBooks;
    }

    @Override
    public void removeUserWithBooks(UserWithBooks userWithBooks) {
        log.info("Remove userWithBooks");
        storage.userWithBooks.remove(userWithBooks);
    }
}