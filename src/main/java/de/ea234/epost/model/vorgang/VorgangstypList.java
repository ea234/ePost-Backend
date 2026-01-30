package de.ea234.epost.model.vorgang;

import de.ea234.epost.repository.VorgangstypRepository;
import de.ea234.epost.services.ServiceListVorgangstypen;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VorgangstypList implements ServiceListVorgangstypen {

  private static final Logger log = LoggerFactory.getLogger( VorgangstypList.class );

  private final VorgangstypRepository vorgangstypRepository;

  public VorgangstypList( VorgangstypRepository pVertragstypRepository )
  {
    this.vorgangstypRepository = pVertragstypRepository;
  }

  @Override
  public List<Vorgangstyp> getList()
  {
    return vorgangstypRepository.findAll();
  }

  @Override
  public int getAnzahl()
  {
    return getList().size();
  }

  @Override
  public Vorgangstyp getIndex( int pIndex )
  {
    return getList().get( pIndex );
  }

  @Override
  public Vorgangstyp getVorgangstyp( String pUid )
  {
    return this.getList().stream().filter( p -> p.isTypNr( pUid ) ).findFirst().orElse( null );
  }

  @Override
  public Map<String, String> getMapUidZuBezeichnung()
  {
    Map<String, String> map = new HashMap<String, String>();

    for ( Vorgangstyp vg : getList() )
    {
      map.put( vg.getTypNr(), vg.getTypNr() + " - " + vg.getTypLangText() );
    }

    return map;
  }

  public void init()
  {
    createUidListe();
  }

  private void createUidListe()
  {
    addVorgangstyp( "10101", "Vertragsabschluss", "Vertragsabschluss", "3", "FA01" );
    addVorgangstyp( "10102", "Vertragsbeendigung wegen Kuendigung", "Ende wg Kuendigung", "3", "FA01" );
    addVorgangstyp( "10103", "Vertragsbeendigung wegen Todesfall", "Ende wg Todesfall", "3", "FA01" );
    addVorgangstyp( "10104", "Vertragsbeendigung wegen Schreiben vom Anwalt", "Ende wg Anwaltsschreiben", "3", "FA01" );
    addVorgangstyp( "10201", "Stammdatenänderung", "Stammdatenänderung", "3", "FA01" );
    addVorgangstyp( "10202", "Änderung Bankverbindung", "Änderung Bankverbindung", "3", "FA01" );
    addVorgangstyp( "10203", "Änderung Freistellungsauftrag", "Änderung FSA", "3", "FA01" );
    addVorgangstyp( "10204", "Änderung Adresse", "Adress Änderung", "3", "FA01" );
    addVorgangstyp( "10300", "Kundenanfrage Prio 1", "Anfrage Prio 1", "1", "FA01" );
    addVorgangstyp( "10301", "Kundenanfrage Prio 2", "Anfrage Prio 2", "2", "FA01" );
    addVorgangstyp( "10302", "Kundenanfrage Prio 3", "Anfrage Prio 3", "3", "FA01" );
    addVorgangstyp( "10303", "Kundenbeschwerde Prio 1", "Beschwerde Prio 1", "1", "FA01" );
    addVorgangstyp( "10304", "Kundenbeschwerde Prio 2", "Beschwerde Prio 2", "14", "FA01" );
    addVorgangstyp( "10400", "Zahlungseingang", "Zahlungseingang", "3", "FA01" );
    addVorgangstyp( "10401", "Stornobuchung", "Stornobuchung", "3", "FA01" );
    addVorgangstyp( "10402", "Umbuchung", "Umbuchung", "3", "FA01" );
    addVorgangstyp( "10500", "Urkunde Eidesstattliche Versicherung", "Urkunde Eid. Versicherung", "3", "FA01" );
    addVorgangstyp( "10501", "Urkunde Haftbefehl", "Urkunde Haftbefehl", "3", "FA01" );
  }

  private void addVorgangstyp( String typNr, String typLangText, String typKurzText, String typPrioritaet, String typAbteilung )
  {
    Vorgangstyp x_new_typ = getVorgangstyp( typNr );

    if ( x_new_typ == null )
    {
      Vorgangstyp new_typ = new Vorgangstyp();

      new_typ.setTypNr( typNr );
      new_typ.setTypLangText( typLangText );
      new_typ.setTypKurzText( typKurzText );
      new_typ.setTypPrioritaet( Integer.valueOf( typPrioritaet ).intValue() );
      new_typ.setTypAbteilung( typAbteilung );

      vorgangstypRepository.save( new_typ );

      log.info( "VorgangstypList - addVorgangstyp - Created " + new_typ.getTypNr() + " " + new_typ.getTypLangText() );
    }
  }
}
