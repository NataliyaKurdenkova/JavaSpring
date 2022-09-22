package com.edu.ulab.app.repository;

import com.edu.ulab.app.entity.UserEntity;

public interface UserRepository {

    UserEntity findUserById(Long id);
    UserEntity findUser(UserEntity user);

    UserEntity saveUser(UserEntity user);

    UserEntity updateUser(UserEntity user);

    UserEntity removeUser(UserEntity user);



}
