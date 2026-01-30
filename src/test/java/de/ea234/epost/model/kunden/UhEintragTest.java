package de.ea234.epost.model.kunden;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Date;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.ea234.epost.model.kunden.UhEintrag;
import org.junit.jupiter.api.Assertions;

public class UhEintragTest {

  private static Validator validator;

  @BeforeAll
  public static void setupValidatorInstance()
  {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    validator = factory.getValidator();
  }

  @Test
  public void testValidUhEintrag()
  {
    // Instanz ohne AllArgsConstructor verwenden
    UhEintrag eintrag = new UhEintrag();

    // Explizite Setters verwenden
    eintrag.setId( 1L );
    eintrag.setStammNummer( "SN-12345" );
    eintrag.setVertragNummer( "VN-67890" );
    eintrag.setPaginierNr( "PAG-001" );
    eintrag.setDatumEingang( new Date( 1685577600000L ) ); // Beispiel-Datum
    eintrag.setDatumErledigung( new Date( 1672531200000L ) ); // Beispielzeitpunkt
    eintrag.setTypKurzText( "KURZTEXT" );
    eintrag.setBearbeiter( "MA-01" );

    Set< ConstraintViolation< UhEintrag>> violations = validator.validate( eintrag );

    assertThat( violations ).isEmpty();

  }

  @Test
  public void testNotBlankStammNummer()
  {
    // Instanz ohne AllArgsConstructor verwenden
    UhEintrag eintrag = new UhEintrag();

    // Explizite Setters verwenden
    eintrag.setId( 1L );
    eintrag.setStammNummer( "SN-12345" );
    eintrag.setStammNummer( null );

    eintrag.setVertragNummer( "VN-67890" );
    eintrag.setPaginierNr( "PAG-001" );
    eintrag.setDatumEingang( new Date( 1685577600000L ) ); // Beispiel-Datum
    eintrag.setDatumErledigung( new Date( 1672531200000L ) ); // Beispielzeitpunkt
    eintrag.setTypKurzText( "KURZTEXT" );
    eintrag.setBearbeiter( "MA-01" );

    Set< ConstraintViolation< UhEintrag>> violations = validator.validate( eintrag );
    
    assertThat( violations ).extracting( v -> v.getPropertyPath().toString() )
      .contains( "stammNummer" );
  }

  @Test
  public void testNotBlankPaginierNr()
  {
    // Instanz ohne AllArgsConstructor verwenden
    UhEintrag eintrag = new UhEintrag();

    // Explizite Setters verwenden
    eintrag.setId( 1L );
    eintrag.setStammNummer( "SN-12345" );
    eintrag.setVertragNummer( "VN-67890" );
    eintrag.setPaginierNr( "PAG-001" );
    eintrag.setDatumEingang( new Date( 1685577600000L ) ); // Beispiel-Datum
    eintrag.setDatumErledigung( new Date( 1672531200000L ) ); // Beispielzeitpunkt
    eintrag.setTypKurzText( "KURZTEXT" );
    eintrag.setBearbeiter( "MA-01" );

    
    eintrag.setPaginierNr( "PAG-001" );
    eintrag.setPaginierNr( null );
    

    Set< ConstraintViolation< UhEintrag>> violations = validator.validate( eintrag );

    assertThat( violations ).extracting( v -> v.getPropertyPath().toString() ).contains( "paginierNr" );
  }
}
