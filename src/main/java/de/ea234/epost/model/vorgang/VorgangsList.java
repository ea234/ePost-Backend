package de.ea234.epost.model.vorgang;

import de.ea234.epost.model.benutzer.Benutzer;
import de.ea234.epost.model.workflow.WorkflowAktivitaet;
import de.ea234.epost.model.workflow.WorkflowStatus;
import de.ea234.epost.repository.VorgangRepository;
import de.ea234.epost.util.FkDatum;
import org.springframework.stereotype.Service;
import de.ea234.epost.services.ServiceListVorgaenge;
import de.ea234.epost.util.FkPaginiernummer;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VorgangsList implements ServiceListVorgaenge {

  private final VorgangRepository vorgangRepository;

  private final FkPaginiernummer fkPaginiernummer;

  @Override
  public List<Vorgang> getListAll()
  {
    return vorgangRepository.findAll();
  }

  @Override
  public int getAnzahl()
  {
    return getListAll().size();
  }

  public Vorgang add( Vorgang pVorgang )
  {
    getListAll().add( pVorgang );

    return pVorgang;
  }

  @Override
  public Vorgang setVorgangInBearbeitung( String pID, Benutzer pBenutzer )
  {
    Vorgang epost_vorgang = getVorgangByEPostId( pID );

    epost_vorgang.setBearbeiter( pBenutzer.getUserName() );

    epost_vorgang.setWfStatus( WorkflowStatus.IN_BEARBEITUNG.toString() );

    return epost_vorgang;
  }

  @Override
  public Vorgang setVorgangBearbeitungAbgeschlossen( String pID, Benutzer pBenutzer )
  {
    Vorgang epost_vorgang = getVorgangByEPostId( pID );

    epost_vorgang.setBearbeiter( pBenutzer.getUserName() );

    epost_vorgang.setWfStatus( WorkflowStatus.ABGESCHLOSSEN.toString() );

    epost_vorgang.setDatumEndeTatsaechlich( FkDatum.getDate() );

    return epost_vorgang;
  }

  @Override
  public Vorgang getVorgangByEPostId( String pEPostID )
  {
    return this.getListAll().stream().filter( epost_vorgang -> epost_vorgang.istEPostID( pEPostID ) ).findFirst().orElse( null );
  }

  @Override
  public List<Vorgang> getListUserName( String pBenutzerName )
  {
    return this.getListAll().stream().filter( epost_vorgang -> epost_vorgang.istBearbeiter( pBenutzerName ) ).collect( Collectors.toList() );
  }

  @Override
  public List<Vorgang> getListAktivitaetBearbeitung()
  {
    return this.getListAll().stream().filter( epost_vorgang -> epost_vorgang.istAktivitaet( WorkflowAktivitaet.VORGANG_BEARBEITEN.toString() ) ).collect( Collectors.toList() );
  }

  @Override
  public List<Vorgang> getListAktivitaetStart()
  {
    return this.getListAll().stream().filter( epost_vorgang -> epost_vorgang.istAktivitaet( WorkflowAktivitaet.START.toString() ) ).collect( Collectors.toList() );
  }

  @Override
  public List<Vorgang> getListAktivitaetEnde()
  {
    return this.getListAll().stream().filter( epost_vorgang -> epost_vorgang.istAktivitaet( WorkflowAktivitaet.ENDE.toString() ) ).collect( Collectors.toList() );
  }
}
