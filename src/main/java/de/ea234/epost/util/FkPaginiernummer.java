package de.ea234.epost.util;

import de.ea234.epost.services.ServiceTagesEingang;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import de.ea234.epost.model.vorgang.TagesEingang;

@Component
@AllArgsConstructor
public class FkPaginiernummer {

  private final ServiceTagesEingang serviceTagesEingang;

  private long getTagesDokumentNr( long pDatumLong )
  {
    TagesEingang tages_eingang = serviceTagesEingang.getTagesEingang( pDatumLong );

    if ( tages_eingang == null )
    {
      serviceTagesEingang.updateTageseingang( pDatumLong, 1 );

      return 1;
    }

    return tages_eingang.getAnzahleingaenge().longValue();
  }

  private void saveTagesDokumentNr( long pDatumLong, long pTagesDokumentNr )
  {
    serviceTagesEingang.updateTageseingang( pDatumLong, pTagesDokumentNr );
  }

  private static long tagesDokumentDatum = 0;

  public String getNewPaginiernummer()
  {
    long datum_akt = FkDatum.getLong();

    if ( datum_akt != tagesDokumentDatum )
    {
      tagesDokumentDatum = datum_akt;
    }

    long tages_dok_nr = getTagesDokumentNr( tagesDokumentDatum );

    tages_dok_nr ++;

    saveTagesDokumentNr( datum_akt, tages_dok_nr );

    System.out.println( "de.ea234.epost.util.FkPaginiernummer.getNewPaginiernummer() " +  + tagesDokumentDatum + String.format( "%05d", tages_dok_nr ) );
    return "" + tagesDokumentDatum + String.format( "%05d", tages_dok_nr );
  }

  public static boolean istPaginiernummer( String pEingabe )
  {
    //           1 
    // 0123456789012
    // 2026011500001
    // JJJJMMTT12345
    //
    // Jahr 4 stellig bis Index 3

    /*
     * Pruefung: Eingabe vorhanden?
     * Ist keine Eingabe vorhanden, ist es keine Paginiernummer.
     */
    if ( pEingabe != null )
    {
      /*
       * Pruefung: Eingabe genau 11 Zeichen lang?
       * Ist die Eingabe ungleich 11 Zeichen, ist es keine Paginiernummer
       */
      if ( pEingabe.length() == 13 )
      {
        try
        {
          /*
           * Per Vorgabe wird die maximalanzahl des Monats auf 31 Tage gestellt.
           * Nur im Februar wird die Tagesanzahl auf 29 geaendert.
           *  
           * Schaltjahre werden nicht beruecksichtigt, da das Jahr nicht gespeichert
           * und auch nicht eindeutig ist.
           */
          int max_tage = 31;

          int akt_zahl = 0;

          int akt_index = 0;

          int akt_jahr = 0;

          int akt_monat = 0;

          int akt_tag = 0;

          while ( akt_index < 11 )
          {
            /*
             * Aktuelles Zeichen aus der Eingabe lesen
             */
            char akt_char = pEingabe.charAt( akt_index );

            /*
             * Zeichenpruefung
             * Ist das aktuelle Zeichen keine Zahl, wird die Funktion mit FALSE verlassen.
             */
            if ( (akt_char < '0') || (akt_char > '9') )
            {
              return false;
            }

            /*
             * Berechnung Akt-Zahl
             */
            akt_zahl = (akt_zahl * 10) + (((int) akt_char) - 48);

            if ( akt_index == 3 )
            {
              /*
               * Index = 3
               * Die Variable "akt_zahl" enthaelt das Jahr
               */
              if ( (akt_zahl < 1980) || (akt_zahl > 9999) )
              {
                return false;
              }

              akt_jahr = akt_zahl;

              akt_zahl = 0;
            }
            else if ( akt_index == 5 )
            {
              /*
               * Index = 5
               * Die Variable "akt_zahl" enthaelt den Monat.
               */
              if ( (akt_zahl < 1) || (akt_zahl > 12) )
              {
                return false;
              }

              if ( akt_zahl == 2 )
              {
                max_tage = 29;
              }

              akt_monat = akt_zahl;

              akt_zahl = 0;
            }
            else if ( akt_index == 7 )
            {
              /*
               * Index = 7
               * Die Variable "akt_zahl" enthaelt den Tag.
               */
              if ( (akt_zahl < 1) || (akt_zahl > max_tage) )
              {
                return false;
              }

              akt_tag = akt_zahl;

              akt_zahl = 0;
            }
            else if ( akt_index > 7 )
            {
              /*
               * Bei einem Index groesser als 7, muessen nur Zahlen geprueft werden.
               *  
               * Das wird durch die obige Pruefung gemacht.
               *  
               * Die Variable "akt_zahl" wird auf 0 gestellt, um Ueberlauefe zu verhindern. 
               */
              akt_zahl = 0;
            }

            /*
             * Leseprozess ein Zeichen weiter stellen 
             */
            akt_index ++;
          }

          /*
           * Ist die While-Schleife beendet wird TRUE zurueck gegeben.
           */
          return true;
        }
        catch (Exception e)
        {
          // Fehler in der While-Schleife ergibt eine Rueckgabe von FALSE
        }
      }
    }

    /*
     * Die Vorgaberueckgabe der Funktion ist FALSE
     */
    return false;
  }
}
