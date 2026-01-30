package de.ea234.epost.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ea234.epost.model.benutzer.Benutzer;
import de.ea234.epost.services.ServiceListBenutzer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestControllerBenutzer.class)
public class RestControllerBenutzerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ServiceListBenutzer benutzer_service;

  private final ObjectMapper objectMapper = new ObjectMapper();

  private Benutzer sampleBenutzer;

  @BeforeEach
  void setUp()
  {
    sampleBenutzer = new Benutzer();

    sampleBenutzer.setId( 1L );
    sampleBenutzer.setUserName( "stpbe" );
    sampleBenutzer.setVorName( "Vorname" );
    sampleBenutzer.setNachName( "Nachname" );
    sampleBenutzer.setEmail( "vorname.nachname@web.de" );
  }

  @Test
  void testPingEndpoint() throws Exception
  {
    mockMvc.perform( get( "/api/benutzer/ping" ) ).andExpect( status().isOk() ).andExpect( content().string( org.hamcrest.Matchers.containsString( "RestControllerBenutzer" ) ) );
  }

  @Test
  void testGetAllUser() throws Exception
  {
    List benutzerList = Collections.singletonList( sampleBenutzer );

    Mockito.when( benutzer_service.getList() ).thenReturn( benutzerList );

    mockMvc.perform( get( "/api/benutzer" ) )
      .andExpect( status().isOk() )
      .andExpect( content().contentType( "application/json" ) );

  }

  @Test
  void testGetAnzahl() throws Exception
  {
    Mockito.when( benutzer_service.getAnzahl() ).thenReturn( 1 );

    mockMvc.perform( get( "/api/benutzer/count" ) )
      .andExpect( status().isOk() )
      .andExpect( content().string( "1" ) );
  }
}
