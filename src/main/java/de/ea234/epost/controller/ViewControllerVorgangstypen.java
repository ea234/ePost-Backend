package de.ea234.epost.controller;

import de.ea234.epost.config.EPostConfig;
import de.ea234.epost.model.benutzer.Benutzer;
import de.ea234.epost.model.vorgang.Vorgangstyp;
import de.ea234.epost.services.ServiceListVorgangstypen;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewControllerVorgangstypen {

  private static final Logger log = LoggerFactory.getLogger( ViewControllerVorgangstypen.class );

  private final EPostConfig ePostConfig;

  private final ServiceListVorgangstypen serviceListVorgangstypen;

  public ViewControllerVorgangstypen( EPostConfig pEPostConfig, ServiceListVorgangstypen pServiceVorgangstypen )
  {
    serviceListVorgangstypen = pServiceVorgangstypen;

    ePostConfig = pEPostConfig;
  }

  /*
   *****************************************************************************
   * Mapping fuer die Listenanzeige der Vorgaenge
   *****************************************************************************
   */
  @GetMapping("/vorgangstypen")
  public String showVorgangstypenList(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    Model model, HttpSession pHttpSession )
  {
    log.info( " #### LOG #### ViewControllerVorgangstypen - showVorgangstypenList" );

    /*
     * Check ob noch ein aktiver Benutzer in der Sesseion gespeichert ist
     */
    Benutzer aktiver_benutzer = (Benutzer) pHttpSession.getAttribute( "aktiverBenutzer" );

    if ( aktiver_benutzer == null )
    {
      log.info( " #### LOG #### ViewControllerVorgangstypen - Kein aktiver Benutzer in Session vorhanden" );

      return "redirect:/epost";
    }

    List< Vorgangstyp> list_vg_typen = serviceListVorgangstypen.getList();


    /* 
     * Berechne Start- und End-Index fuer die Paginierung
     */
    int list_index_start = page * size;

    int list_index_end = Math.min( list_index_start + size, list_vg_typen.size() );

    /*
     * Erstellung der Subliste fuer die Anzeige anhand der Paginierung
     */
    List<Vorgangstyp> list_page_vg_typen = list_vg_typen.subList( list_index_start, list_index_end );

    /*
     * Daten fuer Thymeleaf in das Modell schreiben
     */
    model.addAttribute( "benutzer", aktiver_benutzer );

    model.addAttribute( "vorgangstypen", list_page_vg_typen );

    model.addAttribute( "vorgangstypen_gesamt", list_vg_typen.size() );

    model.addAttribute( "appName", ePostConfig.getName() );
    model.addAttribute( "appVersion", ePostConfig.getVersion() );

    model.addAttribute( "currentPage", page );
    model.addAttribute( "currentSize", size );
    model.addAttribute( "totalPages", (int) Math.ceil( list_vg_typen.size() / (double) size ) );

    /*
     * View-Namen fuer die Vorgangsliste zurückgeben
     */
    return "typen-list";
  }
}
