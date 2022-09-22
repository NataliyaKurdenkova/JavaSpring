package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.impl.UserWithBooksRepositoryImpl;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BookServiceNewImpl implements BookService {

    private final BookMapper bookMapper;

    public BookServiceNewImpl(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    UserWithBooksRepositoryImpl repository = new UserWithBooksRepositoryImpl();

    @Override
    public BookDto createBook(BookDto bookDto) {
        log.info("Create book: {}", bookDto);
        BookEntity book = bookMapper.bookDtoToBookEntity(bookDto);
        log.info("Mapped book dto: {}", book);
        repository.saveBook(book);
        log.info("Save book");
        return bookMapper.bookEntityToBookDto(book);
    }

    @Override
    public List<BookDto> updateBook(List<BookDto> listBooksDtoNew, List<BookDto> listBooksDtoOld, Long idUser) {

        List<BookDto> listBooksNew = new ArrayList<>();

        for (BookDto book : listBooksDtoNew) {
            BookEntity bookEntity = bookMapper.bookDtoToBookEntity(book);
            log.info("Mapped book entity: {}", book);
            bookEntity.setUserId(idUser);
            BookDto bookDto = bookMapper.bookEntityToBookDto(repository.updateBook(bookEntity));
            log.info("Mapped book dto: {}", book);

            listBooksNew.add(bookDto);
            log.info("Update book: {}", book);
        }

        log.info("Update list book: {}", listBooksNew);
        return listBooksNew;
    }


    @Override
    public List<BookDto> getListBooks(Long id) {
        log.info("Get list books by id: {}", id);
        List<BookEntity> list = repository.getAllBookIdByUserId(id);

        List<BookDto> listDto = new ArrayList<>();
        for (BookEntity bookEntity : list) {
            listDto.add(bookMapper.bookEntityToBookDto(bookEntity));
            log.info("Mapped book dto: {}", bookEntity);
        }
        return listDto;
    }

    @Override
    public void deleteBookById(List<Long> idBooks) {
        log.info("delete book list");
        for (Long b : idBooks) {
            BookEntity book = repository.findBookById(b);
            log.info("Get book: {}", book);
            repository.removeBook(book);
            log.info("Delete book: {}", book);
        }
    }
}

