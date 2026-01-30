package de.ea234.epost.repository;

import de.ea234.epost.model.vorgang.Vorgangstyp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VorgangstypRepository extends JpaRepository<Vorgangstyp, Long> 
{
  
}
