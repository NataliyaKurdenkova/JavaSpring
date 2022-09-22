package com.edu.ulab.app.entity;

import lombok.*;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserWithBooks {
    private Long userId;
    private List<Long> booksIdList;


}
