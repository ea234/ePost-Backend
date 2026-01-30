package de.ea234.epost.services;

import de.ea234.epost.model.vorgang.TagesEingang;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ServiceTagesEingang {

  public List< TagesEingang> getList();

  public int getAnzahl();

  public TagesEingang getIndex( int pIndex );

  public TagesEingang getTagesEingang( long pLongTagesdatum );

  public void updateTageseingang( long pLongTagesdatum, long pAnzahl );
}
