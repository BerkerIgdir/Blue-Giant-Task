package com.mavidev.task.web.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryClass {

    @NotEmpty(message = "This field can not be empty")
    private String name;

    @NotEmpty(message = "This field can not be empty")
    private String surname;

}
