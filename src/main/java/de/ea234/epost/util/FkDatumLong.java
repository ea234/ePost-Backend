package de.ea234.epost.util;

import java.util.Calendar;
import java.util.Date;

/**
 * <pre>
 * Ein Long-Datum ist ein Datum dessen Format JJJJMMTT ist.
 *
 * Ein Long-Datum hat den Namen, weil dieses Format hauptsaechlich mit dem Typ "long" gespeichert wird.
 *
 * Das Format kann natuerlich auch in einem String gespeichert werden.
 *
 *
 * Datumsbestandteile aus dem Long-Datumsformat extrahieren:
 *
 * long datum_long = 20100830;
 *
 * int datum_jahr  = (int) ( datum_long * 0.0001 );                                     // " + datum_jahr  + "
 * int datum_monat = (int) ( ( datum_long - ( datum_jahr * 10000 ) ) * 0.01 );          // " + datum_monat + "
 * int datum_tag   = (int) ( datum_long - ( datum_jahr * 10000 + datum_monat * 100 ) ); // " + datum_tag   + "
 *
 * return (long) ( ( datum_jahr * 10000 ) + ( datum_monat * 100 ) + datum_tag );
 *
 *
 * Das Zerlegen eines Stringdatums mittels den Funktionen "substring" und "Integer.parseInt" hat
 * sich als zu langsam erwiesen. Die unten stehenden Funktionen erledigen die Aufgabe mindestens
 * 6 mal schneller.
 *
 * long datum_long = datum_jahr * 10000 + datum_monat * 100 + datum_tag;
 *
 * if ( datum_long > 0 )
 * {
 *   String datum_string = "" + datum_long;
 *
 *   int datum_jahr  = Integer.parseInt( datum_string.substring( 0, 4 ) ); // Zeitfresser
 *   int datum_monat = Integer.parseInt( datum_string.substring( 4, 6 ) ); // Zeitfresser
 *   int datum_tag   = Integer.parseInt( datum_string.substring( 6, 8 ) ); // Zeitfresser
 * }
 *
 * Nicht eindeutige Datumsangaben
 * 20.05.2005 = 20051205
 * 12.12.1212 = 12121212
 *
 * </pre>
 */
public class FkDatumLong {
  //public void startDatumsloopWoche( long pDatumStichtag )
  //{
  //  if ( pDatumStichtag < 20250101 )
  //  {
  //    return;
  //  }
  //
  //  Date datum_parameter = FkDatumLong.parseToDate( "" + pDatumStichtag );
  //
  //  Date datum_montag = FkDatum.getDateWocheMontag( datum_parameter );
  //
  //  Date datum_sonntag = FkDatum.getDateWocheSonntag( datum_parameter );
  //
  //  long datum_long_von = FkDatum.getLong( datum_montag  );
  //  long datum_long_bis = FkDatum.getLong( datum_sonntag );
  //
  //  Calendar datum_ergebnis = Calendar.getInstance();
  //
  //  datum_ergebnis.setTime( datum_montag );
  //
  //  long kalender_woche = datum_ergebnis.get( Calendar.WEEK_OF_YEAR );
  //
  //  String verzeichnis_auswertung =  "c:/daten/gesamt_status_woche/";
  //
  //  String datei_name_auswertung = verzeichnis_auswertung + "app_status_gesamt_kw_" + ( kalender_woche < 10 ? "0" : "" ) + kalender_woche + "_von_" + datum_long_von + "_bis_" + pDatumBis + ".txt";
  //
  //  String log_auswertung = "Gesamtstatus KW " + kalender_woche + " von " + FkDatum.getWochentagDatum( datum_long_von ) + " bis " + FkDatum.getWochentagDatum( datum_long_bis );
  //
  //  FkDatei.schreibeDatei( datei_name_auswertung, log_auswertung );
  //}
  //
  //private static void startLoopDatumsbereich( String pDatumVon, String pDatumBis )
  //{
  //  long datum_long_von = FkDatumLong.parseToLong( pDatumVon );
  //  long datum_long_bis = FkDatumLong.parseToLong( pDatumBis );
  //
  //  int zaehler_tage_aktuell = 0;
  //  int zaehler_tage_maximal = 30;
  //
  //  if ( ( datum_long_von > 0 ) && ( datum_long_bis > 0 ) )
  //  {
  //    while ( ( datum_long_von <= datum_long_bis ) && ( zaehler_tage_aktuell < zaehler_tage_maximal ) )
  //    {
  //      FkLogger.wl( "Datum " + datum_long_von + " " + FkDatumLong.parseToString( "" + datum_long_von ) );
  //
  //      datum_long_von = FkDatum.longDateAddTage( datum_long_von, 1 );
  //
  //      zaehler_tage_aktuell++;
  //    }
  //  }
  //}

  /**
   * <pre>
   * Prueft den Eingabestring auf ein enthaltenes Datum ab.
   *
   * Ist der Parameter "pString" ein Datum, wird das Datum im Format JJJJMMTT zurueckgegeben.
   *
   * Wird kein gueltiges Datum erkannt, wird 0 zurueckgegeben.
   * Auf die Eingabe wird kein Trim gemacht.
   *
   * Bei den Angaben mit Trennzeichen, muss das Jahr immer 4stellig angegeben werden.
   * Die Monats- und Tageswerte koennen 1 bis 2 Stellen haben. Bei mehr als 2 Stellen
   * im Tag oder Monat, wird "null" zurueckgegeben.
   *
   * Es werden folgende Formate in der Reihenfolge geprueft:
   *
   * Format 1: TT.MM.JJJJ, T.MM.JJJJ, TT.M.JJJJ, T.M.JJJJ
   * Format 2: JJJJMMTT
   * Format 3: JJJJ-MM-TT, JJJJ-MM-T, JJJJ-M-TT, JJJJ-M-T
   * Format 4: TT-MM-JJJJ, T-MM-JJJJ, TT-M-JJJJ, T-M-JJJJ
   *
   * FkDatumLong.parseToLong( "06.01.2010"    ) = 20100106
   * FkDatumLong.parseToLong( "06.1.2010"     ) = 20100106
   * FkDatumLong.parseToLong( "6.01.2010"     ) = 20100106
   *
   * FkDatumLong.parseToLong( "20100106"      ) = 20100106
   *
   * FkDatumLong.parseToLong( "2010-01-06"    ) = 20100106
   * FkDatumLong.parseToLong( "2010-01-6"     ) = 20100106
   * FkDatumLong.parseToLong( "2010-1-06"     ) = 20100106
   * FkDatumLong.parseToLong( "2010-1-6"      ) = 20100106
   *
   * FkDatumLong.parseToLong( "06-01-2010"    ) = 20100106
   * FkDatumLong.parseToLong( "06-1-2010"     ) = 20100106
   * FkDatumLong.parseToLong( "6-01-2010"     ) = 20100106
   * FkDatumLong.parseToLong( "6-1-2010"      ) = 20100106
   *
   * FkDatumLong.parseToLong( "2010061"       ) = 0
   * FkDatumLong.parseToLong( "2010.12.01"    ) = 0
   * FkDatumLong.parseToLong( null            ) = 0
   * FkDatumLong.parseToLong( ""              ) = 0
   * FkDatumLong.parseToLong( "2010 01 06"    ) = 0
   * FkDatumLong.parseToLong( "28-02-2010"    ) = 20100228
   * FkDatumLong.parseToLong( "29-02-2010"    ) = 0
   * FkDatumLong.parseToLong( "02-28-2010"    ) = 0
   * FkDatumLong.parseToLong( "00-01-2010"    ) = 0
   * FkDatumLong.parseToLong( "06-00-2010"    ) = 0
   * FkDatumLong.parseToLong( "06-01-0000"    ) = 0
   * FkDatumLong.parseToLong( "EF-GH-ABCD"    ) = 0
   *
   * FkDatumLong.parseToLong( "1111111"       ) = 0
   * FkDatumLong.parseToLong( "30.2.2010"     ) = 0
   *
   * Ungueltige Werte
   * FkDatumLong.parseToLong( "99.05.2010"    ) = 0
   * FkDatumLong.parseToLong( "01.99.2010"    ) = 0
   * FkDatumLong.parseToLong( "01.05.1234"    ) = 0
   *
   * FkDatumLong.parseToLong( "9999999999"    ) = 0
   * FkDatumLong.parseToLong( "0000000001"    ) = 0
   * FkDatumLong.parseToLong( "00.00.0000"    ) = 0
   *
   * Ungueltige Zeichen:
   * FkDatumLong.parseToLong( "1A.05.2010"    ) = 0 = Tag ungueltig
   * FkDatumLong.parseToLong( "10.0B.2010"    ) = 0 = Monat ungueltig
   * FkDatumLong.parseToLong( "10.05.C010"    ) = 0 = Jahr ungueltig
   *
   * FkDatumLong.parseToLong( "ABCDEFGH"      ) = 0 = Eingabe hat keine Zahlen
   * FkDatumLong.parseToLong( null            ) = 0 = Eingabe ist nicht gesetzt
   *
   * Zu viele Trennzeichen:
   * FkDatumLong.parseToLong( "1..05.2010"    ) = 0
   * FkDatumLong.parseToLong( "1.05..2010"    ) = 0
   * FkDatumLong.parseToLong( "1.05.20.10"    ) = 0
   *
   * FkDatumLong.parseToLong( "1-22010-"      ) = 0
   * FkDatumLong.parseToLong( "1-2-2010-"     ) = 0
   * FkDatumLong.parseToLong( "-1-2-2010"     ) = 0
   * FkDatumLong.parseToLong( "1--2-2010"     ) = 0
   * FkDatumLong.parseToLong( "12---2010"     ) = 0
   *
   * FkDatumLong.parseToLong( "2020--1-02"    ) = 0
   * FkDatumLong.parseToLong( "2020-01--2"    ) = 0
   *
   * Trennzeichenkombinationen
   * FkDatumLong.parseToLong( "2010-01.01"    ) = 0
   * FkDatumLong.parseToLong( "2010.01-01"    ) = 0
   *
   * FkDatumLong.parseToLong( "1.02-2010"     ) = 0
   * FkDatumLong.parseToLong( "01-2.2010"     ) = 0
   *
   * Zu viele vorlaufende 0en:
   * FkDatumLong.parseToLong( "006.1.2010"    ) = 0
   * FkDatumLong.parseToLong( "6.001.2010"    ) = 0
   * FkDatumLong.parseToLong( "6.1.002010"    ) = 0
   *
   * FkDatumLong.parseToLong( "0.6.2010"      ) = 0 - Tagesanteil wird nicht gelesen, daher 0
   * FkDatumLong.parseToLong( "6.0.2010"      ) = 0 - Monatsanteil wird nicht gelesen, daher 0
   *
   * FkDatumLong.parseToLong( "0808.2010"     ) = 0 = erster Punkt nicht an erwarteter Stelle
   *                                                  erster Punkt zu spaet in der Eingabe
   *                                                  (bzw. korrekter: Monat wird als 808 gelesen, was zu gross ist)
   *
   * FkDatumLong.parseToLong( "0008.2010"     ) = 0 = es wird kein Tag gelesen, weswegen das Datum abgewiesen wird
   *
   * FkDatumLong.parseToLong( "0.08.2010"     ) = 0 = der Tag ist 0, weswegen das Datum abgewiesen wird
   *
   * FkDatumLong.parseToLong( "2010.8.008"    ) = 0
   * FkDatumLong.parseToLong( "2010.08008"    ) = 0
   * FkDatumLong.parseToLong( "2010.00008"    ) = 0
   *
   * FkDatumLong.parseToLong( "2020-01-02"    ) = 20200102
   * FkDatumLong.parseToLong( "2020-1-02"     ) = 20200102
   * FkDatumLong.parseToLong( "2020-01-2"     ) = 20200102
   * FkDatumLong.parseToLong( "2020-1-2"      ) = 20200102
   * FkDatumLong.parseToLong( "2020-1-002"    ) = 0 = Tag ist 3 stellig
   * FkDatumLong.parseToLong( "2020-001-2"    ) = 0 = Monat ist 3 stellig
   * FkDatumLong.parseToLong( "02020-1-02"    ) = 0 = Jahr ist 4 stellig
   * FkDatumLong.parseToLong( "002020-1-2"    ) = 0 = Jahr ist 5 stellig
   * FkDatumLong.parseToLong( "2020001002"    ) = 0 = Eingabe zu lang, Tag und Monat 3 stellig
   * FkDatumLong.parseToLong( "2010-01001"    ) = 0
   * FkDatumLong.parseToLong( "2010-00001"    ) = 0
   *
   * </pre>
   *
   * @param pString die auf ein Datum zu parsende Eingabe
   * @return Ein Datum im Format JJJJMMTT als Long, wenn sich aus der Eingabe
   * ein Datum parsen laesst. Im Fehlerfall wird 0 zurueckgegeben.
   */
  public static long parseToLong( String pString )
  {
    /*
     * Pruefung: Eingabe gesetzt ?
     * 
     * Ist die Eingabe "null", bekommt der Aufrufer 0 zurueck.
     */
    if ( pString == null )
    {
      return 0; // Fehler: EINGABE_NICHT_GESETZT
    }

    /*
     * Test 1: Datum im Format TT.MM.JJJJ, T.MM.JJJJ, TT.M.JJJJ, T.M.JJJJ
     * 
     * Das zuerst gepruefte Muster ist ein Datum im Format Tag Monat Jahr. 
     * Dabei koennen beim Tag und Monat die fuehrenden 0en weggelassen werden.
     * Wird kein gueltiges Datum in diesem Muster erkannt, wird von der 
     * Funktion 0 zurueckgegeben.
     */
    long datum_long = FkDatumLong.getLongAusTTPMMPJJJJ( pString );

    /*
     * Pruefung: Ergebnis der ersten Parserfunktion 0 ?
     * 
     * Ist das Ergebnis der ersten Parserfunktion gleich 0, konnte kein Long-Datum 
     * aus der Eingabe ermittelt werden. Es wird die zweite Parserfunktion gestartet,
     * welche ein anderes Datumsmuster prueft. Dieses Vorgehen wird fuer die zwei 
     * weiteren Parserfunktion wiederholt, wobei andere Datumsmuster geprueft werden. 
     */
    if ( datum_long == 0 )
    {
      /*
       * Test 2: Datum im Format JJJJMMTT
       * 
       * Das zweite Muster ist ein Datum im Format Jahr Monat Tag. 
       * Dabei muss das Jahr 4stellig, Monat und Jahr 2 stellig uebergeben worden sein. 
       * Die Eingabe muss eine Laenge von 8 Zeichen haben.
       */
      datum_long = FkDatumLong.getLongAusJJJJMMTT( pString );
    }

    if ( datum_long == 0 )
    {
      /*
       * Test 3: Datum im Format JJJJ-MM-TT, JJJJ-MM-T, JJJJ-M-TT, JJJJ-M-T
       */
      datum_long = FkDatumLong.getLongAusJJJJxMMxTT( pString, '-' );
    }

    if ( datum_long == 0 )
    {
      /*
       * Test 4: Datum im Format TT-MM-JJJJ, T-MM-JJJJ, TT-M-JJJJ, T-M-JJJJ
       */
      datum_long = FkDatumLong.getLongAusTTxMMxJJJJ( pString, '-' );
    }

    /*
     * Am Funktionsende wird der Wert der Variablen "datum_long" zurueckgegeben.
     * Konnte kein Datum geparst werden, ist der Rueckgabewert 0.
     */
    return datum_long;
  }

  /**
   * <pre>
   * Prueft den Eingabestring auf ein enthaltenes Datum ab.
   *
   * Ist der Parameter "pString" ein Datum, wird das Datum im Format TT.MM.JJJJ zurueckgegeben.
   *
   * Wird kein gueltiges Datum erkannt, wird "null" zurueckgegeben.
   *
   * Bei den Angaben mit Trennzeichen, muss das Jahr immer 4stellig angegeben werden.
   * Die Monats- und Tageswerte koennen 1 bis 2 Stellen haben. Bei mehr als 2 Stellen
   * im Tag oder Monat, wird "null" zurueckgegeben.
   *
   * Es werden folgende Formate in der Reihenfolge geprueft:
   *
   * Format 1: TT.MM.JJJJ, T.MM.JJJJ, TT.M.JJJJ, T.M.JJJJ
   * Format 2: JJJJMMTT
   * Format 3: JJJJ-MM-TT, JJJJ-MM-T, JJJJ-M-TT, JJJJ-M-T
   * Format 4: TT-MM-JJJJ, T-MM-JJJJ, TT-M-JJJJ, T-M-JJJJ
   *
   * FkDatumLong.parseToString( "06.01.2010" ) = 06.01.2010
   * FkDatumLong.parseToString( "06.1.2010"  ) = 06.01.2010
   * FkDatumLong.parseToString( "6.01.2010"  ) = 06.01.2010
   *
   * FkDatumLong.parseToString( "20100106"   ) = 01.06.2010
   *
   * FkDatumLong.parseToString( "2010-01-06" ) = 01.06.2010
   * FkDatumLong.parseToString( "2010-01-6"  ) = 01.06.2010
   * FkDatumLong.parseToString( "2010-1-06"  ) = 01.06.2010
   * FkDatumLong.parseToString( "2010-1-6"   ) = 01.06.2010
   *
   * FkDatumLong.parseToString( "06-01-2010" ) = 06.01.2010
   * FkDatumLong.parseToString( "06-1-2010"  ) = 06.01.2010
   * FkDatumLong.parseToString( "6-01-2010"  ) = 06.01.2010
   * FkDatumLong.parseToString( "6-1-2010"   ) = 06.01.2010
   *
   * FkDatumLong.parseToString( "2010061"    ) = null
   * FkDatumLong.parseToString( "2010.12.01" ) = null
   * FkDatumLong.parseToString( null         ) = null
   * FkDatumLong.parseToString( ""           ) = null
   * FkDatumLong.parseToString( "2010 01 06" ) = null
   * FkDatumLong.parseToString( "02-29-2010" ) = null
   * FkDatumLong.parseToString( "00-01-2010" ) = null
   * FkDatumLong.parseToString( "06-00-2010" ) = null
   * FkDatumLong.parseToString( "06-01-0000" ) = null
   * FkDatumLong.parseToString( "EF-GH-ABCD" ) = null
   * FkDatumLong.parseToString( "28-02-2010" ) = 28.02.2010
   * FkDatumLong.parseToString( "29-02-2010" ) = null
   * FkDatumLong.parseToString( "02-28-2010" ) = null
   * </pre>
   *
   * @param pString die auf ein Datum zu parsende Eingabe
   * @return Ein Datum im Format TT.MM.JJJJ als String, wenn sich aus der
   * Eingabe ein Datum parsen laesst. Ist in der Eingabe kein gueltiges Datum
   * enthalten, wird null zurueckgegeben.
   */
  public static String parseToString( String pString )
  {
    /*
     * Aus der Eingabe wird versucht eine Datum im Long-Format zu parsen. 
     * Dieses wird ueber die Funktion "parseToLong" erledigt.
     * Die Funktion gibt 0 zurueck, wenn kein Datum gelesen werden konnte.
     */
    long datum_long = parseToLong( pString );

    /*
     * Pruefung: Konnte ein Datum gelesen werden ?
     */
    if ( datum_long > 0 )
    {
      /*
       * Wurde im Parameter "pString" ein gueltiges Datum uebergeben, ist in 
       * der Variablen "datum_long" das Datum im Format JJJJMMTT gespeichert. 
       * Aus dieser Variablen werden die einzelnen Datums-Bestandteile wieder 
       * herausgerechnet. Mit den seperaten Werten wird dann der Ergebnisstring 
       * im Format TT.MM.JJJJ erstellt und zurueckgegeben.
       */
      int datum_jahr = (int) (datum_long * 0.0001);

      int datum_monat = (int) ((datum_long - (datum_jahr * 10000)) * 0.01);

      int datum_tag = (int) (datum_long - (datum_jahr * 10000 + datum_monat * 100));

      return (datum_tag < 10 ? "0" : "") + datum_tag + (datum_monat < 10 ? ".0" : ".") + datum_monat + "." + datum_jahr;
    }

    /*
     * Ist in der Eingabe kein Datum vorhanden, wird "null" zurueckgegeben.
     */
    return null; // Fehler: EINGABE_IST_KEIN_DATUM
  }

