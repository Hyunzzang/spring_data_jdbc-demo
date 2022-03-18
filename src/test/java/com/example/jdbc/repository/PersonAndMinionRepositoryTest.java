package com.example.jdbc.repository;

import com.example.jdbc.testdb1.model.Minion;
import com.example.jdbc.testdb1.model.Person;
import com.example.jdbc.testdb1.repository.MinionRepository;
import com.example.jdbc.testdb1.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PersonAndMinionRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MinionRepository minionRepository;

    @Test
    public void aggregateReferenceTest() {
        AggregateReference<Person, Long> scarletReference = AggregateReference.to(personRepository.save(new Person("Scarlet")).getId());

        Minion bob = minionRepository.save(new Minion("Bob", scarletReference));
        Minion kevin = minionRepository.save(new Minion("Kevin", scarletReference));
        Minion stuart = minionRepository.save(new Minion("Stuart", scarletReference));

        AggregateReference<Person, Long> gruReference = AggregateReference.to(personRepository.save(new Person("Gru")).getId());
        Minion tim = minionRepository.save(new Minion("Tim", gruReference));

        Collection<Minion> minionsOfScarlet = minionRepository.findByEvilMaster(scarletReference.getId());

        assertThat(minionsOfScarlet).extracting(m -> m.getName()).containsExactlyInAnyOrder("Bob", "Kevin", "Stuart");
    }


}
