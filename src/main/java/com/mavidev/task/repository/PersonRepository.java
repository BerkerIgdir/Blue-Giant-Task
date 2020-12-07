package com.mavidev.task.repository;

import com.mavidev.task.entity.Person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {

    Optional<Person> findById(UUID id);

//    @Query("From Person p ORDER BY p.name DESC")
    List<Person> findAllByName(String name);

//    @Query("From Person p ORDER BY p.surname DESC")
    List<Person> findAllBySurname(String surname);

    Optional<Person> findByNameAndSurname(String name, String surname);

}