package de.ea234.epost.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.ea234.epost.config.EPostConfig;
import de.ea234.epost.services.TestDokumentErsteller;
import de.ea234.epost.model.kunden.Kunde;
import de.ea234.epost.services.ServiceListKunde;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/kunde")
public class RestControllerKunde {

  private static final Logger log = LoggerFactory.getLogger( RestControllerKunde.class );

  private final EPostConfig ePostConfig;

  private final ServiceListKunde serviceListKunde;

  private final TestDokumentErsteller testDokumentErsteller;

  public RestControllerKunde( EPostConfig pEPostConfig, ServiceListKunde pServiceListKunde, TestDokumentErsteller pTestDokumentErsteller )
  {
    this.serviceListKunde = pServiceListKunde;

    this.ePostConfig = pEPostConfig;

    this.testDokumentErsteller = pTestDokumentErsteller;
  }

  @GetMapping(path = "/ping", produces = MediaType.TEXT_PLAIN_VALUE)
  public String ping()
  {
    // http://localhost:8080/api/kunde/ping

    return "RestControllerKunde " + System.currentTimeMillis();
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Kunde> getKundenListe()
  {
    // http://localhost:8080/api/kunde

    log.info( "RestControllerKunde - getKundenListe" );

    return serviceListKunde.getList();
  }

  @GetMapping(path = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
  public int getKundenAnzahl()
  {
    // http://localhost:8080/api/kunde/count

    log.info( "RestControllerKunde - getKundenAnzahl" );

    return serviceListKunde.getAnzahl();
  }

  /*
   * ---------------------------------------------------------------------------
   */
  @GetMapping(path = "/testEingangJeKunde", produces = MediaType.APPLICATION_JSON_VALUE)
  public String testErstelleTestdokumenteJeKunde()
  {
    // http://localhost:8080/api/kunde/testEingangJeKunde

    log.info( "RestControllerKunde - testErstelleTestdokumenteJeKunde" );

    testDokumentErsteller.doErstelleTestEingangJeKunde();

    return "RestControllerKunde config " + ePostConfig.getName() + " " + ePostConfig.getVersion();
  }

  @GetMapping(path = "/writeTestEingang", produces = MediaType.APPLICATION_JSON_VALUE)
  public String testErstelleTestEingang()
  {
    // http://localhost:8080/api/kunde/writeTestEingang

    log.info( "RestControllerKunde - testErstelleTestdokumenteKunde0" );

    testDokumentErsteller.doErstelleTestEingang( 0, 1 );

    return "RestControllerKunde writeTestEingang " + ePostConfig.getName() + " " + ePostConfig.getVersion();
  }

  @GetMapping(path = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
  public String testGetXml()
  {
    // http://localhost:8080/api/kunde/xml

    log.info( "RestControllerKunde - testGetXml" );

    XmlMapper xmlMapper = new XmlMapper();

    String xml_string = "";

    try
    {
      xml_string = xmlMapper.writeValueAsString( (Object) serviceListKunde.getList() );
    }
    catch (JsonProcessingException ex)
    {
      System.getLogger( RestControllerKunde.class.getName() ).log( System.Logger.Level.ERROR, (String) null, ex );

      xml_string = "Fehler " + ex.getMessage();
    }

    return xml_string;
  }
}
