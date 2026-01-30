package de.ea234.epost.model.kunden;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "kunde_adresse")
@AllArgsConstructor
@Getter
@Setter
public class Adresse {

  public Adresse()
  {
  }

  @Override
  public String toString()
  {
    return "Adresse{" + "strasse=" + strasse + ", ort=" + ort + ", postleitzahl=" + postleitzahl + '}';
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "strasse", nullable = false, length = 150)
  private String strasse;

  @Column(name = "ort", nullable = false, length = 150)
  private String ort;

  @Column(name = "plz", nullable = false, length = 10)
  private String postleitzahl;

  @Column(name = "land", nullable = false, length = 50)
  private String land;

  public String getPlzOrt()
  {
    return postleitzahl + " " + ort;
  }
}
