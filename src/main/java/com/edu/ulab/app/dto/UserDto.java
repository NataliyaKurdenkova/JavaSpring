package com.edu.ulab.app.dto;

import lombok.*;

import java.util.Objects;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String fullName;
    private String title;
    private int age;



}