package de.ea234.epost.controller;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import de.ea234.epost.config.EPostConfig;
import de.ea234.epost.model.RequestContext;
import de.ea234.epost.model.benutzer.Benutzer;
import de.ea234.epost.model.vorgang.Vorgang;
import de.ea234.epost.services.ServiceListBenutzer;
import de.ea234.epost.services.ServiceListKunde;
import de.ea234.epost.services.ServiceListVorgaenge;
import de.ea234.epost.services.ServiceListVorgangstypen;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import org.springframework.ui.Model;

public class ViewControllerVorgangTest {

  @MockitoBean
  private EPostConfig ePostConfig;

  @MockitoBean
  private ServiceListVorgaenge serviceListVorgaenge;

  @MockitoBean
  private RequestContext requestContext;

  @MockitoBean
  private ServiceListVorgangstypen serviceListVorgangstypen;

  @MockitoBean
  private ServiceListKunde serviceListKunde;

  @MockitoBean
  private ServiceListBenutzer serviceListBenutzer;

  @InjectMocks
  private de.ea234.epost.controller.ViewControllerVorgang viewController;

  @BeforeEach
  void setUp()
  {
    MockitoAnnotations.openMocks( this );
  }

  @Test
  void testHomeRedirectWhenNoUserInSession()
  {
    HttpSession session = mock( HttpSession.class );

    when( session.getAttribute( "aktiverBenutzer" ) ).thenReturn( null );

    String result = viewController.home( session );

    assertEquals( "redirect:/epost", result );

  }

  @Test
  void testHomeRedirectToVorgaengeWhenUserInSession()
  {
    HttpSession session = mock( HttpSession.class );

    Benutzer benutzer = new Benutzer();

    when( session.getAttribute( "aktiverBenutzer" ) ).thenReturn( benutzer );

    String result = viewController.home( session );

    assertEquals( "redirect:/vorgaenge", result );
  }

  @Test
  void testShowAllVorgaengeRequiresActiveUser()
  {
    HttpSession session = mock( HttpSession.class );

    when( session.getAttribute( "aktiverBenutzer" ) ).thenReturn( null );

    Model model = mock( Model.class );

    String view = viewController.showAllVorgaenge( 0, 10, model, session );

    assertEquals( "redirect:/epost", view );
  }


  @Test
  void testShowAllVorgaengePaginierungUndModel1()
  {
    /*
     * Mocks erstellen 
     */
    ServiceListVorgaenge mockVorgaenge = mock( ServiceListVorgaenge.class );
    
    ServiceListVorgangstypen mockVorgangstypen = mock( ServiceListVorgangstypen.class );
    
    EPostConfig mockConfig = mock( EPostConfig.class );
    
    RequestContext mockContext = mock( RequestContext.class );
    
    ServiceListKunde mockKunde = mock( ServiceListKunde.class );
    
    ServiceListBenutzer mockBenutzer = mock( ServiceListBenutzer.class );

    /*
     * Controller mit Mocks konstruieren 
     */
    ViewControllerVorgang vc = new ViewControllerVorgang( mockConfig, mockVorgaenge, mockContext, mockVorgangstypen, mockKunde, mockBenutzer );
    
    /*
     * Verhalten der Mocks 
     */
    HttpSession session = mock( HttpSession.class );
    
    Benutzer benutzer = new Benutzer();
    
    benutzer.setUserName( "tester" );

    when( session.getAttribute( "aktiverBenutzer" ) ).thenReturn( benutzer );

    Vorgang v1 = new Vorgang();
    
    Vorgang v2 = new Vorgang();
    
    List vorgangList = Arrays.asList( v1, v2 );

    when( mockVorgaenge.getListAktivitaetBearbeitung() ).thenReturn( vorgangList );
    
    when( mockVorgangstypen.getMapUidZuBezeichnung() ).thenReturn( Collections.emptyMap() );

    Model model = mock( Model.class );

    String view = vc.showAllVorgaenge( 0, 10, model, session );

    assertEquals( "vorgangs-list", view );
  }
}
