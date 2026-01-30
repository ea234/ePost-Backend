package de.ea234.epost.model.workflow;

import de.ea234.epost.config.EPostConfig;
import de.ea234.epost.repository.VorgangRepository;
import de.ea234.epost.model.vorgang.Vorgang;
import de.ea234.epost.model.workflow.activities.ActivityStatistikAbschluss;
import de.ea234.epost.model.workflow.activities.ActivityVorgangBearbeiten;
import de.ea234.epost.model.workflow.activities.ActivityVorgangStart;
import de.ea234.epost.model.workflow.activities.ActivityWorkflow;
import de.ea234.epost.repository.UnterlagenHistorieRepository;
import de.ea234.epost.services.ServiceListVorgangstypen;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class WorkflowBackgroundAgent implements Runnable {

  private static final Logger log = LoggerFactory.getLogger( WorkflowBackgroundAgent.class );

  private final EPostConfig ePostConfig;

  private final VorgangRepository vorgangRepository;

  private boolean isRunning = false;

  private boolean m_knz_verz_ueberwachung_laeuft = false;

  private UnterlagenHistorieRepository unterlagenHistorieRepository;

  private ServiceListVorgangstypen serviceListVorgangstypen;

  private List< WfAktivitaet> list_wf_aktivitaeten = null;

  public WorkflowBackgroundAgent( EPostConfig pEPostConfig, VorgangRepository pVorgangRepository, UnterlagenHistorieRepository pUnterlagenHistorieRepository, ServiceListVorgangstypen pServiceListVorgangstypen )
  {
    this.ePostConfig = pEPostConfig;

    this.vorgangRepository = pVorgangRepository;

    unterlagenHistorieRepository = pUnterlagenHistorieRepository;

    serviceListVorgangstypen = pServiceListVorgangstypen;

    isRunning = false;
  }

  @Override
  public void run()
  {
    doRun( "zeitablauf" );
  }

  public boolean doRun( String pModus )
  {
    if ( isRunning )
    {
      log.info( "WorkflowBackgroundAgent.run() " + pModus + " - Backgrounder ist bereits aktiv" );
    }
    else
    {
      isRunning = true;

      log.info( "WorkflowBackgroundAgent.run() " + pModus );

      doMove();

      doActivities();

      isRunning = false;
    }

    return true;
  }

  private int doMove()
  {
    try
    {
      doVorgangMove( WorkflowAktivitaet.START, WorkflowStatus.READY_TO_ROUTE, WorkflowAktivitaet.WORKFLOW );

      doVorgangMove( WorkflowAktivitaet.WORKFLOW, WorkflowStatus.READY_TO_ROUTE, WorkflowAktivitaet.VORGANG_BEARBEITEN );

      doVorgangMove( WorkflowAktivitaet.VORGANG_BEARBEITEN, WorkflowStatus.READY_TO_ROUTE, WorkflowAktivitaet.STATISTIK_ABSCHLUSS );

      doVorgangMove( WorkflowAktivitaet.STATISTIK_ABSCHLUSS, WorkflowStatus.READY_TO_ROUTE, WorkflowAktivitaet.ENDE );
    }
    catch (Exception exp)
    {
      log.error( "Fehler errWorkflowBackgroundAgent ", exp );
    }

    return 1;
  }

  private int doActivities()
  {
    System.out.println( "#######################################################" );
    System.out.println( "### doActivities  " );
    System.out.println( "#######################################################" );

    for ( WfAktivitaet cur_aktivitaet : getWfListAktivitaeten() )
    {
      List<Vorgang> vorgaenge = vorgangRepository.findByWfAktivitaetAndWfStatus( cur_aktivitaet.getWorkflowAktivitaetCur().toString(),
        cur_aktivitaet.getWorkflowStatus().toString() );

      doActivity( cur_aktivitaet, vorgaenge );

      doVorgangMove( cur_aktivitaet.getWorkflowAktivitaetCur(), WorkflowStatus.READY_TO_ROUTE, cur_aktivitaet.getWorkflowAktivitaetNext() );
    }

    return 1;
  }

  private int doVorgangMove( WorkflowAktivitaet pWfAktivitaet, WorkflowStatus pWfStatus, WorkflowAktivitaet pAktivitaetNext )
  {
    System.out.println( "#### doVorgangMove " + pWfAktivitaet + ", " + pWfStatus + " " + pAktivitaetNext );

    List<Vorgang> vorgaenge = vorgangRepository.findByWfAktivitaetAndWfStatus( pWfAktivitaet.toString(), pWfStatus.toString() );

    System.out.println( "#### doVorgangMove  Anzahl " + pWfAktivitaet + ", " + pWfStatus + " " + pAktivitaetNext + " " + vorgaenge.size() );

    for ( Vorgang cur_vorgang : vorgaenge )
    {
      System.out.println( "#### Vorgang PagNr " + cur_vorgang.getPaginierNr() + " Aktivität " + cur_vorgang.getWfAktivitaet() );

      cur_vorgang.setWfAktivitaet( pAktivitaetNext.toString() );

      if ( pAktivitaetNext == WorkflowAktivitaet.ENDE )
      {
        cur_vorgang.setWfStatus( WorkflowStatus.ABGESCHLOSSEN.toString() );
      }
      else
      {
        cur_vorgang.setWfStatus( WorkflowStatus.NEU.toString() );
      }

      vorgangRepository.save( cur_vorgang );
    }

    return vorgaenge.size();
  }

  @Transactional
  private int doActivity( WfAktivitaet pWfAktivitaet, List<Vorgang> pListVorgaenge )
  {
    System.out.println( "#######################################################" );
    System.out.println( "doAktivitaet  " + pWfAktivitaet.getWorkflowAktivitaetCur().toString() + " " + pListVorgaenge.size() );
    System.out.println( "#######################################################" );

    if ( pListVorgaenge != null )
    {
      for ( Vorgang cur_vorgang : pListVorgaenge )
      {
        /*
         * do vorgang
         */
        int activity_result = pWfAktivitaet.doActivity( cur_vorgang );

        if ( activity_result == 1 )
        {
          System.out.println( "#######################################################" );
          System.out.println( "Update " );
          System.out.println( "#######################################################" );

          cur_vorgang.setWfStatus( WorkflowStatus.READY_TO_ROUTE.toString() );

          vorgangRepository.save( cur_vorgang );
        }
      }
    }

    return 1;
  }

  private List<WfAktivitaet> getWfListAktivitaeten()
  {
    if ( list_wf_aktivitaeten == null )
    {
      list_wf_aktivitaeten = new ArrayList<WfAktivitaet>();

      list_wf_aktivitaeten.add( new ActivityVorgangStart( WorkflowAktivitaet.START, WorkflowAktivitaet.WORKFLOW, WorkflowStatus.NEU ) );

      list_wf_aktivitaeten.add( new ActivityWorkflow( WorkflowAktivitaet.WORKFLOW, WorkflowAktivitaet.VORGANG_BEARBEITEN, WorkflowStatus.NEU ) );

      list_wf_aktivitaeten.add( new ActivityVorgangBearbeiten( WorkflowAktivitaet.VORGANG_BEARBEITEN, WorkflowAktivitaet.STATISTIK_ABSCHLUSS, WorkflowStatus.ABGESCHLOSSEN ) );

      list_wf_aktivitaeten.add( new ActivityStatistikAbschluss( WorkflowAktivitaet.STATISTIK_ABSCHLUSS, WorkflowAktivitaet.ENDE, WorkflowStatus.NEU, unterlagenHistorieRepository, serviceListVorgangstypen ) );
    }

    return list_wf_aktivitaeten;
  }

}
