package de.ea234.epost.model.workflow;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.ea234.epost.config.EPostConfig;
import de.ea234.epost.model.dokument.Dokument;
import de.ea234.epost.model.vorgang.Vorgang;
import de.ea234.epost.model.vorgang.VorgangXml;
import de.ea234.epost.repository.DokumentRepository;
import de.ea234.epost.repository.VorgangRepository;
import de.ea234.epost.services.ServiceListKunde;
import de.ea234.epost.util.FkDatei;
import de.ea234.epost.util.FkDatum;
import de.ea234.epost.util.FkDatumLong;
import de.ea234.epost.util.FkPaginiernummer;
import de.ea234.epost.util.FkPdf;
import de.ea234.epost.util.FkZahl;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class VerzeichnisUeberwacher implements Runnable {

  private static final Logger log = LoggerFactory.getLogger( VerzeichnisUeberwacher.class );

  private final ServiceListKunde serviceListKunde;

  private final VorgangRepository vorgangRepository;

  private final DokumentRepository dokumentRepository;

  private final EPostConfig ePostConfig;

  private boolean m_knz_verz_ueberwachung_laeuft = false;

  public VerzeichnisUeberwacher( EPostConfig pEPostConfig, ServiceListKunde pServiceListKunde, VorgangRepository pVorgangRepository, DokumentRepository pDokumentRepository )
  {
    this.serviceListKunde = pServiceListKunde;

    this.ePostConfig = pEPostConfig;

    this.vorgangRepository = pVorgangRepository;

    this.dokumentRepository = pDokumentRepository;
  }

  @Override
  public void run()
  {
    log.info( "VerzeichnisUeberwacher.run() " + FkDatum.getWochentagDatumUndZeit() );

    checkInputDir();
  }

  private boolean checkInputDir()
  {
    if ( m_knz_verz_ueberwachung_laeuft )
    {
      return false;
    }

    m_knz_verz_ueberwachung_laeuft = true;

    /*
     * Liste der RDY-Dateien ermitteln 
     */
    List<String> result = getListRdyFiles( ePostConfig.getPathVerzeichnisInput() );

    if ( result != null )
    {
      for ( String rdy_dat_name : result )
      {
        wl( "Datei " + rdy_dat_name );

        /*
         * Dateiname ohne Erweiterung erstellen
         * (hier ist noch der Pfad mit enthalten, welches OK ist)
         */
        String datei_name_ohne_erweiterung = FkDatei.getDateiNameOhneErweiterung( rdy_dat_name );

        /*
         * Erstelle die Dateinamen fuer die PDF- und XMl-Datei
         */
        String pdf_datei_name = datei_name_ohne_erweiterung + ".pdf";

        String xml_datei_name = datei_name_ohne_erweiterung + ".xml";

        wl( "datei_name_ohne_erweiterung " + datei_name_ohne_erweiterung );

        wl( "pdf_datei_name " + pdf_datei_name );

        wl( "xml_datei_name " + xml_datei_name );

        Path path_pdf_datei = Path.of( pdf_datei_name );

        Path path_xml_datei = Path.of( xml_datei_name );

        /*
         * Pruefung ob alle 3 Dateien vorhanden sind.
         */
        boolean pdf_datei_exists = Files.exists( path_pdf_datei );

        boolean xml_datei_exists = Files.exists( path_xml_datei );

        boolean knz_paginier_nr_ok = false;

        boolean knz_datei_lieferung_ok = false;

        boolean knz_pdf_datei_struktur_ok = false;

        boolean knz_parse_xml_ok = false;

        boolean knz_verarbeitung_ok = true;

        Vorgang new_vorgang = null;

        Dokument new_dok = null;

        /*
         * Check Dateinamen auf gueltige Paginiernummer
         */
        if ( pdf_datei_exists && xml_datei_exists )
        {
          String paginier_nummer_aus_datei_name = FkDatei.getDateiNameOhneErweiterung( path_pdf_datei.getFileName() );

          knz_paginier_nr_ok = FkPaginiernummer.istPaginiernummer( paginier_nummer_aus_datei_name );

          knz_pdf_datei_struktur_ok = FkPdf.istPdfDatei( pdf_datei_name );

          wl( "paginier_nummer_aus_datei_name " + paginier_nummer_aus_datei_name );

          knz_datei_lieferung_ok = knz_paginier_nr_ok && knz_pdf_datei_struktur_ok;
        }

        wl( "knz_paginier_nr_ok             " + knz_paginier_nr_ok );
        wl( "knz_pdf_datei_struktur_ok      " + knz_pdf_datei_struktur_ok );
        wl( "knz_datei_lieferung_ok         " + knz_datei_lieferung_ok );

        VorgangXml instanz_xml_vorgang = null;

        if ( knz_datei_lieferung_ok )
        {
          XmlMapper xmlMapper = new XmlMapper();

          try
          {
            File xml_file = new File( xml_datei_name );

            instanz_xml_vorgang = xmlMapper.readValue( xml_file, VorgangXml.class );

            knz_parse_xml_ok = true;
          }
          catch (IOException err_inst)
          {
            log.error( "Fehler beim parsen der XMl-Datei ", err_inst );

            knz_verarbeitung_ok = false;
          }
        }

        if ( instanz_xml_vorgang != null )
        {
          /*
           * Erstellung eines Vorganges aus den Daten der XML-Datei
           */

          String epost_id = FkZahl.getUUID();

          new_vorgang = new Vorgang();

          new_vorgang.setWfStatus( "NEU" );

          new_vorgang.setWfAktivitaet( "START" );

          new_vorgang.setWfEPostID( epost_id );

          new_vorgang.setPaginierNr( instanz_xml_vorgang.getPaginierNr() );

          new_vorgang.setDatumEingang( FkDatumLong.parseToDate( instanz_xml_vorgang.getDatumEingang() ) );

          new_vorgang.setStammNr( instanz_xml_vorgang.getStammNr() );

          new_vorgang.setVertragNummer( instanz_xml_vorgang.getVertragNummer() );

          new_vorgang.setVorgangTypNr( instanz_xml_vorgang.getVorgangTypNr() );

          new_vorgang.setBearbeiter( null ); // noch kein Bearbeiter

          new_vorgang.setDatumEndeGeplant( null ); // noch kein geplantes End-Datum

          byte[] bytes = null;

          try (InputStream in = Files.newInputStream( path_pdf_datei ))
          {
            bytes = in.readAllBytes();
          }
          catch (IOException err_inst)
          {
            log.error( "Fehler beim lesen der PDF-Datei ", err_inst );

            knz_verarbeitung_ok = false;
          }

          if ( bytes != null )
          {
            new_dok = new Dokument();

            UUID dokument_id = UUID.randomUUID();

            new_dok.setDokumentID( dokument_id.toString() );

            new_dok.setDateiDaten( bytes );

            new_dok.setPaginierNr( instanz_xml_vorgang.getPaginierNr() );

            new_dok.setDatumEingang( FkDatumLong.parseToDate( instanz_xml_vorgang.getDatumEingang() ) );

            new_dok.setDateiTyp( "PDF" );
          }
        }

        if ( (knz_verarbeitung_ok) && (new_dok != null) && (new_vorgang != null) )
        {
          boolean knz_db_ok = speichereDokUndVorgang( new_dok, new_vorgang );

          if ( knz_db_ok )
          {
            /*
             * Verarbeitete Dateien in das Tagesverzeichnis verschieben.
             */
            String paginier_nummer_aus_datei_name = FkDatei.getDateiNameOhneErweiterung( path_pdf_datei.getFileName() );

            Path rdy_datei_sicherung = ePostConfig.getPathTagesverzeichnis().resolve( Path.of( paginier_nummer_aus_datei_name + ".rdy" ) );

            Path xml_datei_sicherung = ePostConfig.getPathTagesverzeichnis().resolve( Path.of( paginier_nummer_aus_datei_name + ".xml" ) );

            Path pdf_datei_sicherung = ePostConfig.getPathTagesverzeichnis().resolve( Path.of( paginier_nummer_aus_datei_name + ".pdf" ) );

            Path path_rdy_datei = Path.of( rdy_dat_name );

            moveFile( path_rdy_datei, rdy_datei_sicherung );

            moveFile( path_xml_datei, xml_datei_sicherung );

            moveFile( path_pdf_datei, pdf_datei_sicherung );
          }
        }
      }
    }

    m_knz_verz_ueberwachung_laeuft = false;

    return true;
  }

  private boolean moveFile( Path pPathSource, Path pPathDestination )
  {
    boolean knz_move_ok = false;

    try
    {
      Files.move( pPathSource, pPathDestination, StandardCopyOption.ATOMIC_MOVE );

      knz_move_ok = true;
    }
    catch (IOException err_inst)
    {
      log.error( "Fehler: Atomic move failed: ", err_inst );
    }

    return knz_move_ok;
  }

  @Transactional
  private boolean speichereDokUndVorgang( Dokument new_dok, Vorgang new_vorgang )
  {
    boolean knz_db_ok = false;

    if ( (new_dok != null) && (new_vorgang != null) )
    {
      try
      {
        dokumentRepository.save( new_dok );

        vorgangRepository.save( new_vorgang );

        knz_db_ok = true;
      }
      catch (Exception err_inst)
      {
        log.error( "Fehler beim Speichern in die Datenbank ", err_inst );
      }

    }

    return knz_db_ok;
  }

  private List< String> getListRdyFiles( Path pPath )
  {
    List< String> result = null;

    try (Stream< Path> walk = Files.walk( pPath ))
    {
      result = walk.filter( path_inst ->  ! Files.isDirectory( path_inst ) )
        .map( path_inst -> path_inst.toString() )
        .filter( f -> f.endsWith( "rdy" ) )
        .collect( Collectors.toList() );
    }
    catch (IOException err_inst)
    {
      log.error( "Verzeichnisueberwacher IOException ", err_inst );
    }

    return result;
  }

  private void wl( String pString )
  {
    log.info( pString );
  }
}
