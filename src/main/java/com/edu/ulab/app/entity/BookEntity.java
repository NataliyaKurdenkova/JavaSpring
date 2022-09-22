package com.edu.ulab.app.entity;

import lombok.*;

import java.util.Objects;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {
    @EqualsAndHashCode.Exclude
    private Long id;
    @EqualsAndHashCode.Exclude
    private Long userId;
    private String title;
    private String author;
    private long pageCount;


}
