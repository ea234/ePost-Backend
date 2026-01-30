package de.ea234.epost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.ea234.epost.model.dokument.Dokument;

@Repository
public interface DokumentRepository extends JpaRepository<Dokument, Long> {

  Dokument findByPaginierNr( String paginierNr );
}
