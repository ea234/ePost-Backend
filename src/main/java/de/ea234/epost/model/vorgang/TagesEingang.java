package de.ea234.epost.model.vorgang;

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
@Table(name = "tageseingang")
@AllArgsConstructor
@Getter
@Setter
public class TagesEingang {

  public TagesEingang()
  {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "tages_datum", unique = true, nullable = false)
  private Long tagesDatum;

  @Column(name = "anzahl_eingaenge", nullable = false)
  private Long anzahleingaenge;

  public boolean istTagesdatum( long pDatum )
  {
    return tagesDatum.longValue() == pDatum;
  }
}
