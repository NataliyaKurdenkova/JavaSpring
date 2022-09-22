package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserWithBooks;
import com.edu.ulab.app.exception.EntityNotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.impl.UserWithBooksRepositoryImpl;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.service.UserWithBooksService;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacadeNew {

    public static UserWithBooksRepositoryImpl repo = new UserWithBooksRepositoryImpl();

    private final UserService userService;
    private final BookService bookService;
    private final UserWithBooksService userWithBooksService;

    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacadeNew(UserService userService,
                             BookService bookService,
                             UserWithBooksService userWithBooksService, UserMapper userMapper,
                             BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userWithBooksService = userWithBooksService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);
        if (userDto == null) throw new EntityNotFoundException("UserDto equals null. UserDto can't be null ");

        UserDto createdUser = userService.createUser(userDto);

        log.info("Created user: {}", createdUser);

        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        UserWithBooks userWithBooks = userWithBooksService.createJoinUserAndBooks(createdUser.getId(), bookIdList);
        log.info("Created userWithBooks: {}", userWithBooks);


        return UserBookResponse.builder()
                .userId(userWithBooks.getUserId())
                .booksIdList(userWithBooks.getBooksIdList())
                .build();
    }

    public UserBookResponse updateUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);

        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        List<BookDto> updBookDto = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .toList();

        UserDto updatedUserOld;

        UserWithBooks userWithBooks;

        log.info("Get user in BD");
        updatedUserOld = userService.getUser(userDto);

        if (updatedUserOld==null) {
            return createUserWithBooks(userBookRequest);
        }

        userService.updateUser(updatedUserOld);
        log.info("Update user: {}", updatedUserOld);

        List<BookDto> bookDtosOld = bookService.getListBooks(updatedUserOld.getId());

        if (bookDtosOld.equals(null)) new EntityNotFoundException("bookDtosOld not found");

        List<BookDto> bookDtos = bookService.updateBook(updBookDto, bookDtosOld, updatedUserOld.getId());

        log.info("Update list books: {}", updBookDto);

        userWithBooks = userWithBooksService.updateJoinUserAndBooks(updatedUserOld.getId(), bookDtos
                .stream()
                .map(BookDto::getId)
                .toList());
        log.info("Update userWithBooks: {}", userWithBooks);

        return UserBookResponse.builder()
                .userId(userWithBooks.getUserId())
                .booksIdList(userWithBooks.getBooksIdList())
                .build();
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        log.info("get UserWithBooks by id: {}", userId);
        UserWithBooks userWithBooks = userWithBooksService.getUserWithBook(userId);
        if (userWithBooks==null) new EntityNotFoundException("userWithBooks not found");

        return UserBookResponse.builder()
                .userId(userWithBooks.getUserId())
                .booksIdList(userWithBooks.getBooksIdList())
                .build();

    }

    public void deleteUserWithBooks(Long userId) {
        log.info("delete UserWithBooks by id: {}", userId);
        UserWithBooks userWithBooks = userWithBooksService.getUserWithBook(userId);
        log.info("get UserWithBooks by id: {}", userWithBooks);
        if (userWithBooks==null) new EntityNotFoundException("userWithBooks not found");
        userService.deleteUserById(userWithBooks.getUserId());
        log.info("delete UserWithBooks by id: {}", userId);
        bookService.deleteBookById(userWithBooks.getBooksIdList());
        log.info("delete Books");
        userWithBooksService.deleteJoinUserAndBooks(userWithBooks);
        log.info("delete UserWithBooks");

    }

}
