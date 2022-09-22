package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.entity.UserWithBooks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageNew {
    public Map<Long, UserEntity> users=new HashMap<>();

    public Map<Long, BookEntity> books=new HashMap<>();

    public Map<Long, UserWithBooks> userWithBooks=new HashMap<>();

}