  /**
   * <pre>
   * Prueft den Eingabestring auf ein enthaltenes Datum ab.
   *
   * Ist der Parameter "pString" ein Datum, wird das Datum im Format TT.MM.JJJJ zurueckgegeben.
   *
   * Wird kein gueltiges Datum erkannt, wird "null" zurueckgegeben.
   *
   * Die Datumsbestandteile werden durch das uebergebene Trennzeichen getrennt.
   * Vom Parameter "pTrennzeichen" werden maximal 5 Stellen genommen.
   * Ist der Parameter "pTrennzeichen" gleich "null" wird kein Trennzeichen gesetzt.
   *
   * Bei den Angaben mit Trennzeichen, muss das Jahr immer 4stellig angegeben werden.
   * Die Monats- und Tageswerte koennen 1 bis 2 Stellen haben. Bei mehr als 2 Stellen
   * im Tag oder Monat, wird "null" zurueckgegeben.
   *
   * Es werden folgende Formate in der Reihenfolge geprueft:
   *
   * Format 1: TT.MM.JJJJ, T.MM.JJJJ, TT.M.JJJJ, T.M.JJJJ
   * Format 2: JJJJMMTT
   * Format 3: JJJJ-MM-TT, JJJJ-MM-T, JJJJ-M-TT, JJJJ-M-T
   * Format 4: TT-MM-JJJJ, T-MM-JJJJ, TT-M-JJJJ, T-M-JJJJ
   *
   * FkDatumLong.parseToStringTTPMMPJJJJ( "06.01.2010", "." ) = 06.01.2010
   * FkDatumLong.parseToStringTTPMMPJJJJ( "6.01.2010",  "." ) = 06.01.2010
   * FkDatumLong.parseToStringTTPMMPJJJJ( "06.1.2010",  "." ) = 06.01.2010
   * FkDatumLong.parseToStringTTPMMPJJJJ( "6.1.2010",   "." ) = 06.01.2010
   *
   * FkDatumLong.parseToStringTTPMMPJJJJ( "20100106",   "-" ) = 06-01-2010
   *
   * FkDatumLong.parseToStringTTPMMPJJJJ( "2010-01-06", "-" ) = 06-01-2010
   *
   * FkDatumLong.parseToStringTTPMMPJJJJ( "06.01.2010", null ) = 06012010
   *
   * FkDatumLong.parseToStringTTPMMPJJJJ( "06.01.2010",        "-"  ) = 06-01-2010
   * FkDatumLong.parseToStringTTPMMPJJJJ( "06.01.2010",       " - " ) = 06 - 01 - 2010
   * FkDatumLong.parseToStringTTPMMPJJJJ( "06.01.2010", "-ABC-DEF-" ) = 06-ABC-01-ABC-2010 (Maximal 5 Stellen des Trennzeichen-Strings)
   *
   * FkDatumLong.parseToStringTTPMMPJJJJ(         null, "-" ) = null
   * FkDatumLong.parseToStringTTPMMPJJJJ(   "ABCDEFGH", "-" ) = null
   *
   * FkDatumLong.parseToStringTTPMMPJJJJ( "88.01.2010", "." ) = null
   * FkDatumLong.parseToStringTTPMMPJJJJ( "06.88.2010", "." ) = null
   * FkDatumLong.parseToStringTTPMMPJJJJ( "06.01.0000", "." ) = null
   * </pre>
   *
   * @param pString die auf ein Datum zu parsende Eingabe
   * @param pTrennzeichen der zu verwendende Trennzeichen-String fuer die
   * Ausgabe. Bei Uebergabe von "null" wird kein Trennzeichen gesetzt. Es werden
   * maximal 5 Stellen genommen.
   * @return Ein Datum im Format TT.MM.JJJJ als String, wenn sich aus der
   * Eingabe ein Datum parsen laesst. Im Fehlerfall wird null zurueckgegeben.
   */
  public static String parseToStringTTPMMPJJJJ( String pString, String pTrennzeichen )
  {
    /*
     * Aus der Eingabe wird versucht eine Datum im Long-Format zu parsen. 
     * Dieses wird ueber die Funktion "parseToLong" erledigt.
     * Die Funktion gibt 0 zurueck, wenn kein Datum gelesen werden konnte.
     */
    long datum_long = parseToLong( pString );

    /*
     * Pruefung: Konnte ein Datum gelesen werden ?
     */
    if ( datum_long > 0 )
    {
      /*
       * Wurde im Parameter "pString" ein gueltiges Datum uebergeben, ist in 
       * der Variablen "datum_long" das Datum im Format JJJJMMTT gespeichert. 
       * Aus dieser Variablen werden die einzelnen Datums-Bestandteile wieder 
       * herausgerechnet. Mit den seperaten Werten wird dann der Ergebnisstring 
       * im Format TT.MM.JJJJ erstellt und zurueckgegeben.
       */
      int datum_jahr = (int) (datum_long * 0.0001);

      int datum_monat = (int) ((datum_long - (datum_jahr * 10000)) * 0.01);

      int datum_tag = (int) (datum_long - (datum_jahr * 10000 + datum_monat * 100));

      /*
       * Trennzeichenstring
       * Ist der Parameter "pTrennzeichen" gleich "null", wird ein Leerstring als 
       * Trennzeichen genommen. Ist der Parameter gesetzt, werden maximal die ersten 
       * 5 Zeichen des Parameters genommen.
       */
      String str_trennzeichen = pTrennzeichen == null ? "" : pTrennzeichen.substring( 0, (pTrennzeichen.length() > 5 ? 5 : pTrennzeichen.length()) );

      return (datum_tag < 10 ? "0" : "") + datum_tag + str_trennzeichen + (datum_monat < 10 ? "0" : "") + datum_monat + str_trennzeichen + datum_jahr;
    }

    /*
     * Ist in der Eingabe kein Datum vorhanden, wird "null" zurueckgegeben.
     */
    return null; // Fehler: EINGABE_IST_KEIN_DATUM
  }

  public static String parseToStringTTPMMPJJJJ_2( String pString, String pTrennzeichen )
  {
    //String datum_string = FkDatumLong.parseToStringTTPMMPJJJJ_2();

    /*
     * Die Eingabe wird zuerst mit dem Muster TTMMJJJJ auf ein Datum untersucht.  
     * Liegt keine Eingabe vor, wird mit der normalen parser-Funktion versucht,
     * ein Datum aus der Eingabe zu lesen. 
     * 
     * Es geht hier zuerst um das erste Muster. Es koennte aber sein, dass sich 
     * die Eingabestruktur aendert. Somit muessen auch die anderen Muster 
     * untersucht werden. 
     */
    long datum_long = FkDatumLong.getLongAusTTMMJJJJ( pString );

    if ( datum_long == 0 )
    {
      datum_long = parseToLong( pString );
    }

    /*
     * Pruefung: Konnte ein Datum gelesen werden ?
     */
    if ( datum_long > 0 )
    {
      /*
       * Wurde im Parameter "pString" ein gueltiges Datum uebergeben, ist in 
       * der Variablen "datum_long" das Datum im Format JJJJMMTT gespeichert. 
       * Aus dieser Variablen werden die einzelnen Datums-Bestandteile wieder 
       * herausgerechnet. Mit den seperaten Werten wird dann der Ergebnisstring 
       * im Format TT.MM.JJJJ erstellt und zurueckgegeben.
       */
      int datum_jahr = (int) (datum_long * 0.0001);

      int datum_monat = (int) ((datum_long - (datum_jahr * 10000)) * 0.01);

      int datum_tag = (int) (datum_long - (datum_jahr * 10000 + datum_monat * 100));

      /*
       * Trennzeichenstring
       * Ist der Parameter "pTrennzeichen" gleich "null", wird ein Leerstring als 
       * Trennzeichen genommen. Ist der Parameter gesetzt, werden maximal die ersten 
       * 5 Zeichen des Parameters genommen.
       */
      String str_trennzeichen = pTrennzeichen == null ? "" : pTrennzeichen.substring( 0, (pTrennzeichen.length() > 5 ? 5 : pTrennzeichen.length()) );

      return (datum_tag < 10 ? "0" : "") + datum_tag + str_trennzeichen + (datum_monat < 10 ? "0" : "") + datum_monat + str_trennzeichen + datum_jahr;
    }

    /*
     * Ist in der Eingabe kein Datum vorhanden, wird "null" zurueckgegeben.
     */
    return null; // Fehler: EINGABE_IST_KEIN_DATUM
  }

  /**
   * <pre>
   * Prueft den Eingabestring auf ein enthaltenes Datum ab.
   *
   * Ist der Parameter "pString" ein Datum, wird das Datum im Format JJMMTT zurueckgegeben.
   *
   * Wird kein gueltiges Datum erkannt, wird "null" zurueckgegeben.
   *
   * Bei den Angaben mit Trennzeichen, muss das Jahr immer 4stellig angegeben werden.
   * Die Monats- und Tageswerte koennen 1 bis 2 Stellen haben. Bei mehr als 2 Stellen
   * im Tag oder Monat, wird "null" zurueckgegeben.
   *
   * Es werden folgende Formate in der Reihenfolge geprueft:
   *
   * Format 1: TT.MM.JJJJ, T.MM.JJJJ, TT.M.JJJJ, T.M.JJJJ
   * Format 2: JJJJMMTT
   * Format 3: JJJJ-MM-TT, JJJJ-MM-T, JJJJ-M-TT, JJJJ-M-T
   * Format 4: TT-MM-JJJJ, T-MM-JJJJ, TT-M-JJJJ, T-M-JJJJ
   *
   *
   * FkDatumLong.parseToStringJJMMTT( "20010101"  ) = 010101
   * FkDatumLong.parseToStringJJMMTT( "20991231"  ) = 991231
   *
   * FkDatumLong.parseToStringJJMMTT( "20100115"  ) = 100115
   * FkDatumLong.parseToStringJJMMTT( "20120229"  ) = 120229
   * FkDatumLong.parseToStringJJMMTT( "20100229"  ) = null
   * FkDatumLong.parseToStringJJMMTT( "20100999"  ) = null
   * FkDatumLong.parseToStringJJMMTT( "15810101"  ) = null
   * FkDatumLong.parseToStringJJMMTT( "JJJJMMTT"  ) = null
   * FkDatumLong.parseToStringJJMMTT( "12010"     ) = null
   * FkDatumLong.parseToStringJJMMTT( ""          ) = null
   * FkDatumLong.parseToStringJJMMTT( "null"      ) = null
   * FkDatumLong.parseToStringJJMMTT( "20100115A" ) = null
   * FkDatumLong.parseToStringJJMMTT( "00000000"  ) = null
   * FkDatumLong.parseToStringJJMMTT( "99999999"  ) = null
   * FkDatumLong.parseToStringJJMMTT( "20109901"  ) = null
   * FkDatumLong.parseToStringJJMMTT( "1.1.2010"  ) = 100101
   * </pre>
   *
   * @param pString die auf ein Datum zu parsende Eingabe
   * @return Ein Datum im Format JJMMTT als String, wenn sich aus der Eingabe
   * ein Datum parsen laesst. Im Fehlerfall wird null zurueckgegeben.
   */
  public static String parseToStringJJMMTT( String pString )
  {
    /*
     * Aus der Eingabe wird versucht eine Datum im Long-Format zu parsen. 
     * Dieses wird ueber die Funktion "parseToLong" erledigt.
     * Die Funktion gibt 0 zurueck, wenn kein Datum gelesen werden konnte.
     */
    long datum_long = parseToLong( pString );

    /*
     * Pruefung: Konnte ein Datum gelesen werden ?
     */
    if ( datum_long > 0 )
    {
      /*
       * Wurde im Parameter "pString" ein gueltiges Datum uebergeben, ist in 
       * der Variablen "datum_long" das Datum im Format JJJJMMTT gespeichert. 
       * Aus dieser Variablen werden die einzelnen Datums-Bestandteile wieder 
       * herausgerechnet. Mit den seperaten Werten wird dann der Ergebnisstring 
       * im Format JJMMTT erstellt und zurueckgegeben.
       */
      int datum_jahr_vierstellig = (int) (datum_long * 0.0001);

      int datum_jahrtausend = (int) (datum_jahr_vierstellig * 0.01);

      int datum_jahr_zweistellig = datum_jahr_vierstellig - (datum_jahrtausend * 100);

      int datum_monat = (int) ((datum_long - (datum_jahr_vierstellig * 10000)) * 0.01);

      int datum_tag = (int) (datum_long - (datum_jahr_vierstellig * 10000 + datum_monat * 100));

      return (datum_jahr_zweistellig < 10 ? "0" : "") + datum_jahr_zweistellig + (datum_monat < 10 ? "0" : "") + datum_monat + (datum_tag < 10 ? "0" : "") + datum_tag;
    }

    /*
     * Ist in der Eingabe kein Datum vorhanden, wird "null" zurueckgegeben.
     */
    return null; // Fehler: EINGABE_IST_KEIN_DATUM
  }

  /**
   * <pre>
   * Prueft den Eingabestring auf ein enthaltenes Datum ab.
   *
   * Ist der Parameter "pString" ein Datum, wird das Datum im Format JJJJ.TT.MM zurueckgegeben.
   *
   * Wird kein gueltiges Datum erkannt, wird "null" zurueckgegeben.
   *
   * Die Datumsbestandteile werden durch das uebergebene Trennzeichen getrennt.
   * Vom Parameter "pTrennzeichen" werden maximal 5 Stellen genommen.
   * Ist der Parameter "pTrennzeichen" gleich "null" wird kein Trennzeichen gesetzt.
   *
   * Bei den Angaben mit Trennzeichen, muss das Jahr immer 4stellig angegeben werden.
   * Die Monats- und Tageswerte koennen 1 bis 2 Stellen haben. Bei mehr als 2 Stellen
   * im Tag oder Monat, wird "null" zurueckgegeben.
   *
   * Es werden folgende Formate in der Reihenfolge geprueft:
   *
   * Format 1: TT.MM.JJJJ, T.MM.JJJJ, TT.M.JJJJ, T.M.JJJJ
   * Format 2: JJJJMMTT
   * Format 3: JJJJ-MM-TT, JJJJ-MM-T, JJJJ-M-TT, JJJJ-M-T
   * Format 4: TT-MM-JJJJ, T-MM-JJJJ, TT-M-JJJJ, T-M-JJJJ
   *
   * FkDatumLong.parseToStringJJJJPMMPTT( "06.01.2010", "." ) = 2010.01.06
   * FkDatumLong.parseToStringJJJJPMMPTT( "6.01.2010",  "." ) = 2010.01.06
   * FkDatumLong.parseToStringJJJJPMMPTT( "06.1.2010",  "." ) = 2010.01.06
   * FkDatumLong.parseToStringJJJJPMMPTT( "6.1.2010",   "." ) = 2010.01.06
   *
   * FkDatumLong.parseToStringJJJJPMMPTT( "20100106",   "-" ) = 2010-01-06
   *
   * FkDatumLong.parseToStringJJJJPMMPTT( "2010-01-06", "-" ) = 2010-01-06
   *
   * FkDatumLong.parseToStringJJJJPMMPTT( "06.01.2010",        null ) = 20100106
   * FkDatumLong.parseToStringJJJJPMMPTT( "06.01.2010",         "-" ) = 2010-01-06
   * FkDatumLong.parseToStringJJJJPMMPTT( "06.01.2010",       " - " ) = 2010 - 01 - 06
   * FkDatumLong.parseToStringJJJJPMMPTT( "06.01.2010", "-ABC-DEF-" ) = 2010-ABC-01-ABC-06
   *
   * FkDatumLong.parseToStringJJJJPMMPTT(         null, "-" ) = null
   * FkDatumLong.parseToStringJJJJPMMPTT(   "ABCDEFGH", "-" ) = null
   *
   * FkDatumLong.parseToStringJJJJPMMPTT( "88.01.2010", "." ) = null
   * FkDatumLong.parseToStringJJJJPMMPTT( "06.88.2010", "." ) = null
   * FkDatumLong.parseToStringJJJJPMMPTT( "06.01.0000", "." ) = null
   * </pre>
   *
   * @param pString die auf ein Datum zu parsende Eingabe
   * @param pTrennzeichen das zu verwendende Trennzeichen fuer die Ausgabe. Bei
   * Uebergabe von "null" wird kein Trennzeichen gesetzt. Es werden maximal 5
   * Stellen eines gesetzten Trennzeichens genommen.
   * @return Ein Datum im Format JJJJ<TZ>TT<TZ>MM als String, wenn sich aus der
   * Eingabe ein Datum parsen laesst. Im Fehlerfall wird null zurueckgegeben.
   * Das Trennzeichen im Ergebnis ist das Zeichen aus "pTrennzeichen"
   */
  public static String parseToStringJJJJPMMPTT( String pString, String pTrennzeichen )
  {
    /*
     * Aus der Eingabe wird versucht eine Datum im Long-Format zu parsen. 
     * Dieses wird ueber die Funktion "parseToLong" erledigt.
     * Die Funktion gibt 0 zurueck, wenn kein Datum gelesen werden konnte.
     */
    long datum_long = parseToLong( pString );

    /*
     * Pruefung: Konnte ein Datum gelesen werden ?
     */
    if ( datum_long > 0 )
    {
      /*
       * Wurde im Parameter "pString" ein gueltiges Datum uebergeben, ist in 
       * der Variablen "datum_long" das Datum im Format JJJJMMTT gespeichert. 
       * Aus dieser Variablen werden die einzelnen Datums-Bestandteile wieder 
       * herausgerechnet. Mit den seperaten Werten wird dann der Ergebnisstring 
       * im Format JJJJ.MM.TT erstellt und zurueckgegeben.
       */
      int datum_jahr = (int) (datum_long * 0.0001);

      int datum_monat = (int) ((datum_long - (datum_jahr * 10000)) * 0.01);

      int datum_tag = (int) (datum_long - (datum_jahr * 10000 + datum_monat * 100));

      /*
       * Trennzeichenstring
       * Ist der Parameter "pTrennzeichen" gleich "null", wird ein Leerstring 
       * als Trennzeichen genommen. Ist der Parameter gesetzt, werden maximal  
       * die ersten 5 Zeichen des Parameters genommen.
       */
      String str_trennzeichen = pTrennzeichen == null ? "" : pTrennzeichen.substring( 0, (pTrennzeichen.length() > 5 ? 5 : pTrennzeichen.length()) );

      return datum_jahr + str_trennzeichen + (datum_monat < 10 ? "0" : "") + datum_monat + str_trennzeichen + (datum_tag < 10 ? "0" : "") + datum_tag;
    }

    /*
     * Ist in der Eingabe kein Datum vorhanden, wird "null" zurueckgegeben.
     */
    return null; // Fehler: EINGABE_IST_KEIN_DATUM
  }

