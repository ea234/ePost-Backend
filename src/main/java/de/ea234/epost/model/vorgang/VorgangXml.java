package de.ea234.epost.model.vorgang;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class VorgangXml {

  /*
   * VorgangXML
   * Stellt die XML-Datei dar, welche vom Scan-Dienstleister zur Verfügung gestellt wird.
   * Diese Klasse beinhaltet noch keinie genauen Kundeninformationen.
   * Diese Klasse kennt nur die Vorgangstyp-Nummer (=UID), aber nicht deren Bezeichnung.
   * 
   * Diese Klasse wird nicht in die Datenbank geschrieben. 
   * Aus diesen Daten wird der eigentliche Vorgang, welcher dann in die DB geschrieben wird.
   */
  public VorgangXml()
  {
  }

  @NotBlank(message = "Paginiernummer darf nicht leer sein")
  private String paginierNr;

  @NotNull(message = "Datum Eingang darf nicht leer sein")
  @Size(min = 8, max = 8, message = "Datum Eingang muss 8 Stellen haben (JJJJMMTT)")
  private String datumEingang;

  @NotBlank(message = "Stammnummer darf nicht leer sein")
  @Size(min = 7, max = 20, message = "Stammnummer Längengrenzen nicht eingehalten")
  private String stammNr;

  @NotBlank(message = "Vertragsnummer darf nicht leer sein")
  @Size(min = 7, max = 20, message = "Vertragsnummer Längengrenzen nicht eingehalten")
  private String vertragNummer;

  @NotBlank(message = "Vorgangstyp darf nicht leer sein")
  @Size(min = 7, max = 20, message = "Vorgangtyp Längengrenzen nicht eingehalten")
  private String vorgangTypNr;
}
