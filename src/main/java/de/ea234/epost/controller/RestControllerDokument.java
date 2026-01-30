package de.ea234.epost.controller;

import de.ea234.epost.config.EPostConfig;
import de.ea234.epost.services.TestDokumentErsteller;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import de.ea234.epost.model.dokument.Dokument;
import de.ea234.epost.services.ServiceListDokument;
import de.ea234.epost.util.FkHtml;
import de.ea234.epost.util.FkPaginiernummer;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/dokument")
public class RestControllerDokument {

  private static final Logger log = LoggerFactory.getLogger( RestControllerDokument.class );

  private final ServiceListDokument serviceListDokument;

  private final EPostConfig ePostConfig;

  private final TestDokumentErsteller testDokumentErsteller;// = new TestDokumentErsteller();

  public RestControllerDokument( ServiceListDokument pServiceListDokument, EPostConfig pEPostConfig, TestDokumentErsteller pTestDokumentErsteller )
  {
    serviceListDokument = pServiceListDokument;

    ePostConfig = pEPostConfig;

    testDokumentErsteller = pTestDokumentErsteller;
  }

  @GetMapping(path = "/ping", produces = MediaType.TEXT_PLAIN_VALUE)
  public String ping()
  {
    // http://localhost:8080/api/dokument/ping

    return "RestControllerDokument " + System.currentTimeMillis();
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Dokument> getDokumentListe()
  {
    // http://localhost:8080/api/dokument

    log.info( "RestControllerDokument - getDokumentListe" );

    return serviceListDokument.getList();
  }

  @GetMapping(path = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
  public int getDokumentAnzahl()
  {
    // http://localhost:8080/api/dokument/count

    log.info( "RestControllerDokument - getDokumentAnzahl" );

    return serviceListDokument.getAnzahl();
  }

  @GetMapping(path = "/testGetStaticDokument/{pfad}", produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<byte[]> testGetStaticDokument( @PathVariable String pfad )
  {
    // http://localhost:8080/api/dokument/testGetStaticDokument/static/2026012100002.pdf
    // http://localhost:8080/api/dokument/testGetStaticDokument/static/2026012100004.pdf
    // http://localhost:8080/api/dokument/testGetStaticDokument/static/a.pdf

    try (InputStream in = getClass().getClassLoader().getResourceAsStream( pfad ))
    {
      if ( in == null )
      {
        return ResponseEntity.notFound().build();
      }

      byte[] bytes = in.readAllBytes();

      return ResponseEntity.ok().contentType( MediaType.APPLICATION_PDF ).body( bytes );
    }
    catch (IOException ex)
    {
      log.error( "RestControllerDokument - testGetDokument", ex );

      return ResponseEntity.status( HttpStatusCode.valueOf( 404 ) ).build();
    }
  }

  @GetMapping(path = "/paginr/{paginr}", produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<byte[]> getDokumentByPaginiernummer( @PathVariable String paginr )
  {
    // http://localhost:8080/api/dokument/paginr/2026012000003

    log.info( "RestControllerDokument - getDokumentByPaginiernummer " + paginr );

    if ( FkPaginiernummer.istPaginiernummer( paginr ) )
    {
      log.info( "RestControllerDokument - PagiNr OK " + paginr );

      Dokument pdf_dokument = serviceListDokument.getDokumentByPaginierNr( paginr );

      if ( pdf_dokument != null )
      {
        return ResponseEntity.ok().contentType( MediaType.APPLICATION_PDF ).body( pdf_dokument.getDateiDaten() );
      }

      String str_error_text = "Fehler: Das Dokument fuer die Paginiernummer wurde nicht gefunden ";

      return ResponseEntity.badRequest().contentType( MediaType.TEXT_PLAIN ).body( str_error_text.getBytes( StandardCharsets.UTF_8 ) );
    }

    log.info( "RestControllerDokument - PagiNr fehler " + paginr );

    // Test ungueltige Paginiernummer           http://localhost:8080/api/dokument/paginr/notPagiNr
    // Test fuer eine nicht gefundenes Dokument http://localhost:8080/api/dokument/paginr/2000012100004
    String str_error_text = "Fehler: Die uebergebene Paginiernummer " + FkHtml.sanitize( paginr ) + " ist ungueltig";

    return ResponseEntity.badRequest().contentType( MediaType.TEXT_PLAIN ).body( str_error_text.getBytes( StandardCharsets.UTF_8 ) );
  }
}
