package de.ea234.epost.model.workflow.activities;

import de.ea234.epost.model.vorgang.Vorgang;
import de.ea234.epost.model.workflow.WfAktivitaet;
import de.ea234.epost.model.workflow.WorkflowAktivitaet;
import de.ea234.epost.model.workflow.WorkflowStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivityVorgangBearbeiten extends WfAktivitaet {

  private static final Logger log = LoggerFactory.getLogger( ActivityWorkflow.class );

  public ActivityVorgangBearbeiten( WorkflowAktivitaet pWorkflowAktivitaetCur, WorkflowAktivitaet pWorkflowAktivitaetNext, WorkflowStatus pWorkflowStatus )
  {
    super( pWorkflowAktivitaetCur, pWorkflowAktivitaetNext, pWorkflowStatus );
  }

  @Override
  public int doActivity( Vorgang pVorgang )
  {
    if ( pVorgang.istStatus( WorkflowStatus.ABGESCHLOSSEN.toString() ) )
    {
      pVorgang.setWfStatus( WorkflowStatus.READY_TO_ROUTE.toString() );

      log.info( String.format( "ActivityVorgangBearbeiten %16s %5s %16s   ABGESCHLOSSEN", pVorgang.getPaginierNr(), pVorgang.getVorgangTypNr(), pVorgang.getVertragNummer() ) );

      return 1; // 1 = speichere den Vorgang im Background-Agenten
    }

    log.info( String.format( "ActivityVorgangBearbeiten %16s %5s %16s   Noch nicht abgeschlossen " +  pVorgang.getWfStatus(), pVorgang.getPaginierNr(), pVorgang.getVorgangTypNr(), pVorgang.getVertragNummer() ) );

    return 0; // keine Veraenderung im Background-Agent
  }
}
