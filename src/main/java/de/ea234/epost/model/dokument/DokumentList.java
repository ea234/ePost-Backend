package de.ea234.epost.model.dokument;

import de.ea234.epost.repository.DokumentRepository;
import de.ea234.epost.services.ServiceListDokument;
import de.ea234.epost.services.TestDokumentErsteller;
import de.ea234.epost.util.FkPaginiernummer;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DokumentList implements ServiceListDokument {

  private static final Logger log = LoggerFactory.getLogger( DokumentList.class );

  private final DokumentRepository dokumentRepository;

  private final TestDokumentErsteller testDokumentErsteller;

  public DokumentList( DokumentRepository pDokumentRepository, TestDokumentErsteller pTestDokumentErsteller )
  {
    this.dokumentRepository = pDokumentRepository;

    this.testDokumentErsteller = pTestDokumentErsteller;
  }

  private List<Dokument> listDokument = null;

  public List<Dokument> getList()
  {
    if ( listDokument == null )
    {
      listDokument = new ArrayList<Dokument>();
    }

    return listDokument;
  }

  public int getAnzahl()
  {
    return getList().size();
  }

  public void add( Dokument pDokument )
  {
    getList().add( pDokument );
  }

  public Dokument getIndex( int pIndex )
  {
    return getList().get( pIndex );
  }

  public Dokument getDokumentByPaginierNr( String pPaginierNr )
  {
    if ( FkPaginiernummer.istPaginiernummer( pPaginierNr ) )
    {
      return dokumentRepository.findByPaginierNr( pPaginierNr );
    }

    return null;
  }
}
