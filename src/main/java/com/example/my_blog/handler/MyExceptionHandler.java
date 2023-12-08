package com.example.my_blog.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;

@ControllerAdvice()
public class MyExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest httpServletRequest, Exception e) throws Exception {
        String uri = httpServletRequest.getRequestURI();
        logger.error("Request URL:{}, Exception: {}", uri, e.getMessage());

        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("URL", uri);
        modelAndView.addObject("Exception", e.getMessage());
        modelAndView.setViewName("/error/error");

        return modelAndView;
    }
}
