package com.example.jdbc.testdb1.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * CREATE TABLE person
 * (
 *     id   bigint auto_increment PRIMARY KEY,
 *     name VARCHAR(255)
 * );
 */
@Getter
@NoArgsConstructor
public class Person {
    @Id
    private Long id;
    private String name;

    @Builder
    public Person(String name) {
        this.name = name;
    }
}
