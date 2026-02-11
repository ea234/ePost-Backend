package de.ea234.epost.repository;

import de.ea234.epost.model.kunden.UhEintrag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnterlagenHistorieRepository extends JpaRepository<UhEintrag, Long> {

    List<UhEintrag> findByStammNummer( String stammNummer );
}
