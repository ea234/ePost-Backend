package de.ea234.epost.model.dokument;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "archiv_daten")
@Component
@Getter
@Setter
@AllArgsConstructor
public class Dokument {

  public Dokument()
  {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "dok_id", nullable = false, length = 40)
  private String dokumentID;

  @Column(name = "dok_datum_eingang", nullable = false)
  private Date datumEingang;

  @NotBlank(message = "Paginiernummer darf nicht leer sein")
  @Column(name = "dok_paginier_nr", unique = true, nullable = false, length = 15)
  private String paginierNr;

  @Column(name = "dok_datei_typ", nullable = false, length = 15)
  private String dateiTyp;

  @Lob
  @Column(name = "dok_datei_daten", nullable = true, columnDefinition = "BLOB")
  private byte[] dateiDaten;

  public boolean hasDokumentDaten()
  {
    return (dateiDaten != null) && (dateiDaten.length > 0);
  }

  public boolean hasNoDokumentDaten()
  {
    return  ! ((dateiDaten != null) && (dateiDaten.length > 0));
  }
}
