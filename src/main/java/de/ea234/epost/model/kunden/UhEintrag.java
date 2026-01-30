package de.ea234.epost.model.kunden;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "kunde_uh")
@AllArgsConstructor
@Getter
@Setter
public class UhEintrag {

  public UhEintrag()
  {
  }
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Stammnummer darf nicht leer sein")
  @Column(name = "stamm_nr", nullable = false, length = 20)
  private String stammNummer;

  @Column(name = "vertrag_nr", nullable = false, length = 20)
  private String vertragNummer;

  @NotBlank(message = "Paginiernummer darf nicht leer sein")
  @Column(name = "paginier_nr", nullable = false, length = 15)
  private String paginierNr;

  @Column(name = "datum_eingang", nullable = false)
  private Date datumEingang;

  @Column(name = "datum_erledigung", nullable = false)
  private Date datumErledigung;

  @NotBlank(message = "UID Kurztext darf nicht leer sein")
  @Column(name = "typ_kurz_text", nullable = false, length = 50)
  private String typKurzText;

  @Column(name = "bearbeiter", nullable = false, length = 20)
  private String bearbeiter;
}
