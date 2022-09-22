package com.edu.ulab.app.service;
import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.UserWithBooks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserWithBooksService {
    UserWithBooks createJoinUserAndBooks(Long idUser, List<Long> idBooks);
    UserWithBooks updateJoinUserAndBooks(Long idUser, List<Long>  idBook);
    void deleteJoinUserAndBooks(UserWithBooks userWithBooks);
    UserWithBooks getUserWithBook(Long id);

}
