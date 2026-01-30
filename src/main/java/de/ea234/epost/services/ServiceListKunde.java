package de.ea234.epost.services;

import de.ea234.epost.model.kunden.Kunde;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ServiceListKunde {

  public List< Kunde> getList();

  public int getAnzahl();

  public Kunde getIndex( int pIndex );
  
  public Kunde getKundeByStammnummer( String pStammnummer );

  public void add( Kunde pKunde );
}
