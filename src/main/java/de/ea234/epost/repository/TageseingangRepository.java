package de.ea234.epost.repository;

import de.ea234.epost.model.vorgang.TagesEingang;
import de.ea234.epost.model.vorgang.Vorgang;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TageseingangRepository extends JpaRepository<TagesEingang, Long> {

}
