package de.ea234.epost.util.druck;

public class ElementCsvZeile
{
  /**
   * <pre>
   * Fuehrt einen Class-Cast auf die Klasse "ElementCsvZeile" auf das uebergebene Objekt aus. 
   *
   * Im Falle einer ClassCastException wird "null" zurueckgegeben.
   * Ist das Objekt selber "null" wird "null" zurueckgegeben.
   *
   * ElementCsvZeile instanz = ElementCsvZeile.getClassCast( java_object );
   *
   * if ( instanz == null )
   * {
   *   str_fehler = "Die Instanz \"java_object\" ist keine Instanz der Klasse \"ElementCsvZeile\" ";
   *   return;
   * }
   *
   * </pre>
   *
   * @param  pObjekt        das zu castende Objekt
   * @return das Objekt als Instanz von "ElementCsvZeile", oder "null" im Fehlerfall
   */
  public static ElementCsvZeile getClassCast( Object pObjekt )
  {
    /*
     * Pruefung: pObjekt ungleich null ?
     */
    if ( pObjekt != null )
    {
      try
      {
        return (ElementCsvZeile) pObjekt;
      }
      catch ( ClassCastException abgf_fehler )
      {
        // keine Aktion im Fehlerfall, da Rueckgabe von null
      }
    }

    return null;
  }

  private String m_id_element = "";

  private String m_id_text    = "";

  public String getIdText()
  {
    return m_id_text;
  }

  public void setIdText( String pIdText )
  {
    m_id_text = pIdText;
  }

  public String getIdElement()
  {
    return m_id_element;
  }

  public void setIdElement( String pIdElement )
  {
    m_id_element = pIdElement;
  }

  public void clear()
  {
    m_id_element = null;
    m_csv_string_werte = null;
    m_id_text = null;
    m_csv_ausrichtung = null;
  }

  private String m_csv_string_werte = "";

  private String m_csv_ausrichtung  = null;

  public String getCsvAusrichtung()
  {
    return m_csv_ausrichtung;
  }

  public void setCsvAusrichtung( String pCsvAusrichtung )
  {
    m_csv_ausrichtung = pCsvAusrichtung;
  }

  public String getCsvStringWerte()
  {
    return m_csv_string_werte;
  }

  public void setCsvStringWerte( String pCsvStringWerte )
  {
    m_csv_string_werte = pCsvStringWerte;
  }

  public int getAnzahlCsvWerte()
  {
    return getCsvAnzahlWerte( getCsvStringWerte(), "|" );
  }

  public String getCsvText( int pIndex )
  {
    return getCsvFeldNr( getCsvStringWerte(), "|", pIndex );
  }

  public String getCsvAusrichtung( int pIndex )
  {
    return getString( getCsvFeldNr( getCsvAusrichtung(), "|", pIndex ), "<" );
  }

  public String toString()
  {
    return getCsvStringWerte();
  }

  public String toStringZeile()
  {
    StringBuffer ergebnis_string_buffer = new StringBuffer();

    ergebnis_string_buffer.append( "csv_zeile|" );
    ergebnis_string_buffer.append( m_id_element + "|" );
    ergebnis_string_buffer.append( m_id_text + "|" );
    ergebnis_string_buffer.append( m_csv_string_werte );

    return ergebnis_string_buffer.toString();
  }

