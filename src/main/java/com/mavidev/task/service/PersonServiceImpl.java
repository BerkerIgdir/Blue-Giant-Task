package com.mavidev.task.service;

import com.mavidev.task.dto.PersonDto;
import com.mavidev.task.entity.Person;
import com.mavidev.task.exception.PersonNotFoundException;
import com.mavidev.task.mapper.PersonMapper;
import com.mavidev.task.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//A service layer, which is designed to meet the needs of a restful endpoint.
//Not every method will be used in controller layer.

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    @Autowired
    private final PersonRepository personRepository;
    @Autowired
    private final PersonMapper personMapper;

    @Override
    public PersonDto getById(UUID uuid) throws PersonNotFoundException {
        return personMapper.personToDto(
                personRepository
                        .findById(uuid)
                        .orElseThrow(PersonNotFoundException::new));}

    @Override
    public PersonDto getByNameAndSurname(String name, String surname) {
        return personMapper.personToDto(personRepository
                .findByNameAndSurname(name,surname)
                .orElseThrow(PersonNotFoundException::new));
    }

    @Override
    public Page<PersonDto> getAllByName(String name, Pageable pageable) {

        List<PersonDto> personDtoList = personRepository
                .findAllByName(name)
                .stream()
                .map(personMapper::personToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(personDtoList,pageable,personDtoList.size());
    }

    @Override
    public Page<PersonDto> getAllBySurname(String surname, Pageable pageable) {


        List<PersonDto> personDtoList = personRepository
                .findAllBySurname(surname)
                .stream()
                .map(personMapper::personToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(personDtoList,pageable,personDtoList.size());
    }

    @Override
    public Page<PersonDto> getAll(Pageable pageable) {

        List<PersonDto> personDtoList = personRepository.findAll()
                .stream()
                .map(personMapper::personToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(personDtoList,pageable,personDtoList.size());
    }

    @Override
    public void deleteById(UUID id) {
        personRepository.deleteById(id);
    }

    @Override
    public PersonDto save(Person newPerson) { return personMapper.personToDto(personRepository.save(newPerson)); }
}