  /**
   * <pre>
   * Prueft den Eingabestring auf ein enthaltenes Datum ab.
   *
   * Ist der Parameter "pString" ein Datum, wird das Datum als Instanz von java.util.Date zurueckgegeben.
   *
   * Wird kein gueltiges Datum erkannt, wird "null" zurueckgegeben.
   *
   * Bei den Angaben mit Trennzeichen, muss das Jahr immer 4stellig angegeben werden.
   * Die Monats- und Tageswerte koennen 1 bis 2 Stellen haben. Bei mehr als 2 Stellen
   * im Tag oder Monat, wird "null" zurueckgegeben.
   *
   * Es werden folgende Formate in der Reihenfolge geprueft:
   *
   * Format 1: TT.MM.JJJJ, T.MM.JJJJ, TT.M.JJJJ, T.M.JJJJ
   * Format 2: JJJJMMTT
   * Format 3: JJJJ-MM-TT, JJJJ-MM-T, JJJJ-M-TT, JJJJ-M-T
   * Format 4: TT-MM-JJJJ, T-MM-JJJJ, TT-M-JJJJ, T-M-JJJJ
   *
   * FkDatumLong.parseToDate( "06.01.2010"    ) = Wed Jan 06 00:00:00 CET 2010
   * FkDatumLong.parseToDate( "06.1.2010"     ) = Wed Jan 06 00:00:00 CET 2010
   * FkDatumLong.parseToDate( "6.01.2010"     ) = Wed Jan 06 00:00:00 CET 2010
   *
   * FkDatumLong.parseToDate( "20100106"      ) = Wed Jan 06 00:00:00 CEST 2010
   *
   * FkDatumLong.parseToDate( "2010-01-06"    ) = Wed Jan 06 00:00:00 CEST 2010
   * FkDatumLong.parseToDate( "2010-01-6"     ) = Wed Jan 06 00:00:00 CEST 2010
   * FkDatumLong.parseToDate( "2010-1-06"     ) = Wed Jan 06 00:00:00 CEST 2010
   * FkDatumLong.parseToDate( "2010-1-6"      ) = Wed Jan 06 00:00:00 CEST 2010
   *
   * FkDatumLong.parseToDate( "06-01-2010"    ) = Wed Jan 06 00:00:00 CET 2010
   * FkDatumLong.parseToDate( "06-1-2010"     ) = Wed Jan 06 00:00:00 CET 2010
   * FkDatumLong.parseToDate( "6-01-2010"     ) = Wed Jan 06 00:00:00 CET 2010
   * FkDatumLong.parseToDate( "6-1-2010"      ) = Wed Jan 06 00:00:00 CET 2010
   * FkDatumLong.parseToDate( "2010061"       ) = null
   * FkDatumLong.parseToDate( "2010.12.01"    ) = null
   * FkDatumLong.parseToDate( null,           ) = null
   * FkDatumLong.parseToDate( ""              ) = null
   * FkDatumLong.parseToDate( "2010 01 06"    ) = null
   * FkDatumLong.parseToDate( "02-29-2010"    ) = null
   * FkDatumLong.parseToDate( "00-01-2010"    ) = null
   * FkDatumLong.parseToDate( "06-00-2010"    ) = null
   * FkDatumLong.parseToDate( "06-01-0000"    ) = null
   * FkDatumLong.parseToDate( "EF-GH-ABCD"    ) = null
   * FkDatumLong.parseToDate( "28-02-2010"    ) = Sun Feb 28 00:00:00 CET 2010
   * FkDatumLong.parseToDate( "29-02-2010"    ) = null
   * FkDatumLong.parseToDate( "02-28-2010"    ) = null
   * </pre>
   *
   * @param pString die auf ein Datum zu parsende Eingabe
   * @return Ein Datum im Format TT.MM.JJJJ als String, wenn sich aus der
   * Eingabe ein Datum parsen laesst. Im Fehlerfall wird null zurueckgegeben.
   */
  public static Date parseToDate( String pString )
  {
    /*
     * Aus der Eingabe wird versucht eine Datum im Long-Format zu parsen. 
     * Dieses wird ueber die Funktion "parseToLong" erledigt.
     * Die Funktion gibt 0 zurueck, wenn kein Datum gelesen werden konnte.
     */
    long datum_long = parseToLong( pString );

    /*
     * Pruefung: Konnte ein Datum gelesen werden ?
     */
    if ( datum_long > 0 )
    {
      /*
       * Wurde im Parameter "pString" ein gueltiges Datum uebergeben, ist in 
       * der Variablen "datum_long" das Datum im Format JJJJMMTT gespeichert. 
       * Aus dieser Variablen werden die einzelnen Datums-Bestandteile 
       * wieder herausgerechnet.
       */
      int datum_jahr = (int) (datum_long * 0.0001);

      int datum_monat = (int) ((datum_long - (datum_jahr * 10000)) * 0.01);

      int datum_tag = (int) (datum_long - (datum_jahr * 10000 + datum_monat * 100));

      /*
       * Ergebnisaufbau
       * Das Ergebnis wird in einem Java-Calendar-Objekt aufgebaut.
       * Die Zeit-Werte werden auf 0 gestellt. 
       * Der Aufrufer bekommt das Datum als java.util.Date zurueck.
       */
      Calendar java_calendar = Calendar.getInstance();

      /*
       * Fehlervermeidung
       * Um einen moeglichen Fehler beim setzen der Datumsbestandteile 
       * zu vermeiden, wird der Tag initial auf den Monatsersten gestellt. 
       */
      java_calendar.set( Calendar.DAY_OF_MONTH, 1 );

      java_calendar.set( Calendar.YEAR, datum_jahr );
      java_calendar.set( Calendar.MONTH, datum_monat - 1 );
      java_calendar.set( Calendar.DAY_OF_MONTH, datum_tag );

      /*
       * Die Zeitbestandteile werden auf 0 gesetzt.
       */
      java_calendar.set( Calendar.HOUR_OF_DAY, 0 );
      java_calendar.set( Calendar.MINUTE, 0 );
      java_calendar.set( Calendar.SECOND, 0 );
      java_calendar.set( Calendar.MILLISECOND, 0 );

      return java_calendar.getTime();
    }

    /*
     * Ist in der Eingabe kein Datum vorhanden, wird "null" zurueckgegeben.
     */
    return null; // Fehler: EINGABE_IST_KEIN_DATUM
  }

  /**
   * <pre>
   * Liefert fuer ein gueltiges String-Datum in den Formaten TT.MM.JJJJ, T.MM.JJJJ, T.M.JJJJ oder TT.M.JJJJ das Long-Datum im Format JJJJMMTT.
   *
   * Ist das String-Datum ungueltig, wird 0 zurueckgegeben.
   *
   * Auf die Eingabe wird kein Trim gemacht.
   *
   * Die Eingabe muss eine Laenge zwischen 8 und 10 Stellen haben.
   * Das Trennzeichen fuer die Datumsbestandteile ist der Punkt.
   *
   * Das Jahr muss 4stellig angegeben werden.
   *
   * Die Tages- und Monatseingabe sind 2 Stellen lang.
   * Fuehrende 0en koennen bei diesen Angaben weggelassen werden.
   * Mehr als 2 Stellen in diesen Angaben fuehren zum Fehler.
   *
   * Das Jahr ist gueltig, wenn dieses im Bereich von 1583 und 9999 liegt.
   * Der Monat ist gueltig, wenn dieser im Bereich von 1 bis 12 liegt.
   * Der Tag ist gueltig, wenn dieser dem Gregorianischen-Kalender folgt.
   *
   *
   * FkDatumLong.getLongAusTTPMMPJJJJ( "10.05.2010"  ) = 20100510
   * FkDatumLong.getLongAusTTPMMPJJJJ( "10.5.2010"   ) = 20100510
   * FkDatumLong.getLongAusTTPMMPJJJJ( "1.5.2010"    ) = 20100501
   * FkDatumLong.getLongAusTTPMMPJJJJ( "1.05.2010"   ) = 20100501
   *
   * FkDatumLong.getLongAusTTPMMPJJJJ( " 1.5.2010 "  ) = 0 = Leerzeichen fuehren zum Fehler
   *
   *
   * FkDatumLong.getLongAusTTPMMPJJJJ( "1.5.10"      ) = 0 = zu kurz
   * FkDatumLong.getLongAusTTPMMPJJJJ( "10.05.20100" ) = 0 = zu lang
   *
   * FkDatumLong.getLongAusTTPMMPJJJJ( "1..05.2010"  ) = 0 = mehr Punkte als erwartet
   * FkDatumLong.getLongAusTTPMMPJJJJ( "1.05..2010"  ) = 0
   * FkDatumLong.getLongAusTTPMMPJJJJ( "1.05.20.10"  ) = 0
   *
   * FkDatumLong.getLongAusTTPMMPJJJJ( ".01.052010"  ) = 0 = Start mit Punkt
   * FkDatumLong.getLongAusTTPMMPJJJJ( "01.052010."  ) = 0 = Ende mit Punkt
   *
   * FkDatumLong.getLongAusTTPMMPJJJJ( "10.052010"   ) = 0 = weniger Punkte als erwartet
   * FkDatumLong.getLongAusTTPMMPJJJJ( "10052010"    ) = 0 = keine Trennzeichen in der Eingabe
   * FkDatumLong.getLongAusTTPMMPJJJJ( "1090592010"  ) = 0 = maximale Laenge und keine Punkte
   *
   * FkDatumLong.getLongAusTTPMMPJJJJ( "1A.05.2010"  ) = 0 = ungueltige Zeichen
   * FkDatumLong.getLongAusTTPMMPJJJJ( "10.B.2010"   ) = 0
   * FkDatumLong.getLongAusTTPMMPJJJJ( "10.05.C010"  ) = 0
   *
   * FkDatumLong.getLongAusTTPMMPJJJJ( "00.05.2010"  ) = 0 = Tagesanteil hat den Wert 0
   * FkDatumLong.getLongAusTTPMMPJJJJ( "01.00.2010"  ) = 0 = Monatsanteil hat den Wert 0
   *
   * FkDatumLong.getLongAusTTPMMPJJJJ( "01.13.2010"  ) = 0 = Monat existiert nicht
   * FkDatumLong.getLongAusTTPMMPJJJJ( "30.2.2010"   ) = 0 = Datum ungueltig
   * FkDatumLong.getLongAusTTPMMPJJJJ( "ABCDEFGH"    ) = 0 = nur Text
   * FkDatumLong.getLongAusTTPMMPJJJJ( "AB.CD.EFGH"  ) = 0
   * FkDatumLong.getLongAusTTPMMPJJJJ( "          "  ) = 0
   * FkDatumLong.getLongAusTTPMMPJJJJ( null          ) = 0 = keine Eingabe
   *
   * FkDatumLong.getLongAusTTPMMPJJJJ( "006.1.2010"  ) = 0 = zu viele fuehrende 0en Tag
   * FkDatumLong.getLongAusTTPMMPJJJJ( "6.001.2010"  ) = 0 = zu viele fuehrende 0en Monat
   * FkDatumLong.getLongAusTTPMMPJJJJ( "6.00001.20"  ) = 0 = zu viele fuehrende 0en Monat
   * FkDatumLong.getLongAusTTPMMPJJJJ( "6.1.002010"  ) = 0 = zu viele fuehrende 0en Jahr
   * FkDatumLong.getLongAusTTPMMPJJJJ( "6.1.02010"   ) = 0 = zu viele Stellen Jahr
   * FkDatumLong.getLongAusTTPMMPJJJJ( "6.1.20100"   ) = 0 = zu viele Stellen Jahr
   * FkDatumLong.getLongAusTTPMMPJJJJ( "06.01.02010" ) = 0 = Jahr ungueltig, wegen fuehrender 0 (Jahr muss 4stellig sein)
   * FkDatumLong.getLongAusTTPMMPJJJJ( "6.1.10"      ) = 0 = zu wenig Stellen Jahr
   *
   *
   * FkDatumLong.getLongAusTTPMMPJJJJ( "0808.2010"   ) = 0 = erster Punkt nicht an erwarteter Stelle
   *                                                         erster Punkt zu spaet in der Eingabe
   *                                                         (bzw. korrekter: Monat wird als 808 gelesen, was zu gross ist)
   *
   * FkDatumLong.getLongAusTTPMMPJJJJ( "0008.2010"   ) = 0 = es wird kein Tag gelesen, weswegen das Datum abgewiesen wird
   * </pre>
   *
   * @param pDatum das Eingabedatum im Format "TT.MM.JJJJ" (Tag und Monat
   * koennen einstellig sein, Jahr muss 4 stellig sein)
   * @return das Datum im Long-Format JJJJMMTT oder 0 wenn die Eingabe kein
   * korrektes Datum ist.
   */
  public static long getLongAusTTPMMPJJJJ( String pEingabe )
  {
    /*
     * Grundlegende Funktionsweise
     * Es werden Zahlen gelesen und bei Punkten (=Trennzeichen) erfolgt eine  
     * Zuweisung an die Datumsbestanteile, welche initial -1 sind.
     * 
     * Zahlen werden durch ein Trennzeichen (=Punkt) getrennt. 
     * Die Zahlen werden gelesen und bei einem Trennzeichen wird die aktuell 
     * gelesene Zahl einem Datumsbestandteil zugewiesen.
     * 
     * Ist das aktuelle Zeichen an der Leseposition keine Zahl, wird die Funktion mit 0 verlassen.
     * Ist das aktuelle Zeichen eine Zahl, wird die bestehende Zahl mit 10 multipliziert und die aktuelle Zahl hinzuaddiert.
     */

    try
    {
      /*
       * Pruefung: Ist die Eingabe "null" ?
       * 
       * Ist die Eingabe "null", bekommt der Aufrufer 0 zurueck.
       */
      if ( pEingabe == null )
      {
        return 0; // Fehler: EINGABE_NICHT_GESETZT
      }

      /*
       * Ermittlung der Zeichenanzahl der Eingabe
       */
      int anzahl_eingabe_zeichen = pEingabe.length();

      /*
       * Pruefung: Zeichenanzahl korrekt?
       * 
       * Die Eingabe darf nicht kuerzer als 8 Zeichen sein. 
       * Das sind zwei Punkte und 6 Zahlen. (2.2.2010)
       * 
       * Die Eingabe darf nicht laenger als 10 Zeichen sein. 
       * Das sind zwei Punkte und 8 Zahlen. (02.02.2010)
       * 
       * Liegt eine ungueltige Laenge der Eingabe vor, bekommt der Aufrufer 0 zurueck.
       */
      if ( (anzahl_eingabe_zeichen < 8) || (anzahl_eingabe_zeichen > 10) )
      {
        return 0; // Fehler: EINGABE_LAENGE_NICHT_KORREKT
      }

      ///*
      // * Optionale Variable, um mehrere unterschiedliche Trennzeichen zu ermoeglichen. 
      // */
      //char char_trennzeichen_1 = '.';

      /*
       * Deklaration der Datumsvariablen fuer Tag, Monat und Jahr.
       * Die Variablen werden mit -1 initialisiert.
       */
      int datum_tag = -1;
      int datum_monat = -1;
      int datum_jahr = -1;

      int akt_zahl = 0;

      /*
       * Leseposition
       * Die Variable "akt_index" ist die Index-Position im Eingabestring. 
       * Die Index-Position beginnt bei 0. 
       */
      int akt_index = 0;

      /*
       * Position Punkt Erwartet
       * 
       * Die Position des ersten erwarteten Punktes wird auf 10 gestellt.
       * 
       * Index-Position: 0123456789  0123456789  0123456789  
       * Eingabe:        06.01.2010  006.1.2010  006001.201  
       *                 ZZPZZPZZZZ
       */
      int position_punkt_erwartet = 10;

      /*
       * While-Schleife ueber die Eingabe zum lesen von Tag, Monat und Jahr.
       */
      while ( akt_index < anzahl_eingabe_zeichen )
      {
        /*
         * Aktuelles Zeichen aus der Eingabe lesen
         */
        char akt_char = pEingabe.charAt( akt_index );

        /*
         * Pruefung: Trennzeichen gefunden und aktuelle Zahl groesser 0 ?
         * 
         * Das Trennzeichen muss zweimal vorhanden sein.
         * Bei einem Trennzeichen muss die aktuelle Zahl groesser als 0 sein.
         * Ein Datumsbestandteil kann nicht 0 sein.
         * 
         * Ist die aktuelle Zahl nicht groesser als 0, wird das aktuelle 
         * Zeichen (=Trennzeichen) als ungueltig erkannt und fuehrt in der 
         * naechsten IF-Bedingung zu einem Fehler (Rueckgabe von 0).
         * (Kein Start mit einem Trennzeichen, 2 Trennzeichen hintereinander)
         * 
         * Ist die aktuelle Zahl groesser als 0, wird beim ersten Trennzeichen 
         * der Tag und beim zweiten Trennzeichen der Monat gesetzt. 
         * Das Jahr wird nach der While-Schleife gesetzt.
         * 
         * Kommt in der Eingabe ein drittes Trennzeichen vor, sind Tag und Monat 
         * schon gesetzt und es kommt zur Rueckgabe von 0.
         * 
         * Ist in der Eingabe kein Trennzeichen vorhanden, kommt es eventuell 
         * zu einer Ueberlaufexception, oder nach der While-Schleife ist 
         * der Tag oder der Monat noch auf dem Initialwert von -1, welches 
         * wiederum zum Fehler fuehrt. 
         * 
         * Nach der Zuweisung von Tag oder Monat, wird die aktuelle Zahl  
         * auf 0 gestellt.
         */
        ///*
        // * Optional koennen noch weitere Trennzeichen fuer die Datumsbestandteile 
        // * aufgenommen werden.
        // */
        //if ( ( ( akt_char == '.' ) || ( akt_char == ':' ) || ( akt_char == '/' ) ) && ( akt_zahl > 0 ) )
        if ( (akt_char == '.') && (akt_zahl > 0) )
        {
          if ( (datum_tag == -1) && (akt_index <= 2) )
          {
            /*
             * Tag
             * Der Tag hat minimal eine Stelle und maximal zwei Stellen.
             * 
             * Das Trennzeichen kommt somit an Index-Position 1 oder 2 vor.
             * 
             * Ist die aktuelle Leseposition kleiner gleich 2, ist der Tag 
             * korrekt eingelesen worden und das Trennzeichen steht an einer 
             * korrekten Stelle. 
             * 
             * Die Position des naechsten Punktes liegt maximal 3 Stellen
             * nach der aktuellen Position.
             */
            datum_tag = akt_zahl;

            position_punkt_erwartet = akt_index + 3;

          
           ///*
            // * Optional kann das erste Trennzeichen gespeichert werden um dann mit 
            // * dem zweiten Trennzeichen verglichen zu werden. 
            // */
            //char_trennzeichen_1 = akt_char;
          }
          else if ( (datum_monat == -1) && (akt_index <= position_punkt_erwartet) )
          {
            ///*
            // * Ist das zweite Trennzeichen ungleich dem ersten Trennzeichen, ist 
            // * die Struktur der Trennzeichen nicht korrekt. Der Aufrufer bekommt 
            // * eine 0 zurueck.
            // */
            //if ( akt_char != char_trennzeichen_1 )
            //{
            //  return 0; // Fehler: TRENNZEICHEN_STIMMEN_NICHT_UEBEREIN
            //}

            datum_monat = akt_zahl;

            /* 
             * Pruefung der Laenge des verbleibenden Eingabestrings
             * Um ein korrektes Jahr lesen zu koennen, muessen in der Eingabe 
             * noch 4 Stellen vorhanden sein. 
             * 
             * Ist die Differenz ungleich 4, wird 0 zurueckgegeben.
             * 
             * In der unten stehenden Pruefung wurde die Punkt-Position 
             * beruecksichtigt. Es wird gegen 5 getestet, da die 
             * Leseposition noch nicht weitergestellt wurde.
             */
            if ( (anzahl_eingabe_zeichen - akt_index) != 5 )
            {
              return 0; // Fehler: TRENNZEICHEN_AN_FALSCHER_POSITION
            }
          }
          else
          {
            /*
             * Genereller Fehler Trennzeichen
             * Zu viele Trennzeichen, oder Trennzeichen an falscher Position
             */
            return 0; // Fehler: TRENNZEICHEN_FEHLER
          }

          akt_zahl = 0;
        }
        /*
         * Zeichenpruefung
         * Ist das aktuelle Zeichen keine Zahl, wird die Funktion mit 0 verlassen.
         * Das ist auch der Fall, wenn das erste Zeichen ein Punkt ist, da in 
         * diesem Fall der Wert in der Variablen "akt_zahl" gleich 0 ist.
         */
        else if ( (akt_char < '0') || (akt_char > '9') )
        {
          return 0; // Fehler: EINGABE_ZEICHEN_UNGUELTIG
        }
        else
        {
          /*
           * Berechnung Akt-Zahl
           * Der Wert in der Variablen "akt_zahl" wird mit 10 multipliziert und 
           * der Wert des aktuellen Zeichens hinzugezaehlt. 
           * 
           * Der Wert des aktuellen Zeichens ist der Wert des ASCII-Code abzueglich 48. 
           */
          akt_zahl = (akt_zahl * 10) + (((int) akt_char) - 48);
        }

        /*
         * Leseprozess ein Zeichen weiter stellen 
         */
        akt_index ++;
      }

      /*
       * Nach dem zweiten Trennzeichen sind die restlichen Zahlen das Jahr. 
       * Nach der While-Schleife ist in der Variablen "akt_zahl" das Jahr gesetzt.
       * Das Jahr wird hier mit dem Wert aus "akt_zahl" gesetzt.
       * 
       * Die Variable "akt_zahl" muss sein, es kann keine Variable eingespart werden. 
       * (Wird die Variable eingespart, ist die Funktion langsamer (weil Java-Optimierungen))
       */
      datum_jahr = akt_zahl;

      /*
       * Pruefung: Datumswerte 
       * Der Tag muss groesser als 1 sein, der Maximalwerttest kommt spaeter.
       * Der Monat muss zwischen 1 und 12 liegen
       * Das Jahr muss zwischen 1583 und 9999 liegen 
       */
      if ( (datum_tag > 0) && ((datum_monat >= 1) && (datum_monat <= 12)) && ((datum_jahr >= 1583) && (datum_jahr <= 9999)) )
      {
        /*
         * Ermittlung Maximalwert Tag
         * Fuer die Maximalwertpruefung des Tages wird die Anzahl der Tage von 
         * Monat und Jahr bestimmt. Dabei wird das Schaltjahr beruecksichtigt.
         */
        int anzahl_tage = 31;

        if ( datum_monat == 2 )
        {
          if ( ((datum_jahr % 400) == 0) || ((datum_jahr % 100) > 0) && ((datum_jahr % 4) == 0) )
          {
            anzahl_tage = 29;
          }
          else
          {
            anzahl_tage = 28;
          }
        }
        else if ( datum_monat == 4 || datum_monat == 6 || datum_monat == 9 || datum_monat == 11 )
        {
          anzahl_tage = 30;
        }

        /*
         * Pruefung: Maximalgrenze Tag
         * Es gibt nur dann ein Ergebnis, wenn der Tag kleiner 
         * oder gleich der Maximalgrenze ist.
         */
        if ( datum_tag <= anzahl_tage )
        {
          /*
           * Ergebnisaufbau
           * Rueckgabe im Format JJJJMMTT.
           */
          return datum_jahr * 10000 + datum_monat * 100 + datum_tag;
        }
      }
    }
    catch (Exception err_inst)
    {
      /*
       * Ein Fehler beim Lesen des Datums ergibt eine Rueckgabe von 0.
       */

      System.out.println( err_inst.getMessage() );
    }

    /*
     * Fehler: Rueckgabe von 0
     */
    return 0; // Fehler: EINGABE_IST_KEIN_DATUM
  }

