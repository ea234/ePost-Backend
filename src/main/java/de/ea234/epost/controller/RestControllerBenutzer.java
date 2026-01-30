package de.ea234.epost.controller;

import de.ea234.epost.model.benutzer.Benutzer;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.ea234.epost.services.ServiceListBenutzer;

@RestController
@RequestMapping("/api/benutzer")
public class RestControllerBenutzer {

  private static final Logger log = LoggerFactory.getLogger( RestControllerBenutzer.class );

  private final ServiceListBenutzer serviceListBenutzer;

  public RestControllerBenutzer( ServiceListBenutzer pServiceListBenutzer )
  {
    serviceListBenutzer = pServiceListBenutzer;
  }

  @GetMapping("/ping")
  public String ping()
  {
    // http://localhost:8080/api/benutzer/ping

    return "RestControllerBenutzer " + System.currentTimeMillis();
  }

  @GetMapping
  public List<Benutzer> getAllUser()
  {
    // http://localhost:8080/api/benutzer

    return serviceListBenutzer.getList();
  }

  @GetMapping("/count")
  public int getAnzahl()
  {
    // http://localhost:8080/api/benutzer/count

    return serviceListBenutzer.getAnzahl();
  }
}
