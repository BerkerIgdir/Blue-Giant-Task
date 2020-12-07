package com.mavidev.task.web.controller;

import com.mavidev.task.dto.PersonDto;
import com.mavidev.task.entity.Person;
import com.mavidev.task.mapper.PersonMapper;
import com.mavidev.task.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest(controllers = PersonController.class)
@ActiveProfiles("test")
class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PersonMapper personMapper;

    @MockBean
    private PersonService personService;


    private Page<PersonDto> personDtoPage;

    PersonDto personDtoToAdd1 = PersonDto.builder()
            .name("Berker")
            .surname("Igdir")
            .email("email@email").build();

    PersonDto personDtoToAdd2 = PersonDto.builder()
            .name("Efe Aras")
            .surname("Igdir")
            .email("email1@email").build();

    @BeforeEach
    void setUp() {

       List<PersonDto> personDtoList = new ArrayList<>();

        personDtoList.add(personDtoToAdd1);
        personDtoList.add(personDtoToAdd2);

        personDtoPage = new PageImpl<>(personDtoList);

    }



    @Test
    void personList() throws Exception {

    given(personService.getAll(any(PageRequest.class))).willReturn(personDtoPage);

    mockMvc.perform(get("/person/list"))
            .andExpect(view().name("PersonTable"))
            .andExpect(model().attribute("people",hasSize(2)));

    }

    @Test
    void personQuery() throws Exception {

        mockMvc.perform(get("/person/query"))
                .andExpect(view().name("QueryForm"))
                .andExpect(model().attribute("queryObject",notNullValue()));

    }

    @Test
    void personSearchByName() throws Exception {
        given(personService.getAllByName(any(String.class),any(PageRequest.class)))
                .willReturn(new PageImpl<>(Arrays.asList(personDtoToAdd2)));

        mockMvc.perform(post("/person/searchByName?name=Efe"))
                .andExpect(view().name("PersonTable"))
                .andExpect(model().attribute("people",hasSize(1)));
    }

    @Test
    void personSearchBySurname() {
    }

    @Test
    void personSearchByNameAndSurname() {
    }

    @Test
    void save() throws Exception {
        given(personService.save(any(Person.class)))
                .willReturn(personDtoToAdd1);

        mockMvc.perform(post("/person/save?name=Efe&surname=Igdir&email=email1@email"))
                .andExpect(redirectedUrl("/person/list"));
    }

    @Test
    void saveWithException() throws Exception {
        when(personService.save(any(Person.class)))
                .thenThrow();

        mockMvc.perform(post("/person/save?name=Efe&surname=Igdir&email=email1@email"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("description","User already exists!"))
                .andExpect(view().name("person-form"));
    }
}