  /**
   * <pre>
   * Liefert fuer eine gueltige Datumseingabe im Format JJJJMMTT einen Long-Wert im Format JJJJMMTT.
   * Wandlung eines Stringdatums in einen Long-Wert mit Datumspruefung.
   *
   * Ist die Eingabe kein gueltiges Datum, wird 0 zurueckgegeben.
   *
   * Die Eingabe muss eine Laenge von 8 Stellen haben.
   * Das Jahr muss 4stellig sein. Tag und Monat muessen 2stellig sein.
   *
   * Das Jahr muss mindestens 1583 sein (wegen Gregorianischem Kalender).
   * Maximale Jahresangabe ist 9999.
   *
   * Es wird kein Trim auf die Eingabe gemacht.
   *
   * Eingbe           20100115
   * Index-Position   01234567
   * Zeichen-Positon  12345678
   *
   * FkDatumLong.getLongAusJJJJMMTT( "20100115"  ) = 20100115
   * FkDatumLong.getLongAusJJJJMMTT( "20120229"  ) = 20120229
   * FkDatumLong.getLongAusJJJJMMTT( "20100229"  ) = 0 = Datum existiert nicht
   * FkDatumLong.getLongAusJJJJMMTT( "20100999"  ) = 0 = Tageswert zu hoch (99)
   * FkDatumLong.getLongAusJJJJMMTT( "15810101"  ) = 0 = Jahr zu klein
   * FkDatumLong.getLongAusJJJJMMTT( "00000000"  ) = 0 = Jahr zu klein
   * FkDatumLong.getLongAusJJJJMMTT( "99999999"  ) = 0 = Fehler im Monat und Tag
   * FkDatumLong.getLongAusJJJJMMTT( "20109901"  ) = 0 = Monat zu hoch (99)
   * FkDatumLong.getLongAusJJJJMMTT( "201008  "  ) = 0 = Eingabe endet auf Leerzeichen (kein Trim)
   * FkDatumLong.getLongAusJJJJMMTT( "JJJJMMTT"  ) = 0 = Eingabe keine Zahl
   * FkDatumLong.getLongAusJJJJMMTT( "12010"     ) = 0 = Eingabe zu kurz
   * FkDatumLong.getLongAusJJJJMMTT( ""          ) = 0 = Eingabe zu kurz
   * FkDatumLong.getLongAusJJJJMMTT( null        ) = 0 = Eingabe ist "null" bzw. nicht vorhanden
   * FkDatumLong.getLongAusJJJJMMTT( "20100115A" ) = 0 = Eingabe zu lang
   * FkDatumLong.getLongAusJJJJMMTT( "1.1.2010"  ) = 0 = Eingabe enthaelt ungueltige Zeichen
   *                                                     (keine Trennzeichen erlaubt)
   *
   * String string_datum = "20101230";
   *
   * long datum_long = FkDatumLong.getLongAusJJJJMMTT( string_datum );
   *
   * if ( datum_long > 0 )
   * {
   *   ...
   * }
   *
   * </pre>
   *
   * @param pEingabe die Eingabe im Format JJJJMMTT (Zahlen und 8 Stellen)
   * @return 0 oder ein Datum im Format JJJJMMTT
   */
  public static long getLongAusJJJJMMTT( String pEingabe )
  {
    try
    {
      /*
       * Pruefung: Ist die Eingabe null?
       * Ist die Eingabe null, bekommt der Aufrufer 0 zurueck.
       */
      if ( pEingabe == null )
      {
        return 0; // Fehler: EINGABE_NICHT_GESETZT
      }

      /*
       * Pruefung: Zeichenanzahl korrekt ?
       * Die Eingabe muss genau 8 Stellen lang sein. 
       * Ist die Eingabe ungleich 8 Stellen, bekommt der Aufrufer 0 zurueck.
       */
      if ( pEingabe.length() != 8 )
      {
        return 0; // Fehler: EINGABE_LAENGE_UNGLEICH_8
      }

      /*
       * Deklaration der Datumsvariablen fuer Tag, Monat und Jahr.
       * Die Variablen werden mit -1 initialisiert.
       */
      int datum_tag = -1;
      int datum_monat = -1;
      int datum_jahr = -1;

      int akt_zahl = 0;

      /*
       * Leseposition
       * Die Variable "akt_index" ist die Index-Position im Eingabestring. 
       * Die Index-Position beginnt bei 0 und endet am Index 7. 
       */
      int akt_index = 0;

      /*
       * While-Schleife ueber die Eingabe zum lesen von Tag, Monat und Jahr.
       * 
       * Es werden 8 Zeichen ab Index-Position 0 gelesen.
       * Durch die obige Laengenpruefung ist sichergestellt, dass die Eingabe hier 8 Zeichen hat.
       * 
       * Ist der Leseprozess an der Position 3 und 5 angelangt, wird jeweils 
       * eine Zuordnung an die Datumsbestandteile gemacht. 
       */
      while ( akt_index < 8 )
      {
        /*
         * Aktuelles Zeichen aus der Eingabe lesen
         */
        char akt_char = pEingabe.charAt( akt_index );

        /*
         * Zeichenpruefung
         * Das Datum im Format JJJJMMTT besteht nur aus Zahlen.
         * Ist das aktuelle Zeichen keine Zahl, wird die Funktion mit 0 verlassen.
         */
        if ( (akt_char < '0') || (akt_char > '9') )
        {
          return 0; // Fehler: EINGABE_ZEICHEN_UNGUELTIG
        }

        /*
         * Berechnung Akt-Zahl
         * Der Wert in der Variablen "akt_zahl" wird mit 10 multipliziert und 
         * der Wert des aktuellen Zeichens hinzugezaehlt. 
         * 
         * Der Wert des aktuellen Zeichens ist der Wert des ASCII-Code abzueglich 48.
         */
        akt_zahl = (akt_zahl * 10) + (((int) akt_char) - 48);

        /*
         * Zuweisung von Jahr und Monat nach Index-Positionen
         */
        if ( akt_index == 3 )
        {
          /*
           * Jahr - Index 3 (4 Stellen gelesen)
           * Die ersten 4 Stellen sind Zahlen und geben das Jahr an.
           * Beim Erreichen des 4ten Zeichens wird das Jahr mit dem 
           * Wert der Variablen "akt_zahl" gesetzt.
           * 
           * Die Variable "akt_zahl" wird anschliessend auf 0 gesetzt.
           */
          datum_jahr = akt_zahl;

          akt_zahl = 0;
        }
        else if ( akt_index == 5 )
        {
          /*
           * Monat - Index 5 (6 Stellen gelesen)
           * Die Zeichen 5 und 6 sind Zahlen und geben den Monat an.  
           * Beim Erreichen des 6ten Zeichens wird der Monat mit 
           * dem Wert der Variablen "akt_zahl" gesetzt.
           * 
           * Die Variable "akt_zahl" wird anschliessend auf 0 gesetzt.
           */
          datum_monat = akt_zahl;

          akt_zahl = 0;
        }

        /*
         * Leseprozess ein Zeichen weiter stellen 
         */
        akt_index ++;
      }

      /*
       * Tag 
       * Die Zeichen 7 und 8 sind Zahlen und geben den Tag an.
       * Nach der While-Schleife ist in der Variablen "akt_zahl" der Tag gesetzt.
       * Der Tag wird mit dem Wert aus "akt_zahl" gesetzt.
       */
      datum_tag = akt_zahl;

      /*
       * Pruefung: Datumswerte 
       * Der Tag muss groesser als 1 sein, der Maximalwerttest kommt spaeter.
       * Der Monat muss zwischen 1 und 12 liegen
       * Das Jahr muss zwischen 1583 und 9999 liegen 
       */
      if ( (datum_tag > 0) && ((datum_monat >= 1) && (datum_monat <= 12)) && ((datum_jahr >= 1583) && (datum_jahr <= 9999)) )
      {
        /*
         * Ermittlung Maximalwert Tag
         * Fuer die Maximalwertpruefung des Tages wird die Anzahl der Tage von 
         * Monat und Jahr bestimmt. Dabei wird das Schaltjahr beruecksichtigt.
         */
        int anzahl_tage = 31;

        if ( datum_monat == 2 )
        {
          if ( ((datum_jahr % 400) == 0) || ((datum_jahr % 100) > 0) && ((datum_jahr % 4) == 0) )
          {
            anzahl_tage = 29;
          }
          else
          {
            anzahl_tage = 28;
          }
        }
        else if ( datum_monat == 4 || datum_monat == 6 || datum_monat == 9 || datum_monat == 11 )
        {
          anzahl_tage = 30;
        }

        /*
         * Pruefung: Maximalgrenze Tag
         * Ist der uebergebene Tag kleiner oder gleich der Maximalgrenze,
         * wird das Ergebnisdatum erstellt und zurueckgegeben 
         *
         * Liegt der uebergebene Tag hinter der Maximalgrenze, ist das 
         * Datum ungueltig und der Aufrufer bekommt 0 zurueck. 
         */
        if ( datum_tag <= anzahl_tage )
        {
          /*
           * Ergebnisaufbau
           * Es wird das Datum im Format JJJJMMTT zurueckgegeben.
           */
          return datum_jahr * 10000 + datum_monat * 100 + datum_tag;
        }
      }
    }
    catch (Exception err_inst)
    {
      // Ein Fehler in der While-Schleife ergibt eine Rueckgabe von 0
    }

    return 0; // Fehler: EINGABE_IST_KEIN_DATUM 
  }

  /**
   * <pre>
   * Liefert fuer eine gueltige Datumseingabe im Format TTMMJJJJ einen Long-Wert im Format JJJJMMTT.
   * Wandlung eines Stringdatums in einen Long-Wert mit Datumspruefung.
   *
   * Ist die Eingabe kein gueltiges Datum, wird 0 zurueckgegeben.
   *
   * Die Eingabe muss eine Laenge von 8 Stellen haben.
   * Das Jahr muss 4stellig sein. Tag und Monat muessen 2stellig sein.
   *
   * Das Jahr muss mindestens 1583 sein (wegen Gregorianischem Kalender).
   * Maximale Jahresangabe ist 9999.
   *
   * Es wird kein Trim auf die Eingabe gemacht.
   *
   * Eingbe           15012010
   * Index-Position   01234567
   * Zeichen-Positon  12345678
   *
   *
   * FkDatumLong.getLongAusTTMMJJJJ( "15012010"  ) = 20100115
   * FkDatumLong.getLongAusTTMMJJJJ( "29022012"  ) = 20120229
   * FkDatumLong.getLongAusTTMMJJJJ( "29022010"  ) = 0 = Datum existiert nicht
   * FkDatumLong.getLongAusTTMMJJJJ( "99092010"  ) = 0 = Tageswert zu hoch (99)
   * FkDatumLong.getLongAusTTMMJJJJ( "01132010"  ) = 0 = Monatswert zu hoch (13)
   * FkDatumLong.getLongAusTTMMJJJJ( "01011581"  ) = 0 = Jahr zu klein
   * FkDatumLong.getLongAusTTMMJJJJ( "20101215"  ) = 0
   * FkDatumLong.getLongAusTTMMJJJJ( "TTMMJJJJ"  ) = 0 = Eingabe keine Zahl
   * FkDatumLong.getLongAusTTMMJJJJ( "12010"     ) = 0 = Eingabe zu kurz
   * FkDatumLong.getLongAusTTMMJJJJ( ""          ) = 0 = Eingabe zu kurz
   * FkDatumLong.getLongAusTTMMJJJJ( null        ) = 0 = Eingabe ist "null" bzw. nicht vorhanden
   * FkDatumLong.getLongAusTTMMJJJJ( "15012010A" ) = 0 = Eingabe zu lang
   * FkDatumLong.getLongAusTTMMJJJJ( "00000000"  ) = 0 = Jahr zu klein
   * FkDatumLong.getLongAusTTMMJJJJ( "99999999"  ) = 0 = Fehler im Monat und Tag
   * FkDatumLong.getLongAusTTMMJJJJ( "1.1.2010"  ) = 0 = Eingabe enthaelt ungueltige Zeichen
   *                                                     (keine Trennzeichen erlaubt)
   * String string_datum = "20101230";
   *
   * long datum_long = FkDatumLong.getLongAusTTMMJJJJ( string_datum );
   *
   * if ( datum_long > 0 )
   * {
   *   ...
   * }
   *
   * </pre>
   *
   * @param pEingabe die Eingabe im Format TTMMJJJJ (Zahlen und 8 Stellen)
   * @return 0 oder ein Datum im Format JJJJMMTT
   */
  public static long getLongAusTTMMJJJJ( String pEingabe )
  {
    try
    {
      /*
       * Pruefung: Ist die Eingabe null?
       * Ist die Eingabe null, bekommt der Aufrufer 0 zurueck.
       */
      if ( pEingabe == null )
      {
        return 0; // Fehler: EINGABE_NICHT_GESETZT
      }

      /*
       * Pruefung: Zeichenanzahl korrekt ?
       * Die Eingabe muss genau 8 Stellen lang sein. 
       * Ist die Eingabe ungleich 8 Stellen, bekommt der Aufrufer 0 zurueck.
       */
      if ( pEingabe.length() != 8 )
      {
        return 0; // Fehler: EINGABE_LAENGE_UNGLEICH_8
      }

      /*
       * Deklaration der Datumsvariablen fuer Tag, Monat und Jahr.
       * Die Variablen werden mit -1 initialisiert.
       */
      int datum_tag = -1;
      int datum_monat = -1;
      int datum_jahr = -1;

      int akt_zahl = 0;

      /*
       * Leseposition
       * Die Variable "akt_index" ist die Index-Position im Eingabestring. 
       * Die Index-Position beginnt bei 0 und endet am Index 7. 
       */
      int akt_index = 0;

      /*
       * Index-Position: 01234567  
       * Eingabe:        06012010   
       */

 /*
       * While-Schleife ueber die Eingabe zum lesen von Tag, Monat und Jahr.
       * 
       * Es werden 8 Zeichen ab Index-Position 0 gelesen.
       * Durch die obige Laengenpruefung ist sichergestellt, dass die Eingabe hier 8 Zeichen hat.
       * 
       * Ist der Leseprozess an der Position 1 und 3 angelangt, wird jeweils 
       * eine Zuordnung an die Datumsbestandteile gemacht. 
       */
      while ( akt_index < 8 )
      {
        /*
         * Aktuelles Zeichen aus der Eingabe lesen
         */
        char akt_char = pEingabe.charAt( akt_index );

        /*
         * Zeichenpruefung
         * Das Datum im Format JJJJMMTT besteht nur aus Zahlen.
         * Ist das aktuelle Zeichen keine Zahl, wird die Funktion mit 0 verlassen.
         */
        if ( (akt_char < '0') || (akt_char > '9') )
        {
          return 0; // Fehler: EINGABE_ZEICHEN_UNGUELTIG
        }

        /*
         * Berechnung Akt-Zahl
         * Der Wert in der Variablen "akt_zahl" wird mit 10 multipliziert und 
         * der Wert des aktuellen Zeichens hinzugezaehlt. 
         * 
         * Der Wert des aktuellen Zeichens ist der Wert des ASCII-Code abzueglich 48.
         */
        akt_zahl = (akt_zahl * 10) + (((int) akt_char) - 48);

        /*
         * Zuweisung von Tag und Monat nach Index-Positionen
         */
        if ( akt_index == 1 )
        {
          /*
           * Tag - Index 1 (2 Stellen gelesen)
           * Die ersten 2 Stellen sind Zahlen und geben den Tag an.
           * Beim Erreichen des 2ten Zeichens wird der Tag mit dem 
           * Wert der Variablen "akt_zahl" gesetzt.
           * 
           * Die Variable "akt_zahl" wird anschliessend auf 0 gesetzt.
           */
          datum_tag = akt_zahl;

          akt_zahl = 0;
        }
        else if ( akt_index == 3 )
        {
          /*
           * Monat - Index 3 (4 Stellen gelesen)
           * Die Zeichen an den Indexpositionen 2 und 3 sind Zahlen 
           * und geben den Monat an. Beim Erreichen des 4ten Zeichens 
           * wird der Monat mit dem Wert der Variablen "akt_zahl" gesetzt.
           * 
           * Die Variable "akt_zahl" wird anschliessend auf 0 gesetzt.
           */
          datum_monat = akt_zahl;

          akt_zahl = 0;
        }

        /*
         * Leseprozess ein Zeichen weiter stellen 
         */
        akt_index ++;
      }

      /*
       * Tag 
       * Die Zeichen 4, 5, 6 und 7 sind Zahlen und geben das Jahr an.
       * Nach der While-Schleife ist in der Variablen "akt_zahl" das Jahr gesetzt.
       * Das Jahr wird mit dem Wert aus "akt_zahl" gesetzt.
       */
      datum_jahr = akt_zahl;

      /*
       * Pruefung: Datumswerte 
       * Der Tag muss groesser als 1 sein, der Maximalwerttest kommt spaeter.
       * Der Monat muss zwischen 1 und 12 liegen
       * Das Jahr muss zwischen 1583 und 9999 liegen 
       */
      if ( (datum_tag > 0) && ((datum_monat >= 1) && (datum_monat <= 12)) && ((datum_jahr >= 1583) && (datum_jahr <= 9999)) )
      {
        /*
         * Ermittlung Maximalwert Tag
         * Fuer die Maximalwertpruefung des Tages wird die Anzahl der Tage von 
         * Monat und Jahr bestimmt. Dabei wird das Schaltjahr beruecksichtigt.
         */
        int anzahl_tage = 31;

        if ( datum_monat == 2 )
        {
          if ( ((datum_jahr % 400) == 0) || ((datum_jahr % 100) > 0) && ((datum_jahr % 4) == 0) )
          {
            anzahl_tage = 29;
          }
          else
          {
            anzahl_tage = 28;
          }
        }
        else if ( datum_monat == 4 || datum_monat == 6 || datum_monat == 9 || datum_monat == 11 )
        {
          anzahl_tage = 30;
        }

        /*
         * Pruefung: Maximalgrenze Tag
         * Ist der uebergebene Tag kleiner oder gleich der Maximalgrenze,
         * wird das Ergebnisdatum erstellt und zurueckgegeben 
         *
         * Liegt der uebergebene Tag hinter der Maximalgrenze, ist das 
         * Datum ungueltig und der Aufrufer bekommt 0 zurueck. 
         */
        if ( datum_tag <= anzahl_tage )
        {
          /*
           * Ergebnisaufbau
           * Es wird das Datum im Format JJJJMMTT zurueckgegeben.
           */
          return datum_jahr * 10000 + datum_monat * 100 + datum_tag;
        }
      }
    }
    catch (Exception err_inst)
    {
      // Ein Fehler in der While-Schleife ergibt eine Rueckgabe von 0
    }

    return 0; // Fehler: EINGABE_IST_KEIN_DATUM 
  }

