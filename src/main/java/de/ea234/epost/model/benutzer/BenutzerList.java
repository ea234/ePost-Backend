package de.ea234.epost.model.benutzer;

import de.ea234.epost.repository.BenutzerRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import org.springframework.stereotype.Service;
import de.ea234.epost.services.ServiceListBenutzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Getter
@Setter
public class BenutzerList implements ServiceListBenutzer {

  private static final Logger log = LoggerFactory.getLogger( BenutzerList.class );

  private final BenutzerRepository benutzerRepository;

  public BenutzerList( BenutzerRepository pBenutzerRepository )
  {
    benutzerRepository = pBenutzerRepository;
  }

  public List< Benutzer> getList()
  {
    return benutzerRepository.findAll();
  }

  public void add( Benutzer pBenutzer )
  {
    Benutzer bestehender_benutzer = getBenutzerByUserName( pBenutzer.getUserName() );

    if ( bestehender_benutzer == null )
    {
      benutzerRepository.save( pBenutzer );
    }

    getList().add( pBenutzer );
  }

  public int getAnzahl()
  {
    return getList().size();
  }

  public Benutzer getIndex( int pIndex )
  {
    return getList().get( pIndex );
  }

  public Benutzer getBenutzerByUserName( String pUserName )
  {
    return this.getList().stream().filter( p -> p.istUserName( pUserName ) ).findFirst().orElse( null );
  }
}
