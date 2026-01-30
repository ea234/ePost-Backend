package de.ea234.epost.model.vorgang;

import de.ea234.epost.repository.TageseingangRepository;
import de.ea234.epost.services.ServiceTagesEingang;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagesEingangList implements ServiceTagesEingang {

  private final TageseingangRepository tageseingangRepository;

  @Override
  public List<TagesEingang> getList()
  {
    return tageseingangRepository.findAll();
  }

  @Override
  public int getAnzahl()
  {
    return getList().size();
  }

  @Override
  public TagesEingang getIndex( int pIndex )
  {
    return getList().get( pIndex );
  }

  @Override
  public void updateTageseingang( long pLongTagesdatum, long pAnzahl )
  {
    TagesEingang tages_eingang = getTagesEingang( pLongTagesdatum );

    if ( tages_eingang == null )
    {
      tages_eingang = new TagesEingang();

      tages_eingang.setTagesDatum( pLongTagesdatum );
    }

    tages_eingang.setAnzahleingaenge( pAnzahl );

    tageseingangRepository.save( tages_eingang );
  }

  public TagesEingang getTagesEingang( long pLongTagesdatum )
  {
    return this.getList().stream().filter( teingang -> teingang.istTagesdatum( pLongTagesdatum ) ).findFirst().orElse( null );
  }
}
