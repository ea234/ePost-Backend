package de.ea234.epost.util.druck;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

class ITextStyleCache
{
  /**
   * <pre>
   * Fuehrt einen Class-Cast auf die Klasse "ITextStyleCache" auf das uebergebene Objekt aus. 
   *
   * Im Falle einer ClassCastException wird "null" zurueckgegeben.
   * Ist das Objekt selber "null" wird "null" zurueckgegeben.
   *
   * ITextStyleCache instanz = ITextStyleCache.getClassCast( java_object );
   *
   * if ( instanz == null )
   * {
   *   str_fehler = "Die Instanz \"java_object\" ist keine Instanz der Klasse \"ITextStyleCache\" ";
   *   return;
   * }
   *
   * </pre>
   *
   * @param  pObjekt        das zu castende Objekt
   * @return das Objekt als Instanz von "ITextStyleCache", oder "null" im Fehlerfall
   */
  public static ITextStyleCache getClassCast( Object pObjekt )
  {
    /*
     * Pruefung: pObjekt ungleich null ?
     */
    if ( pObjekt != null )
    {
      try
      {
        return (ITextStyleCache) pObjekt;
      }
      catch ( ClassCastException abgf_fehler )
      {
        // keine Aktion im Fehlerfall, da Rueckgabe von null
      }
    }

    return null;
  }

  /**
   * Die Map, welche die Objekte zu Schluesselnamen abspeichert
   */
  private HashMap< String, Object > m_hash_map            = null;

  private long                      m_time_stamp_erstellt = System.currentTimeMillis();

  public long getTimeStampErstellt()
  {
    return m_time_stamp_erstellt;
  }

  private HashMap< String, Object > getHashMap()
  {
    if ( m_hash_map == null )
    {
      m_hash_map = new HashMap< String, Object >();
    }

    return m_hash_map;
  }

  /**
   * <pre>
   * Speichert das Objekt in der Hashmap unter dem uebergebenen Namen ab.
   * </pre>
   * 
   * @param pName der Name unter dem das Objekt wiedergefunden werden soll
   * @param pObjekt das zu speichernde Objekt
   */
  public void add( String pName, Object pObjekt )
  {
    getHashMap().put( pName, pObjekt );
  }

  /**
   * <pre>
   * Liefert das unter dem uebergebenen Namen gespeicherte Objekt zurueck. 
   * Ist unter diesem Namen kein Objekt gespeichert, wird <code>null</code> zurueckgeliefert.
   * </pre>
   * 
   * @param pName der Name unter dem das Objekt gespeichert wurde
   * @return ein Objekt oder null im Fehlerfall
   */
  public Object get( String pName )
  {
    return getHashMap().get( pName );
  }

  /**
   * <pre>
   * Entfernt das unter dem uebergebenen Namen gespeicherte Objekt aus der Hashmap.
   * Ein eventuell gespeichertes Objekt wird zurueckgegeben. 
   * Ist kein Objekt gespeichert, wird <code>null</code> zurueckgeliefert.
   * </pre>
   * 
   * @param pName der Name unter dem das Objekt gespeichert wurde
   * @return ein Objekt oder null im Fehlerfall
   */
  public Object remove( String pName )
  {
    return getHashMap().remove( pName );
  }

  /**
   * <pre>
   * Liefert die Information zurueck, ob ein Objekt unter dem angegebenen Namen gespeichert ist.
   * </pre>
   * 
   * @param pName der Name des Objektes
   * @return TRUE wenn ein Objekt vorhanden ist, ansonsten FALSE
   */
  public boolean has( String pName )
  {
    return getHashMap().containsKey( pName );
  }

  /**
   * <pre>
   * Liefert die Information zurueck, ob KEIN Objekt unter dem angegebenen Namen gespeichert ist.
   * </pre>
   * 
   * @param pName der Name des Objektes
   * @return TRUE wenn KEIN Objekt unter dem Namen gespeichert ist, sonst FALSE (=Name ist vorhanden)
   */
  public boolean hasNot( String pName )
  {
    return !getHashMap().containsKey( pName );
  }

  /**
   * Loescht alle Mappings in der Hashmap und setzt die Hash-Map-Instanz auf null. 
   */
  public void clear()
  {
    if ( m_hash_map != null )
    {
      Iterator iterator_keys = m_hash_map.keySet().iterator();

      while ( iterator_keys.hasNext() )
      {
        try
        {
          ITextStyle.getClassCast( m_hash_map.get( (String) iterator_keys.next() ) ).clear();
        }
        catch ( Exception err_inst )
        {
          // Keine Debugausgabe in der  CLEAR-Funktion
        }
      }

      m_hash_map.clear();

      m_hash_map = null;
    }
  }

