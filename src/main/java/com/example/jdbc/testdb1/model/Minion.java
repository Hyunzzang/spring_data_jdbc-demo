package com.example.jdbc.testdb1.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

/**
 * CREATE TABLE minion
 * (
 *     id             bigint auto_increment PRIMARY KEY,
 *     name           VARCHAR(255),
 *     evil_master    BIGINT
 * );
 */
@Getter
@NoArgsConstructor
public class Minion {
    @Id
    private Long id;
    private String name;
    private AggregateReference<Person, Long> evilMaster;

    @Builder
    public Minion(String name, AggregateReference<Person, Long> evilMaster) {
        this.name = name;
        this.evilMaster = evilMaster;
    }
}
