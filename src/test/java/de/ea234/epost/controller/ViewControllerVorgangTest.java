package de.ea234.epost.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.ui.Model;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import jakarta.servlet.http.HttpSession;
import de.ea234.epost.services.ServiceListVorgangstypen;
import de.ea234.epost.services.ServiceListVorgaenge;
import de.ea234.epost.services.ServiceListKunde;
import de.ea234.epost.services.ServiceListBenutzer;
import de.ea234.epost.model.workflow.WorkflowStatus;
import de.ea234.epost.model.workflow.WorkflowAktivitaet;
import de.ea234.epost.model.vorgang.Vorgang;
import de.ea234.epost.model.kunden.Kunde;
import de.ea234.epost.model.kunden.Adresse;
import de.ea234.epost.model.benutzer.Benutzer;
import de.ea234.epost.model.RequestContext;
import de.ea234.epost.config.EPostConfig;

@WebMvcTest(ViewControllerVorgang.class)
public class ViewControllerVorgangTest {

  @Autowired
  private MockMvc mockMvc;

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

  @MockitoBean
  private HttpSession httpSession;

  @InjectMocks
  private de.ea234.epost.controller.ViewControllerVorgang viewController;

  private Benutzer benutzer;
  private Vorgang vorgang;
  private Kunde kunde;

  @BeforeEach
  void setup()
  {
    benutzer = new Benutzer();
    benutzer.setUserName( "testUser" );

    vorgang = new Vorgang();
    vorgang.setWfEPostID( "123" );
    vorgang.setStammNr( "STAMM1" );
    vorgang.setWfAktivitaet( WorkflowAktivitaet.VORGANG_BEARBEITEN.toString() );
    vorgang.setWfStatus( WorkflowStatus.NEU.toString() );
    vorgang.setBearbeiter( null );

    kunde = new Kunde();
    kunde.setAddresse( new Adresse() );

    when( httpSession.getAttribute( "aktiverBenutzer" ) )
      .thenReturn( benutzer );

    when( serviceListVorgaenge.getVorgangByEPostId( "123" ) )
      .thenReturn( vorgang );

    when( serviceListKunde.getKundeByStammnummer( "STAMM1" ) )
      .thenReturn( kunde );
  }

  @Test
  void testVorgangAnnehmen_erfolgreich() throws Exception
  {
    when( serviceListVorgaenge.setVorgangInBearbeitung( eq( "123" ), any() ) )
      .thenAnswer( inv ->
      {
        vorgang.setWfStatus( WorkflowStatus.IN_BEARBEITUNG.toString() );
        vorgang.setBearbeiter( "testUser" );
        return vorgang;
      } );

    mockMvc.perform( post( "/vorgang/123/claim" )
      .sessionAttr( "aktiverBenutzer", benutzer ) )
      .andExpect( status().isOk() )
      .andExpect( view().name( "vorgang-details" ) )
      .andExpect( model().attribute( "can_claim", true ) );

    verify( serviceListVorgaenge ).setVorgangInBearbeitung( "123", benutzer );
  }

  @Test
  void testVorgangAnnehmen_bereitsInBearbeitung() throws Exception
  {
    vorgang.setWfStatus( WorkflowStatus.IN_BEARBEITUNG.toString() );

    mockMvc.perform( post( "/vorgang/123/claim" )
      .sessionAttr( "aktiverBenutzer", benutzer ) )
      .andExpect( status().isOk() )
      .andExpect( view().name( "vorgang-details" ) )
      .andExpect( model().attributeExists( "error_message" ) )
      .andExpect( model().attribute( "error_message",
        "Fehler: Der Vorgang befindet sich bereits in Bearbeitung" ) );

    verify( serviceListVorgaenge, never() )
      .setVorgangInBearbeitung( any(), any() );
  }

  @Test
  void testVorgangAnnehmen_falscheAktivitaet() throws Exception
  {
    vorgang.setWfAktivitaet( WorkflowAktivitaet.WORKFLOW.toString() );

    mockMvc.perform( post( "/vorgang/123/claim" )
      .sessionAttr( "aktiverBenutzer", benutzer ) )
      .andExpect( status().isOk() )
      .andExpect( view().name( "vorgang-details" ) )
      .andExpect( model().attributeExists( "error_message" ) )
      .andExpect( model().attribute( "error_message",
        "Fehler: Der Vorgang befindet sich nicht in der Aktivitaet \"Vorgang Bearbeiten\" " ) );

    verify( serviceListVorgaenge, never() )
      .setVorgangInBearbeitung( any(), any() );
  }

  @Test
  void testVorgangAbschliessen_benutzerNichtBearbeiter() throws Exception
  {

    vorgang.setWfStatus( WorkflowStatus.IN_BEARBEITUNG.toString() );
    vorgang.setBearbeiter( "andererUser" );

    mockMvc.perform( post( "/vorgang/123/finish" )
      .sessionAttr( "aktiverBenutzer", benutzer ) )
      .andExpect( status().isOk() )
      .andExpect( view().name( "vorgang-details" ) )
      .andExpect( model().attributeExists( "error_message" ) )
      .andExpect( model().attribute( "error_message",
        "Fehler: Der Vorgang kann von Ihnen nicht beendet werden." ) );

    verify( serviceListVorgaenge, never() )
      .setVorgangBearbeitungAbgeschlossen( any(), any() );
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
