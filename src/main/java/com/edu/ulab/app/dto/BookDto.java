package com.edu.ulab.app.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class BookDto {
    private Long id;
    private Long userId;
    private String title;
    private String author;
    private long pageCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return pageCount == bookDto.pageCount
                && Objects.equals(title, bookDto.title)
                && Objects.equals(author, bookDto.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash( title, author, pageCount);
    }
}
