package de.ea234.epost.model.workflow;

import de.ea234.epost.model.vorgang.Vorgang;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

public abstract class WfAktivitaet
{
  private WorkflowAktivitaet m_workflow_aktivitaet_cur = null;

  private WorkflowAktivitaet m_workflow_aktivitaet_next = null;

  private WorkflowStatus m_workflow_status = null;
  
  public WfAktivitaet( WorkflowAktivitaet pWorkflowAktivitaetCur, WorkflowAktivitaet pWorkflowAktivitaetNext, WorkflowStatus pWorkflowStatus )
  {
    m_workflow_aktivitaet_cur = pWorkflowAktivitaetCur;
    m_workflow_aktivitaet_next = pWorkflowAktivitaetNext;
    m_workflow_status = pWorkflowStatus;
  }
  
  public WorkflowAktivitaet getWorkflowAktivitaetCur()
  {
    return m_workflow_aktivitaet_cur;
  }
  
  public WorkflowAktivitaet getWorkflowAktivitaetNext()
  {
    return m_workflow_aktivitaet_next;
  }
  
  public WorkflowStatus getWorkflowStatus()
  {
    return m_workflow_status;
  }
  
  public abstract int doActivity( Vorgang pVorgang );
}

