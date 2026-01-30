package de.ea234.epost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ea234.epost.model.kunden.Kunde;

@Repository
public interface KundeRepository extends JpaRepository<Kunde, Long> {

}
