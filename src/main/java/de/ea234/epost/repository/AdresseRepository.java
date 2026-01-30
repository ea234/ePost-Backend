package de.ea234.epost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ea234.epost.model.kunden.Adresse;

@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Long> {

}
