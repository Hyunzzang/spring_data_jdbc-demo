package com.example.jdbc.repository;

import com.example.jdbc.test.model.Student;
import com.example.jdbc.test.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@DataJdbcTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    @Transactional
    public void saveAndFindByLastName_test() {
        Student student = Student.builder()
                .firstName("h2")
                .lastName("lee")
                .year(2001)
                .build();
        studentRepository.save(student);

        List<Student> resStudentList = studentRepository.findByLastName("lee");

        assertThat(resStudentList.size()).isNotEqualTo(0);
        assertThat(resStudentList.get(0).getFirstName()).isEqualTo("h2");

        studentRepository.deleteById(student.getStudentId());
    }

    @Test
    public void updateByFirstName_Test() {
        Student student = Student.builder()
                .firstName("h3")
                .lastName("kim")
                .year(2011)
                .build();
        studentRepository.save(student);

        List<Student> resStudentList = studentRepository.findByLastName("park");

        assertThat(resStudentList.size()).isNotEqualTo(0);
        assertThat(resStudentList.get(0).getFirstName()).isEqualTo("h3");

        studentRepository.updateByFirstName(student.getStudentId(), "l5");
        List<Student> updateStudentList = studentRepository.findByLastName("kim");

        assertThat(updateStudentList.size()).isNotEqualTo(0);
        assertThat(updateStudentList.get(0).getFirstName()).isEqualTo("l5");

        studentRepository.deleteById(student.getStudentId());
    }
}
