package de.ea234.epost.controller;

import de.ea234.epost.config.EPostConfig;
import de.ea234.epost.model.workflow.VerzeichnisUeberwacher;
import de.ea234.epost.model.workflow.WorkflowBackgroundAgent;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

  /*
   * ****************************************************************************
   * Klasse fuer die zeitgesteuerten Aufgaben 
   * - Start der Verzeichnisueberwachung auf neue Eingaenge
   * - Start des Background-Agentens
   * ****************************************************************************
   */

  private static final Logger log = LoggerFactory.getLogger( ScheduledTasks.class );

  private ScheduledExecutorService m_scheduled_executor_service = null;

  private final EPostConfig ePostConfig;

  private final VerzeichnisUeberwacher m_verz_ueberwacher;

  private final WorkflowBackgroundAgent m_workflow_background_agent;

  public ScheduledTasks( EPostConfig pEPostConfig, VerzeichnisUeberwacher pVerzeichnisUeberwacher, WorkflowBackgroundAgent pWorkflowBackgroundAgent )
  {
    ePostConfig = pEPostConfig;

    m_verz_ueberwacher = pVerzeichnisUeberwacher;

    m_workflow_background_agent = pWorkflowBackgroundAgent;
  }

  @PostConstruct
  public void init()
  {
    log.debug( "ScheduledTasks.init() " );

    /*
     * java.util.concurrent.ScheduledExecutorService.scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)
     * 
     * Creates and executes a periodic action that becomes enabled first after 
     * the  given initial delay, and subsequently with the given period, that is
     * executions  will commence after initialDelay then initialDelay + period, 
     * then  initialDelay + 2 * period, and so on. 
     * 
     * If any execution of the task encounters an exception, subsequent executions
     * are suppressed. Otherwise, the task will only terminate via cancellation or 
     * termination of the executor. 
     * 
     * If any execution of this task takes longer than its period, then subsequent 
     * executions may start late, but will not concurrently execute.
     * 
     * Parameters:
     * 
     *  command the task to do
     *  executeinitialDelay the time to delay first 
     *  executionperiod the period between successive 
     *  executionsunit the time unit of the initialDelay and period parameters
     *  
     * Returns:
     * 
     *  a ScheduledFuture representing pending completion of the task, and whose
    *   get() method will throw an exception upon cancellation
     *  
     * Throws:
     * 
     * RejectedExecutionException - if the task cannot be scheduled for execution
     * NullPointerException       - if command is null
     * IllegalArgumentException   - if period less than or equal to zero
     *  
     */
    m_scheduled_executor_service = Executors.newSingleThreadScheduledExecutor();

    m_scheduled_executor_service.scheduleAtFixedRate( m_verz_ueberwacher, 15, ePostConfig.getWfIntervallVzUeberwachung(), TimeUnit.MINUTES );

    m_scheduled_executor_service.scheduleAtFixedRate( m_workflow_background_agent, 25, ePostConfig.getWfIntervallBackgrounder(), TimeUnit.MINUTES );

    // scheduler.scheduleAtFixedRate( new SomeDailyJob(),     0,  1, TimeUnit.DAYS    );
    // scheduler.scheduleAtFixedRate( new SomeHourlyJob(),    0,  1, TimeUnit.HOURS   );
    // scheduler.scheduleAtFixedRate( new SomeQuarterlyJob(), 0, 15, TimeUnit.MINUTES );
    //
    // public class SomeDailyJob implements Runnable
    // {
    //   public void run()
    //   {
    //     // Do your job here.
    //   }
    // }
  }

  @PreDestroy
  public void cleanup()
  {
    log.debug( "ScheduledTasks.cleanup() " );

    try
    {
      m_scheduled_executor_service.shutdownNow();
    }
    catch (Exception err_inst)
    {
      log.error( "Fehler CleanUp ScheduledTasks", err_inst );
    }

    m_scheduled_executor_service = null;
  }
}
