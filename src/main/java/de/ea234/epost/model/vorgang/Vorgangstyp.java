package de.ea234.epost.model.vorgang;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vorgangstyp")
@AllArgsConstructor
@Getter
@Setter
public class Vorgangstyp {

  public Vorgangstyp()
  {
  }

  @Override
  public String toString()
  {
    return "Vorgangstyp{" + "id=" + id + ", typNr=" + typNr + ", typLangText=" + typLangText + '}';
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "typ_nr", nullable = false, length = 5, unique = true)
  private String typNr;

  @NotBlank(message = "UID Langtext darf nicht leer sein")
  @Column(name = "typ_langtext", nullable = false, length = 150)
  private String typLangText;

  @NotBlank(message = "UID Kurztext darf nicht leer sein")
  @Column(name = "typ_kurz_text", nullable = false, length = 50)
  private String typKurzText;

  @Column(name = "typ_prioritaet", nullable = false)
  private int typPrioritaet;

  @NotBlank(message = "UID Abteilung darf nicht leer sein")
  @Column(name = "typ_abteilung", nullable = false, length = 20)
  private String typAbteilung;

  public boolean isTypNr( String pTypNr )
  {
    return typNr.equals( pTypNr );
  }
}
