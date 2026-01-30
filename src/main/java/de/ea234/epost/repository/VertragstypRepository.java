package de.ea234.epost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.ea234.epost.model.vorgang.Vorgangstyp;
import java.util.Optional;

@Repository
public interface VertragstypRepository extends JpaRepository<Vorgangstyp, Long> {

  Optional<Vorgangstyp> findById( Long id );
}
