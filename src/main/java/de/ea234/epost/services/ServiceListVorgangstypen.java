package de.ea234.epost.services;

import de.ea234.epost.model.vorgang.Vorgangstyp;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface ServiceListVorgangstypen {

  public List< Vorgangstyp> getList();

  public int getAnzahl();

  public Vorgangstyp getIndex( int pIndex );

  public Vorgangstyp getVorgangstyp( String pUid );
  
  public Map<String, String> getMapUidZuBezeichnung();
}
