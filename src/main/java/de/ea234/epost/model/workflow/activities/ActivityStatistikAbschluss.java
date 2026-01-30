package de.ea234.epost.model.workflow.activities;

import de.ea234.epost.model.kunden.UhEintrag;
import de.ea234.epost.model.vorgang.Vorgang;
import de.ea234.epost.model.vorgang.Vorgangstyp;
import de.ea234.epost.model.workflow.WfAktivitaet;
import de.ea234.epost.model.workflow.WorkflowAktivitaet;
import de.ea234.epost.model.workflow.WorkflowStatus;
import de.ea234.epost.repository.UnterlagenHistorieRepository;
import de.ea234.epost.services.ServiceListVorgangstypen;
import de.ea234.epost.util.FkDatum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivityStatistikAbschluss extends WfAktivitaet {

  private static final Logger log = LoggerFactory.getLogger( ActivityWorkflow.class );

  private final UnterlagenHistorieRepository unterlagenHistorieRepository;

  private final ServiceListVorgangstypen serviceListVorgangstypen;

  public ActivityStatistikAbschluss( WorkflowAktivitaet pWorkflowAktivitaetCur, WorkflowAktivitaet pWorkflowAktivitaetNext, WorkflowStatus pWorkflowStatus, UnterlagenHistorieRepository pUnterlagenHistorieRepository, ServiceListVorgangstypen pServiceListVorgangstypen )
  {
    super( pWorkflowAktivitaetCur, pWorkflowAktivitaetNext, pWorkflowStatus );

    this.serviceListVorgangstypen = pServiceListVorgangstypen;

    this.unterlagenHistorieRepository = pUnterlagenHistorieRepository;
  }

  @Override
  public int doActivity( Vorgang pVorgang )
  {
    log.info( "ActivityStatistikAbschluss %16s %5s %16s ", pVorgang.getPaginierNr(), pVorgang.getVorgangTypNr(), pVorgang.getVertragNummer() );

    if ( unterlagenHistorieRepository == null )
    {
      log.error( "ActivityStatistikAbschluss: UH-Repository nicht gesetzt" );
    }
    else if ( serviceListVorgangstypen == null )
    {
      log.error( "ActivityStatistikAbschluss: List Vorgangstypen nicht gesetzt" );
    }
    else
    {
      log.info( "ActivityStatistikAbschluss: Schreibe unterlage fuer Vorgang" );

      Vorgangstyp vorgang_typ = serviceListVorgangstypen.getVorgangstyp( pVorgang.getVorgangTypNr() );

      UhEintrag uh_eintrag = new UhEintrag();

      uh_eintrag.setBearbeiter( pVorgang.getBearbeiter() != null ? pVorgang.getBearbeiter() : "unbekannt" );
      uh_eintrag.setPaginierNr( pVorgang.getPaginierNr() );
      uh_eintrag.setDatumEingang( pVorgang.getDatumEingang() );
      uh_eintrag.setDatumErledigung( FkDatum.getDate() );

      if ( vorgang_typ == null )
      {
        log.error( "Fehler Vorgangstyp nicht gefunden" );
      }
      else
      {
        uh_eintrag.setTypKurzText( vorgang_typ.getTypKurzText() );
      }

      uh_eintrag.setStammNummer( pVorgang.getStammNr() );

      uh_eintrag.setVertragNummer( pVorgang.getVertragNummer() );

      unterlagenHistorieRepository.save( uh_eintrag );

      return 1;
    }

    return 0;
  }
}
