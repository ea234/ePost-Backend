package de.ea234.epost.repository;

import de.ea234.epost.model.benutzer.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenutzerRepository  extends JpaRepository<Benutzer, Long> {

}