package com.edu.ulab.app.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class UserDto {
    private Long id;
    private String fullName;
    private String title;
    private int age;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return age == userDto.age
                && Objects.equals(fullName, userDto.fullName)
                && Objects.equals(title, userDto.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash( fullName, title, age);
    }



}