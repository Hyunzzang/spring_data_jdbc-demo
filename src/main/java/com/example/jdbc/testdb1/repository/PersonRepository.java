package com.example.jdbc.testdb1.repository;

import com.example.jdbc.testdb1.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {
}
