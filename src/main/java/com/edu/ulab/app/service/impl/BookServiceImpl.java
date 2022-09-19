package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.facade.UserDataFacade;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.impl.RepositoryImpl;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.edu.ulab.app.facade.UserDataFacade.repository;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    RepositoryImpl repo = UserDataFacade.repository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        bookDto.setId(idGen());
        BookEntity book = bookMapper.bookDtoToBookEntity(bookDto);
        log.info("Mapped book dto: {}", book);
        repository.saveBook(book);
        log.info("Save book");
        return bookDto;
    }

    @Override
    public void updateBook(List<BookDto> oldBooksDto, List<BookDto> newBooksDto, Long idUser) {
        for (BookDto bookOld : oldBooksDto) {
            repo.removeBook(bookMapper.bookDtoToBookEntity(bookOld));
        }
        List<Long> list=new ArrayList<>();
        for (BookDto newList : newBooksDto) {
            newList.setId(idGen());
            newList.setUserId(idUser);
            repo.saveBook(bookMapper.bookDtoToBookEntity(newList));
            list.add(newList.getId());
        }
        repo.saveUserWithBooks(idUser,list);

    }


    @Override
    public List<BookDto> getListBooks(Long idUser) {
        List<BookEntity> book = repo.findBookForUserById(idUser);
        List<BookDto> bookDtos = new ArrayList<>();
        for (BookEntity b : book) {
            bookDtos.add(bookMapper.bookEntityToBookDto(b));
        }
        return bookDtos;
    }

    @Override
    public void deleteBookById(List<Long> idBooks) {
        for (long id : idBooks) {
            BookEntity book = repo.findBookById(id);
            log.info("Get book: {}", book);
            repo.removeBook(book);
            log.info("Delete book: {}", book);
        }
    }

    private long idGen() {
        // return new Date().getTime();
        return (long) (Math.random() * 1000000);
    }
}