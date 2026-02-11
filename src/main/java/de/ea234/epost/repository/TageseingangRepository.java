package de.ea234.epost.repository;

import de.ea234.epost.model.vorgang.TagesEingang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TageseingangRepository extends JpaRepository<TagesEingang, Long> {

}
