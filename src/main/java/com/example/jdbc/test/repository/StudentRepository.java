package com.example.jdbc.test.repository;

import com.example.jdbc.test.model.Student;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long> {
    List<Student> findByLastName(@Param("lName") String lastName);

    List<Student> findByLastNameIgnoreCase(@Param("lName") String lastName);

    @Modifying
    @Query("UPDATE student SET first_name = :name WHERE student_id = :id")
    boolean updateByFirstName(@Param("id") Long id, @Param("name") String name);
}
