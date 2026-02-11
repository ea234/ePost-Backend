package de.ea234.epost.util;

import java.util.Calendar;
import java.util.Date;

public class FkDatum {

  /**
   * @return eine Date-Instanz MIT AUSGENULLTER ZEIT
   */
  public static Date getDate()
  {
    Calendar datum_ergebnis = Calendar.getInstance();

//    datum_ergebnis.set( Calendar.HOUR_OF_DAY, 0 );
//    datum_ergebnis.set( Calendar.MINUTE, 0 );
//    datum_ergebnis.set( Calendar.SECOND, 0 );
//    datum_ergebnis.set( Calendar.MILLISECOND, 0 );
    return datum_ergebnis.getTime();
  }

  /**
   * Gibt das aktuelle Datum im Long-Format zurueck.
   *
   * @return das Datum im Format JJJJMMTT
   */
  public static long getLong()
  {
    Calendar datum_ergebnis = Calendar.getInstance();

    return (datum_ergebnis.get( Calendar.YEAR ) * 10000) + ((datum_ergebnis.get( Calendar.MONTH ) + 1) * 100) + datum_ergebnis.get( Calendar.DAY_OF_MONTH );
  }

  /**
   * Gibt das uebergebene Datum als Long im Format JJJJMMTT zurueck.
   *
   * @param pDatum das Datum als Instanz von Java.Util.Date
   * @return Datum im Format JJJJMMTT
   */
  public static long getLong( Date pDatum )
  {
    if ( pDatum == null )
    {
      return 0;
    }

    // Verbraucht zuviel Zeit: return ( pDatum.getYear() * 10000 ) + ( ( pDatum.getMonth() + 1 ) * 100 ) + pDatum.getDate();
    // Kann auch ungenau sein, da das Jahr als Offset zu 1900 genommen wird
    Calendar datum_ergebnis = Calendar.getInstance();

    datum_ergebnis.setTime( pDatum );

    return (datum_ergebnis.get( Calendar.YEAR ) * 10000) + ((datum_ergebnis.get( Calendar.MONTH ) + 1) * 100) + datum_ergebnis.get( Calendar.DAY_OF_MONTH );
  }

  /**
   * @return das aktuelle Datum und die aktuelle Uhrzeit (Wochentag TT.MM.JJJJ
   * HH:MM:SS)
   */
  public static String getWochentagDatumUndZeit()
  {
    Calendar datum_ergebnis = Calendar.getInstance();

    int akt_monat = datum_ergebnis.get( Calendar.MONTH ) + 1;
    int akt_tag = datum_ergebnis.get( Calendar.DATE );
    int stunden = datum_ergebnis.get( Calendar.HOUR_OF_DAY );
    int minuten = datum_ergebnis.get( Calendar.MINUTE );
    int sekunden = datum_ergebnis.get( Calendar.SECOND );

    return String.format( "%5s %02d.%02d.%4d %02d:%02d:%02d", getWochentag(), akt_tag, akt_monat, datum_ergebnis.get( Calendar.YEAR ), stunden, minuten, sekunden );
  }

  public static String getZeit()
  {
    Calendar datum_ergebnis = Calendar.getInstance();

    int stunden = datum_ergebnis.get( Calendar.HOUR_OF_DAY );
    int minuten = datum_ergebnis.get( Calendar.MINUTE );
    int sekunden = datum_ergebnis.get( Calendar.SECOND );

    return String.format( "%02d:%02d:%02d", stunden, minuten, sekunden );
  }

  /**
   * @return das aktuelle Datum (TT.MM.JJJJ)
   */
  public static String getString()
  {
    Calendar datum_ergebnis = Calendar.getInstance();

    int akt_monat = datum_ergebnis.get( Calendar.MONTH ) + 1;
    int akt_tag = datum_ergebnis.get( Calendar.DATE );

    return String.format( "%02d.%02d.%4d", akt_tag, akt_monat, datum_ergebnis.get( Calendar.YEAR ) );
  }

  /**
   * Gibt das uebergebene Datum als String im Format "TT.MM.JJJJ" zurueck.
   *
   * @param pDatum das Datum als Instanz von Java.Util.Date
   * @return das formatierte Datum, oder null, wenn der Parameter null ist
   */
  public static String getString( Date pDatum )
  {
    if ( pDatum == null )
    {
      return null;
    }

    Calendar datum_ergebnis = Calendar.getInstance();

    datum_ergebnis.setTime( pDatum );

    int akt_tag = datum_ergebnis.get( Calendar.DAY_OF_MONTH );

    int akt_monat = datum_ergebnis.get( Calendar.MONTH ) + 1;

    return String.format( "%02d.%02d.%4d", akt_tag, akt_monat, datum_ergebnis.get( Calendar.YEAR ) );
  }