  /**
   * <pre>
   * Gibt das Datum im Format JJJJMMTT als long zurueck.
   *
   * Sind die Datumsangaben ungueltig, wird 0 zurueckgegeben.
   *
   * Das Jahr ist gueltig, wenn dieses im Bereich von 1583 und 9999 liegt.
   * Der Monat ist gueltig, wenn dieser im Bereich von 1 bis 12 liegt.
   * Der Tag ist gueltig, wenn dieser dem Gregorianischen-Kalender folgt.
   *
   * FkDatumLong.getLong(   2010,  6,  1 ) = 20100106
   *
   * FkDatumLong.getLong(   1583,  1,  1 ) = 15830101
   * FkDatumLong.getLong(   1582, 12,  1 ) = 0
   *
   * FkDatumLong.getLong(  -2010,  6,  1 ) = 0 = Jahr negativ
   * FkDatumLong.getLong(   2010, -6,  1 ) = 0 = Monat negativ
   * FkDatumLong.getLong(   2010,  6, -1 ) = 0 = Tag negativ
   * FkDatumLong.getLong(   2010,  2, 29 ) = 0 = Tag gibt es nicht
   * FkDatumLong.getLong(  20101,  6,  1 ) = 0 = Jahr zu gross
   * FkDatumLong.getLong(   2010, 13,  1 ) = 0 = Monat zu gross
   * FkDatumLong.getLong(   2010,  6, 32 ) = 0 = Tag zu gross
   * FkDatumLong.getLong(   1234, 56, 78 ) = 0
   *
   * </pre>
   *
   * @param pJahr das Jahr
   * @param pMonat der Monat
   * @param pTag der Tag
   * @return das formatierte Datum als Datentyp long
   */
  public static long getLong( int pJahr, int pMonat, int pTag )
  {
    /*
     * Pruefung: Datumswerte 
     * Der Tag muss groesser als 1 sein, der Maximalwerttest kommt spaeter.
     * Der Monat muss zwischen 1 und 12 liegen
     * Das Jahr muss zwischen 1583 und 9999 liegen 
     */
    if ( (pTag >= 1) && ((pMonat >= 1) && (pMonat <= 12)) && ((pJahr >= 1583) && (pJahr <= 9999)) )
    {
      /*
       * Ermittlung Maximalwert Tag
       * Fuer die Maximalwertpruefung des Tages wird die Anzahl der Tage von 
       * Monat und Jahr bestimmt. Dabei wird das Schaltjahr beruecksichtigt.
       */
      int anzahl_tage = 31;

      if ( pMonat == 2 )
      {
        if ( ((pJahr % 400) == 0) || ((pJahr % 100) > 0) && ((pJahr % 4) == 0) )
        {
          anzahl_tage = 29;
        }
        else
        {
          anzahl_tage = 28;
        }
      }
      else if ( pMonat == 4 || pMonat == 6 || pMonat == 9 || pMonat == 11 )
      {
        anzahl_tage = 30;
      }

      /*
       * Pruefung: Maximalgrenze Tag
       * Es gibt nur dann ein Ergebnis, wenn der uebergebene Tag kleiner 
       * oder gleich der Maximalgrenze ist.
       */
      if ( pTag <= anzahl_tage )
      {
        /*
         * Ergebnisaufbau
         * Rueckgabe im Format JJJJMMTT.
         */
        return pJahr * 10000 + pMonat * 100 + pTag;
      }
    }

    return 0; // Fehler: EINGABE_IST_KEIN_DATUM
  }

  /**
   * <pre>
   * Prueft den Wert des Parameters "pLongWert" darauf ab, ob dieses ein Datum im Format JJJJMMTT ist.
   *
   * Ist es ein Long-Datum, wird der Eingabeparameter zurueckgegeben.
   *
   * Ist es kein Long-Datum, wird 0 zurueckgegeben.
   *
   * Das Jahr muss groesser gleich 1583 und kleiner als 9999 sein.
   *
   * Diese Funktion dient dazu, ein Long-Datum zu pruefen.
   *
   * Das Jahr ist gueltig, wenn dieses im Bereich von 1583 und 9999 liegt.
   * Der Monat ist gueltig, wenn dieser im Bereich von 1 bis 12 liegt.
   * Der Tag ist gueltig, wenn dieser dem Gregorianischen-Kalender folgt.
   *
   * FkDatumLong.getLong(  20100101 ) = 20100101
   * FkDatumLong.getLong(  20120229 ) = 20120229
   *
   * FkDatumLong.getLong(  20100100 ) = 0 = Tag ist 0
   * FkDatumLong.getLong(  20100199 ) = 0 = Tag zu gross
   *
   * FkDatumLong.getLong(  20100001 ) = 0 = Monat ist 0
   * FkDatumLong.getLong(  20109901 ) = 0 = Monat zu gross
   *
   * FkDatumLong.getLong(  20100229 ) = 0 = Tag gibt es nicht
   *
   * FkDatumLong.getLong(  15830101 ) = 15830101 = Eingabe gleich Untergrenze
   * FkDatumLong.getLong(  15820101 ) = 0        = Eingabe zu klein
   *
   * FkDatumLong.getLong(  99991231 ) = 99991231 = Eingabe ist Obergrenze
   * FkDatumLong.getLong(  99991232 ) = 0        = Eingabe zu gross
   *
   * FkDatumLong.getLong(         0 ) = 0 = Eingabe zu klein
   * FkDatumLong.getLong( -20100101 ) = 0 = Eingabe negativ
   * FkDatumLong.getLong( 201001011 ) = 0 = Eingabe zu gross
   * FkDatumLong.getLong( 220100101 ) = 0 = Eingabe zu gross
   *
   * </pre>
   *
   * @param pLongWert der auf ein Datum zu pruefende Wert
   * @return 0 wenn der Wert im Parameter "pLongWert" kein Datum ergibt, sonst
   * das Datum im Format JJJJMMTT
   */
  public static long getLong( long pLongWert )
  {
    /*
     * Parameterpruefung auf Einhaltung der Grenzen von 15830101 bis 99991231.
     */
    if ( (pLongWert >= 15830101) && (pLongWert <= 99991231) )
    {
      /*
       * Rausrechnen von Jahr, Monat und Tag aus der Eingabe
       */
      int datum_jahr = (int) (pLongWert * 0.0001);

      int datum_monat = (int) ((pLongWert - (datum_jahr * 10000)) * 0.01);

      int datum_tag = (int) (pLongWert - (datum_jahr * 10000 + datum_monat * 100));

      /*
       * Pruefung: Datumswerte 
       * Der Tag muss groesser als 0 sein, der Maximalwerttest kommt spaeter.
       * Der Tag kann an dieser Stelle eventuell 0 sein.
       * Der Monat muss zwischen 1 und 12 liegen
       * Das Jahr wurde implizit geprueft und muss kein zweites mal geprueft werden. 
       */
      if ( (datum_tag > 0) && ((datum_monat >= 1) && (datum_monat <= 12)) )
      {
        /*
         * Ermittlung Maximalwert Tag
         * Fuer die Maximalwertpruefung des Tages wird die Anzahl der Tage von 
         * Monat und Jahr bestimmt. Dabei wird das Schaltjahr beruecksichtigt.
         */
        int anzahl_tage = 31;

        if ( datum_monat == 2 )
        {
          if ( ((datum_jahr % 400) == 0) || ((datum_jahr % 100) > 0) && ((datum_jahr % 4) == 0) )
          {
            anzahl_tage = 29;
          }
          else
          {
            anzahl_tage = 28;
          }
        }
        else if ( datum_monat == 4 || datum_monat == 6 || datum_monat == 9 || datum_monat == 11 )
        {
          anzahl_tage = 30;
        }

        /*
         * Pruefung: Maximalgrenze Tag
         * Es gibt nur dann ein Ergebnis, wenn der uebergebene Tag kleiner 
         * oder gleich der Maximalgrenze ist.
         */
        if ( datum_tag <= anzahl_tage )
        {
          /*
           * Ergebnisaufbau
           * Bei der Rueckgabe wird das Datum aus dem Parameter zurueckgegeben,
           * da die Pruefung ergeben hat, dass das ein gueltiges Datum ist und 
           * schon im korrektem Format vorliegt. Es muss nicht gerechnet werden.
           */
          return pLongWert;
        }
      }
    }

    return 0; // Fehler: EINGABE_IST_KEIN_DATUM
  }

  /**
   * <pre>
   * Gibt das Datum im Format JJJJMMTT als long zurueck.
   *
   * Sind die Datumsangaben ungueltig, wird 0 zurueckgegeben.
   *
   * Das Jahr ist gueltig, wenn dieses im Bereich von 1583 und 9999 liegt.
   * Der Monat ist gueltig, wenn dieser im Bereich von 1 bis 12 liegt.
   * Der Tag ist gueltig, wenn dieser dem Gregorianischen-Kalender folgt.
   *
   * FkDatumLong.getLong( 2010,  6, 1 ) = 20100106
   *
   * FkDatumLong.getLong( 1583,  1, 1 ) = 15830101
   * FkDatumLong.getLong( 1582, 12, 1 ) = 0
   *
   * FkDatumLong.getLong( -2010,  6,  1 ) = 0 = Jahr negativ
   * FkDatumLong.getLong(  2010, -6,  1 ) = 0 = Monat negativ
   * FkDatumLong.getLong(  2010,  6, -1 ) = 0 = Tag negativ
   * FkDatumLong.getLong(  2010,  2, 29 ) = 0 = Tag gibt es nicht
   * FkDatumLong.getLong(  1234, 56, 78 ) = 0
   * </pre>
   *
   * @param pJahr das Jahr
   * @param pMonat der Monat
   * @param pTag der Tag
   * @return das formatierte Datum als Datentyp long
   */
  public static long getLong( long pJahr, long pMonat, long pTag )
  {
    /*
     * Pruefung: Datumswerte 
     * Der Tag muss groesser als 1 sein, der Maximalwerttest kommt spaeter.
     * Der Monat muss zwischen 1 und 12 liegen
     * Das Jahr muss zwischen 1583 und 9999 liegen 
     */
    if ( (pTag >= 1) && ((pMonat >= 1) && (pMonat <= 12)) && ((pJahr >= 1583) && (pJahr <= 9999)) )
    {
      /*
       * Ermittlung Maximalwert Tag
       * Fuer die Maximalwertpruefung des Tages wird die Anzahl der Tage von 
       * Monat und Jahr bestimmt. Dabei wird das Schaltjahr beruecksichtigt.
       */
      int anzahl_tage = 31;

      if ( pMonat == 2 )
      {
        if ( ((pJahr % 400) == 0) || ((pJahr % 100) > 0) && ((pJahr % 4) == 0) )
        {
          anzahl_tage = 29;
        }
        else
        {
          anzahl_tage = 28;
        }
      }
      else if ( pMonat == 4 || pMonat == 6 || pMonat == 9 || pMonat == 11 )
      {
        anzahl_tage = 30;
      }

      /*
       * Pruefung: Maximalgrenze Tag
       * Es gibt nur dann ein Ergebnis, wenn der uebergebene Tag kleiner 
       * oder gleich der Maximalgrenze ist.
       */
      if ( pTag <= anzahl_tage )
      {
        /*
         * Ergebnisaufbau
         * Rueckgabe im Format JJJJMMTT.
         */
        return pJahr * 10000 + pMonat * 100 + pTag;
      }
    }

    return 0; // Fehler: EINGABE_IST_KEIN_DATUM
  }

  public static String getSystemDateTimeDataObjectEA( String pDatum )
  {
    try
    {
      /*
       * Pruefung: Ist die Eingabe null?
       * Ist die Eingabe null, bekommt der Aufrufer null zurueck.
       */
      if ( pDatum == null )
      {
        return null; // Fehler: EINGABE_NICHT_GESETZT
      }

      /*
       * Pruefung: Zeichenanzahl korrekt?
       * Die Eingabe muss genau 8 Stellen lang sein. 
       * Ist die Eingabe ungleich 8 Stellen, bekommt der Aufrufer null zurueck.
       */
      if ( pDatum.length() != 14 )
      {
        return null; // Fehler: EINGABE_LAENGE_NICHT_KORREKT
      }

      /*
       * Deklaration der Datumsvariablen fuer Tag, Monat und Jahr, sowie 
       * fuer die Zeitwerte Stunde, Minute und Sekunde. Die Variablen 
       * werden mit -1 initialisiert.
       */
      int datum_tag = -1;
      int datum_monat = -1;
      int datum_jahr = -1;

      int akt_stunde = -1;
      int akt_minute = -1;
      int akt_sekunde = -1;

      int akt_zahl = 0;

      int akt_index = 0;

      /*
       * While-Schleife ueber die Eingabe zum lesen von Tag, Monat und Jahr.
       */
      while ( akt_index < 14 )
      {
        /*
         * Aktuelles Zeichen aus der Eingabe lesen
         */
        char akt_char = pDatum.charAt( akt_index );

        /*
         * Zeichenpruefung
         * Das Datum im Format JJJJMMTT besteht nur aus Zahlen.
         * Ist das aktuelle Zeichen keine Zahl, wird die Funktion mit null verlassen.
         */
        if ( (akt_char < '0') || (akt_char > '9') )
        {
          return null; // Fehler: EINGABE_ZEICHEN_UNGUELTIG
        }

        /*
         * Berechnung Akt-Zahl
         * Der Wert in der Variablen "akt_zahl" wird mit 10 multipliziert und 
         * der Wert des aktuellen Zeichens hinzugezaehlt. 
         * 
         * Der Wert des aktuellen Zeichens ist der Wert des ASCII-Code abzueglich 48. 
         */
        akt_zahl = (akt_zahl * 10) + (((int) akt_char) - 48);

        /*
         * Zuweisung von Jahr und Monat nach Index-Positionen
         */
        if ( akt_index == 3 )
        {
          /*
           * Index 3 (4 Stellen gelesen)
           * Die ersten 4 Stellen sind Zahlen und geben das Jahr an.
           * Beim Erreichen des 4ten Zeichens wird das Jahr mit dem 
           * Wert der Variablen "akt_zahl" gesetzt.
           * 
           * Die Variable "akt_zahl" wird anschliessend auf 0 gesetzt.
           */
          datum_jahr = akt_zahl;

          akt_zahl = 0;
        }
        else if ( akt_index == 5 )
        {
          /*
           * Index 5
           * Die Zeichen 5 und 6 sind Zahlen und geben den Monat an.  
           * Beim Erreichen des 6ten Zeichens wird der Monat mit 
           * dem Wert der Variablen "akt_zahl" gesetzt.
           * 
           * Die Variable "akt_zahl" wird anschliessend auf 0 gesetzt.
           */
          datum_monat = akt_zahl;

          akt_zahl = 0;
        }
        else if ( akt_index == 7 )
        {
          datum_tag = akt_zahl;

          akt_zahl = 0;
        }
        else if ( akt_index == 9 )
        {
          akt_stunde = akt_zahl;

          akt_zahl = 0;
        }
        else if ( akt_index == 11 )
        {
          akt_minute = akt_zahl;

          akt_zahl = 0;
        }

        /*
         * Leseprozess ein Zeichen weiter stellen 
         */
        akt_index ++;
      }

      /*
       * Die Zeichen 7 und 8 sind Zahlen und geben den Tag an.
       * Nach der While-Schleife ist in der Variablen "akt_zahl" der Tag gesetzt.
       * Der Tag wird mit dem Wert aus "akt_zahl" gesetzt.
       */
      akt_sekunde = akt_zahl;

      /*
       * Pruefung: Datumswerte 
       * Der Tag muss groesser als 0 sein, der Maximalwert test kommt spaeter.
       * Der Monat muss zwischen 1 und 12 liegen
       * Das Jahr muss zwischen 1583 und 9999 liegen 
       */
      if ( (datum_tag > 0) && ((datum_monat >= 1) && (datum_monat <= 12)) && ((datum_jahr >= 1583) && (datum_jahr <= 9999)) )
      {
        /*
         * Ermittlung Maximalwert Tag
         * Fuer die Maximalwertpruefung des Tages wird die Anzahl der Tage von 
         * Monat und Jahr bestimmt. Dabei wird das Schaltjahr beruecksichtigt.
         */
        int anzahl_tage = 31;

        if ( datum_monat == 2 )
        {
          if ( ((datum_jahr % 400) == 0) || ((datum_jahr % 100) > 0) && ((datum_jahr % 4) == 0) )
          {
            anzahl_tage = 29;
          }
          else
          {
            anzahl_tage = 28;
          }
        }
        else if ( datum_monat == 4 || datum_monat == 6 || datum_monat == 9 || datum_monat == 11 )
        {
          anzahl_tage = 30;
        }

        /*
         * Pruefung: Maximalgrenze Tag
         * Ist der uebergebene Tag kleiner oder gleich der Maximalgrenze,
         * wird der Ergebnisstring erstellt. 
         *
         * Liegt der uebergebene Tag hinter der Maximalgrenze, ist das 
         * Datum ungueltig und es gibt kein Ergebnis. 
         */
        if ( datum_tag <= anzahl_tage )
        {
          return datum_jahr + (datum_monat < 10 ? "-0" : "-") + datum_monat + (datum_tag < 10 ? "-0" : "-") + datum_tag + (akt_stunde < 10 ? "T0" : "T") + akt_stunde + (akt_minute < 10 ? ":0" : ":") + akt_minute + (akt_sekunde < 10 ? ":0" : ":") + akt_sekunde;
        }
      }
    }
    catch (Exception err_inst)
    {
      /*
       * Ein Fehler beim Lesen des Datums ergibt eine Rueckgabe von null.
       */
    }

    /*
     * Funktionsende
     * Der Aufrufer bekommt das erstellte Ergebnis zurueck. 
     */
    return null; // Fehler: EINGABE_IST_KEIN_DATUM
  }

