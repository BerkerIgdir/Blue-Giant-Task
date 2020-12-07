package com.mavidev.task.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
public class CustomErrorController implements ErrorController {


    @RequestMapping("/error")
    public String handleError(ModelMap map){

         map.addAttribute("msg","An error occured.");

        return "ErrorPage";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
