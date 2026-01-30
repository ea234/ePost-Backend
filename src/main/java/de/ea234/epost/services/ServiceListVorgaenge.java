package de.ea234.epost.services;

import de.ea234.epost.model.benutzer.Benutzer;
import de.ea234.epost.model.vorgang.Vorgang;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ServiceListVorgaenge {

  public List<Vorgang> getListAll();

  public List<Vorgang> getListUserName( String pBenutzerName );

  public List<Vorgang> getListAktivitaetBearbeitung();

  public List<Vorgang> getListAktivitaetStart();

  public List<Vorgang> getListAktivitaetEnde();

  public int getAnzahl();

  public Vorgang getVorgangByEPostId( String pID );

  public Vorgang setVorgangInBearbeitung( String pID, Benutzer pBenutzer );

  public Vorgang setVorgangBearbeitungAbgeschlossen( String pID, Benutzer pBenutzer );

}
