package de.ea234.epost.controller;

import de.ea234.epost.config.EPostConfig;
import de.ea234.epost.model.RequestContext;
import de.ea234.epost.model.benutzer.Benutzer;
import de.ea234.epost.model.kunden.Adresse;
import de.ea234.epost.model.kunden.Kunde;
import de.ea234.epost.model.vorgang.Vorgang;
import de.ea234.epost.model.workflow.WorkflowAktivitaet;
import de.ea234.epost.model.workflow.WorkflowStatus;
import de.ea234.epost.services.ServiceListBenutzer;
import de.ea234.epost.services.ServiceListKunde;
import de.ea234.epost.services.ServiceListVorgaenge;
import de.ea234.epost.services.ServiceListVorgangstypen;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewControllerVorgang {

  private static final Logger log = LoggerFactory.getLogger( ViewControllerVorgang.class );

  private final EPostConfig ePostConfig;

  private final ServiceListVorgaenge serviceListVorgaenge;

  private final ServiceListVorgangstypen serviceListVorgangstypen;

  private final ServiceListKunde serviceListKunde;

  private final ServiceListBenutzer serviceListBenutzer;

  private final RequestContext requestContext;

  public ViewControllerVorgang( EPostConfig pEPostConfig, ServiceListVorgaenge pServiceListVorgaenge, RequestContext pRequestContext, ServiceListVorgangstypen pServiceVorgangstypen, ServiceListKunde pServiceListKunde, ServiceListBenutzer pServiceListBenutzer )
  {
    this.serviceListVorgaenge = pServiceListVorgaenge;

    this.requestContext = pRequestContext;

    serviceListVorgangstypen = pServiceVorgangstypen;

    ePostConfig = pEPostConfig;

    serviceListKunde = pServiceListKunde;

    serviceListBenutzer = pServiceListBenutzer;
  }

  @GetMapping("/")
  public String home( HttpSession pHttpSession )
  {
    log.info( " #### LOG #### ViewControllerVorgang - home " );

    Benutzer aktiver_benutzer = (Benutzer) pHttpSession.getAttribute( "aktiverBenutzer" );

    if ( aktiver_benutzer == null )
    {
      return "redirect:/epost";
    }

    return "redirect:/vorgaenge";
  }

  /*
   *****************************************************************************
   * Mapping fuer die Listenanzeige der Vorgaenge
   *****************************************************************************
   */
  @GetMapping("/vorgaenge")
  public String showAllVorgaenge(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    Model model, HttpSession pHttpSession )
  {
    log.info( " #### LOG #### ViewControllerVorgang - showAllVorgaenge " );

    /*
     * Check ob noch ein aktiver Benutzer in der Sesseion gespeichert ist
     */
    Benutzer aktiver_benutzer = (Benutzer) pHttpSession.getAttribute( "aktiverBenutzer" );

    if ( aktiver_benutzer == null )
    {
      log.info( " #### LOG #### ViewControllerVorgang - Kein aktiver Benutzer in Session vorhanden" );

      return "redirect:/epost";
    }

    String ui_text_filter = "";

    /*
     * Vorgangsfilterung durchfuehren
     */
    String filter_vorgangsart = (String) pHttpSession.getAttribute( "filterVorgangsart" );

    if ( filter_vorgangsart == null )
    {
      filter_vorgangsart = "aktivitaet_bearbeitung";
    }

    List<Vorgang> list_vorgaenge = null;

    if ( filter_vorgangsart.equals( "aktivitaet_bearbeitung" ) )
    {
      list_vorgaenge = serviceListVorgaenge.getListAktivitaetBearbeitung();

      ui_text_filter = "Vorgänge in Bearbeitung";
    }
    else if ( filter_vorgangsart.equals( "meine_vorgaenge" ) )
    {
      list_vorgaenge = serviceListVorgaenge.getListUserName( aktiver_benutzer.getUserName() );

      ui_text_filter = "Meine Vorgänge";
    }
    else if ( filter_vorgangsart.equals( "aktivitaet_start" ) )
    {
      list_vorgaenge = serviceListVorgaenge.getListAktivitaetStart();

      ui_text_filter = "Vorgänge in Aktivität Start";
    }
    else if ( filter_vorgangsart.equals( "aktivitaet_ende" ) )
    {
      list_vorgaenge = serviceListVorgaenge.getListAktivitaetEnde();

      ui_text_filter = "Vorgänge in Aktivität Ende";
    }
    else
    {
      list_vorgaenge = serviceListVorgaenge.getListAll();

      ui_text_filter = "alle Vorgänge";
    }

    /* 
     * Berechne Start- und End-Index fuer die Paginierung
     */
    int list_index_start = page * size;

    int list_index_end = Math.min( list_index_start + size, list_vorgaenge.size() );

    /*
     * Erstellung der Subliste fuer die Anzeige anhand der Paginierung
     */
    List<Vorgang> list_page_vorgaenge = list_vorgaenge.subList( list_index_start, list_index_end );

    /*
     * Daten fuer Thymeleaf in das Modell schreiben
     */
    model.addAttribute( "benutzer", aktiver_benutzer );

    model.addAttribute( "vorgaenge", list_page_vorgaenge );

    model.addAttribute( "vorgaengstypen", serviceListVorgangstypen.getMapUidZuBezeichnung() );
    model.addAttribute( "typMap", serviceListVorgangstypen.getMapUidZuBezeichnung() );

    model.addAttribute( "appName", ePostConfig.getName() );
    model.addAttribute( "appVersion", ePostConfig.getVersion() );
    model.addAttribute( "maxVorgaenge", 30 );

    model.addAttribute( "ui_text_filter", ui_text_filter );
    model.addAttribute( "vorgaenge_gesamt", "" + list_vorgaenge.size() );

    model.addAttribute( "currentPage", page );
    model.addAttribute( "currentSize", size );
    model.addAttribute( "totalPages", (int) Math.ceil( list_vorgaenge.size() / (double) size ) );

    /*
     * View-Namen fuer die Vorgangsliste zurückgeben
     */
    return "vorgangs-list";
  }

  /*
   *****************************************************************************
   * Mappings fuer das setzen von Anzeigefiltern
   *****************************************************************************
   */
  @GetMapping("/vorgaenge/filter/my")
  public String setFilterMeineVorgaenge( Model model, HttpSession pHttpSession )
  {
    log.info( " #### LOG #### ViewControllerVorgang - setFilterMeineVorgaenge" );

    Benutzer aktiver_benutzer = (Benutzer) pHttpSession.getAttribute( "aktiverBenutzer" );

    if ( aktiver_benutzer == null )
    {
      log.info( " #### LOG #### ViewControllerVorgang - Kein aktiver Benutzer in Session vorhanden" );

      return "redirect:/epost";
    }

    pHttpSession.setAttribute( "filterVorgangsart", "meine_vorgaenge" );

    return "redirect:/vorgaenge";
  }

  @GetMapping("/vorgaenge/filter/all")
  public String setFilterAlleVorgaenge( Model model, HttpSession pHttpSession )
  {
    log.info( " #### LOG #### ViewControllerVorgang - setFilterAlleVorgaenge" );

    Benutzer aktiver_benutzer = (Benutzer) pHttpSession.getAttribute( "aktiverBenutzer" );

    if ( aktiver_benutzer == null )
    {
      log.info( " #### LOG #### ViewControllerVorgang - Kein aktiver Benutzer in Session vorhanden" );

      return "redirect:/epost";
    }

    pHttpSession.setAttribute( "filterVorgangsart", "alle_vorgaenge" );

    return "redirect:/vorgaenge";
  }

  @GetMapping("/vorgaenge/filter/start")
  public String setFilterAktivitaetStart( Model model, HttpSession pHttpSession )
  {
    log.info( " #### LOG #### ViewControllerVorgang - setFilterAktivitaetStart" );

    Benutzer aktiver_benutzer = (Benutzer) pHttpSession.getAttribute( "aktiverBenutzer" );

    if ( aktiver_benutzer == null )
    {
      log.info( " #### LOG #### ViewControllerVorgang - Kein aktiver Benutzer in Session vorhanden" );

      return "redirect:/epost";
    }

    pHttpSession.setAttribute( "filterVorgangsart", "aktivitaet_start" );

    return "redirect:/vorgaenge";
  }

  @GetMapping("/vorgaenge/filter/ende")
  public String setFilterAktivitaetEnde( Model model, HttpSession pHttpSession )
  {
    log.info( " #### LOG #### ViewControllerVorgang - setFilterAktivitaetEnde" );

    Benutzer aktiver_benutzer = (Benutzer) pHttpSession.getAttribute( "aktiverBenutzer" );

    if ( aktiver_benutzer == null )
    {
      log.info( " #### LOG #### ViewControllerVorgang - Kein aktiver Benutzer in Session vorhanden" );

      return "redirect:/epost";
    }

    pHttpSession.setAttribute( "filterVorgangsart", "aktivitaet_ende" );

    return "redirect:/vorgaenge";
  }

  @GetMapping("/vorgaenge/filter/bearb")
  public String setFilterAktivitaeBearbeitung( Model model, HttpSession pHttpSession )
  {
    log.info( " #### LOG #### ViewControllerVorgang - setFilterAktivitaeBearbeitung" );

    Benutzer aktiver_benutzer = (Benutzer) pHttpSession.getAttribute( "aktiverBenutzer" );

    if ( aktiver_benutzer == null )
    {
      log.info( " #### LOG #### ViewControllerVorgang - Kein aktiver Benutzer in Session vorhanden" );

      return "redirect:/epost";
    }

    pHttpSession.setAttribute( "filterVorgangsart", "aktivitaet_bearbeitung" );

    return "redirect:/vorgaenge";
  }

  /*
   *****************************************************************************
   * Mapping fuer die Detailansicht eines Vorgangs
   *****************************************************************************
   */
  @GetMapping("/vorgang/{id}")
  public String showVorgangDetails( @PathVariable String id, Model model, HttpSession pHttpSession )
  {
    log.info( " #### LOG #### ViewControllerVorgang - showVorgangDetails " );

    Benutzer aktiver_benutzer = (Benutzer) pHttpSession.getAttribute( "aktiverBenutzer" );

    if ( aktiver_benutzer == null )
    {
      log.info( " #### LOG #### ViewControllerVorgang - Kein aktiver Benutzer in Session vorhanden" );

      return "redirect:/epost";
    }

    Vorgang bestehender_vorgang = serviceListVorgaenge.getVorgangByEPostId( id );

    Kunde bestehender_kunde = serviceListKunde.getKundeByStammnummer( bestehender_vorgang.getStammNr() );

    Adresse best_adresse = bestehender_kunde.getAddresse();

    Benutzer bearbeiter_x = serviceListBenutzer.getBenutzerByUserName( bestehender_vorgang.getBearbeiter() );

    boolean ist_aktivitaet_bearbeiten = bestehender_vorgang.istAktivitaet( WorkflowAktivitaet.VORGANG_BEARBEITEN.toString() );

    boolean ist_status_bearbeitung = bestehender_vorgang.istStatus( WorkflowStatus.IN_BEARBEITUNG.toString() );

    boolean ist_status_beendet = bestehender_vorgang.istStatus( WorkflowStatus.ABGESCHLOSSEN.toString() );

    boolean ist_bearbeiter_gleich_benutzer = bestehender_vorgang.istBearbeiter( aktiver_benutzer.getUserName() );

    /*
     * Ein Vorgang kann angenommen werden, wenn
     * - der Vorgang in der Aktivitaet "Vorgang bearbeiten" ist
     * - der Vorgang noch nicht im Status "in bearbeitung" ist
     * - der Vorgang noch nicht beendet ist
     */
    boolean can_claim = (ist_aktivitaet_bearbeiten) && ( ! ist_status_bearbeitung) && ( ! ist_status_beendet);

    /*
     * Ein Vorgang kann abgeschlossen werden, wenn
     * - der Vorgang in der Aktivitaet "Vorgang bearbeiten" ist
     * - der Vorgang noch im Status "in bearbeitung" ist
     * - der aktive Benutzer der Bearbeiter ist.
     */
    boolean can_close = (ist_aktivitaet_bearbeiten) && (ist_status_bearbeitung) && (ist_bearbeiter_gleich_benutzer) && ( ! ist_status_beendet);

    if ( ePostConfig.istTestVorgangClaimFehlermeldungen() )
    {
      can_claim = true;
      can_close = true;
    }

    model.addAttribute( "bearbeiter_vor_und_nachname", "" );

    if ( bearbeiter_x != null )
    {
      model.addAttribute( "bearbeiter_vor_und_nachname", bearbeiter_x.getVorUndNachname() );

    }

    model.addAttribute( "can_claim", can_claim );
    model.addAttribute( "can_close", can_close );

    model.addAttribute( "ist_aktivitaet_bearbeiten", ist_aktivitaet_bearbeiten );
    model.addAttribute( "ist_status_bearbeitung", ist_status_bearbeitung );
    model.addAttribute( "ist_bearbeiter_gleich_benutzer", ist_status_bearbeitung );

    model.addAttribute( "typMap", serviceListVorgangstypen.getMapUidZuBezeichnung() );

    model.addAttribute( "kunde", bestehender_kunde );
    model.addAttribute( "adresse", best_adresse );
    model.addAttribute( "vorgang", bestehender_vorgang );
    model.addAttribute( "benutzer", aktiver_benutzer );

    return "vorgang-details";
  }

  /*
   * ****************************************************************************
   * Mappings fuer Vorgangsannahme und Beendigung
   * ****************************************************************************
   */
  @PostMapping("/vorgang/{id}/claim")
  public String setVorgangInBearbeitung( @PathVariable String id, Model model, HttpSession pHttpSession )
  {
    log.info( " #### LOG #### ViewControllerVorgang - setVorgangInBearbeitung " );

    Benutzer aktiver_benutzer = (Benutzer) pHttpSession.getAttribute( "aktiverBenutzer" );

    if ( aktiver_benutzer == null )
    {
      log.info( " #### LOG #### ViewControllerVorgang - Kein aktiver Benutzer in Session vorhanden" );

      return "redirect:/epost";
    }

    model.addAttribute( "benutzer", aktiver_benutzer );

    Vorgang bestehender_vorgang = serviceListVorgaenge.getVorgangByEPostId( id );

    boolean ist_aktivitaet_bearbeiten = bestehender_vorgang.istAktivitaet( WorkflowAktivitaet.VORGANG_BEARBEITEN.toString() );

    boolean ist_status_bearbeitung = bestehender_vorgang.istStatus( WorkflowStatus.IN_BEARBEITUNG.toString() );

    boolean ist_status_beendet = bestehender_vorgang.istStatus( WorkflowStatus.ABGESCHLOSSEN.toString() );

    boolean ist_bearbeiter_gleich_benutzer = bestehender_vorgang.istBearbeiter( aktiver_benutzer.getUserName() );

    /*
     * Ein Vorgang kann angenommen werden, wenn
     * - der Vorgang in der Aktivitaet "Vorgang bearbeiten" ist
     * - der Vorgang noch nicht im Status "in bearbeitung" ist
     */
    boolean can_claim = (ist_aktivitaet_bearbeiten) && ( ! ist_status_bearbeitung) && ( ! ist_status_beendet);

    /*
     * Ein Vorgang kann abgeschlossen werden, wenn
     * - der Vorgang in der Aktivitaet "Vorgang bearbeiten" ist
     * - der Vorgang noch im Status "in bearbeitung" ist
     * - der aktive Benutzer der Bearbeiter ist.
     */
    boolean can_close = (ist_aktivitaet_bearbeiten) && (ist_status_bearbeitung) && (ist_bearbeiter_gleich_benutzer) && ( ! ist_status_beendet);

    String error_message = "";

    if ( can_claim )
    {
      bestehender_vorgang = serviceListVorgaenge.setVorgangInBearbeitung( id, aktiver_benutzer );

      ist_status_bearbeitung = true;

      ist_status_beendet = false;

      can_claim = false; // Vorgang wurde grade angenommen, nicht nochmal annehmen

      can_close = true; // Vorgang kann nun geschlossen werden, da aktueller Benutzer gleich Bearbeiter
    }
    else
    {
      if ( ist_aktivitaet_bearbeiten == false )
      {
        error_message = "Fehler: Der Vorgang befindet sich nicht in der Aktivitaet \"Vorgang Bearbeiten\" ";
      }
      else if ( ist_status_bearbeitung )
      {
        error_message = "Fehler: Der Vorgang befindet sich bereits in Bearbeitung";
      }
      else if ( ist_status_beendet )
      {
        error_message = "Fehler: Der Vorgang ist bereits beendet";
      }
      else
      {
        error_message = "Der Vorgang kann nicht angenommen werden.";
      }

      model.addAttribute( "error_message", error_message );
    }

    Kunde bestehender_kunde = serviceListKunde.getKundeByStammnummer( bestehender_vorgang.getStammNr() );

    Adresse best_adresse = bestehender_kunde.getAddresse();

    model.addAttribute( "can_claim", can_claim );
    model.addAttribute( "can_close", can_close );

    model.addAttribute( "ist_aktivitaet_bearbeiten", ist_aktivitaet_bearbeiten );
    model.addAttribute( "ist_status_bearbeitung", ist_status_bearbeitung );
    model.addAttribute( "ist_bearbeiter_gleich_benutzer", ist_status_bearbeitung );

    model.addAttribute( "typMap", serviceListVorgangstypen.getMapUidZuBezeichnung() );

    model.addAttribute( "kunde", bestehender_kunde );
    model.addAttribute( "adresse", best_adresse );
    model.addAttribute( "vorgang", bestehender_vorgang );
    model.addAttribute( "benutzer", aktiver_benutzer );

    return "vorgang-details";
  }

  @PostMapping("/vorgang/{id}/finish")
  public String setVorgangBearbeitungAbgeschlossen( @PathVariable String id, Model model, HttpSession pHttpSession )
  {
    log.info( " #### LOG #### ViewControllerVorgang - setVorgangInBearbeitung " );

    Benutzer aktiver_benutzer = (Benutzer) pHttpSession.getAttribute( "aktiverBenutzer" );

    if ( aktiver_benutzer == null )
    {
      log.info( " #### LOG #### ViewControllerVorgang - Kein aktiver Benutzer in Session vorhanden" );

      return "redirect:/epost";
    }

    model.addAttribute( "benutzer", aktiver_benutzer );

    Vorgang bestehender_vorgang = serviceListVorgaenge.getVorgangByEPostId( id );

    boolean ist_aktivitaet_bearbeiten = bestehender_vorgang.istAktivitaet( WorkflowAktivitaet.VORGANG_BEARBEITEN.toString() );

    boolean ist_status_bearbeitung = bestehender_vorgang.istStatus( WorkflowStatus.IN_BEARBEITUNG.toString() );

    boolean ist_status_beendet = bestehender_vorgang.istStatus( WorkflowStatus.ABGESCHLOSSEN.toString() );

    boolean ist_bearbeiter_gleich_benutzer = bestehender_vorgang.istBearbeiter( aktiver_benutzer.getUserName() );

    /*
     * Ein Vorgang kann angenommen werden, wenn
     * - der Vorgang in der Aktivitaet "Vorgang bearbeiten" ist
     * - der Vorgang noch nicht im Status "in bearbeitung" ist
     */
    boolean can_claim = (ist_aktivitaet_bearbeiten) && ( ! ist_status_bearbeitung) && ( ! ist_status_beendet);

    /*
     * Ein Vorgang kann abgeschlossen werden, wenn
     * - der Vorgang in der Aktivitaet "Vorgang bearbeiten" ist
     * - der Vorgang noch im Status "in bearbeitung" ist
     * - der aktive Benutzer der Bearbeiter ist.
     */
    boolean can_close = (ist_aktivitaet_bearbeiten) && (ist_status_bearbeitung) && (ist_bearbeiter_gleich_benutzer) && ( ! ist_status_beendet);

    String error_message = "";

    if ( can_close )
    {
      bestehender_vorgang = serviceListVorgaenge.setVorgangBearbeitungAbgeschlossen( id, aktiver_benutzer );

      ist_status_bearbeitung = false;

      can_close = false;
      can_claim = false;
    }
    else
    {
      if ( ist_status_beendet )
      {
        error_message = "Fehler: Der Vorgang ist bereits beendet";
      }
      else if ( ist_status_bearbeitung == false )
      {
        error_message = "Fehler: Der Vorgang befindet sich nicht in Bearbeitung.";
      }
      else if ( ist_bearbeiter_gleich_benutzer == false )
      {
        error_message = "Fehler: Der Vorgang kann von Ihnen nicht beendet werden.";
      }
      else
      {
        error_message = "Fehler: Der Vorgang kann nicht beendet werden.";
      }

      model.addAttribute( "error_message", error_message );
    }

    Kunde bestehender_kunde = serviceListKunde.getKundeByStammnummer( bestehender_vorgang.getStammNr() );

    Adresse best_adresse = bestehender_kunde.getAddresse();

    model.addAttribute( "can_claim", can_claim );
    model.addAttribute( "can_close", can_close );

    model.addAttribute( "ist_aktivitaet_bearbeiten", ist_aktivitaet_bearbeiten );
    model.addAttribute( "ist_status_bearbeitung", ist_status_bearbeitung );
    model.addAttribute( "ist_bearbeiter_gleich_benutzer", ist_status_bearbeitung );

    model.addAttribute( "typMap", serviceListVorgangstypen.getMapUidZuBezeichnung() );

    model.addAttribute( "kunde", bestehender_kunde );
    model.addAttribute( "adresse", best_adresse );
    model.addAttribute( "vorgang", bestehender_vorgang );
    model.addAttribute( "benutzer", aktiver_benutzer );

    return "vorgang-details";
  }
}
