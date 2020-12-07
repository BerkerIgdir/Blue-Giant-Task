package com.mavidev.task.service;

import com.mavidev.task.dto.PersonDto;
import com.mavidev.task.entity.Person;
import com.mavidev.task.exception.PersonNotFoundException;
import com.mavidev.task.mapper.PersonMapper;
import com.mavidev.task.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


//Unit/Behaviour test with no context interference

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonServiceImpl personService;

    private Person person;

    private Person person1;

    @BeforeEach
    void setUp() {

        person = Person.builder()
                .name("Berker")
                .surname("Igdir")
                .email("berkerigdir93@gmail.com")
                .creationDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        person1 = Person.builder()
                .name("Efe Aras")
                .surname("Igdir")
                .email("berkerigdir93@gmail.com")
                .creationDate(Timestamp.valueOf(LocalDateTime.now()))
                .lastModifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();


    }

    @Test
    void getById() {

        UUID id = UUID.randomUUID();

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        PersonDto personDto = PersonDto.builder()
                .name(person.getName())
                .surname(person.getSurname())
                .email(person.getEmail())
                .creationTime(null)
                .updateTime(null).build();

        when(personMapper.personToDto(person)).thenReturn(personDto);

        assertEquals("Berker",personService.getById(id).name);
        verify(personRepository,times(1)).findById(id);
        verify(personMapper,times(1)).personToDto(person);

    }

    @Test
    void getByIdThrows(){
        given(personRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class,() -> personService.getById(UUID.randomUUID()));

    }



    //getAll getAllByName and getAllBySurname are virtually identical functions, therefore I do not need to test it thrice
    @Test
    void getAllByNameSurnamePage(){

        List<Person> personList = Arrays.asList(person,person1);

        given(personRepository.findAllByName(any())).willReturn(personList);

        PersonDto personDto = PersonDto.builder()
                .name(person.getName())
                .surname(person.getSurname())
                .email(person.getEmail())
                .creationTime(null)
                .updateTime(null).build();

        PersonDto personDto1 = PersonDto.builder()
                .name(person1.getName())
                .surname(person1.getSurname())
                .email(person1.getEmail())
                .creationTime(null)
                .updateTime(null).build();

        given(personMapper.personToDto(person)).willReturn(personDto);
        given(personMapper.personToDto(person1)).willReturn(personDto1);

        assertEquals(2, personService.getAllByName("name",PageRequest.of(1,2)).getSize());

        verify(personRepository,times(1)).findAllByName("name");
        verify(personMapper,times(1)).personToDto(person);
        verify(personMapper,times(1)).personToDto(person1);

    }

    @Test
    void delete(){

        personService.deleteById(UUID.randomUUID());

        verify(personRepository,times(1)).deleteById(any(UUID.class));

    }

    @Test
    void save(){

        given(personRepository.save(any(Person.class))).willReturn(person1);

        personService.save(person);

        verify(personRepository,times(1)).save(person);
        verify(personMapper,times(1)).personToDto(person1);

    }
}