  /**
   * @return den Namen des aktuellen Wochentages
   */
  public static String getWochentag()
  {
    switch ( Calendar.getInstance().get( Calendar.DAY_OF_WEEK ) )
    {
      case Calendar.MONDAY:

        return "Montag";

      case Calendar.TUESDAY:

        return "Dienstag";

      case Calendar.WEDNESDAY:

        return "Mittwoch";

      case Calendar.THURSDAY:

        return "Donnerstag";

      case Calendar.FRIDAY:

        return "Freitag";

      case Calendar.SATURDAY:

        return "Samstag";
    }

    return "Sonntag";
  }

  /**
   * @param pDatumVon ein Von-Datum
   * @param pDatumBis ein Bis-Datum
   * @return die Differenz in Jahren zwischen den beiden Datumsangaben, 0 wenn
   * ein Parameter "null" ist
   */
  public static int getAnzahlJahre( Date pDatumVon, Date pDatumBis )
  {
    try
    {
      if ( pDatumBis != null && pDatumVon != null )
      {
        int anzahl_jahre = 0;

        /*
         * Das Jahr aus dem Bis-Datum holen
         */
        Calendar datum_von = Calendar.getInstance();

        Calendar datum_bis = Calendar.getInstance();

        datum_von.setTime( pDatumVon );

        datum_bis.setTime( pDatumBis );

        /*
         * Differenz zwischen den Jahren ausrechnen
         */
        anzahl_jahre = datum_bis.get( Calendar.YEAR ) - datum_von.get( Calendar.YEAR );

        return anzahl_jahre;
      }
    }
    catch (Exception err_inst)
    {
      // keine Aktion, da Rueckgabe von 0
    }

    return 0;
  }

  /**
   * <pre>
   * Addiert die Anzahl als Jahre auf das uebergebene Datum.
   *
   * Schaltjahr: Aus 29 wird 28
   *
   * FkDatum.addJahre( 19.02.2012, 1, -1 ) = 01.02.2013
   * FkDatum.addJahre( 19.02.2012, 1,  0 ) = 19.02.2013
   * FkDatum.addJahre( 19.02.2012, 1,  1 ) = 28.02.2013
   *
   * FkDatum.addJahre(       null, 6,  0 ) = null
   *
   * FkDatum.addJahre( 19.02.2012, -1, -1 ) = 01.02.2011
   * FkDatum.addJahre( 19.02.2012, -2,  1 ) = 28.02.2010
   * </pre>
   *
   * @param pDatum das Datum auf welches die Jahre hinzugezaehlt werden sollen
   * @param pAnzahl die Anzahl der Jahre
   * @param pKnzTag Kennzeichen fuer den Tag im Ergebnisdatum (-1 =
   * Monatserster, 1 = Monatsletzter, 0 = keine Veraenderung)
   * @return das sich ergebende Datum
   */
  public static Date addJahre( Date pDatum, int pAnzahl, int pKnzTag )
  {
    if ( pDatum == null )
    {
      return null;
    }

    Calendar datum_ergebnis = Calendar.getInstance();

    datum_ergebnis.setTime( pDatum );

    datum_ergebnis.add( Calendar.YEAR, pAnzahl );

    datum_ergebnis.set( Calendar.HOUR_OF_DAY, 0 );
    datum_ergebnis.set( Calendar.MINUTE, 0 );
    datum_ergebnis.set( Calendar.SECOND, 0 );
    datum_ergebnis.set( Calendar.MILLISECOND, 0 );

    /*
     * Auswertung Parameter pKnzTag
     * bei kleiner 0 wird das Datum auf den ersten gestellt
     * bei groesser 0 wird das Datum auf den Monatsletzten gestellt
     * bei 0 bleibt es bei dem berechneten Datum
     */
    if ( pKnzTag < 0 )
    {
      datum_ergebnis.set( Calendar.DAY_OF_MONTH, 1 );
    }
    else if ( pKnzTag > 0 )
    {
      datum_ergebnis.set( Calendar.DAY_OF_MONTH, datum_ergebnis.getActualMaximum( Calendar.DAY_OF_MONTH ) );
    }

    return datum_ergebnis.getTime();
  }

