package de.ea234.epost.controller;

import de.ea234.epost.model.RequestContext;
import de.ea234.epost.model.benutzer.Benutzer;
import de.ea234.epost.services.ServiceListBenutzer;
import de.ea234.epost.util.FkHtml;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewControllerLogin {

  private static final Logger log = LoggerFactory.getLogger( ViewControllerLogin.class );

  private final ServiceListBenutzer serviceListBenutzer;

  public ViewControllerLogin( ServiceListBenutzer pServiceListBenutzer )
  {
    this.serviceListBenutzer = pServiceListBenutzer;
  }

  /*
   * ****************************************************************************
   * Mappings fuer die generelle LogIn-Seite
   * ****************************************************************************
   */
  @GetMapping("/epost")
  public String showEPostLogIn( Model model )
  {
    return "epost-login";
  }

  /*
   * ****************************************************************************
   * Mapping fuer das Benutzer Log-In
   * ****************************************************************************
   */
  @PostMapping("/epost/login")
  public String doUserLogIn( @RequestParam("userName") String userName, @RequestParam("userPassword") String userPassword, Model model, HttpSession pHttpSession, RequestContext pRequestContext )
  {
    String pUserName = userName;
    String pUserPassword = userPassword;

    log.info( " #### LOG #### ViewControllerLogin - " + pRequestContext.getRequestId() + " doUserLogin " + pUserName + "  " + pUserPassword );

    Benutzer best_benutzer = serviceListBenutzer.getBenutzerByUserName( pUserName );

    if ( best_benutzer == null )
    {
      // Fehler: user nicht gefunden

      log.info( "Benutzer nicht gefunden" );

      model.addAttribute( "loginError", "Benutzer nicht gefunden" );
    }
    else
    {
      if ( best_benutzer.checkPassword( pUserPassword ) )
      {
        log.info( " #### LOG #### ViewControllerLogin -  Weiterleitung nach vorg" );

        pHttpSession.setAttribute( "aktiverBenutzer", best_benutzer );

        return "redirect:/vorgaenge";
      }

      model.addAttribute( "loginError", "Kennwort stimmt nicht" );
    }

    return "epost-login";
  }

  /*
   * ****************************************************************************
   * Mapping fuer das Benutzer Log-Out
   * ****************************************************************************
   */
  @GetMapping("/epost/logout")
  public String doUserLogOut( Model model, HttpSession pHttpSession )
  {
    log.info( " #### LOG #### ViewControllerLogin - doUserLogOut " );

    Benutzer aktiverBenutzer = (Benutzer) pHttpSession.getAttribute( "aktiverBenutzer" );

    if ( aktiverBenutzer != null )
    {
      FkHtml.removeSessionAttributes( pHttpSession );
    }

    model.addAttribute( "loginInfo", "Benutzer wurde ausgeloggt" );

    return "epost-login";
  }
}