  private static int getCsvAnzahlWerte( String pCsvListe, String pTrennzeichen )
  {
    int str_zaehler = 0;

    /*
     * Pruefung der Eingabeparameter
     * pCsvListe      ==> muss gesetzt sein (laenge > 0 / ohne trim)
     * pTrennzeichen  ==> muss gesetzt sein (laenge > 0 / ohne trim)
     */
    if ( ( ( pCsvListe != null ) && ( pCsvListe.length() > 0 ) ) && ( pTrennzeichen != null ) )
    {
      /*
       * Erstes Auftreten des Trennzeichens ermitteln.
       */
      int pos_ende = pCsvListe.indexOf( pTrennzeichen );
      int pos_start = 0;
      int feld_zaehler = 0;

      /*
       * Suchschleife fuer das Feld
       * Die Suchschleife wird solange durchlaufen, wie 
       * ... das Trennzeichen noch gefunden wird
       * ... der Feldzaehler noch unter 32123 ist
       */
      while ( ( pos_ende >= 0 ) && ( feld_zaehler < 32123 ) )
      {
        /*
         * Naechstes Trennzeichen suchen 
         * Von der berechneten neuen Start-Position wird das Trennzeichen gesucht.
         * Die Position wird in der Variablen "pos_ende" gespeichert. 
         */
        pos_ende = pCsvListe.indexOf( pTrennzeichen, pos_start );

        if ( pos_ende >= 0 )
        {
          str_zaehler++;
        }

        /* 
         * Berechnung der neuen Startposition
         * Die Variable "pos_start" zeigt auf das erste Zeichen der Rueckgabe. 
         * Diese Position ist die aktuell gefundene Position des Trennzeichens zuzuglich dessen Laenge.
         */
        pos_start = pos_ende + pTrennzeichen.length();

        /*
         * Feldzaehler um 1 erhoehen und naechsten Schleifendurchlauf machen.
         */
        feld_zaehler++;
      }

      str_zaehler++;
    }

    return str_zaehler;
  }

