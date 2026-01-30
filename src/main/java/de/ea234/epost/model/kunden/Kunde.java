package de.ea234.epost.model.kunden;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "kunde_basisdaten")
@AllArgsConstructor
@Getter
@Setter
public class Kunde {

  public Kunde()
  {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Stammnummer darf nicht leer sein")
  @Column(name = "stamm_nr", nullable = false, length = 20)
  private String stammNummer;

  @Column(name = "anrede", nullable = false, length = 10)
  private String anrede;

  @NotBlank(message = "Vorname darf nicht leer sein")
  @Column(name = "vor_name", nullable = false, length = 50)
  private String vorName;

  @NotBlank(message = "Nachname darf nicht leer sein")
  @Column(name = "nach_name", nullable = false, length = 50)
  private String nachName;

  @Email(message = "Email muss gültig sein")
  @Column(name = "email", unique = true, length = 255)
  private String email;

  @Column(name = "geb_datum", unique = false)
  private Date geburtsDatum;

  private String telefon;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "address_id")
  private Adresse addresse;

  public String getVorUndNachname()
  {
    return vorName + " " + nachName;
  }
    
  @OneToMany(
    mappedBy = "kunde",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    //fetch = FetchType.LAZY
    fetch = FetchType.EAGER
  )
  @ToString.Exclude  // Wichtig für Lombok!
  @JsonManagedReference
  private List< Vertrag> vertraege = new ArrayList< Vertrag>();

  /*
   * https://www.baeldung.com/hibernate-initialize-proxy-exception
   * org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: 
   */
  //
  public boolean istStammnummer( String pStammnummer )
  {
    return stammNummer.equals( pStammnummer );
  }

  public void addVertrag( Vertrag pVertrag )
  {
    vertraege.add( pVertrag );

    pVertrag.setKunde( this );
  }

  public void removeVertrag( Vertrag pVertrag )
  {
    vertraege.remove( pVertrag );

    pVertrag.setKunde( null );
  }
}
