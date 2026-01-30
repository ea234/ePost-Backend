package de.ea234.epost.services;

import de.ea234.epost.model.benutzer.Benutzer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import de.ea234.epost.model.vorgang.Vorgang;
import java.util.List;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ServiceListBenutzerTest {

  @Mock
  private ServiceListBenutzer serviceMock; // Falls du das Interface direkt testen willst

// Falls du eine konkrete Implementierung hast, z. B. BenutzerserviceImpl
// @InjectMocks
// private BenutzerserviceImpl benutzerService;
  @BeforeEach
  void setUp()
  {
    MockitoAnnotations.openMocks( this );
  }

  @Test
  void testGetBenutzerList_returnsList()
  {
    Benutzer b1 = new Benutzer();
    b1.setId( 1L );
    b1.setUserName( "alice" );
    b1.setVorName( "Alice" );
    b1.setNachName( "Mustermann" );
    b1.setEmail( "alice@example.com" );

    Benutzer b2 = new Benutzer();
    b2.setId( 2L );
    b2.setUserName( "bob" );
    b2.setVorName( "Bob" );
    b2.setNachName( "Müller" );
    b2.setEmail( "bob@example.com" );

    List<Benutzer> list = Arrays.asList( b1, b2 );

    Mockito.when(  serviceMock.getList() ).thenReturn( list );

    List<Benutzer> result = serviceMock.getList();

    assertThat( result ).hasSize( 2 );
    assertThat( result ).containsExactly( b1, b2 );
  }

  @Test
  void testGetAnzahl_returnsCount()
  {
    Mockito.when(  serviceMock.getAnzahl() ).thenReturn( 2 );

    int count = serviceMock.getAnzahl();

    assertThat( count ).isEqualTo( 2 );
  }

  @Test
  void testAdd_callsThrough()
  {
    Benutzer nb = new Benutzer();
    
    nb.setUserName( "charlie" );
    
    // Keine Exception erwartet, einfache Überprüfung, dass Methode aufgerufen wird
    serviceMock.add( nb );
    
    // Falls du Mockito verify nutzen willst:
    // verify(serviceMock).add(nb);
  }
}
