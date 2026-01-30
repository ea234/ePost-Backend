package de.ea234.epost.services;

import de.ea234.epost.model.benutzer.Benutzer;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ServiceListBenutzer {

  public List< Benutzer> getList();

  public int getAnzahl();

  public void add( Benutzer pBenutzer );
  
  public Benutzer getIndex( int pIndex );
  
  public Benutzer getBenutzerByUserName( String pUserName );
}