  /**
   * <pre>
   * Erstellt aus der Eingabe JJJJxMMxTT oder JJJJxMxTT oder JJJJxMMxT die Ausgabe JJJJMMTT.
   *
   * Das Eingabedatum muss korrekt sein.
   *
   * Die Eingabe muss eine Laenge zwischen 8 und 10 Stellen haben.
   *
   * Das Trennzeichen muss in der Eingabe vorkommen.
   *
   * Das Trennzeichen darf keine Zahl sein.
   *
   * Ist die Eingabe kein gueltiges Datum, wird 0 zurueckgegeben.
   *
   * Das Jahr muss 4stellig angegeben werden.
   *
   * Die Tages- und Monatseingabe sind 2 Stellen lang.
   * Fuehrende 0en koennen weggelassen werden.
   * Mehr als 2 Stellen in diesen Angaben fuehren zum Fehler.
   *
   * Das Jahr ist gueltig, wenn dieses im Bereich von 1583 und 9999 liegt.
   * Der Monat ist gueltig, wenn dieser im Bereich von 1 bis 12 liegt.
   * Der Tag ist gueltig, wenn dieser dem Gregorianischen-Kalender folgt.
   *
   * FkDatumLong.getLongAusJJJJxMMxTT( "2010-01-06",    '-' ) = 20100106
   * FkDatumLong.getLongAusJJJJxMMxTT( "2010-01-6",     '-' ) = 20100106
   * FkDatumLong.getLongAusJJJJxMMxTT( "2010-1-06",     '-' ) = 20100106
   * FkDatumLong.getLongAusJJJJxMMxTT( "2010-1-6",      '-' ) = 20100106
   * FkDatumLong.getLongAusJJJJxMMxTT( " 2010-01-06 ",  '-' ) = 0
   * FkDatumLong.getLongAusJJJJxMMxTT( "2010 01 06",    '-' ) = 0 = Trennzeichen nicht in Eingabe vorhanden
   * FkDatumLong.getLongAusJJJJxMMxTT( "2010-02-31",    '-' ) = 0
   * FkDatumLong.getLongAusJJJJxMMxTT( "2010-13-01",    '-' ) = 0
   * FkDatumLong.getLongAusJJJJxMMxTT( "2010.12.01",    '-' ) = 0
   * FkDatumLong.getLongAusJJJJxMMxTT( "20101201",      '-' ) = 0
   * FkDatumLong.getLongAusJJJJxMMxTT( "2010-02-28",    '-' ) = 20100228
   * FkDatumLong.getLongAusJJJJxMMxTT( "2010-02-29",    '-' ) = 0
   * FkDatumLong.getLongAusJJJJxMMxTT( "1234-67-90",    '-' ) = 0
   * FkDatumLong.getLongAusJJJJxMMxTT( "ABCD-EF-GH",    '-' ) = 0
   * FkDatumLong.getLongAusJJJJxMMxTT( "2010-01-06",    'X' ) = 0        = Trennzeichen nicht in Eingabe vorhanden
   * FkDatumLong.getLongAusJJJJxMMxTT( "2010X06X01",    'X' ) = 20100106
   * FkDatumLong.getLongAusJJJJxMMxTT( null,            '-' ) = 0        = Eingabe nicht gesetzt
   * FkDatumLong.getLongAusJJJJxMMxTT( "",              '-' ) = 0        = Eingabe ist ein Leerstring
   *
   * FkDatumLong.getLongAusJJJJxMMxTT( "0000-06-01",    '-' ) = 0        = Jahr ist 0
   * FkDatumLong.getLongAusJJJJxMMxTT( "2010-00-01",    '-' ) = 0        = Monat ist 0
   * FkDatumLong.getLongAusJJJJxMMxTT( "2010-06-00",    '-' ) = 0        = Tag ist 0
   *
   * FkDatumLong.getLongAusJJJJxMMxTT( "2020-01-02",    '-' ) = 20200102
   * FkDatumLong.getLongAusJJJJxMMxTT( "2020-1-02",     '-' ) = 20200102
   * FkDatumLong.getLongAusJJJJxMMxTT( "2020-01-2",     '-' ) = 20200102
   * FkDatumLong.getLongAusJJJJxMMxTT( "2020-1-2",      '-' ) = 20200102
   *
   * FkDatumLong.getLongAusJJJJxMMxTT( "2020-1-002",    '-' ) = 0        = zu viele fuehrende 0en Tag
   * FkDatumLong.getLongAusJJJJxMMxTT( "2020-001-2",    '-' ) = 0        = zu viele fuehrende 0en Monat
   * FkDatumLong.getLongAusJJJJxMMxTT( "02020-1-02",    '-' ) = 0        = zu viele fuehrende 0en Jahr
   * FkDatumLong.getLongAusJJJJxMMxTT( "002020-1-2",    '-' ) = 0        = zu viele fuehrende 0en Jahr
   * FkDatumLong.getLongAusJJJJxMMxTT( "2020-0001-",    '-' ) = 0        = zu viele fuehrende 0en Monat, kein Tag
   * FkDatumLong.getLongAusJJJJxMMxTT( "2020-00001",    '-' ) = 0        = zu viele fuehrende 0en Monat, kein Tag
   *
   * FkDatumLong.getLongAusJJJJxMMxTT( "20-01-02",      '-' ) = 0        = zu wenig Stellen Jahr
   * FkDatumLong.getLongAusJJJJxMMxTT( "2020001002",    '-' ) = 0        = Kein Trennzeichen
   * FkDatumLong.getLongAusJJJJxMMxTT( "2020--1-02",    '-' ) = 0        = zu viele Trennzeichen
   * FkDatumLong.getLongAusJJJJxMMxTT( "2020-01--2",    '-' ) = 0        = zu viele Trennzeichen
   *
   * FkDatumLong.getLongAusJJJJxMMxTT( "2020001002",    '0' ) = 0        = Trennzeichen ist eine Zahl
   * FkDatumLong.getLongAusJJJJxMMxTT( "2020901902",    '9' ) = 0        = Trennzeichen ist eine Zahl
   *
   * </pre>
   *
   * @param pDatum die Eingabe
   * @param pTrennzeichen das Trennzeichen der Datumsbestandteile
   * @return 0 oder ein Datum im Format JJJJMMTT als long-Wert
   */
  public static long getLongAusJJJJxMMxTT( String pDatum, char pTrennzeichen )
  {
    try
    {
      /*
       * Pruefung: Ist die Eingabe null?
       * Ist die Eingabe null, bekommt der Aufrufer 0 zurueck.
       */
      if ( pDatum == null )
      {
        return 0; // Fehler: EINGABE_NICHT_GESETZT
      }

      /*
       * Pruefung: Trennzeichen gleich Zahl ?
       * 
       * Das Trennzeichen selber darf keine Zahl sein.
       * Wurde als Trennzeichen eine Zahl uebergeben, wird 
       * 0 zurueckgegeben.
       */
      if ( (pTrennzeichen >= '0') && (pTrennzeichen <= '9') )
      {
        return 0; // Fehler: TRENNZEICHEN_IST_ZAHL
      }

      /*
       * Ermittlung der Zeichenanzahl der Eingabe
       */
      int anzahl_eingabe_zeichen = pDatum.length();

      /*
       * Pruefung: Zeichenanzahl korrekt?
       * 
       * Die Eingabe darf nicht kuerzer als 8 Zeichen sein. 
       * Das sind zwei Punkte und 6 Zahlen.
       * 
       * Die Eingabe darf nicht laenger als 10 Zeichen sein. 
       * Das sind zwei Punkte und 8 Zahlen.
       * 
       * Liegt eine ungueltige Laenge der Eingabe vor, bekommt der Aufrufer 0 zurueck.
       */
      if ( (anzahl_eingabe_zeichen < 8) || (anzahl_eingabe_zeichen > 10) )
      {
        return 0; // Fehler: EINGABE_LAENGE_NICHT_KORREKT
      }

      /*
       * Deklaration der Datumsvariablen fuer Tag, Monat und Jahr.
       * Die Variablen werden mit -1 initialisiert.
       */
      int datum_tag = -1;
      int datum_monat = -1;
      int datum_jahr = -1;

      int akt_zahl = 0;

      /*
       * Leseposition
       * Die Variable "akt_index" ist die Index-Position im Eingabestring. 
       * Die Index-Position beginnt bei 0. 
       */
      int akt_index = 0;

      /*
       * While-Schleife ueber die Eingabe zum lesen von Tag, Monat und Jahr.
       */
      while ( akt_index < anzahl_eingabe_zeichen )
      {
        /*
         * Aktuelles Zeichen aus der Eingabe lesen
         */
        char akt_char = pDatum.charAt( akt_index );

        /*
         * Pruefung: Trennzeichen gefunden und aktuelle Zahl groesser 0 ?
         * 
         * Das Trennzeichen muss zweimal vorhanden sein.
         * Bei einem Trennzeichen muss die aktuelle Zahl groesser als 0 sein.
         * Ein Datumsbestandteil kann nicht 0 sein.
         * 
         * Ist die aktuelle Zahl nicht groesser als 0, wird das aktuelle 
         * Zeichen (=Trennzeichen) als ungueltig erkannt und fuehrt in der 
         * naechsten IF-Bedingung zu einem Fehler (Rueckgabe von 0).
         * (Kein Start mit einem Trennzeichen, 2 Trennzeichen hintereinander)
         * 
         * Ist die aktuelle Zahl groesser als 0, wird beim ersten Trennzeichen 
         * das Jahr und beim zweiten Trennzeichen der Monat gesetzt. 
         * Der Tag wird nach der While-Schleife gesetzt.
         * 
         * Wird ein drittes Trennzeichen erkannt, sind Jahr und Monat 
         * schon gesetzt und es kommt zur Rueckgabe von 0.
         * 
         * Ist in der Eingabe kein Trennzeichen vorhanden, kommt es eventuell 
         * zu einer Ueberlaufexception, oder nach der While-Schleife ist 
         * der Monat oder das Jahr noch auf dem Initialwert von -1.
         * 
         * Nach der Zuweisung von Jahr oder Monat, wird die aktuelle Zahl  
         * auf 0 gestellt.
         */
        if ( (akt_char == pTrennzeichen) && (akt_zahl > 0) )
        {
          if ( (datum_jahr == -1) && (akt_index == 4) )
          {
            /*
             * Das Jahr hat 4 Stellen. Das erste Trennzeichen kommt somit
             * an der Indexposition 5.
             * 
             * Das Jahr ist korrekt eingelesen, wenn das erste Trennzeichen 
             * an der 4ten Indexposition steht (= 5te Stelle im String).
             */

            datum_jahr = akt_zahl;
          }
          else if ( (datum_monat == -1) && (akt_index <= 7) )
          {
            /*
             * Der Monat ist korrekt eingelesen, wenn das zweite Trennzeichen 
             * an einer Indexposition kleiner gleich 7 steht. 
             * 
             * Das Jahr wird immer 4stellig gelesen, daher kann an dieser 
             * Stelle die 7 als konstanter Wert stehen.
             */
            datum_monat = akt_zahl;

            /*
             * Um eine korrekte Tagesangebe lesen zu koennen, duerfen in 
             * der Eingabe nicht mehr als 2 Stellen uebrig bleiben. 
             * 
             * Da hier die Lesepositon noch nicht erhoeht worden ist, 
             * muss die Pruefung auf groesser 3 lauten.
             * 
             * Bleiben mehr Stellen uebrig, wird 0 zurueckgegeben.
             *
             * Wird kein Tag angegeben, ist in der Variablen "akt_zahl"
             * der Wert 0 gespeichert, was in der Datumspruefung zu
             * einem Fehler fuehrt.
             */
            if ( (anzahl_eingabe_zeichen - akt_index) > 3 )
            {
              return 0; // Fehler: TRENNZEICHEN_AN_FALSCHER_POSITION
            }
          }
          else
          {
            return 0; // Fehler: TRENNZEICHEN_FEHLER
          }

          akt_zahl = 0;
        }
        /*
         * Zeichenpruefung
         * Ist das aktuelle Zeichen keine Zahl, wird die Funktion mit 0 verlassen.
         * Das ist auch der Fall, wenn das erste Zeichen ein Punkt ist, da in 
         * diesem Fall der Wert in der Variablen "akt_zahl" gleich 0 ist.
         */
        else if ( (akt_char < '0') || (akt_char > '9') )
        {
          return 0; // Fehler: EINGABE_ZEICHEN_UNGUELTIG
        }
        else
        {
          /*
           * Berechnung Akt-Zahl
           * Der Wert in der Variablen "akt_zahl" wird mit 10 multipliziert und 
           * der Wert des aktuellen Zeichens hinzugezaehlt. 
           * 
           * Der Wert des aktuellen Zeichens ist der Wert des ASCII-Code abzueglich 48. 
           */
          akt_zahl = (akt_zahl * 10) + (((int) akt_char) - 48);
        }

        /*
         * Leseprozess ein Zeichen weiter stellen 
         */
        akt_index ++;
      }

      /*
       * Nach der While-Schleife ist in der Variablen "akt_zahl" der Tag gesetzt.
       * Der Tag wird hier mit dem Wert aus "akt_zahl" gesetzt.
       */
      datum_tag = akt_zahl;

      /*
       * Pruefung: Datumswerte 
       * Der Tag muss groesser als 0 sein, der Maximalwert test kommt spaeter.
       * Der Monat muss zwischen 1 und 12 liegen
       * Das Jahr muss zwischen 1583 und 9999 liegen 
       */
      if ( (datum_tag > 0) && ((datum_monat >= 1) && (datum_monat <= 12)) && ((datum_jahr >= 1583) && (datum_jahr <= 9999)) )
      {
        /*
         * Ermittlung Maximalwert Tag
         * Fuer die Maximalwertpruefung des Tages wird die Anzahl der Tage von 
         * Monat und Jahr bestimmt. Dabei wird das Schaltjahr beruecksichtigt.
         */
        int anzahl_tage = 31;

        if ( datum_monat == 2 )
        {
          if ( ((datum_jahr % 400) == 0) || ((datum_jahr % 100) > 0) && ((datum_jahr % 4) == 0) )
          {
            anzahl_tage = 29;
          }
          else
          {
            anzahl_tage = 28;
          }
        }
        else if ( datum_monat == 4 || datum_monat == 6 || datum_monat == 9 || datum_monat == 11 )
        {
          anzahl_tage = 30;
        }

        /*
         * Pruefung: Maximalgrenze Tag
         * Es gibt nur dann ein Ergebnis, wenn der uebergebene Tag kleiner 
         * oder gleich der Maximalgrenze ist.
         */
        if ( datum_tag <= anzahl_tage )
        {
          /*
           * Der Aufrufer bekommt das Datum im Format JJJJMMTT zurueck.
           */
          return datum_jahr * 10000 + datum_monat * 100 + datum_tag;
        }
      }
    }
    catch (Exception err_inst)
    {
      /*
       * Ein Fehler beim Lesen des Datums ergibt eine Rueckgabe von 0.
       */
    }

    /*
     * Bei einem Fehler, bekommt der Aufrufer 0 zurueck. 
     */
    return 0; // Fehler: EINGABE_IST_KEIN_DATUM
  }

