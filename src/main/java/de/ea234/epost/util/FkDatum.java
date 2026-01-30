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

    return String.format( "%02d:%02d:%02d",  stunden, minuten, sekunden );
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

}