  public String toString()
  {
    String ergebnis_str = "";

    try
    {
      int zaehler = 0;

      Iterator iterator_keys = getHashMap().keySet().iterator();

      while ( iterator_keys.hasNext() )
      {
        ergebnis_str = ergebnis_str + "[" + zaehler + "] " + iterator_keys.next() + "\n";
        zaehler++;
      }
    }
    catch ( Exception err_inst )
    {
      ergebnis_str = ergebnis_str + "[Fehler: ITextStyleCache] " + err_inst.getMessage();
    }

    return ergebnis_str;
  }

  public static synchronized String getID()
  {
    String ergebnis_id = "";

    Calendar datum = Calendar.getInstance();

    int stunden = 0;
    int minuten = 0;
    int sekunden = 0;

    stunden = datum.get( Calendar.HOUR_OF_DAY );
    minuten = datum.get( Calendar.MINUTE );
    sekunden = datum.get( Calendar.SECOND );

    ergebnis_id += "" + ( ( datum.get( Calendar.YEAR ) * 10000 ) + ( ( datum.get( Calendar.MONTH ) + 1 ) * 100 ) + datum.get( Calendar.DATE ) );
    ergebnis_id += ( stunden < 10 ? "0" : "" ) + stunden + ( minuten < 10 ? "0" : "" ) + minuten + ( getLaufendeZahl() + getRandomString( 10, null, null ) ).substring( 0, 5 ) + ( sekunden < 10 ? "0" : "" ) + sekunden;

    while ( sekunden == Calendar.getInstance().get( Calendar.SECOND ) )
    {
      // eine Sekunde warten
    }

    return ergebnis_id;
  }

  private static int LAUFENDE_ZAHL = 1;

  /**
   * Erhoeht die laufende Zahl und gibt diese zurueck.
   * @return eine fortlaufende Zahl
   */
  private static int getLaufendeZahl()
  {
    /*
     * Die laufende Zahl wird um 1 erhoeht
     */
    LAUFENDE_ZAHL++;

    /*
     * Pruefung: laufende Zahl gleich Integer.MAX_VALUE ?
     * 
     * Erreicht die laufende Zahl den Wert Integer.MAX_VALUE, wird die 
     * laufende Zahl auf 0 gesetzt.
     */
    if ( LAUFENDE_ZAHL == Integer.MAX_VALUE )
    {
      LAUFENDE_ZAHL = 0;
    }

    /*
     * Am ende wird die Zahl dem Aufrufer zurueckgegeben.
     */
    return LAUFENDE_ZAHL;
  }

  /** Instanz fuer den Zufallszahlengenerator */
  private static Random random_instanz = new Random();

  /**
   * Rueckgabe einer zufaelligen Zeichenfolge in der angegebenen Laenge
   * 
   * @param pLaenge die Laenge des Rueckgabestrings
   * @param pGrundmenge die Grundmenge 
   * @return einen String in der angegebenen Laenge mit zufaelligen Zeichen
   */
  private static String getRandomString( int pLaenge, String pStrBestehend, String pGrundmenge )
  {
    if ( pLaenge <= 0 )
    {
      return "";
    }

    if ( pGrundmenge == null )
    {
      pGrundmenge = "EHOYABNAFRTJEPDBWIAAEEJIAOUEODIVLAROGUUOYUELUOEUOMIAXNOTFESIMWEAIUEUAOKAUZPOVHUQIKUQEUOAAZAIUIIGEXCCOIISZ";
    }

    if ( pStrBestehend != null )
    {
      if ( pStrBestehend.length() >= pLaenge )
      {
        return pStrBestehend.substring( 0, pLaenge );
      }
    }

    int random_zahl = pGrundmenge.length();

    /*
     * Stringbuffer fuer das Ergebnis erstellen
     */
    StringBuffer str_ergebnis = new StringBuffer();

    int zaehler_ergebnis = 1;

    if ( pStrBestehend != null )
    {
      zaehler_ergebnis = pStrBestehend.length();

      str_ergebnis.append( pStrBestehend );
    }

    while ( zaehler_ergebnis <= pLaenge )
    {
      /*
       * Je Durchlauf einen zufaelligen Index erstellen,
       * damit aus der Grundmenge, das dort stehende 
       * Zeichen dem Ergebnis hinzufuegen. 
       */
      str_ergebnis.append( pGrundmenge.charAt( random_instanz.nextInt( random_zahl ) ) );

      /*
       * Zaehler erhoehen.
       */
      zaehler_ergebnis++;
    }

    return str_ergebnis.toString();
  }
}
