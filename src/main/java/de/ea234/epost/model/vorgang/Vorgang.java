package de.ea234.epost.model.vorgang;

import de.ea234.epost.model.workflow.WorkflowAktivitaet;
import de.ea234.epost.model.workflow.WorkflowStatus;
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
@Table(name = "vorgangliste")
@AllArgsConstructor
@Getter
@Setter
public class Vorgang {

  public Vorgang()
  {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "wf_epost_id", nullable = false, length = 40)
  private String wfEPostID;

  @Column(name = "wf_status", nullable = false, length = 40)
  private String wfStatus;

  @Column(name = "wf_aktivitaet", nullable = false, length = 40)
  private String wfAktivitaet;

  @NotBlank(message = "Paginiernummer darf nicht leer sein")
  @Column(name = "paginier_nr", nullable = false, length = 15)
  private String paginierNr;

  @Column(name = "datum_eingang", nullable = false)
  private Date datumEingang;

  @Column(name = "datum_ende_geplant", nullable = true)
  private Date datumEndeGeplant;

  @Column(name = "datum_ende_tatsaechlich", nullable = true)
  private Date datumEndeTatsaechlich;

  @Column(name = "vertrag_stamm_nr", nullable = true, length = 20)
  private String stammNr;

  @Column(name = "vertrag_nr", nullable = false, length = 20)
  private String vertragNummer;

  @Column(name = "vorgang_typ_nr", nullable = false, length = 5)
  private String vorgangTypNr;

  @Column(name = "bearbeiter", nullable = true, length = 20)
  private String bearbeiter;

  public boolean istEPostID( String pEPostID )
  {
    return wfEPostID.equals( pEPostID );
  }

  public String getBearbeiterUi()
  {
    if ( bearbeiter == null )
    {
      return "";
    }

    return bearbeiter;
  }

  public boolean istBearbeiter( String pBenutzerName )
  {
    if ( bearbeiter == null )
    {
      return false;
    }

    return bearbeiter.equals( pBenutzerName );
  }

  public boolean istAktivitaet( String pWorkflowAktivitaet )
  {
    if ( wfAktivitaet == null )
    {
      return false;
    }

    return wfAktivitaet.equals( pWorkflowAktivitaet );
  }

  public boolean istStatus( String pWorkflowStatus )
  {
    if ( wfStatus == null )
    {
      return false;
    }

    return wfStatus.equals( pWorkflowStatus );
  }
}
