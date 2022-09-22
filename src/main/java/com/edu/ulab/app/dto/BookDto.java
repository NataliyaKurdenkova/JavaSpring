package com.edu.ulab.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    @EqualsAndHashCode.Exclude
    private Long id;
    @EqualsAndHashCode.Exclude
    private Long userId;
    private String title;
    private String author;
    private long pageCount;
}