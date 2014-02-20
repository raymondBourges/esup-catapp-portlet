package org.esupportail.catapp.web.controllers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.portlet.ModelAndView;

@Slf4j
public abstract class AbstractExceptionController {

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) {
		
		ModelMap model = new ModelMap();
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
    	PrintStream print = new PrintStream(output);
    	ex.printStackTrace(print);
    	String exceptionStackTrace = new String(output.toByteArray());
    	model.put("exceptionStackTrace", exceptionStackTrace);
    	
    	model.put("exceptionMessage", ex.getMessage());

    	
        return new ModelAndView("exception", model);
	}

	
}
