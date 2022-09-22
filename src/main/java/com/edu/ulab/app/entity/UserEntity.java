package com.edu.ulab.app.entity;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String fullName;
    private String title;
    private int age;


}
