package de.ea234.epost.controller;

import de.ea234.epost.model.workflow.VerzeichnisUeberwacher;
import de.ea234.epost.model.workflow.WorkflowBackgroundAgent;
import de.ea234.epost.services.ServiceListVorgaenge;
import de.ea234.epost.services.TestDokumentErsteller;
import de.ea234.epost.util.FkDatum;
import de.ea234.epost.util.FkHtml;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wf")
public class RestControllerWorkflow {

  private static final Logger log = LoggerFactory.getLogger( RestControllerWorkflow.class );

  private final ServiceListVorgaenge serviceListVorgaenge;

  private final VerzeichnisUeberwacher verzeichnisUeberwacher;

  private final WorkflowBackgroundAgent m_workflow_background_agent;

  private final TestDokumentErsteller testDokumentErsteller;

  public RestControllerWorkflow( ServiceListVorgaenge pServiceVorgang, VerzeichnisUeberwacher pVerzeichnisUeberwacher, WorkflowBackgroundAgent pWorkflowBackgroundAgent, TestDokumentErsteller pTestDokumentErsteller )
  {
    this.serviceListVorgaenge = pServiceVorgang;

    this.verzeichnisUeberwacher = pVerzeichnisUeberwacher;

    this.m_workflow_background_agent = pWorkflowBackgroundAgent;

    testDokumentErsteller = pTestDokumentErsteller;
  }

  /*
   * ****************************************************************************
   * PING
   * ****************************************************************************
   */
  @GetMapping(path = "/ping", produces = MediaType.TEXT_PLAIN_VALUE)
  public String ping()
  {
    // http://localhost:8080/api/wf/ping

    log.info( "RestControllerVorgang ping " + FkDatum.getWochentagDatumUndZeit() );

    return "RestControllerVorgang " + System.currentTimeMillis();
  }

  /*
   * ****************************************************************************
   * Mappings um fuer jeden Kunden einen Testeingang zu erstellen
   * ****************************************************************************
   */
  @GetMapping(path = "/startTestdokumente", produces = MediaType.APPLICATION_JSON_VALUE)
  public String startErstelleTestdokumente()
  {
    // http://localhost:8080/api/wf/startTestdokumente

    log.info( "RestControllerVorgang startErstelleTestdokumente " + FkDatum.getWochentagDatumUndZeit() );

    testDokumentErsteller.doErstelleTestEingangJeKunde();

    Properties prop_json = new Properties();

    prop_json.setProperty( "status", "OK" );
    prop_json.setProperty( "message", "Testdokumente wurden erstellt - " + FkDatum.getWochentagDatumUndZeit() );
    prop_json.setProperty( "datum", FkDatum.getString() );
    prop_json.setProperty( "zeit", FkDatum.getZeit() );

    return FkHtml.getStringJson( prop_json );
  }

  /*
   * ****************************************************************************
   * Mapping um die Verzeichnisueberwachung manuell starten zu koennen
   * ****************************************************************************
   */
  @GetMapping(path = "/startVzUeberwachung", produces = MediaType.APPLICATION_JSON_VALUE)
  public String startVerzeichnisUeberwachung()
  {
    // http://localhost:8080/api/wf/startVzUeberwachung

    log.info( "RestControllerVorgang startVerzeichnisUeberwachung " + FkDatum.getWochentagDatumUndZeit() );

    verzeichnisUeberwacher.run();

    Properties prop_json = new Properties();

    prop_json.setProperty( "status", "OK" );
    prop_json.setProperty( "message", "Verzeichnisueberwachung manuell gestartet - " + FkDatum.getWochentagDatumUndZeit() );
    prop_json.setProperty( "datum", FkDatum.getString() );
    prop_json.setProperty( "zeit", FkDatum.getZeit() );

    return FkHtml.getStringJson( prop_json );
  }

  /*
   * ****************************************************************************
   * Mapping um den Background-Agenten manuell starten zu koennen
   * ****************************************************************************
   */
  @GetMapping(path = "/startWfBackgrounder", produces = MediaType.APPLICATION_JSON_VALUE)
  public String startWfBackgrounder()
  {
    // http://localhost:8080/api/wf/startWfBackgrounder

    m_workflow_background_agent.doRun( "manuell" );

    Properties prop_json = new Properties();

    prop_json.setProperty( "status", "OK" );
    prop_json.setProperty( "message", "Background-Agent manuell gestartet -" + FkDatum.getWochentagDatumUndZeit() );
    prop_json.setProperty( "datum", FkDatum.getString() );
    prop_json.setProperty( "zeit", FkDatum.getZeit() );

    return FkHtml.getStringJson( prop_json );
  }
}
