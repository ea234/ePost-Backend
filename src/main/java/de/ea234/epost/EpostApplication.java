package de.ea234.epost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
@SpringBootApplication(exclude =
{
  org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration.class
})
public class EpostApplication {

  /*
   * Fuer eigenes Fehlerhandling muss die Klasse ErrorMvcAutoConfiguration ausgeschlossen werden.
   */
  public static void main( String[] args )
  {
    SpringApplication.run( EpostApplication.class, args );
  }

}
