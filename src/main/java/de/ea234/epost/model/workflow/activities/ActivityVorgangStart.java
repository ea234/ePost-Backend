package de.ea234.epost.model.workflow.activities;

import de.ea234.epost.model.vorgang.Vorgang;
import de.ea234.epost.model.vorgang.Vorgangstyp;
import de.ea234.epost.model.workflow.WfAktivitaet;
import de.ea234.epost.model.workflow.WorkflowAktivitaet;
import de.ea234.epost.model.workflow.WorkflowStatus;
import de.ea234.epost.services.ServiceListVorgangstypen;
import java.util.Calendar;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Setter
public class ActivityVorgangStart extends WfAktivitaet {

  private static final Logger log = LoggerFactory.getLogger( ActivityWorkflow.class );

  private ServiceListVorgangstypen serviceListVorgangstypen;

  public ActivityVorgangStart( WorkflowAktivitaet pWorkflowAktivitaetCur, WorkflowAktivitaet pWorkflowAktivitaetNext, WorkflowStatus pWorkflowStatus )
  {
    super( pWorkflowAktivitaetCur, pWorkflowAktivitaetNext, pWorkflowStatus );
  }

  @Override
  public int doActivity( Vorgang pVorgang )
  {
    log.info( String.format( "cActivityVorgangStart %16s %5s %16s ", pVorgang.getPaginierNr(), pVorgang.getVorgangTypNr(), pVorgang.getVertragNummer() ) );

    Vorgangstyp vt = serviceListVorgangstypen.getVorgangstyp( pVorgang.getVorgangTypNr() );

    pVorgang.setDatumEndeGeplant( vt.getDatumEndeGeplant( Calendar.getInstance().getTime() ) );

    if ( pVorgang.getWfStatus().equals( WorkflowStatus.NEU ) )
    {
      pVorgang.setWfStatus( WorkflowStatus.READY_TO_ROUTE.toString() );

      return 1;
    }

    return 1;
  }
}
