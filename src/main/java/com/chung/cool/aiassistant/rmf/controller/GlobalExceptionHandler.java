package com.chung.cool.aiassistant.rmf.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex, Model model) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("status", 500);
        mav.addObject("error", ex.getClass().getSimpleName());
        mav.addObject("message", ex.getMessage());
        mav.addObject("timestamp", new Date());
        mav.setViewName("error");
        return mav;
    }
}

