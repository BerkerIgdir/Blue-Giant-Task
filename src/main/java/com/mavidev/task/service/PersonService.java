package com.mavidev.task.service;

import com.mavidev.task.dto.PersonDto;
import com.mavidev.task.entity.Person;
import com.mavidev.task.exception.PersonNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PersonService {

    PersonDto getById(UUID uuid) throws PersonNotFoundException;

    PersonDto getByNameAndSurname(String name, String surname);

    Page<PersonDto> getAllByName(String name, Pageable pageable);

    Page<PersonDto> getAllBySurname(String surname, Pageable pageable);

    Page<PersonDto> getAll(Pageable pageable);

    void deleteById(UUID id);

    PersonDto save(Person newPerson);

}
