package de.ea234.epost.model.kunden;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "kunde_vertrag")
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(
  {
    "kunde"
  })
public class Vertrag {

  public Vertrag()
  {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Stammnummer darf nicht leer sein")
  @Column(name = "stamm_nr", nullable = false, length = 20)
  private String stammNummer;

  @Column(name = "vt_nr", nullable = false, length = 200)
  private String vertragsNummer;

  @Column(name = "datum_abschluss", nullable = false)
  private Date datumAbschluss;

  @Column(name = "datum_ende", nullable = true)
  private Date datumEnde;

  @NotBlank(message = "Bezeichnung darf nicht leer sein")
  @Column(name = "bezeichnung", nullable = false, length = 100)
  private String bezeichnung;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "kunde_id")
  @ToString.Exclude  // Wichtig für Lombok!
  private Kunde kunde;

}
