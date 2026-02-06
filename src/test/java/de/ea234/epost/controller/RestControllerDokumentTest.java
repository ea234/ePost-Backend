package de.ea234.epost.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.ea234.epost.services.ServiceListDokument;
import java.nio.charset.StandardCharsets;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestControllerDokumentTest.class)
public class RestControllerDokumentTest {

  private ServiceListDokument serviceListDokument;

  private RestControllerDokument controller;

  @BeforeEach
  void setUp()
  {
    serviceListDokument = Mockito.mock( ServiceListDokument.class );

    controller = new RestControllerDokument( serviceListDokument );
  }

  @Test
  void testUngueltigePaginiernummer()
  {
    // Ungueltige Paginiernummer z.B. "abc" -> erwartet 400 Bad Request mit Text 
    String invalid = "abc";

    ResponseEntity<byte[]> response = (ResponseEntity<byte[]>) controller.getDokumentByPaginiernummer( invalid );

    // Erwartung: 400 Bad Request und Textinhalt
    assertEquals( 400, response.getStatusCodeValue() );

    byte[] body = response.getBody();

    if ( body != null )
    {
      String text = new String( body, StandardCharsets.UTF_8 );

      assert text.contains( "Fehler" ) || text.contains( "Paginiernummer" );
    }
  }

  @Test
  void testDokumentNichtGefunden()
  {
    // Gueltige Paginiernummer, aber kein Dokument vorhanden 

    String validPagi = "2000012000000";

    Mockito.when( serviceListDokument.getDokumentByPaginierNr( validPagi ) ).thenReturn( null );

    ResponseEntity<byte[]> response = (ResponseEntity<byte[]>) controller.getDokumentByPaginiernummer( validPagi );

    // Erwartung: 404 Not Found mit Text "Fehler: Das Dokument fuer die Paginiernummer wurde nicht gefunden"
    assertEquals( 404, response.getStatusCodeValue() );

    byte[] body = response.getBody();

    if ( body != null )
    {
      String text = new String( body, StandardCharsets.UTF_8 );

      assert text.contains( "Das Dokument fuer die Paginiernummer" );
    }
  }
}
