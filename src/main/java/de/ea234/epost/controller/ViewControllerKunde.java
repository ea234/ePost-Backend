package de.ea234.epost.controller;

import de.ea234.epost.config.EPostConfig;
import de.ea234.epost.model.RequestContext;
import de.ea234.epost.model.benutzer.Benutzer;
import de.ea234.epost.model.kunden.Adresse;
import de.ea234.epost.model.kunden.Kunde;
import de.ea234.epost.model.kunden.UhEintrag;
import de.ea234.epost.model.vorgang.Vorgang;
import de.ea234.epost.repository.UnterlagenHistorieRepository;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewControllerKunde {

  private static final Logger log = LoggerFactory.getLogger( ViewControllerKunde.class );

  private final EPostConfig ePostConfig;

  private final ServiceListVorgaenge serviceListVorgaenge;

  private final ServiceListVorgangstypen serviceListVorgangstypen;

  private final ServiceListKunde serviceListKunde;

  private final ServiceListBenutzer serviceListBenutzer;

  private final UnterlagenHistorieRepository unterlagenHistorieRepository;

  private final RequestContext requestContext;

  public ViewControllerKunde( EPostConfig pEPostConfig, ServiceListVorgaenge pServiceListVorgaenge, RequestContext pRequestContext, ServiceListVorgangstypen pServiceVorgangstypen, ServiceListKunde pServiceListKunde, ServiceListBenutzer pServiceListBenutzer, UnterlagenHistorieRepository pUnterlagenHistorieRepository )
  {
    serviceListVorgaenge = pServiceListVorgaenge;

    requestContext = pRequestContext;

    serviceListVorgangstypen = pServiceVorgangstypen;

    ePostConfig = pEPostConfig;

    serviceListKunde = pServiceListKunde;

    serviceListBenutzer = pServiceListBenutzer;

    unterlagenHistorieRepository = pUnterlagenHistorieRepository;
  }

  /*
   *****************************************************************************
   * Mapping fuer die Listenanzeige der Vorgaenge
   *****************************************************************************
   */
  @GetMapping("/kunden")
  public String showKundenList(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "id") String sort,
    @RequestParam(defaultValue = "asc") String order,
     Model model, HttpSession pHttpSession )
  {
    log.info( " #### LOG #### ViewControllerKunde - showKundenList" );

    /*
     * Check ob noch ein aktiver Benutzer in der Sesseion gespeichert ist
     */
    Benutzer aktiver_benutzer = (Benutzer) pHttpSession.getAttribute( "aktiverBenutzer" );

    if ( aktiver_benutzer == null )
    {
      log.info( " #### LOG #### ViewControllerKunde - Kein aktiver Benutzer in Session vorhanden" );

      return "redirect:/epost";
    }

    List<Kunde> list_kunden = null;

    
    
    

    List<Vorgang> list_vorgaenge = null;
    
    
    if ( list_kunden == null )
    {
      list_kunden = serviceListKunde.getSortedList( sort, order );
    }

    
    
    
    
    
    /* 
     * Berechne Start- und End-Index fuer die Paginierung
     */
    int list_index_start = page * size;

    int list_index_end = Math.min( list_index_start + size, list_kunden.size() );

    /*
     * Erstellung der Subliste fuer die Anzeige anhand der Paginierung
     */
    List<Kunde> list_page_kunden = list_kunden.subList( list_index_start, list_index_end );

    /*
     * Daten fuer Thymeleaf in das Modell schreiben
     */
    model.addAttribute( "benutzer", aktiver_benutzer );

    model.addAttribute( "kunden", list_page_kunden );

    model.addAttribute( "appName", ePostConfig.getName() );
    model.addAttribute( "appVersion", ePostConfig.getVersion() );
    model.addAttribute( "maxVorgaenge", 30 );

    model.addAttribute("currentSort", sort);
    model.addAttribute("currentOrder", order);

    model.addAttribute( "currentPage", page );
    model.addAttribute( "currentSize", size );
    
    model.addAttribute( "totalPages", (int) Math.ceil( list_kunden.size() / (double) size ) );

    /*
     * View-Namen fuer die Vorgangsliste zurückgeben
     */
    return "kunde-list";
  }
  
  
  
  @GetMapping("/kunden/filter/all")
  public String setFilterAlleKunden( Model model, HttpSession pHttpSession )
  {
    log.info( " #### LOG #### ViewControllerKunde - setFilterAlleKunden" );

    Benutzer aktiver_benutzer = (Benutzer) pHttpSession.getAttribute( "aktiverBenutzer" );

    if ( aktiver_benutzer == null )
    {
      log.info( " #### LOG #### ViewControllerVorgang - Kein aktiver Benutzer in Session vorhanden" );

      return "redirect:/epost";
    }

    pHttpSession.setAttribute( "filterVorgangsart", "alle_kunden" );

    return "redirect:/vorgaenge";
  }


  /*
   *****************************************************************************
   * Mapping fuer die Detailansicht eines Vorgangs
   *****************************************************************************
   */
  @GetMapping("/kunde/{id}")
  public String showKundeDetails( @PathVariable String id, Model model, HttpSession pHttpSession )
  {
    log.info( " #### LOG #### ViewControllerKunde - showKundeDetails " );

    Benutzer aktiver_benutzer = (Benutzer) pHttpSession.getAttribute( "aktiverBenutzer" );

    if ( aktiver_benutzer == null )
    {
      log.info( " #### LOG #### ViewControllerKunde - Kein aktiver Benutzer in Session vorhanden" );

      return "redirect:/epost";
    }

    model.addAttribute( "benutzer", aktiver_benutzer );

    Kunde bestehender_kunde = serviceListKunde.getKundeByStammnummer( id );

    model.addAttribute( "kunde", bestehender_kunde );

    Adresse best_adresse = bestehender_kunde.getAddresse();

    model.addAttribute( "adresse", best_adresse );

    List<UhEintrag> uh_liste = unterlagenHistorieRepository.findByStammNummer( bestehender_kunde.getStammNummer() );

    model.addAttribute( "uh_liste", uh_liste );

    return "kunde-details";
  }
}
