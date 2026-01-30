package de.ea234.epost.repository;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import de.ea234.epost.model.dokument.Dokument;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("h2")
public class DokumentRepositoryTest {

  @Autowired
  private DokumentRepository dokumentRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Test
  void testSaveAndFindDokument()
  {
    // given
    Dokument d = new Dokument();
    // Felder setzen (nur, was nötig ist, hier einige Pflichtfelder)
    d.setDokumentID( "DOC-001" );
    d.setDatumEingang( new Date() );
    d.setPaginierNr( "PN-12345" );
    d.setDateiTyp( "PDF" );
    // Datei-Daten optional, hier leer lassen

    // when
    Dokument saved = dokumentRepository.save( d );
    // ensure entity is flushed and ID generated
    entityManager.flush();

    // then
    Optional<Dokument> found = dokumentRepository.findById( saved.getId() );
    assertThat( found ).isPresent();
    assertThat( found.get().getDokumentID() ).isEqualTo( "DOC-001" );
  }

  @Test
  void testHasDokumentDatenHelpers()
  {
    Dokument d = new Dokument();
    d.setDokumentID( "DOC-002" );
    d.setDatumEingang( new Date() );
    d.setPaginierNr( "PN-54321" );
    d.setDateiTyp( "TXT" );
    // dateiDaten nicht setzen, daher null

    dokumentRepository.save( d );
    entityManager.flush();

    Optional<Dokument> optional = dokumentRepository.findById( d.getId() );
    assertThat( optional ).isPresent();
    Dokument loaded = optional.get();

    assertThat( loaded.hasDokumentDaten() ).isFalse();
    assertThat( loaded.hasNoDokumentDaten() ).isTrue();
  }
}
