package com.example.jdbc.testdb1.repository;

import com.example.jdbc.testdb1.model.Minion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MinionRepository extends CrudRepository<Minion, Long> {

    public List<Minion> findByEvilMaster(Long id);
}
