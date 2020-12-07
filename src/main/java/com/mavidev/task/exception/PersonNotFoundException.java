package com.mavidev.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "COULD NOT BE FOUND")
public class PersonNotFoundException extends RuntimeException {


}
