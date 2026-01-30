package de.ea234.epost.model;

import de.ea234.epost.model.benutzer.BenutzerList;
import de.ea234.epost.model.kunden.KundeList;
import de.ea234.epost.model.vorgang.VorgangstypList;
import de.ea234.epost.model.workflow.WorkflowBackgroundAgent;
import de.ea234.epost.services.TestDokumentErsteller;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AppTestInit {

  private final BenutzerList benutzerList;

  private final VorgangstypList vorgangstypList;

  private final KundeList kundeList;

  private final TestDokumentErsteller testDokumentErsteller;

  private final WorkflowBackgroundAgent workflowBackgroundAgent;

  @EventListener(ApplicationReadyEvent.class)
  public void initTestdaten()
  {
    benutzerList.init();

    vorgangstypList.init();

    kundeList.init();

    testDokumentErsteller.doErstelleTestEingangJeKunde();

    workflowBackgroundAgent.doRun( "Test" );
  }
}