  /**
   * <pre>
   * Erstellt aus der Eingabe TTxMMxJJJJ die Ausgabe JJJJMMTT.
   *
   * Das Eingabedatum muss korrekt sein.
   *
   * Die Eingabe muss eine Laenge zwischen 8 und 10 Stellen haben.
   *
   * Das Trennzeichen muss in der Eingabe vorkommen.
   * Das Trennzeichen darf keine Zahl sein.
   *
   * Ist die Eingabe kein gueltiges Datum, wird 0 zurueckgegeben.
   *
   * Das Jahr muss 4stellig angegeben werden.
   *
   * Die Tages- und Monatseingabe sind 2 Stellen lang.
   * Fuehrende 0en koennen bei diesen Angaben weggelassen werden.
   * Mehr als 2 Stellen in diesen Angaben fuehren zum Fehler.
   *
   * FkDatumLong.getLongAusTTxMMxJJJJ( "06-01-2010",    '-' ) = 20100106
   * FkDatumLong.getLongAusTTxMMxJJJJ( "06-1-2010",     '-' ) = 20100106
   * FkDatumLong.getLongAusTTxMMxJJJJ( "6-01-2010",     '-' ) = 20100106
   * FkDatumLong.getLongAusTTxMMxJJJJ( "6-1-2010",      '-' ) = 20100106
   * FkDatumLong.getLongAusTTxMMxJJJJ( "06-01-2010 ",   '-' ) = 0
   * FkDatumLong.getLongAusTTxMMxJJJJ( "06 01-2010",    '-' ) = 0
   * FkDatumLong.getLongAusTTxMMxJJJJ( "02-31-2010",    '-' ) = 0
   * FkDatumLong.getLongAusTTxMMxJJJJ( "13-01-2010",    '-' ) = 20100113
   * FkDatumLong.getLongAusTTxMMxJJJJ( "01-12-2010",    '-' ) = 20101201
   * FkDatumLong.getLongAusTTxMMxJJJJ( "02-28-2010",    '-' ) = 0
   * FkDatumLong.getLongAusTTxMMxJJJJ( "02-29-2010",    '-' ) = 0
   * FkDatumLong.getLongAusTTxMMxJJJJ( "67-90-1234",    '-' ) = 0
   * FkDatumLong.getLongAusTTxMMxJJJJ( "EF-GH-ABCD",    '-' ) = 0
   * FkDatumLong.getLongAusTTxMMxJJJJ( "06-01-2010",    'X' ) = 0
   * FkDatumLong.getLongAusTTxMMxJJJJ( "06X01X2010",    'X' ) = 20100106
   * FkDatumLong.getLongAusTTxMMxJJJJ( null,            '-' ) = 0
   * FkDatumLong.getLongAusTTxMMxJJJJ( "",              '-' ) = 0
   *
   * FkDatumLong.getLongAusTTxMMxJJJJ( "00-01-2010",    '-' ) = 0
   * FkDatumLong.getLongAusTTxMMxJJJJ( "06-00-2010",    '-' ) = 0
   * FkDatumLong.getLongAusTTxMMxJJJJ( "06-01-0000",    '-' ) = 0
   *
   * FkDatumLong.getLongAusTTxMMxJJJJ( "06.01.2010",    '.' ) = 20100106
   * FkDatumLong.getLongAusTTxMMxJJJJ( "006.1.2010",    '.' ) = 0
   * FkDatumLong.getLongAusTTxMMxJJJJ( "6.001.2010",    '.' ) = 0
   * FkDatumLong.getLongAusTTxMMxJJJJ( "006.1.2010",    '.' ) = 0
   * FkDatumLong.getLongAusTTxMMxJJJJ( "6.1.002010",    '.' ) = 0
   * FkDatumLong.getLongAusTTxMMxJJJJ( "006001-2010",   '-' ) = 0
   *
   * FkDatumLong.getLongAusTTxMMxJJJJ( "0100202020",    '0' ) = 0
   * FkDatumLong.getLongAusTTxMMxJJJJ( "0190292020",    '9' ) = 0
   * </pre>
   *
   * @param pDatum die Eingabe
   * @param pTrennzeichen das Trennzeichen der Datumsbestandteile. Das
   * Trennzeichen darf keine Zahl sein.
   * @return 0 oder ein Datum im Format JJJJMMTT als long-Wert
   */
  public static long getLongAusTTxMMxJJJJ( String pDatum, char pTrennzeichen )
  {
    try
    {
      /*
       * Pruefung: Ist die Eingabe null?
       * Ist die Eingabe null, bekommt der Aufrufer 0 zurueck.
       */
      if ( pDatum == null )
      {
        return 0; // Fehler: EINGABE_NICHT_GESETZT
      }

      /*
       * Pruefung: Trennzeichen gleich Zahl ?
       * 
       * Das Trennzeichen selber darf keine Zahl sein.
       * Wurde als Trennzeichen eine Zahl uebergeben, wird 
       * 0 zurueckgegeben.
       */
      if ( (pTrennzeichen >= '0') && (pTrennzeichen <= '9') )
      {
        return 0; // Fehler: TRENNZEICHEN_IST_ZAHL
      }

      /*
       * Ermittlung der Zeichenanzahl der Eingabe
       */
      int anzahl_eingabe_zeichen = pDatum.length();

      /*
       * Pruefung: Zeichenanzahl korrekt?
       * 
       * Die Eingabe darf nicht kuerzer als 8 Zeichen sein. 
       * Das sind zwei Punkte und 6 Zahlen.
       * 
       * Die Eingabe darf nicht laenger als 10 Zeichen sein. 
       * Das sind zwei Punkte und 8 Zahlen.
       * 
       * Liegt eine ungueltige Laenge der Eingabe vor, bekommt der Aufrufer 0 zurueck.
       */
      if ( (anzahl_eingabe_zeichen < 8) || (anzahl_eingabe_zeichen > 10) )
      {
        return 0; // Fehler: EINGABE_LAENGE_NICHT_KORREKT
      }

      /*
       * Deklaration der Datumsvariablen fuer Tag, Monat und Jahr.
       * Die Variablen werden mit -1 initialisiert.
       */
      int datum_tag = -1;
      int datum_monat = -1;
      int datum_jahr = -1;

      int akt_zahl = 0;

      /*
       * Leseposition
       * Die Variable "akt_index" ist die Index-Position im Eingabestring. 
       * Die Index-Position beginnt bei 0. 
       */
      int akt_index = 0;

      /*
       * Position Punkt Erwartet
       * 
       * Die Position des ersten erwarteten Punktes wird auf 5 gestellt.
       * 
       * Index-Position: 0123456789  
       * Eingabe:        06-01-2010  
       */
      int position_punkt_erwartet = 5;

      /*
       * While-Schleife ueber die Eingabe zum lesen von Tag, Monat und Jahr.
       */
      while ( akt_index < anzahl_eingabe_zeichen )
      {
        /*
         * Aktuelles Zeichen aus der Eingabe lesen
         */
        char akt_char = pDatum.charAt( akt_index );

        /*
         * Pruefung: Trennzeichen gefunden und aktuelle Zahl groesser 0 ?
         * 
         * Das Trennzeichen muss zweimal vorhanden sein.
         * Bei einem Trennzeichen muss die aktuelle Zahl groesser als 0 sein.
         * Ein Datumsbestandteil kann nicht 0 sein.
         * 
         * Ist die aktuelle Zahl nicht groesser als 0, wird das aktuelle 
         * Zeichen (=Trennzeichen) als ungueltig erkannt und fuehrt in der 
         * naechsten IF-Bedingung zu einem Fehler (Rueckgabe von 0).
         * (Kein Start mit einem Trennzeichen, 2 Trennzeichen hintereinander)
         * 
         * Ist die aktuelle Zahl groesser als 0, wird beim ersten Trennzeichen 
         * der Tag und beim zweiten Trennzeichen der Monat gesetzt. 
         * Das Jahr wird nach der While-Schleife gesetzt.
         * 
         * Wird ein drittes Trennzeichen erkannt, sind Tag und Monat 
         * schon gesetzt und es kommt zur Rueckgabe von 0.
         * 
         * Ist in der Eingabe kein Trennzeichen vorhanden, kommt es eventuell 
         * zu einer Ueberlaufexception, oder nach der While-Schleife ist 
         * der Monat oder das Jahr noch auf dem Initialwert von -1.
         * 
         * Nach der Zuweisung von Tag oder Monat, wird die aktuelle Zahl  
         * auf 0 gestellt.
         */
        if ( (akt_char == pTrennzeichen) && (akt_zahl > 0) )
        {
          if ( (datum_tag == -1) && (akt_index <= 2) )
          {
            /*
             * Tag
             * Der Tag hat minimal eine Stelle und maximal zwei Stellen.
             * 
             * Das Trennzeichen kommt somit an Position 1 oder 2 vor.
             * 
             * Ist die aktuelle Leseposition kleiner gleich 2, ist der Tag 
             * korrekt eingelesen worden. 
             * 
             * Die Position des naechsten Punktes liegt maximal 3 Stellen
             * nach der aktuellen Position.
             */
            datum_tag = akt_zahl;

            position_punkt_erwartet = akt_index + 3;
          }
          else if ( (datum_monat == -1) && (akt_index <= position_punkt_erwartet) )
          {
            datum_monat = akt_zahl;

            /*
             * Um ein korrektes Jahr zu lesen, muessen in der Eingabe 
             * noch 4 Stellen vorhanden sein. 
             * 
             * Ist die Differenz ungleich 4, wird 0 zurueckgegeben.
             * 
             * In der unten stehenden Pruefung wurde die Punkt-Position 
             * beruecksichtigt. Es wird gegen 5 getestet, da die 
             * Leseposition noch nicht weitergestellt wurde.
             */
            if ( (anzahl_eingabe_zeichen - akt_index) != 5 )
            {
              return 0; // Fehler: TRENNZEICHEN_AN_FALSCHER_POSITION
            }
          }
          else
          {
            return 0; // Fehler: TRENNZEICHEN_FEHLER
          }

          akt_zahl = 0;
        }
        /*
         * Zeichenpruefung
         * Ist das aktuelle Zeichen keine Zahl, wird die Funktion mit null verlassen.
         * Das ist auch der Fall, wenn das erste Zeichen ein Punkt ist, da in 
         * diesem Fall der Wert in der Variablen "akt_zahl" gleich 0 ist.
         */
        else if ( (akt_char < '0') || (akt_char > '9') )
        {
          return 0; // Fehler: EINGABE_ZEICHEN_UNGUELTIG
        }
        else
        {
          /*
           * Berechnung Akt-Zahl
           * Der Wert in der Variablen "akt_zahl" wird mit 10 multipliziert und 
           * der Wert des aktuellen Zeichens hinzugezaehlt. 
           * 
           * Der Wert des aktuellen Zeichens ist der Wert des ASCII-Code abzueglich 48. 
           */
          akt_zahl = (akt_zahl * 10) + (((int) akt_char) - 48);
        }

        /*
         * Leseprozess ein Zeichen weiter stellen 
         */
        akt_index ++;
      }

      /*
       * Nach der While-Schleife ist in der Variablen "akt_zahl" das Jahr gesetzt.
       * Das Jahr wird hier mit dem Wert aus "akt_zahl" gesetzt.
       */
      datum_jahr = akt_zahl;

      /*
       * Pruefung: Datumswerte 
       * Der Tag muss groesser als 0 sein, der Maximalwert test kommt spaeter.
       * Der Monat muss zwischen 1 und 12 liegen
       * Das Jahr muss zwischen 1583 und 9999 liegen 
       */
      if ( (datum_tag > 0) && ((datum_monat >= 1) && (datum_monat <= 12)) && ((datum_jahr >= 1583) && (datum_jahr <= 9999)) )
      {
        /*
         * Ermittlung Maximalwert Tag
         * Fuer die Maximalwertpruefung des Tages wird die Anzahl der Tage von 
         * Monat und Jahr bestimmt. Dabei wird das Schaltjahr beruecksichtigt.
         */
        int anzahl_tage = 31;

        if ( datum_monat == 2 )
        {
          if ( ((datum_jahr % 400) == 0) || ((datum_jahr % 100) > 0) && ((datum_jahr % 4) == 0) )
          {
            anzahl_tage = 29;
          }
          else
          {
            anzahl_tage = 28;
          }
        }
        else if ( datum_monat == 4 || datum_monat == 6 || datum_monat == 9 || datum_monat == 11 )
        {
          anzahl_tage = 30;
        }

        /*
         * Pruefung: Maximalgrenze Tag
         * Es gibt nur dann ein Ergebnis, wenn der uebergebene Tag kleiner 
         * oder gleich der Maximalgrenze ist.
         */
        if ( datum_tag <= anzahl_tage )
        {
          /*
           * Der Aufrufer bekommt das Datum im Format JJJJMMTT zurueck.
           */
          return datum_jahr * 10000 + datum_monat * 100 + datum_tag;
        }
      }
    }
    catch (Exception err_inst)
    {
      /*
       * Ein Fehler beim Lesen des Datums ergibt eine Rueckgabe von 0.
       */
    }

    /*
     * Bei einem Fehler, bekommt der Aufrufer 0 zurueck. 
     */
    return 0; // Fehler: EINGABE_IST_KEIN_DATUM
  }

  /**
   * <pre>
   * Gibt vom uebergebenen Datum den Jahres und Monatsbestandteil als long zurueck.
   *
   * Ist "pDatum" gleich "null" wird 0 zurueckgegeben.
   * </pre>
   *
   * @param pDatum das Datum als Instanz von java.util.Date
   * @return einen long-Wert im Format JJJJMM
   */
  public static long getJJJJMM( Date pDatum )
  {
    /*
     * Pruefung: "pDatum" gleich "null" ?
     * 
     * Ist der Parameter gleich "null" wird 0 zurueckgegeben.
     */
    if ( pDatum == null )
    {
      return 0; // Fehler: EINGABE_IST_NICHT_GESETZT
    }

    Calendar datum_ergebnis = Calendar.getInstance();

    datum_ergebnis.setTime( pDatum );

    return (datum_ergebnis.get( Calendar.YEAR ) * 100) + (datum_ergebnis.get( Calendar.MONTH ) + 1);
  }

  /**
   * <pre>
   * Gibt vom aktuellen Datum den Jahres und Monatsbestandteil als long zurueck.
   * </pre>
   *
   * @return einen long-Wert im Format JJJJMM
   */
  public static long getJJJJMM()
  {
    return ((Calendar.getInstance().get( Calendar.YEAR ) * 100) + (Calendar.getInstance().get( Calendar.MONTH ) + 1));
  }

  /**
   * <pre>
   * Gibt vom Parameterdatum den Jahres und Monatsbestandteil als long zurueck.
   *
   * Das Datum wird auf Plausibilitat geprueft.
   * Ergibt die Eingabe kein Datum, wird 0 zurueckgegeben.
   *
   * </pre>
   *
   * @param pDatum ein Datum im Format JJJJMMTT
   * @return Ergibt die Eingabe ein gueltiges Datum, wird long-Wert im Format
   * JJJJMM zurueckgegeben. Ist die Eingabe kein Datum, wird 0 zurueckgegeben.
   */
  public static long getJJJJMM( long pDatum )
  {
    /*
     * Aus dem Parameterwert werden die Angaben fuer das Jahr und den Monat rausgerechnet.
     */
    int datum_jahr = (int) (pDatum * 0.0001);

    int datum_monat = (int) ((pDatum - (datum_jahr * 10000)) * 0.01);

    int datum_tag = (int) (pDatum - (datum_jahr * 10000 + datum_monat * 100));

    /*
     * Pruefung: Datumswerte 
     * Der Tag muss groesser als 0 sein, der Maximalwert test kommt spaeter.
     * Der Monat muss zwischen 1 und 12 liegen
     * Das Jahr muss zwischen 1583 und 9999 liegen 
     */
    if ( (datum_tag > 0) && ((datum_monat >= 1) && (datum_monat <= 12)) && ((datum_jahr >= 1583) && (datum_jahr <= 9999)) )
    {
      /*
       * Ermittlung Maximalwert Tag
       * Fuer die Maximalwertpruefung des Tages wird die Anzahl der Tage von 
       * Monat und Jahr bestimmt. Dabei wird das Schaltjahr beruecksichtigt.
       */
      int anzahl_tage = 31;

      if ( datum_monat == 2 )
      {
        if ( ((datum_jahr % 400) == 0) || ((datum_jahr % 100) > 0) && ((datum_jahr % 4) == 0) )
        {
          anzahl_tage = 29;
        }
        else
        {
          anzahl_tage = 28;
        }
      }
      else if ( datum_monat == 4 || datum_monat == 6 || datum_monat == 9 || datum_monat == 11 )
      {
        anzahl_tage = 30;
      }

      /*
       * Pruefung: Maximalgrenze Tag
       * Es gibt nur dann ein Ergebnis, wenn der uebergebene Tag kleiner 
       * oder gleich der Maximalgrenze ist.
       */
      if ( datum_tag <= anzahl_tage )
      {
        /*
         * Der Aufrufer bekommt das Datum im Format JJJJMM zurueck.
         */
        return datum_jahr * 100 + datum_monat;
      }
    }

    return 0; // Fehler: EINGABE_MONAT_ODER_JAHR_FALSCH
  }

  /**
   * <pre>
   * Liefert fuer eine gueltige Datumseingabe, das Datum im Long-Format zurueck.
   *
   * Ergeben die Eingaben ein ungueltiges Datum, wird 0 zurueckgegeben.
   *
   * Das Jahr muss 4stellig uebergeben werden.
   * Der Tag und der Monat sind 1 bis 2 stellig zu uebergeben.
   *
   * FkDatumDate.getLongAusTagMonatJahr( "01", "02", "9999" ) = 99990201
   * FkDatumDate.getLongAusTagMonatJahr( "01", "01", "2010" ) = 20100101
   *
   * FkDatumDate.getLongAusTagMonatJahr(  "1", "02", "2010" ) = 20100201
   * FkDatumDate.getLongAusTagMonatJahr( "01",  "2", "2010" ) = 20100201
   * FkDatumDate.getLongAusTagMonatJahr(  "1",  "2", "2010" ) = 20100201
   *
   * FkDatumDate.getLongAusTagMonatJahr( "29", "02", "2010" ) = 0
   * FkDatumDate.getLongAusTagMonatJahr( "29", "02", "2011" ) = 0
   * FkDatumDate.getLongAusTagMonatJahr( "29", "02", "2012" ) = 20120229
   *
   * Ungueltiger Wertebereich:
   * FkDatumDate.getLongAusTagMonatJahr( "00", "01", "2010" ) = 0
   * FkDatumDate.getLongAusTagMonatJahr( "32", "01", "2010" ) = 0
   * FkDatumDate.getLongAusTagMonatJahr( "01", "00", "2010" ) = 0
   * FkDatumDate.getLongAusTagMonatJahr( "01", "13", "2010" ) = 0
   *
   * Zu viele fuehrende 0en:
   * FkDatumDate.getLongAusTagMonatJahr( "001", "02", "2010" ) = 0
   * FkDatumDate.getLongAusTagMonatJahr( "01", "002", "2010" ) = 0
   * FkDatumDate.getLongAusTagMonatJahr( "01", "02", "02010" ) = 0
   *
   * Ungueltige Zeichen in der Eingabe
   * FkDatumDate.getLongAusTagMonatJahr( "x1", "02", "2010" ) = 0
   * FkDatumDate.getLongAusTagMonatJahr( "01", "x2", "2010" ) = 0
   * FkDatumDate.getLongAusTagMonatJahr( "01", "02", "20x0" ) = 0
   *
   * FkDatumDate.getLongAusTagMonatJahr( "1.", "02", "2010" ) = 0
   * FkDatumDate.getLongAusTagMonatJahr( "01", ".2", "2010" ) = 0
   *
   * Uebergabe von "null":
   * FkDatumDate.getLongAusTagMonatJahr( null, "02", "2010" ) = 0
   * FkDatumDate.getLongAusTagMonatJahr( "01", null, "2010" ) = 0
   * FkDatumDate.getLongAusTagMonatJahr( "01", "02",   null ) = 0
   *
   * </pre>
   *
   * @param pTag der Tag
   * @param pMonat der Monat (1 bis 12)
   * @param pJahr das Jahr (1583 bis 9999)
   * @return Fuer ein gueltiges Datum, die Eingabe im Format JJJJMMTT. Fuer ein
   * ungueltiges Datum, wird 0 zurueckgegeben.
   */
  public static long getLongAusTagMonatJahr( String pTag, String pMonat, String pJahr )
  {
    try
    {
      /*
       * JAHR
       * Das Jahr muss eine 4stellige Zahl sein. 
       * Es wird die Funktion "parseZahl4" aufgerufen, welche eben solche Zahlen parst. 
       * Ist die Eingabe keine 4stellige Zahl, wird 0 zurueckgegeben.
       */
      int datum_jahr = parseZahl4( pJahr );

      /*
       * Das gelesene Jahr wird gegen die gueltigen Grenzwerte geprueft. 
       * Das Jahr muss groesser/gleich 1583 und kleiner 9999 sein. 
       * Liegt das geparste Jahr ausserhalb dieser Werte, wird keine weitere 
       * Verarbeitung gemacht. Der Aufrufer bekommt 0 zurueck.
       */
      if ( (datum_jahr >= 1583) && (datum_jahr <= 9999) )
      {
        /*
         * MONAT 
         * Der Monat ist eine 2stellige Zahl sein. 
         * Der Monat muss groesser/gleich 1 und kleiner/gleich 12 sein.
         */
        int datum_monat = parseZahl2( pMonat );

        /*
         * Nachdem der Monat geparst worden ist, wird der Wert gegen die 
         * gueltigen Werte geprueft. Liegt der Wert aussserhalb dieser Grenzen,
         * wird 0 zurueckgegeben.
         * 
         * Der Monat muss zwischen 1 und 12 liegen
         */
        if ( (datum_monat >= 1) && (datum_monat <= 12) )
        {
          /*
           * TAG
           * Der Tag ist eine 2stellige Zahl. 
           * Der Tag wird aus der Eingabe geparst.
           */
          int datum_tag = parseZahl2( pTag );

          /*
           * Es wird nach dem parsen des Tages geprueft, ob
           * der Tag groesser als 0 ist. Ist der Tag 0, wird
           * 0 zurueckgegeben. 
           */
          if ( datum_tag > 0 )
          {
            /*
             * Ermittlung Maximalwert Tag
             * Fuer die Maximalwertpruefung des Tages wird die Anzahl der Tage von 
             * Monat und Jahr bestimmt. Dabei wird das Schaltjahr beruecksichtigt.
             */
            int anzahl_tage = 31;

            if ( datum_monat == 2 )
            {
              if ( ((datum_jahr % 400) == 0) || ((datum_jahr % 100) > 0) && ((datum_jahr % 4) == 0) )
              {
                anzahl_tage = 29;
              }
              else
              {
                anzahl_tage = 28;
              }
            }
            else if ( datum_monat == 4 || datum_monat == 6 || datum_monat == 9 || datum_monat == 11 )
            {
              anzahl_tage = 30;
            }

            /*
             * Pruefung: Maximalgrenze Tag
             * Es gibt nur dann ein Ergebnis, wenn der uebergebene Tag kleiner 
             * oder gleich der Maximalgrenze ist.
             */
            if ( datum_tag <= anzahl_tage )
            {
              /*
               * Ergebnisaufbau
               * Rueckgabe im Format JJJJMMTT.
               */
              return datum_jahr * 10000 + datum_monat * 100 + datum_tag;
            }
          }
        }
      }
    }
    catch (Exception err_inst)
    {
      /*
       * Ein Fehler beim Lesen des Datums ergibt eine Rueckgabe von 0.
       */
    }

    /*
     * Fehler: Rueckgabe von 0
     */
    return 0;
  }

