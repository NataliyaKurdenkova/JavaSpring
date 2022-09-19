package com.edu.ulab.app.repository.impl;

import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.entity.UserWithBooks;
import com.edu.ulab.app.repository.Repository;
import com.edu.ulab.app.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class RepositoryImpl implements Repository {
    Storage storage = new Storage();

    @Override
    public UserEntity findUserById(Long id) {
        for (UserEntity user : storage.users) {
            if (user.getId().equals(id))
                return user;
        }
        return null;
    }

    @Override
    public UserEntity findUser(UserEntity userEntity) {
        for (UserEntity user : storage.users) {
            if (user.equals(userEntity))
                return user;
        }
        return null;
    }

    @Override
    public UserWithBooks findUserWithBooks(Long id) {
        for (UserWithBooks user : storage.userWithBooks) {
            if (user.getUserId().equals(id))
                return user;
        }
        return null;
    }

    @Override
    public BookEntity findBookById(Long id) {
        for (BookEntity book : storage.books) {
            if (book.getId().equals(id))
                return book;
        }
        return null;
    }

    @Override
    public BookEntity findBook(BookEntity bookEntity) {
        for (BookEntity book : storage.books) {
            if (book.equals(bookEntity))
                return book;
        }
        return null;
    }



    @Override
    public Boolean saveUser(UserEntity user) {
        return storage.users.add(user);
    }

    @Override
    public Boolean saveUserWithBooks(UserWithBooks userWithBooks) {

        return storage.userWithBooks.add(userWithBooks);
    }

    @Override
    public Boolean saveUserWithBooks(Long idUser, List<Long> idListBooks) {
        UserWithBooks userWithBooks=new UserWithBooks(idUser,idListBooks);
        return saveUserWithBooks(userWithBooks);

    }

    @Override
    public Boolean removeUserWithBooks(UserWithBooks userWithBooks) {
        return storage.userWithBooks.remove(userWithBooks);
    }

    @Override
    public List<Long> findBookIdForUserById(Long idUser) {
        List<Long> listIdBooks = new ArrayList<>();
        for (BookEntity book : storage.books)
            if (book.getUserId().equals(idUser))
                listIdBooks.add(book.getId());
        return listIdBooks;

    }

    @Override
    public Boolean updateUser(UserEntity user) {
        UserEntity userEntity = findUserById(user.getId());
       // List<Long> booksId = findBookIdForUserById(user.getId());
        storage.users.remove(userEntity);
        return storage.users.add(user);
    }

    @Override
    public List<BookEntity> findBookForUserById(Long idUser) {
        List<BookEntity> listBooks = new ArrayList<>();

        for (BookEntity book : storage.books)
            if (book.getUserId().equals(idUser))
                listBooks.add(book);
        return listBooks;
    }

    @Override
    public Boolean saveBook(BookEntity book) {

        return storage.books.add(book);
    }

    @Override
    public Boolean removeBook(BookEntity book) {

        return storage.books.remove(book);
    }

    @Override
    public Boolean removeUser(UserEntity user) {

        return storage.users.remove(user);
    }


}