  /**
   * <pre>
   * Gibt aus einem Trennzeichen separierten String das angegebene Feld zurueck.
   * Ist die Eingabe oder die Feldnummer nicht vorhanden oder die Feldnummer kleiner gleich 0, wird null zurueckgegeben.
   * Ist das Trennzeichen nicht in der Eingabe vorhanden, wird null zurueckgegeben.
   * Es wird kein TRIM auf das CSV-Feld ausgefuehrt.
   *   
   * getCsvFeldNr( "1,2,3,4,5,6,7,8,9", ",", 1 ) = "1"
   * getCsvFeldNr( "1,2,3,4,5,6,7,8,9", ",", 2 ) = "2"
   * getCsvFeldNr( "1,2,3,4,5,6,7,8,9", ",", 3 ) = "3"
   * getCsvFeldNr( "1,2,3,4,5,6,7,8,9", ",", 7 ) = "7"
   * getCsvFeldNr( "1,2,3,4,5,6,7,8,9", ",", 8 ) = "8"
   * getCsvFeldNr( "1,2,3,4,5,6,7,8,9", ",", 9 ) = "9"
   *   
   * getCsvFeldNr( "1 2 3 4 5 6 7 8 9",  " ",  6 ) = "6" = Trennzeichen ist Leerzeichen 
   *   
   * getCsvFeldNr( "1,2,3,4,5,6,7,8,9",  ";",  1 ) = null = Trennzeichen nicht in Eingabe vorhanden
   * getCsvFeldNr( "1,2,3,4,5,6,7,8,9",  ",",  0 ) = null = Feldnummer nicht vorhanden
   * getCsvFeldNr( "1,2,3,4,5,6,7,8,9",  ",", 11 ) = null = Feldnummer nicht vorhanden
   * getCsvFeldNr( "1,2,3,4,5,6,7,8,9", null,  3 ) = null = Trennzeichen ist null
   * getCsvFeldNr( null,                 ",",  2 ) = null = Eingabe ist null
   * getCsvFeldNr( "                 ",  " ",  6 ) = ""   = ... geht auch
   *  
   * getCsvFeldNr( "A -#- B -#- C -#- D -#- E -#- F", " -#- ",  3 ) = "C" = Trennzeichen laenger als 1 Zeichen
   *
   * getCsvFeldNr( "1",  ",", 1 ) = "1"   = erstes Feld, kein Trennzeichen vorhanden aber Text, dann Eingabe = Ergebnis
   * getCsvFeldNr(  "",  ",", 1 ) = null
   * getCsvFeldNr( null, ",", 1 ) = null
   * 
   * 
   * Beispieldurchlauf (und Kopiervorlage):
   *
   *   String csv_eingabe_string = "1,2,3,4,5,6,7,8,9,0";
   *   
   *   csv_eingabe_string += ",A,B,C,,D,E,F,G,H,";
   *   
   *   int    csv_feld_index     = 1;
   *   String csv_feld_string    = getCsvFeldNr( csv_eingabe_string, ",", csv_feld_index );
   *   
   *   while ( ( csv_feld_string != null ) && ( csv_feld_index < 100 ) )
   *   {
   *     if ( ( csv_feld_string != null ) && ( csv_feld_string.trim().length() > 0 ) )
   *     {
   *       System.out.println( "csv_feld_index[" + csv_feld_index + "] = " + csv_feld_string );
   *     }
   *     else 
   *     {
   *       System.out.println( "csv_feld_index[" + csv_feld_index + "] = nicht gesetzt" );
   *     }
   *     
   *     csv_feld_index++;
   *     csv_feld_string = getCsvFeldNr( csv_eingabe_string, ",", csv_feld_index );
   *   }
   *   
   * </pre>
   * 
   * @param pCsvListe die durch ein Trennzeichen getrennten Werte 
   * @param pCsvTrennzeichen das Tennzeichen (oder Trennstring)
   * @param pFeldNr die Feldnummer fuer die Rueckgabe (max 3000)
   * @return den String der Feldnummer, oder null, wenn die Eingabe oder das Feld nicht vorhanden ist
   */
  private static String getCsvFeldNr( String pCsvListe, String pCsvTrennzeichen, int pFeldNr )
  {
    String str_ergebnis = null; // Speicherung fuer den Rueckgabewert, initialisiert mit null

    /*
     * Parameterpruefung
     * pCsvListe      = muss gesetzt sein (laenge > 0 / ohne trim)
     * pTrennzeichen  = muss gesetzt sein (laenge > 0 / ohne trim)
     * pFeldNr        = muss groesser als 0 sein
     */
    if ( ( ( pCsvListe != null ) && ( pCsvListe.length() > 0 ) ) && ( pCsvTrennzeichen != null ) && ( pCsvTrennzeichen.length() > 0 ) && ( pFeldNr > 0 ) )
    {
      /*
       * Erstes Auftreten des Trennzeichens ermitteln.
       */
      int pos_ende = pCsvListe.indexOf( pCsvTrennzeichen );

      if ( pFeldNr == 1 )
      {
        /*
         * Rueckgabe bei Feld 1
         * Es muss keine Schleife gestartet werden. Fuer eine Rueckgabe eines 
         * Teilstrings, muss das Trennzeichen in der Eingabe gefunden worden sein.
         * 
         * Ist die Variable "pos_ende" groesser als 0, wird dem Aufrufer der Teilstring
         * vom Stringanfang bis zum ersten gefundenen Trennzeichen.
         * 
         * FALSCH: Steht die Variable "pos_ende" auf -1, wurde das Trennzeichen nicht gefunden, 
         *         der Aufrufer bekommt eine null-Referenz zurueck.
         *         
         * Wird das Trennzeichen nicht gefunden, aber der String fuer die CSV-Werte ist gesetzt
         * und es handelt sich um das erste Feld, ist der gesamte CSV-String das Ergebnis.
         */
        if ( pos_ende >= 0 )
        {
          str_ergebnis = pCsvListe.substring( 0, pos_ende );
        }
        else
        {
          str_ergebnis = pCsvListe;
        }
      }
      else
      {
        /*
         * Rueckgabe ab Feld-Nr 2
         * Ab der Rueckgabe-Feld-Nr 2 muss das Feld innerhalb der Eingabe gesucht werden. 
         * Dieses erfolgt ueber eine Schleife. Zusaetzlich werden hier zwei weitere 
         * Variablen benoetigt.
         */
        int pos_start = 0; // Merker fuer Startposition wird nur bei Rueckgabeelement > 2 benoetigt
        int feld_zaehler = 1; // Feldzaehler startet bei dem ersten Element

        /*
         * Suchschleife fuer das Feld
         * Die Suchschleife wird solange durchlaufen, wie 
         * ... das Trennzeichen noch gefunden wird
         * ... der Feldzaehler noch kleiner 32123 ist
         * ... der Feldzaehler ungleich der Rueckgabe-Feldnummer ist
         */
        while ( ( pos_ende >= 0 ) && ( feld_zaehler < 32123 ) && ( feld_zaehler != pFeldNr ) )
        {
          /* 
           * Berechnung der neuen Startposition
           * Die Variable "pos_start" zeigt auf das erste Zeichen der Rueckgabe. 
           * Diese Position ist die aktuell gefundene Position des Trennzeichens zuzuglich dessen Laenge.
           */
          pos_start = pos_ende + pCsvTrennzeichen.length();

          /*
           * Naechstes Trennzeichen suchen 
           * Von der berechneten neuen Start-Position wird das Trennzeichen gesucht.
           * Die Position wird in der Variablen "pos_ende" gespeichert. 
           */
          pos_ende = pCsvListe.indexOf( pCsvTrennzeichen, pos_start );

          /* Feldzaehler
           * Feldzaehler um 1 erhoehen und naechsten Schleifendurchlauf machen.
           */
          feld_zaehler++;
        }

        /*
         * Pruefung: Feldnummer gefunden?
         * Nach der Schleife muss der Feldzaehler mit der Rueckgabefeldnummer uebereinstimmen.
         *  
         * Ist das nicht der Fall, ist die Feldnummer nicht in der Eingabe enthalten und der 
         * Aufrufer bekommt eine null-Referenz als Rueckgabe.
         * 
         * Stimmen die Nummern ueberein, wird die Rueckgabe aus der Eingabe herausgelesen.
         */
        if ( feld_zaehler == pFeldNr )
        {
          /* 
           * Wert in "pos_ende"
           * Wurde noch ein weiteres vorkommen des Trennzeichens gefunden, ist die 
           * Variable "pos_ende" groesser 0. Das Ergebnis wird vom Startindex bis zu dem 
           * naechsten Auftreten des Trennzeichens herausgelesen.
           *  
           * Ist kein weiteres Trennzeichen vorhanden, wird vom Startindex bis zum Stringende 
           * abgeschnitten.
           */
          if ( pos_ende > 0 )
          {
            str_ergebnis = pCsvListe.substring( pos_start, pos_ende );
          }
          else
          {
            str_ergebnis = pCsvListe.substring( pos_start );
          }
        }
      }
    }

    return str_ergebnis;
  }

