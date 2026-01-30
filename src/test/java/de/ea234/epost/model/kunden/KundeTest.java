package de.ea234.epost.model.kunden;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class KundeTest {

  private static Validator validator;

  @BeforeAll
  static void setupValidatorInstance()
  {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    validator = factory.getValidator();
  }

  @Test
  @DisplayName("Validierbar: korrekte Felder")
  void validKundeSollValidiertWerden()
  {
    Kunde k = new Kunde();
    k.setStammNummer( "SN-001" );
    k.setAnrede( "Herr" );
    k.setVorName( "Max" );
    k.setNachName( "Mustermann" );
    k.setEmail( "max@example.com" );

    Set<ConstraintViolation<Kunde>> violations = validator.validate( k );

    assertThat( violations ).isEmpty();
  }

  @Test
  @DisplayName("Validation fehlschlägt bei leeren Feldern")
  void validationFehltBeiLeeremStammNummer()
  {
    Kunde k = new Kunde();

    k.setStammNummer( null ); // @NotBlank 
    k.setAnrede( "Herr" );
    k.setVorName( "Max" );
    k.setNachName( "Mustermann" );
    k.setEmail( "max@example.com" );

    Set<ConstraintViolation<Kunde>> violations = validator.validate( k );

    assertThat( violations ).isNotEmpty();
  }

  @Test
  @DisplayName("Email-Validierung funktioniert")
  void invalidEmailSollValidierungErzeugen()
  {
    Kunde k = new Kunde();

    k.setStammNummer( "SN-002" );
    k.setAnrede( "Frau" );
    k.setVorName( "Erika" );
    k.setNachName( "Musterfrau" );
    k.setEmail( "nicht-gültig" );

    Set<ConstraintViolation<Kunde>> violations = validator.validate( k );

    assertThat( violations ).isNotEmpty();
  }
}
