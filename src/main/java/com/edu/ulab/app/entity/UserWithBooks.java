package com.edu.ulab.app.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserWithBooks {
    private Long userId;
    private List<Long> booksIdList;

    public UserWithBooks(Long userId, List<Long> booksIdList) {
        this.userId = userId;
        this.booksIdList = booksIdList;
    }

    public UserWithBooks() {
    }
}
