package de.ea234.epost.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ViewErrorController {

  /*
   * https://medium.com/@jovannypcg/understanding-springs-controlleradvice-cd96a364033f
   * https://www.baeldung.com/spring-boot-custom-error-page
   */
  
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleAll( Exception ex, Model model )
  {
    model.addAttribute( "error", " interner Serverfehler" );

    model.addAttribute( "message", ex.getMessage() );

    return "error";
  }
}
