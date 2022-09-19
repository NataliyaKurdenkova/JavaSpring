package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserWithBooks;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.impl.RepositoryImpl;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacade {
    public static RepositoryImpl repository = new RepositoryImpl();
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

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

        UserWithBooks userWithBooks = userService.createJoinUserAndBooks(createdUser.getId(), bookIdList);
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
        UserDto updatedUser;
        List<BookDto> bookDtosOld = new ArrayList<>();
        try {
            updatedUser = userService.getUser(userDto);
        } catch (NullPointerException e) {
            return createUserWithBooks(userBookRequest);
        }

        List<BookDto> updBookDto = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .toList();


        userService.updateUser(updatedUser);
        log.info("Update user: {}", updatedUser);

        try {
            bookDtosOld = bookService.getListBooks(updatedUser.getId());

        } catch (NullPointerException e) {
            log.info(e.getMessage());

        }

        bookService.updateBook(bookDtosOld, updBookDto, updatedUser.getId());
        List<BookDto> list = bookService.getListBooks(updatedUser.getId());

        log.info("Update Books: {}", updBookDto);
        return UserBookResponse.builder()
                .userId(updatedUser.getId())
                .booksIdList(list.stream()
                        .map(BookDto::getId)
                        .toList())
                .build();


    }

    public UserBookResponse getUserWithBooks(Long userId) {
        UserWithBooks userWithBooks=new UserWithBooks();
        try {
            userWithBooks = userService.getUserWithBook(userId);
        }catch (NullPointerException e){
            log.info("userWithBooks not find");
            log.info(e.getMessage());
        }

            return UserBookResponse.builder()
                    .userId(userWithBooks.getUserId())
                    .booksIdList(userWithBooks.getBooksIdList())
                    .build();

    }

    public void deleteUserWithBooks(Long userId) {
        UserWithBooks userWithBooks = userService.getUserWithBook(userId);
        userService.deleteUserById(userWithBooks.getUserId());
        bookService.deleteBookById(userWithBooks.getBooksIdList());
        userService.deleteJoinUserAndBooks(userWithBooks);

    }
}
