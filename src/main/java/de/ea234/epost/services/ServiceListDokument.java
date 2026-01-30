package de.ea234.epost.services;

import de.ea234.epost.model.dokument.Dokument;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ServiceListDokument {

  public List< Dokument> getList();

  public int getAnzahl();

  public Dokument getIndex( int pIndex );

  public Dokument getDokumentByPaginierNr( String pPaginierNr );

  public void add( Dokument pDokument );
}
