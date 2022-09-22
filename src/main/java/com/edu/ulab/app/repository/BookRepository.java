package com.edu.ulab.app.repository;

import com.edu.ulab.app.entity.BookEntity;

public interface BookRepository {
    BookEntity findBookById(Long id);


    BookEntity saveBook(BookEntity book);

    BookEntity removeBook(BookEntity book);

    BookEntity updateBook(BookEntity book);
}
