package com.mavidev.task.bootstrap;

import com.mavidev.task.dto.PersonDto;
import com.mavidev.task.entity.Person;
import com.mavidev.task.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonLoader implements CommandLineRunner {

    @Autowired
    private final PersonMapper personMapper;

    @Override
    public void run(String... args) throws Exception {
        log.info("BOOTSTRAP IS WORKING");
    onLoad();
    }

    void onLoad(){

        Person person = Person.builder()
                .name("Beker")
                .surname("Igdir")
                .creationDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        PersonDto personDto = personMapper.personToDto(person);

        log.info(personDto.getCreationTime().toString());

    }

}
