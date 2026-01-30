package de.ea234.epost.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.junit.jupiter.api.Test;

import de.ea234.epost.model.vorgang.Vorgang;
import java.util.List;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("h2")
public class VorgangRepositoryTest {

  @Autowired
  private VorgangRepository vorgangRepository;

  @Test
  void testCreateReadAndSearchVorgang()
  {
    // Einen Vorgang erstellen (ohne ID, da GeneratedValue) 
    Vorgang v = new Vorgang();
    v.setWfEPostID( "EP-1001" );
    v.setWfStatus( "OPEN" );
    v.setWfAktivitaet( "Eingegangen" );
    v.setPaginierNr( "P-0001" );
    v.setDatumEingang( new java.util.Date() );
    v.setDatumEndeGeplant( null );
    v.setDatumEndeTatsaechlich( null );
    v.setVertragNummer( "1234567" );
    v.setVorgangTypNr( "T-01" );
    v.setBearbeiter( null );

    // Speichern
    Vorgang saved = vorgangRepository.save( v );

    assertThat( saved.getId() ).isNotNull();

    // Lesen per ID
    Optional<Vorgang> readOpt = vorgangRepository.findById( saved.getId() );

    assertThat( readOpt ).isPresent();

    Vorgang read = readOpt.get();

    assertThat( read.getWfEPostID() ).isEqualTo( "EP-1001" );
    assertThat( read.getWfAktivitaet() ).isEqualTo( "Eingegangen" );

    // Suche nach wfAktivitaet
    List<Vorgang> byAktivitaet = vorgangRepository.findByWfAktivitaet( "Eingegangen" );

    assertThat( byAktivitaet ).isNotEmpty();
    assertThat( byAktivitaet ).extracting( Vorgang::getId ).contains( saved.getId() );

    // Suche nach wfAktivitaet und wfStatus
    List<Vorgang> byAktivitaetUndStatus = vorgangRepository.findByWfAktivitaetAndWfStatus( "Eingegangen", "OPEN" );

    assertThat( byAktivitaetUndStatus ).isNotEmpty();
    assertThat( byAktivitaetUndStatus.get( 0 ).getId() ).isEqualTo( saved.getId() );

    v.setWfStatus( "READY" );

    Vorgang saved2 = vorgangRepository.save( v );

    assertThat( saved2.getId() ).isNotNull();
    assertThat( saved2.getId() ).isEqualTo( saved.getId() );

    // Lesen per ID
    Optional<Vorgang> readOpt2 = vorgangRepository.findById( saved.getId() );

    assertThat( readOpt2 ).isPresent();

    Vorgang read2 = readOpt2.get();

    assertThat( saved2.getWfStatus() ).isEqualTo( "READY" );

  }
}
