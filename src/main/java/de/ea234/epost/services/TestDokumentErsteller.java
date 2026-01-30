package de.ea234.epost.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.ea234.epost.config.EPostConfig;
import de.ea234.epost.model.dokument.Dokument;
import de.ea234.epost.model.kunden.Kunde;
import de.ea234.epost.model.vorgang.Vorgang;
import de.ea234.epost.model.vorgang.VorgangXml;
import de.ea234.epost.model.vorgang.Vorgangstyp;
import de.ea234.epost.util.FkDatei;
import de.ea234.epost.util.FkDatum;
import de.ea234.epost.util.FkDatumLong;
import de.ea234.epost.util.FkPaginiernummer;
import de.ea234.epost.util.druck.TestDokumentKunde;
import java.nio.file.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestDokumentErsteller {

  private static final Logger log = LoggerFactory.getLogger( TestDokumentErsteller.class );

  private final FkPaginiernummer fkPaginiernummer;

  private final EPostConfig ePostConfig;

  private final ServiceListKunde serviceListKunde;

  private final ServiceListVorgangstypen serviceListVorgangstypen;

  public TestDokumentErsteller( EPostConfig pEPostConfig, ServiceListKunde pServiceListKunde, ServiceListVorgangstypen pServiceVorgangstypen, FkPaginiernummer pFkPaginiernummer )
  {
    ePostConfig = pEPostConfig;

    serviceListKunde = pServiceListKunde;

    serviceListVorgangstypen = pServiceVorgangstypen;

    fkPaginiernummer = pFkPaginiernummer;
  }

  private int index_vorgangstyp = 0;

  public void doErstelleTestEingangJeKunde()
  {
    int anzahl_vorg_typen = serviceListVorgangstypen.getAnzahl();
    
    for ( int index_kunde = 0; index_kunde < serviceListKunde.getAnzahl(); index_kunde ++ )
    {
      doErstelleTestEingang( index_kunde, index_vorgangstyp );
      
      if ( index_vorgangstyp >= anzahl_vorg_typen )
      {
        index_vorgangstyp = 0;
      }
      else
      {
        index_vorgangstyp++;
      }
    }
  }

  public void doErstelleTestEingang( int pIndexKunde, int pIndexVorgangstyp )
  {
    if ( FkDatei.getAnzahlDateien( ePostConfig.getVerzeichnis_input() ) > 9 )
    {
      log.info( "doErstelleTestEingang: Im Inputverzeichnis sind genug Dateien vorhanden. Keine neuen Testdaten. " );

      return;
    }

    Kunde kunde_instanz = serviceListKunde.getIndex( pIndexKunde );

    Vorgangstyp vorg_typ_instanz = serviceListVorgangstypen.getIndex( pIndexVorgangstyp );

    VorgangXml instanz_xml_vorgang = new VorgangXml();

    instanz_xml_vorgang.setPaginierNr( fkPaginiernummer.getNewPaginiernummer() );

    instanz_xml_vorgang.setDatumEingang( FkDatum.getString() );

    instanz_xml_vorgang.setStammNr( kunde_instanz.getStammNummer() );

    instanz_xml_vorgang.setVertragNummer( kunde_instanz.getVertraege().get( 0 ).getVertragsNummer() );

    /*
     * Der Testvorgang kennt nur die 
     */
    instanz_xml_vorgang.setVorgangTypNr( vorg_typ_instanz.getTypNr() );

    Vorgang new_1vorgang1 = new Vorgang();

    new_1vorgang1.setWfStatus( "NEU" );

    new_1vorgang1.setWfAktivitaet( "START" );

    new_1vorgang1.setWfEPostID( "34504707043257" );

    new_1vorgang1.setPaginierNr( instanz_xml_vorgang.getPaginierNr() );

    new_1vorgang1.setDatumEingang( FkDatumLong.parseToDate( instanz_xml_vorgang.getDatumEingang() ) );

    new_1vorgang1.setStammNr( instanz_xml_vorgang.getStammNr() );

    new_1vorgang1.setVertragNummer( instanz_xml_vorgang.getVertragNummer() );

    new_1vorgang1.setVorgangTypNr( instanz_xml_vorgang.getVorgangTypNr() );

    Dokument new_dokument = new Dokument();

    new_dokument.setDatumEingang( FkDatumLong.parseToDate( instanz_xml_vorgang.getDatumEingang() ) );

    new_dokument.setPaginierNr( instanz_xml_vorgang.getPaginierNr() );

    new_dokument.setDateiTyp( "PDF" );

    try
    {
      byte[] pdf_daten = TestDokumentKunde.getTestDokument( kunde_instanz, new_1vorgang1 );

      new_dokument.setDateiDaten( pdf_daten );
    }
    catch (Exception ex)
    {
      log.error( "Fehler beim erstellen des PDF-Dokumentes", ex );
    }

    doSchreibeTestInputDokumente( instanz_xml_vorgang, new_dokument );
  }

  private boolean doSchreibeTestInputDokumente( VorgangXml pVorgang, Dokument pDokument )
  {
    String datei_name = pVorgang.getPaginierNr();

    if ( pDokument.hasNoDokumentDaten() )
    {
      log.error( "Fehler: Kann keine Testdaten erstellen. Es sind keine Dokumentdaten vorhanden" );

      return false;
    }

    boolean knz_dokumente_geschrieben = true;

    XmlMapper xmlMapper = new XmlMapper();

    String xml_string = "";

    try
    {
      xml_string = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString( (Object) pVorgang );

      xml_string = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + xml_string;
    }
    catch (JsonProcessingException ex)
    {
      log.error( "Fehler beim erstellen XML Vorgang", ex );

      knz_dokumente_geschrieben = false;
    }

    try
    {
      Files.writeString( ePostConfig.getPathToInput( datei_name + ".xml" ), xml_string );
    }
    catch (Exception ex)
    {
      log.error( "Fehler beim schreiben der XML Datei", ex );

      knz_dokumente_geschrieben = false;
    }

    try
    {
      Files.write( ePostConfig.getPathToInput( datei_name + ".pdf" ), pDokument.getDateiDaten() );
    }
    catch (Exception ex)
    {
      log.error( "Fehler beim schreiben der PDF Datei", ex );

      knz_dokumente_geschrieben = false;
    }

    try
    {
      Files.writeString( ePostConfig.getPathToInput( datei_name + ".rdy" ), "Dokument erstellt am " + FkDatum.getWochentagDatumUndZeit() );
    }
    catch (Exception ex)
    {
      log.error( "Fehler beim schreiben der RDY Datei", ex );

      knz_dokumente_geschrieben = false;
    }

    return knz_dokumente_geschrieben;
  }
}