  public static Date getWerktagPlusX( Date pDate, int pWerktagNr )
  {
    Calendar calendar_x = Calendar.getInstance();

    if ( pDate != null )
    {
      calendar_x.setTime( pDate );
    }

    int anzahl_tage = 0;
    int zaehler_werktag = 0;
    int werktag_nr_abs = Math.abs( pWerktagNr );

    int max_tage_schleife = werktag_nr_abs * 10;

    if ( max_tage_schleife == 0 )
    {
      werktag_nr_abs = 1;
      max_tage_schleife = 31;
    }

    while ( (zaehler_werktag < werktag_nr_abs) && (anzahl_tage < max_tage_schleife) )
    {
      // wl( "Werktag Nr " + zaehler_werktag + " Anzahl Tage " + anzahl_tage + " Datum " + getString( calendar_x ) );

      if ( istFeiertag( calendar_x ) == false )
      {
        zaehler_werktag ++;
      }

      if ( zaehler_werktag < werktag_nr_abs )
      {
        if ( pWerktagNr >= 0 )
        {
          calendar_x.add( Calendar.DATE, 1 );
        }
        else
        {
          calendar_x.add( Calendar.DATE, -1 );
        }
      }

      anzahl_tage++;
    }

    return calendar_x.getTime();
  }

  private static boolean istFeiertag( Calendar pCalendar )
  {
    if ( pCalendar == null )
    {
      return false;
    }

    switch ( pCalendar.get( Calendar.DAY_OF_WEEK ) )
    {
      case Calendar.SATURDAY:
      case Calendar.SUNDAY:

        return true;
    }

    int monat_tag = (((pCalendar.get( Calendar.MONTH ) + 1)) * 100) + pCalendar.get( Calendar.DAY_OF_MONTH );

    if ( monat_tag == 1224 )
    {
      return true;
    }
    if ( monat_tag == 1225 )
    {
      return true; // erster Weihnachtstag
    }
    if ( monat_tag == 1226 )
    {
      return true; // zweiter Weihnatstag
    }
    if ( monat_tag == 101 )
    {
      return true; // erster Januar
    }
    if ( monat_tag == 1003 )
    {
      return true; // Feiertag in DE
    }
    if ( monat_tag == 501 )
    {
      return true; // erster Mai
    }
    
    return istOsterFeiertag( pCalendar );
  }

  private static boolean istOsterFeiertag( Calendar pCalendar )
  {
    int monat_tag = (((pCalendar.get( Calendar.MONTH ) + 1)) * 100) + pCalendar.get( Calendar.DAY_OF_MONTH );

    /*
     * Berechnung Ostersonntag nach der Gausschen-Formel
     * 
     * https://www.java-forum.org/thema/feiertage-berechnen.14254/
     * 
     */
    int ostersonntag_jahr = pCalendar.get( Calendar.YEAR );

    final int JAHRESZYKLUS_19 = 19;

    int jahr_mod_4 = ostersonntag_jahr % 4;

    int jahr_mod_7 = ostersonntag_jahr % 7;

    int schalttage_falsch_100 = ostersonntag_jahr / 100; // = k
    int schalttage_korrektur = ostersonntag_jahr / 400; // = q

    int schalttage_abzug_1 = schalttage_falsch_100 - schalttage_korrektur;

    int schalttage_abzug_korrektur = schalttage_abzug_1 - 2;

    int m1 = (8 * schalttage_falsch_100 + 13) / 25 - 2;

    int m = (15 + schalttage_abzug_korrektur - m1) % 30;

    int n = (6 + schalttage_abzug_korrektur) % 7;

    int jahr_mod_19 = ostersonntag_jahr % JAHRESZYKLUS_19; // = a

    int jahreszyklen_19 = JAHRESZYKLUS_19 * jahr_mod_19;

    int d = (m + jahreszyklen_19) % 30;

    if ( d == 29 )
    {
      d = 28;
    }
    else if ( d == 28 && jahr_mod_19 >= 11 )
    {
      d = 27;
    }

    int jahr_2 = 2 * jahr_mod_4;
    int jahr_7 = 4 * jahr_mod_7;
    int jahr_6d = 6 * d;

    int temp_1 = jahr_2 + jahr_7 + jahr_6d;

    //int e = ( 2 * jahr_mod_4 + 4 * jahr_mod_7 + 6 * d + n ) % 7;
    //int e = ( jahr_2 + jahr_7 + jahr_6d + n ) % 7;
    int e = (temp_1 + n) % 7;

    int ostersonntag_tag = 21 + d + e + 1;

    int ostersonntag_monat = 3;

    if ( ostersonntag_tag > 31 )
    {
      ostersonntag_tag = ostersonntag_tag % 31;

      ostersonntag_monat = 4;
    }

    if ( monat_tag == getDateMMTT( ostersonntag_jahr, ostersonntag_monat, ostersonntag_tag ) )
    {
      return true; // datum_oster_sonntag  
    }

    Date m_datum_oster_sonntag = getDate( ostersonntag_jahr, ostersonntag_monat, ostersonntag_tag );

    if ( monat_tag == getDateRelativTage( m_datum_oster_sonntag, 1, 0 ) )
    {
      return true; // datum_oster_montag   
    }

    if ( monat_tag == getDateRelativTage( m_datum_oster_sonntag, -46, 0 ) )
    {
      return true; // datum_aschermittwoch 
    }

    if ( monat_tag == getDateRelativTage( m_datum_oster_sonntag, 39, 0 ) )
    {
      return true; // datum_himmelfahrt    
    }

    if ( monat_tag == getDateRelativTage( m_datum_oster_sonntag, 49, 0 ) )
    {
      return true; // datum_pfingstsonntag 
    }

    if ( monat_tag == getDateRelativTage( m_datum_oster_sonntag, 50, 0 ) )
    {
      return true; // datum_pfingstmonntag 
    }

    if ( monat_tag == getDateRelativTage( m_datum_oster_sonntag, -2, 0 ) )
    {
      return true; // datum_karfreitag     
    }

    if ( monat_tag == getDateRelativTage( m_datum_oster_sonntag, 60, 0 ) )
    {
      return true; // datum_fronleichnam   
    }

    return false;
  }

