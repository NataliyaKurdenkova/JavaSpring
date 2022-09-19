package com.edu.ulab.app.service;


import com.edu.ulab.app.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto userDto);

    void updateBook(List<BookDto> oldBooksDto,List<BookDto> newBooksDto,Long idUser);

    //BookDto getBookById(Long id);

    List<BookDto> getListBooks(Long id);

    void deleteBookById(List<Long> idBooks);
}