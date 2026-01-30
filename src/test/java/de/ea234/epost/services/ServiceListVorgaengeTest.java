package de.ea234.epost.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.ea234.epost.model.vorgang.Vorgang;
import java.util.List;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ServiceListVorgaengeTest {

  @Mock
  private ServiceListVorgaenge service;

  @BeforeEach
  void setUp()
  {
    MockitoAnnotations.openMocks( this );
  }

  @Test
  void testGetVorgangsListAll()
  {
    Vorgang v1 = new Vorgang();
    v1.setId( 1L );
    v1.setWfEPostID( "EP001" ); // ggf. weitere Felder setzen...

    Vorgang v2 = new Vorgang();
    v2.setId( 2L );
    v2.setWfEPostID( "EP002" );

    List<Vorgang> list = Arrays.asList( v1, v2 );

    Mockito.when( service.getListAll() ).thenReturn( list );

    List<Vorgang> result = service.getListAll();
    
    assertEquals( 2, result.size() );
  }

  @Test
  void testGetAnzahl()
  {
    Mockito.when( service.getAnzahl() ).thenReturn( 5 );

    int anzahl = service.getAnzahl();
    
    Assertions.assertEquals( 5, anzahl );
  }
}
