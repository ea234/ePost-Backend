package de.ea234.epost.repository;

import de.ea234.epost.model.vorgang.Vorgang;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VorgangRepository extends JpaRepository<Vorgang, Long> {

  List<Vorgang> findByWfAktivitaet( String wfAktivitaet );

  List<Vorgang> findByWfAktivitaetAndWfStatus( String wfAktivitaet, String wfStatus );
}
