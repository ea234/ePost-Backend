package de.ea234.epost.model.kunden;

import de.ea234.epost.repository.KundeRepository;
import de.ea234.epost.services.ServiceListKunde;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KundeList implements ServiceListKunde {

  private static final Logger log = LoggerFactory.getLogger( KundeList.class );

  private final KundeRepository kundeRepository;

  public KundeList( KundeRepository pVertragstypRepository )
  {
    this.kundeRepository = pVertragstypRepository;
  }

  @Override
  public List<Kunde> getList()
  {
    return kundeRepository.findAll();
  }

  public List<Kunde> getSortedList( String pSortedBy, String pSortOrder )
  {
    List<Kunde> sorted = getList();

    Comparator<Kunde> comparator = switch ( pSortedBy.toLowerCase() )
    {
      case "vorname" ->
        Comparator.comparing( Kunde::getVorName );

      case "nachname" ->
        Comparator.comparing( Kunde::getNachName );

      case "stammnummer" ->
        Comparator.comparing( Kunde::getStammNummer );

      default ->
        Comparator.comparing( Kunde::getId );
    };

    if ( "desc".equalsIgnoreCase( pSortOrder ) )
    {
      comparator = comparator.reversed();
    }

    sorted.sort( comparator );

    return sorted;
  }

  @Override
  public int getAnzahl()
  {
    return getList().size();
  }

  @Override
  public void add( Kunde pKunde )
  {
    Kunde bestands_kunde = getKundeByStammnummer( pKunde.getStammNummer() );

    if ( bestands_kunde == null )
    {
      kundeRepository.save( pKunde );
    }
  }

  @Override
  public Kunde getIndex( int pIndex )
  {
    return getList().get( pIndex );
  }

  @Override
  public Kunde getKundeByStammnummer( String pStammnummer )
  {
    return this.getList().stream().filter( p_kunde -> p_kunde.istStammnummer( pStammnummer ) ).findFirst().orElse( null );
  }

}
