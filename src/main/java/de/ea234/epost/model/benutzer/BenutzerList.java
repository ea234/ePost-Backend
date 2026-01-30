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

  @Override
  public Benutzer getBenutzerByUserName( String pUserName )
  {
    return this.getList().stream().filter( p -> p.istUserName( pUserName ) ).findFirst().orElse( null );
  }

  public void init()
  {
    doTestDaten();
  }

  private void doTestDaten()
  {
    System.out.println( "🔴 doTestdaten Kunde" );

    addIniBenutzer( "abrad", "Alons", "Brader" );
    addIniBenutzer( "cdean", "Charlie", "Dean" );
    addIniBenutzer( "ehart", "Egon", "Hartling" );
    addIniBenutzer( "gfulk", "Gert", "Fulken" );
    addIniBenutzer( "sniss", "Sabine", "Nissen" );
    addIniBenutzer( "mhuan", "Maria", "Huana" );
  }

  private void addIniBenutzer( String pUserName, String pVorname, String pNachname )
  {
    Benutzer b_benutzer = getBenutzerByUserName( pUserName );

    if ( b_benutzer == null )
    {
      Benutzer neuer_benutzer = new Benutzer();

      neuer_benutzer.setUserName( pUserName );
      neuer_benutzer.setVorName( pVorname );
      neuer_benutzer.setNachName( pNachname );
      neuer_benutzer.setEmail( pVorname + "." + pNachname + "@firma.de" );

      neuer_benutzer.setUserPassword( pUserName );

      benutzerRepository.save( neuer_benutzer );
    }
  }
}
