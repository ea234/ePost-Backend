package de.ea234.epost.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import de.ea234.epost.config.EPostConfig;
import de.ea234.epost.model.RequestContext;
import de.ea234.epost.model.benutzer.Benutzer;
import de.ea234.epost.model.kunden.Kunde;
import de.ea234.epost.repository.UnterlagenHistorieRepository;
import de.ea234.epost.services.ServiceListBenutzer;
import de.ea234.epost.services.ServiceListKunde;
import de.ea234.epost.services.ServiceListVorgaenge;
import de.ea234.epost.services.ServiceListVorgangstypen;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ViewControllerKunde.class)
public class ViewControllerKundeTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private EPostConfig ePostConfig;

  @MockitoBean
  private ServiceListVorgaenge serviceListVorgaenge;

  @MockitoBean
  private ServiceListVorgangstypen serviceListVorgangstypen;

  @MockitoBean
  private ServiceListKunde serviceListKunde;

  @MockitoBean
  private ServiceListBenutzer serviceListBenutzer;

  @MockitoBean
  private UnterlagenHistorieRepository unterlagenHistorieRepository;

  @MockitoBean
  private RequestContext requestContext;

  private final static String SESSION_ATTR_BENUTZER = "aktiverBenutzer";

  @BeforeEach
  void setup()
  { 
    Mockito.when( ePostConfig.getName() ).thenReturn( "DemoApp" );
    
    Mockito.when( ePostConfig.getVersion() ).thenReturn( "1.0" );
  }

  @Test
  void testShowKundenList_keinAktiverBenutzer_redirect() throws Exception
  {
    mockMvc.perform( get( "/kunden" ) )
      .andExpect( status().is3xxRedirection() )
      .andExpect( view().name( "redirect:/epost" ) );
  }

  @Test
  void testShowKundenList_ok() throws Exception
  {
    Benutzer benutzer = new Benutzer();
    Mockito.when( serviceListKunde.getList() ).thenReturn( Arrays.asList( new Kunde(), new Kunde(), new Kunde() ) );

    mockMvc.perform( get( "/kunden" )
      .sessionAttr( SESSION_ATTR_BENUTZER, benutzer )
      .param( "page", "0" )
      .param( "size", "2" ) )
      .andExpect( status().isOk() )
      .andExpect( view().name( "kunde-list" ) )
      .andExpect( model().attributeExists( "benutzer" ) )
      .andExpect( model().attributeExists( "kunden" ) )
      .andExpect( model().attributeExists( "appName" ) )
      .andExpect( model().attributeExists( "appVersion" ) );
  }
}