  /**
   * <pre>
   * Ist der Parameter "pString" gleich null oder getrimmt ein Leerstring, wird der Vorgabestring zurueckgegeben.
   * 
   * FkString.getString( "A",  "B" ) = "A" = pString gesetzt
   *  
   * FkString.getString( "",   "B" ) = "B" = pString nicht gesetzt
   * FkString.getString( "  ", "B" ) = "B" = pString nicht gesetzt (trim)
   * FkString.getString( null, "B" ) = "B" = pString ist null
   * 
   * FkString.getString( null, null ) = null = Vorgabestring ist selber null 
   * </pre>
   * 
   * @param pString der zu pruefende String
   * @param pVorgabeWert der String, welcher zurueckgegeben wird, wenn der String aus pString ein Leerstring ist.
   * @return den Eingabestring, sofern nicht null und mindestens 1 Zeichen, sonst den Vorgabewert
   */
  private static String getString( String pString, String pVorgabeWert )
  {
    /*
     * Pruefung: "pString" ungleich "null" und getrimmt eine Laenge groesser als 0 ?
     */
    if ( ( pString != null ) && ( pString.trim().length() > 0 ) )
    {
      /*
       * Ist der Eingabestring gesetzt und hat eine Laenge groesser als 0 Zeichen, 
       * wird der Eingabestring zurueckgegeben. 
       */
      return pString;
    }

    /*
     * Ist der Eingabestring null oder ein Leerstring, wird 
     * der Vorgabestring zurueckgegeben.
     */
    return pVorgabeWert;
  }
}
