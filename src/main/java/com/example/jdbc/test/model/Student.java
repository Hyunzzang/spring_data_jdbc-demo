package com.example.jdbc.test.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * create table student
 * (
 *     student_id bigint auto_increment,
 *     first_name varchar(20) null,
 *     last_name  varchar(20) null,
 *     year       int         null,
 *     constraint student_id
 *         unique (student_id)
 * );
 */
@Getter
@Setter
@NoArgsConstructor
public class Student {
    @Id
    private Long studentId;
    private String firstName;
    private String lastName;
    private Integer year;

    @Builder
    public Student(String firstName, String lastName, Integer year) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;
    }
}
