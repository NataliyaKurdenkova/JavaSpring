package com.edu.ulab.app.service;


import com.edu.ulab.app.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto userDto);

    List<BookDto> updateBook(List<BookDto> BooksDtoNew, List<BookDto> BooksDtoOld, Long id);

    //BookDto getBookById(Long id);

    List<BookDto> getListBooks(Long id);

    void deleteBookById(List<Long> idBooks);
}