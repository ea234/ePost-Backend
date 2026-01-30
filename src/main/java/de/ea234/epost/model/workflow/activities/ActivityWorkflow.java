package de.ea234.epost.model.workflow.activities;

import de.ea234.epost.model.vorgang.Vorgang;
import de.ea234.epost.model.workflow.WfAktivitaet;
import de.ea234.epost.model.workflow.WorkflowAktivitaet;
import de.ea234.epost.model.workflow.WorkflowStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivityWorkflow extends WfAktivitaet {

  private static final Logger log = LoggerFactory.getLogger( ActivityWorkflow.class );

  public ActivityWorkflow( WorkflowAktivitaet pWorkflowAktivitaetCur, WorkflowAktivitaet pWorkflowAktivitaetNext, WorkflowStatus pWorkflowStatus )
  {
    super( pWorkflowAktivitaetCur, pWorkflowAktivitaetNext, pWorkflowStatus );
  }

  @Override
  public int doActivity( Vorgang pVorgang )
  {
    log.info( "ActivityWorkflow %16s %5s %16s ", pVorgang.getPaginierNr(), pVorgang.getVorgangTypNr(), pVorgang.getVertragNummer() );
    
    /*
     * Platzhalter Aktivitaet
     *
     * Normalerweise werden in dieser Aktivitaet automatische Workflows ausgefuehrt.
     * Es koennte hier z.B. ein Aufruf an einen weiteren Service erfolgen.
     * Es koennte hier z.B. ein Aufruf einer Stored-Procedure erfolgen.
     *
     * Das wuerde den Rahmen des Referenzprojektes sprengen. 
     * Es geht hier lediglich darum, die Funktion fuer die Bearbeitung von 
     * Vorgaengen zu implementieren.
     */

    return 1;
  }
}
