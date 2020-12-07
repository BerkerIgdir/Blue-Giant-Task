package com.mavidev.task.web.controller;


import com.mavidev.task.dto.PersonDto;
import com.mavidev.task.exception.PersonNotFoundException;
import com.mavidev.task.mapper.PersonMapper;
import com.mavidev.task.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Controller
@RequiredArgsConstructor
public class PersonController {


    @GetMapping("/")
    public String redirect(){

        return "HomeScreen";
    }

    @Autowired
    private final PersonService personService;

    @Autowired
    private final PersonMapper personMapper;

    @GetMapping("/main")
    public String mainPage(){

        return "redirect:/";
    }

    @GetMapping("/person/form")
    public String saveForm(ModelMap map){

        if(map.getAttribute("description") == null)
            map.addAttribute("description","Please enter your informations");

        return "person-form";
    }

    @GetMapping("/person/list")
    public String personList(Model theModel){

        List<PersonDto> personDtoList = personService.getAll(PageRequest.of(1,20)).stream().collect(Collectors.toList());


        theModel.addAttribute("people",personDtoList);

        return "PersonTable";
    }

    @GetMapping("/person/query")
    public String personQuery(Model theModel){

        theModel.addAttribute("queryObject",new QueryClass());
        theModel.addAttribute("surnameDescription","");
        theModel.addAttribute("nameDescription","");
        theModel.addAttribute("combinedDescription","");
        return "QueryForm";
    }

    @PostMapping("/person/searchByName")
    public String personSearchByName(@RequestParam("name") String name, Model theModel){

        if(name.isEmpty()) {

            theModel.addAttribute("queryObject", new QueryClass());
            theModel.addAttribute("nameDescription","Name can not be empty!");
            return "QueryForm";
        }

        List<PersonDto> personDtoList = personService
                .getAllByName(name,PageRequest.of(1,20))
                .stream().collect(Collectors.toList());

        if(personDtoList.isEmpty()) {
            theModel.addAttribute("queryObject", new QueryClass());
            theModel.addAttribute("nameDescription","Nobody could be found by this name.");
            return "QueryForm";
        }

        theModel.addAttribute("people",personDtoList);

        return "PersonTable";
    }

    @PostMapping("/person/searchBySurname")
    public String personSearchBySurname(@RequestParam("surname") String surname, Model theModel){

        if(surname.isEmpty()) {
            QueryClass queryObject = new QueryClass();
            theModel.addAttribute("queryObject", queryObject);
            theModel.addAttribute("surnameDescription","Surname can not be empty!");
            return "QueryForm";
        }

        List<PersonDto> personDtoList = personService
                .getAllBySurname(surname,PageRequest.of(1,20))
                .stream().collect(Collectors.toList());

        if(personDtoList.isEmpty()) {
            QueryClass queryObject = new QueryClass("",null);
            theModel.addAttribute("queryObject", queryObject);
            theModel.addAttribute("surnameDescription","Nobody could be found by this surname.");
            return "QueryForm";
        }

        theModel.addAttribute("people",personDtoList);

        return "PersonTable";
    }

    @PostMapping("/person/searchByNameAndSurname")
    public String personSearchByNameAndSurname(@Valid @ModelAttribute("queryObject") QueryClass queryClass, BindingResult bindingResult, Model theModel){

      if(bindingResult.hasErrors()) {

          return "QueryForm";
      }

      try {
          PersonDto personDto = personService.getByNameAndSurname(queryClass.getName(), queryClass.getSurname());
          theModel.addAttribute("people",personDto);

          return "PersonTable";
      }
      catch (PersonNotFoundException e){

          theModel.addAttribute("queryObject",new QueryClass());
          theModel.addAttribute("combinedDescription","Person you are looking for could not be found.");
          return "QueryForm";

      }


    }


    @PostMapping("/person/save")
    public String save(@RequestParam(value = "name",required = true) String name,
                       @RequestParam(value= "surname",required = true) String surname,
                       @RequestParam(value = "email",required = true) String email,ModelMap theModel) {

        PersonDto personDtoToSave = PersonDto.builder().name(name).surname(surname).email(email).build();

        try {
            personService.save(personMapper.personDtoToPerson(personDtoToSave));
        }
        catch(Exception e) {

            log.info(e.toString());

            theModel.addAttribute("description","User already exists!");
            return "person-form";
        }
        return "redirect:/person/list";
    }

}