  private static Date getDate( long pJahr, long pMonat, long pTag )
  {
    Calendar datum_ergebnis = Calendar.getInstance();

    datum_ergebnis.set( Calendar.YEAR, (int) pJahr );
    datum_ergebnis.set( Calendar.MONTH, (int) (pMonat - 1) );
    datum_ergebnis.set( Calendar.DAY_OF_MONTH, (int) pTag );

    return datum_ergebnis.getTime();
  }

  private static int getDateMMTT( long pJahr, long pMonat, long pTag )
  {
    Calendar datum_ergebnis = Calendar.getInstance();

    datum_ergebnis.set( Calendar.YEAR, (int) pJahr );
    datum_ergebnis.set( Calendar.MONTH, (int) (pMonat - 1) );
    datum_ergebnis.set( Calendar.DAY_OF_MONTH, (int) pTag );

    return ((datum_ergebnis.get( Calendar.MONTH ) + 1) * 100) + datum_ergebnis.get( Calendar.DAY_OF_MONTH );
  }

  /**
   * Liefert das aktuelle Datum, evtl. veraendert durch den Parameter
   * pAnzahlTage
   *
   * @param pDatum (optional) das Ausgangsdatum. Ist der Parameter gleich null,
   * wird das aktuelle Datum genommen
   * @param pAnzahlTage die Anzahl der hinzuzuaddierenden Tage
   * @param pKnzTag -1 = Monatserster, 1 = Monatsletzter, 0 keine Veraenderung
   * @return das sich ergebende relative Datum als java.util.Date, welches
   * eventuell durch pKnzTag angepasst wird
   */
  public static int getDateRelativTage( Date pDatum, int pAnzahlTage, int pKnzTag )
  {
    Calendar datum_ergebnis = Calendar.getInstance();

    if ( pDatum != null )
    {
      datum_ergebnis.setTime( pDatum );
    }

    datum_ergebnis.set( Calendar.HOUR_OF_DAY, 0 );
    datum_ergebnis.set( Calendar.MINUTE, 0 );
    datum_ergebnis.set( Calendar.SECOND, 0 );
    datum_ergebnis.set( Calendar.MILLISECOND, 0 );

    datum_ergebnis.add( Calendar.DAY_OF_MONTH, pAnzahlTage );

    /*
     * Auswertung Parameter pKnzTag
     * bei kleiner 0 wird das Datum auf den ersten gestellt
     * bei groesser 0 wird das Datum auf den Monatsletzten gestellt
     * bei 0 bleibt es bei dem berechneten Datum
     */
    if ( pKnzTag < 0 )
    {
      datum_ergebnis.set( Calendar.DAY_OF_MONTH, 1 );
    }
    else if ( pKnzTag > 0 )
    {
      datum_ergebnis.set( Calendar.DAY_OF_MONTH, datum_ergebnis.getActualMaximum( Calendar.DAY_OF_MONTH ) );
    }

    return ((datum_ergebnis.get( Calendar.MONTH ) + 1) * 100) + datum_ergebnis.get( Calendar.DAY_OF_MONTH );
  }

}
