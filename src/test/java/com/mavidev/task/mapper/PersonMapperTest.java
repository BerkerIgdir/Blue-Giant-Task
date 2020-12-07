package com.mavidev.task.mapper;

import com.mavidev.task.dto.PersonDto;
import com.mavidev.task.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PersonMapperTest {

    //A test, in order to test Mapper class' functionality.

    @Autowired
    private PersonMapper personMapper;

    private Person person;

    @BeforeEach
    void setUp() {

        person = Person.builder()
                .name("Berker")
                .surname("Igdir")
                .email("berkerigdir93@gmail.com")
                .creationDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();

    }

    @Test
    void personToDto() {

        PersonDto personDto = personMapper.personToDto(person);

        assertEquals("Berker",personDto.name);
        assertEquals("Igdir",personDto.surname);
        assertEquals("berkerigdir93@gmail.com",personDto.email);
        assertNotNull(personDto.creationTime);
        assertNotNull(personDto.updateTime);

      log.info(personDto.creationTime.toString());

      log.info(personDto.updateTime.toString());

    }

    @Test
    void personDtoToPerson() {

        PersonDto personDto = personMapper.personToDto(person);

        Person newPerson = personMapper.personDtoToPerson(personDto);


        assertEquals("Berker",person.getName());
        assertEquals("Igdir",person.getSurname());
        assertEquals("berkerigdir93@gmail.com",person.getEmail());
        assertNotNull(newPerson.getCreationDate());
        assertNotNull(newPerson.getLastModifiedDate());


    }

}