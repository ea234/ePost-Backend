package de.ea234.epost.controller;

import de.ea234.epost.model.vorgang.Vorgang;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.ea234.epost.services.ServiceListVorgaenge;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/vorgang")
public class RestControllerVorgang {

  private final ServiceListVorgaenge serviceListVorgaenge;

  public RestControllerVorgang( ServiceListVorgaenge pServiceVorgang )
  {
    this.serviceListVorgaenge = pServiceVorgang;
  }

  @GetMapping(path = "/ping", produces = MediaType.TEXT_PLAIN_VALUE)
  public String ping()
  {
    // http://localhost:8080/api/vorgang/ping

    return "RestControllerVorgang " + System.currentTimeMillis();
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Vorgang> getVorgangListe()
  {
    // http://localhost:8080/api/vorgang

    return serviceListVorgaenge.getListAll();
  }

  @GetMapping(path = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
  public int getVorgangAnzahl()
  {
    // http://localhost:8080/api/vorgang/count

    return serviceListVorgaenge.getAnzahl();
  }
}