  /**
   * @param pEingabe die zu parsende Zahl mit 4 Stellen
   * @return einen Intwert der Eingabe, oder 0 bei einem Fehler
   */
  private static int parseZahl4( String pEingabe )
  {
    /*
     * Pruefung: Ist die Eingabe null?
     * Ist die Eingabe null, bekommt der Aufrufer 0 zurueck.
     */
    if ( pEingabe == null )
    {
      return 0;
    }

    /*
     * Pruefung: Zeichenanzahl korrekt?
     * Die Eingabe muss genau 4 Zeichen entsprechen. 
     * Ist die Eingabe ungleich 4, bekommt der Aufrufer 0 zurueck.
     */
    if ( pEingabe.length() != 4 )
    {
      return 0;
    }

    int akt_zahl = 0;

    int akt_index = 0;

    /*
     * While-Schleife ueber die Eingabe zum lesen der Zahl aus der Eingabe
     */
    while ( akt_index < 4 )
    {
      /*
       * Aktuelles Zeichen aus der Eingabe lesen
       */
      char akt_char = pEingabe.charAt( akt_index );

      /*
       * Zeichenpruefung
       * Ist das aktuelle Zeichen keine Zahl, wird die Funktion mit 0 verlassen.
       */
      if ( (akt_char < '0') || (akt_char > '9') )
      {
        return 0;
      }

      /*
       * Berechnung Akt-Zahl
       * Der Wert in der Variablen "akt_zahl" wird mit 10 multipliziert und 
       * der Wert des aktuellen Zeichens hinzugezaehlt. 
       * 
       * Der Wert des aktuellen Zeichens ist der Wert des ASCII-Code abzueglich 48. 
       */
      akt_zahl = (akt_zahl * 10) + (((int) akt_char) - 48);

      /*
       * Leseprozess ein Zeichen weiter stellen 
       */
      akt_index ++;
    }

    /*
     * Nach der While-Schleife wird die gelesene Jahreszahl zurueckgegeben.
     * Es werden keine Pruefungen auf den Wertebereich gemacht.
     */
    return akt_zahl;
  }

  /**
   * @param pEingabe die zu parsende Zahl mit 2 Stellen
   * @return einen Intwert der Eingabe, oder 0 bei einem Fehler
   */
  private static int parseZahl2( String pEingabe )
  {
    /*
     * Pruefung: Ist die Eingabe null?
     * Ist die Eingabe null, bekommt der Aufrufer 0 zurueck.
     */
    if ( pEingabe == null )
    {
      return 0;
    }

    int anzahl_zeichen = pEingabe.length();

    /*
     * Pruefung: Zeichenanzahl korrekt?
     * Die Eingabe muss 1 oder 2 Zeichen haben. 
     * Ist die Eingabe gleich 0 oder laenger als 2, bekommt der Aufrufer 0 zurueck.
     */
    if ( (anzahl_zeichen == 0) || (anzahl_zeichen > 2) )
    {
      return 0;
    }

    int akt_zahl = 0;

    int akt_index = 0;

    /*
     * While-Schleife ueber die Eingabe zum lesen der Zahl aus der Eingabe
     */
    while ( akt_index < anzahl_zeichen )
    {
      /*
       * Aktuelles Zeichen aus der Eingabe lesen
       */
      char akt_char = pEingabe.charAt( akt_index );

      /*
       * Zeichenpruefung
       * Ist das aktuelle Zeichen keine Zahl, wird die Funktion mit 0 verlassen.
       */
      if ( (akt_char < '0') || (akt_char > '9') )
      {
        return 0;
      }

      /*
       * Berechnung Akt-Zahl
       * Der Wert in der Variablen "akt_zahl" wird mit 10 multipliziert und 
       * der Wert des aktuellen Zeichens hinzugezaehlt. 
       * 
       * Der Wert des aktuellen Zeichens ist der Wert des ASCII-Code abzueglich 48. 
       */
      akt_zahl = (akt_zahl * 10) + (((int) akt_char) - 48);

      /*
       * Leseprozess ein Zeichen weiter stellen 
       */
      akt_index ++;
    }

    return akt_zahl;
  }

  /**
   * Gibt das aktuelle Datum im Long-Format zurueck.
   *
   * @return das Datum im Format JJJJMMTT
   */
  public static long getLongHeute()
  {
    Calendar datum_ergebnis = Calendar.getInstance();

    return (datum_ergebnis.get( Calendar.YEAR ) * 10000) + ((datum_ergebnis.get( Calendar.MONTH ) + 1) * 100) + datum_ergebnis.get( Calendar.DAY_OF_MONTH );
  }

  /**
   * Gibt das Datum von gestern im Long-Format zurueck.
   *
   * @return das gestrige Datum im Format JJJJMMTT
   */
  public static long getLongGestern()
  {
    Calendar datum_ergebnis = Calendar.getInstance();

    datum_ergebnis.add( Calendar.DAY_OF_MONTH, -1 );

    return (datum_ergebnis.get( Calendar.YEAR ) * 10000) + ((datum_ergebnis.get( Calendar.MONTH ) + 1) * 100) + datum_ergebnis.get( Calendar.DAY_OF_MONTH );
  }

  /**
   * Gibt das Datum von Vorgestern im Long-Format zurueck.
   *
   * @return das gestrige Datum im Format JJJJMMTT
   */
  public static long getLongVorGestern()
  {
    Calendar datum_ergebnis = Calendar.getInstance();

    datum_ergebnis.add( Calendar.DAY_OF_MONTH, -2 );

    return (datum_ergebnis.get( Calendar.YEAR ) * 10000) + ((datum_ergebnis.get( Calendar.MONTH ) + 1) * 100) + datum_ergebnis.get( Calendar.DAY_OF_MONTH );
  }

  /**
   * @return das Datum des letzten Monatsultimo im Format JJJJMMTT
   */
  public static long getLongLetzterMonatsultimo()
  {
    // FkDatumLong.getLongLetzterMonatsultimo()

    /*
     * Aktuelles Datum als Instanz von java.util.Calendar erstellen
     * und in der Instanz die Zeitbestandteile ausnullen.
     */
    Calendar datum_ergebnis = Calendar.getInstance();

    /*
     * Bevor ein Monat abgezogen wird, wird vorsorglich der 
     * Tag auf den ersten des Monats gestellt. (Fehlervermeidung)
     */
    datum_ergebnis.set( Calendar.DAY_OF_MONTH, 1 );

    datum_ergebnis.set( Calendar.HOUR_OF_DAY, 0 );
    datum_ergebnis.set( Calendar.MINUTE, 0 );
    datum_ergebnis.set( Calendar.SECOND, 0 );
    datum_ergebnis.set( Calendar.MILLISECOND, 0 );

    /*
     * Vom Datum wird ein Monat abgezogen
     */
    datum_ergebnis.add( Calendar.MONTH, -1 );

    /*
     * Der Tag wird auf das Maximum des Monats gesetzt
     */
    datum_ergebnis.set( Calendar.DAY_OF_MONTH, datum_ergebnis.getActualMaximum( Calendar.DAY_OF_MONTH ) );

    return (datum_ergebnis.get( Calendar.YEAR ) * 10000) + ((datum_ergebnis.get( Calendar.MONTH ) + 1) * 100) + datum_ergebnis.get( Calendar.DAY_OF_MONTH );
  }

  /**
   * Gibt das Datum von Morgen im Long-Format zurueck.
   *
   * @return das gestrige Datum im Format JJJJMMTT
   */
  public static long getLongMorgen()
  {
    Calendar datum_ergebnis = Calendar.getInstance();

    datum_ergebnis.add( Calendar.DAY_OF_MONTH, 1 );

    return (datum_ergebnis.get( Calendar.YEAR ) * 10000) + ((datum_ergebnis.get( Calendar.MONTH ) + 1) * 100) + datum_ergebnis.get( Calendar.DAY_OF_MONTH );
  }

  /**
   * <pre>
   *
   * FkDatumLong.getLongMonatTag( 20101224, -1 ) = 1201 = Monatserster
   * FkDatumLong.getLongMonatTag( 20101224,  0 ) = 1224 = keine Veraenderung im Tag
   * FkDatumLong.getLongMonatTag( 20101224,  1 ) = 1231 = Monatsletzter
   *
   * FkDatumLong.getLongMonatTag( -20101224, 0 ) = 0
   * FkDatumLong.getLongMonatTag( 120101224, 0 ) = 0
   * FkDatumLong.getLongMonatTag( 0,         0 ) = 0
   * </pre>
   *
   * @param pDatum das Datum im Format JJJJMMTT
   * @param pKnzTag -1 = Monatserster, 1 = Monatsletzter, 0 keine Veraenderung
   * im Tag
   * @return vom uebergebenen Datum den Monat und den Tag im Format MMTT
   */
  public static long getLongMonatTag( long pDatum, int pKnzTag )
  {
    int datum_jahr = (int) (pDatum * 0.0001);
    int datum_monat = (int) ((pDatum - (datum_jahr * 10000)) * 0.01);
    int datum_tag = (int) (pDatum - (datum_jahr * 10000 + datum_monat * 100));

    /*
     * Pruefung: Datumswerte 
     * Der Tag muss groesser als 0 sein, der Maximalwert test kommt spaeter.
     * Der Monat muss zwischen 1 und 12 liegen
     * Das Jahr muss zwischen 1583 und 9999 liegen 
     */
    if ( (datum_tag > 0) && ((datum_monat >= 1) && (datum_monat <= 12)) && ((datum_jahr >= 1583) && (datum_jahr <= 9999)) )
    {
      if ( pKnzTag > 0 )
      {
        /*
         * pKnzTag > 0
         * Ist der Parameter "pKnzTag" groesser 0, wird der Tag auf den Monatsletzten gestellt. 
         */

        datum_tag = 31;

        switch ( datum_monat )
        {
          case 2:
          {
            if ( ((datum_jahr % 400) == 0) || ((datum_jahr % 100) > 0) && ((datum_jahr % 4) == 0) )
            {
              datum_tag = 29;
            }
            else
            {
              datum_tag = 28;
            }
            break;
          }

          case 4:
          case 6:
          case 9:
          case 11:
            datum_tag = 30;
            break;
        }
      }
      else if ( pKnzTag < 0 )
      {
        /*
         * pKnzTag < 0
         * Ist der Parameter "pKnzTag" kleiner 0, wird der Tag auf den Monatsersten gestellt. 
         */

        datum_tag = 1;
      }

      return datum_monat * 100 + datum_tag;
    }

    return 0;
  }

  /**
   * <pre>
   * Gibt fuer ein KORREKTES Eingabedatum im Long-Format das Monatsende-Datum im Long-Format zurueck.
   *
   * FkDatumLong.getMonatsEnde( 20101215 ) = 20101231
   * FkDatumLong.getMonatsEnde( 15012010 ) = 0 = Der Tag stimmt nicht
   * FkDatumLong.getMonatsEnde( 0        ) = 0 = Eingabe ist 0
   * FkDatumLong.getMonatsEnde( 99999999 ) = 0 = Kein korrektes Eingabedum
   * FkDatumLong.getMonatsEnde( 12010    ) = 0 = Kein korrektes Eingabedum
   *
   * FkDatumLong.getMonatsEnde( -20101230 ) = 0 = Eingabe ist negativ
   * FkDatumLong.getMonatsEnde( -1        ) = 0 = Eingabe ist negativ
   * FkDatumLong.getMonatsEnde( -123456   ) = 0 = Eingabe ist negativ
   * </pre>
   *
   * @return das gestrige Datum im Format JJJJMMTT
   */
  public static long getMonatsEnde( long pDatum )
  {
    int datum_jahr = (int) (pDatum * 0.0001);
    int datum_monat = (int) ((pDatum - (datum_jahr * 10000)) * 0.01);
    int datum_tag = (int) (pDatum - (datum_jahr * 10000 + datum_monat * 100));

    /*
     * Pruefung: Datumswerte 
     * Der Tag muss groesser als 0 sein, der Maximalwert test kommt spaeter.
     * Der Monat muss zwischen 1 und 12 liegen
     * Das Jahr muss zwischen 1583 und 9999 liegen 
     */
    if ( (datum_tag > 0) && ((datum_monat >= 1) && (datum_monat <= 12)) && ((datum_jahr >= 1583) && (datum_jahr <= 9999)) )
    {
      /*
       * Ermittlung Maximalwert Tag
       * Fuer die Maximalwertpruefung des Tages wird die Anzahl der Tage von 
       * Monat und Jahr bestimmt. Dabei wird das Schaltjahr beruecksichtigt.
       */
      int anzahl_tage = 31;

      if ( datum_monat == 2 )
      {
        if ( ((datum_jahr % 400) == 0) || ((datum_jahr % 100) > 0) && ((datum_jahr % 4) == 0) )
        {
          anzahl_tage = 29;
        }
        else
        {
          anzahl_tage = 28;
        }
      }
      else if ( datum_monat == 4 || datum_monat == 6 || datum_monat == 9 || datum_monat == 11 )
      {
        anzahl_tage = 30;
      }

      /*
       * Pruefung: Maximalgrenze Tag
       * Es gibt nur dann ein Ergebnis, wenn der uebergebene Tag kleiner 
       * oder gleich der Maximalgrenze ist.
       */
      if ( datum_tag <= anzahl_tage )
      {
        /*
         * Der Aufrufer bekommt das Datum im Format JJJJMMTT zurueck.
         */
        return datum_jahr * 10000 + datum_monat * 100 + anzahl_tage;
      }
    }

    return 0;
  }

  /**
   * <pre>
   * Konvertiert das Parameterdatum zu einem Datum im Format "JJJJ-MM-TT"
   *
   * Ist in pLongDatum kein korrektes Datum vorhanden, wird null zurueckgegeben.
   *
   * FkDatumLong.getIsoDatum( 20100115 ) = "2010-01-15"
   *
   * FkDatumLong.getIsoDatum( 20100229 ) = null
   * FkDatumLong.getIsoDatum( 20110229 ) = null
   * FkDatumLong.getIsoDatum( 20120229 ) = "2012-02-29"
   * FkDatumLong.getIsoDatum( 20130229 ) = null
   * FkDatumLong.getIsoDatum( 20140229 ) = null
   * FkDatumLong.getIsoDatum( 20150229 ) = null
   * FkDatumLong.getIsoDatum( 20160229 ) = "2016-02-29"
   *
   * FkDatumLong.getIsoDatum( 99999999 ) = null
   * FkDatumLong.getIsoDatum(      234 ) = null
   * FkDatumLong.getIsoDatum(       -1 ) = null
   *
   * </pre>
   *
   * @param pLongDatum die Eingabe im Format JJJJMMTT
   * @return null oder ein Datum im Format JJJJ-MM-TT
   */
  public static String getIsoDatum( long pLongDatum )
  {
    String vorgabe_rueckgabe = null;

    if ( pLongDatum < 0 )
    {
      return vorgabe_rueckgabe;
    }

    if ( pLongDatum > 99991231 )
    {
      return vorgabe_rueckgabe;
    }

    int datum_jahr = (int) (pLongDatum * 0.0001);

    if ( datum_jahr == 0 )
    {
      return vorgabe_rueckgabe;
    }

    int datum_monat = (int) ((pLongDatum - (datum_jahr * 10000)) * 0.01);

    if ( datum_monat == 0 )
    {
      return vorgabe_rueckgabe;
    }

    if ( datum_monat > 12 )
    {
      return vorgabe_rueckgabe;
    }

    int datum_tag = (int) (pLongDatum - (datum_jahr * 10000 + datum_monat * 100));

    if ( datum_tag == 0 )
    {
      return vorgabe_rueckgabe;
    }

    int anzahl_tage = 31;

    switch ( datum_monat )
    {
      case 2:
      {
        if ( ((datum_jahr % 400) == 0) || ((datum_jahr % 100) > 0) && ((datum_jahr % 4) == 0) )
        {
          anzahl_tage = 29;
        }
        else
        {
          anzahl_tage = 28;
        }

        break;
      }

      case 4:
      case 6:
      case 9:
      case 11:
        anzahl_tage = 30;
        break;
    }

    if ( datum_tag > anzahl_tage )
    {
      return vorgabe_rueckgabe;
    }

    char x_trennzeichen = '-';

    return (datum_jahr < 10 ? "000" : (datum_jahr < 100 ? "00" : (datum_jahr < 1000 ? "0" : ""))) + datum_jahr + x_trennzeichen + (datum_monat < 10 ? "0" : "") + datum_monat + x_trennzeichen + (datum_tag < 10 ? "0" : "") + datum_tag;
  }

  /**
   * <pre>
   * Berechnet die Tagesdifferenz zwischen den beiden Datumsangaben.
   *
   * FkDatumLong.getTagesDifferenz( 20101010, 20101010 ) = 0
   * FkDatumLong.getTagesDifferenz( 20101010, 20101011 ) = 1
   * FkDatumLong.getTagesDifferenz( 20101010, 20101012 ) = 2
   * FkDatumLong.getTagesDifferenz( 20101010, 20101013 ) = 3
   *
   * FkDatumLong.getTagesDifferenz( 20101010, 20111015 ) = 370
   * FkDatumLong.getTagesDifferenz( 20111015, 20101010 ) = 370
   * </pre>
   *
   * @param pDatumA das erste Datum im Format JJJJMMTT
   * @param pDatumB das zweite Datum im Format JJJJMMTT
   * @return die Differenz als positiver Wert in Tagen zwischen den beiden
   * Datumsangaben
   */
  public static long getTagesDifferenz( long pDatumAL, long pDatumBL )
  {
    Date pDatumA = FkDatumLong.parseToDate( "" + pDatumAL );
    Date pDatumB = FkDatumLong.parseToDate( "" + pDatumBL );

    if ( (pDatumA != null) && (pDatumB != null) )
    {
      int stunden_datum_1 = (int) (pDatumA.getTime() / 3600000);
      int stunden_datum_2 = (int) (pDatumB.getTime() / 3600000);

      if ( stunden_datum_1 > stunden_datum_2 )
      {
        return (long) ((stunden_datum_1 - stunden_datum_2) * 0.0416666666666667);

      }
      else
      {
        return (long) ((stunden_datum_2 - stunden_datum_1) * 0.0416666666666667);
      }
    }

    return 0;
  }

  public static boolean istSchaltjahr( long pDatum )
  {
    boolean fkt_ergebnis = false;

    /*
     * Aus dem Parameterwert werden die Angaben fuer das Jahr und den Monat rausgerechnet.
     */
    int datum_jahr = (int) (pDatum * 0.0001);

    int datum_monat = (int) ((pDatum - (datum_jahr * 10000)) * 0.01);

    int datum_tag = (int) (pDatum - (datum_jahr * 10000 + datum_monat * 100));

    /*
     * Pruefung: Datumswerte 
     * Der Tag muss groesser als 0 sein, der Maximalwert test kommt spaeter.
     * Der Monat muss zwischen 1 und 12 liegen
     * Das Jahr muss zwischen 1583 und 9999 liegen 
     */
    if ( (datum_tag > 0) && ((datum_monat >= 1) && (datum_monat <= 12)) && ((datum_jahr >= 1583) && (datum_jahr <= 9999)) )
    {
      if ( datum_monat == 2 )
      {
        if ( ((datum_jahr % 400) == 0) || ((datum_jahr % 100) > 0) && ((datum_jahr % 4) == 0) )
        {
          fkt_ergebnis = true;
        }
      }
    }

    return fkt_ergebnis;
  }

}
