package com.example.jdbc.repository;

import com.example.jdbc.test.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JdbcTemplateTest {
    @Autowired
    private JdbcTemplate baseJdbcTemplate;

    @Autowired
    private NamedParameterJdbcOperations baseNamedParameterJdbcOperations;

    private static final RowMapper<Student> ROW_MAPPER = new BeanPropertyRowMapper<>(Student.class);

    @Test
    @Transactional
    public void insertDataTest() {
        baseJdbcTemplate.execute("INSERT INTO student(first_name,last_name, year) VALUES('Victor', 'Hugo', 1999)");
//        baseJdbcTemplate.execute("INSERT INTO student(first_name,last_name, year) VALUES('Dante', 'Alighieri', 2002)");
//        baseJdbcTemplate.execute("INSERT INTO student(first_name,last_name, year) VALUES('Stefan', 'Zweig', 2005)");
//        baseJdbcTemplate.execute("INSERT INTO student(first_name,last_name, year) VALUES('Oscar', 'Wilde', 2011)");


        // todo: 도메인 객체에 필드에 set메소드가 없을 경우 객체에 주입이 되지 않음.
        List<Student> studentList = baseNamedParameterJdbcOperations.query(
                "SELECT student_id, first_name, last_name, year FROM student WHERE last_name = :name ORDER BY student_id",
                new MapSqlParameterSource("name", "Hugo"), ROW_MAPPER);

        assertThat(studentList.size()).isNotEqualTo(0);
        assertThat(studentList.get(0).getFirstName()).isEqualTo("Victor");
    }
}
