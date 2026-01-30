package de.ea234.epost.util.druck;

class FkStringFeld
{
  /**
   * <pre>
   * Ersetzt im Parameter "pString" den Suchstring gegen den Ersatzstring. 
   * 
   * FkString.replaceSubstring( "ABC.DEF.GHI.JKL", ".", " # " ); = "ABC # DEF # GHI # JKL"
   * FkString.replaceSubstring( "ABC.DEF.GHI.JKL", "-", " # " ); = "ABC.DEF.GHI.JKL"
   * FkString.replaceSubstring( "ABC.DEF.GHI.JKL", ".", ""    ); = "ABCDEFGHIJKL"
   * FkString.replaceSubstring( "ABC.DEF.GHI.JKL", ".", null  ); = "ABC.DEF.GHI.JKL"
   * FkString.replaceSubstring( null,              ".", null  ); = null
   * </pre>
   * 
   * @param pString der String in welchem ersetzt werden soll
   * @param pSuchString der zu suchende Text
   * @param pErsatzString der Ersatztext
   * @return pString mit den gemachten Ersetzungen
   */
  static String replaceSubstring( String pString, String pSuchString, String pErsatzString )
  {
    /*
     * Parameterpruefung:
     * pString darf nicht null sein und muss eine Laenge von groesser 0 haben
     * pSuchString darf nicht null sein und muss eine Laenge von groesser 0 haben 
     * pErsatzString darf nicht null sein (darf aber eine Laenge von 0 haben)
     */
    if ( pString != null && pSuchString != null && pErsatzString != null && pString.length() > 0 && pSuchString.length() > 0 )
    {
      int laenge_such_string = pSuchString.length();
      int laenge_ersatz_string = pErsatzString.length();
      int aktuelle_start_position = 0;
      int aktuelle_position_gefunden = pString.indexOf( pSuchString, aktuelle_start_position );

      /*
       * While-Schleife
       * Die Schleife laeuft solange, wie noch der Suchstring in der Eingabe gefunden wird. 
       * Kann der Suchstring ab der aktuellen Position nicht mehr gefunden werden, ist der 
       * Wert in der Variablen "aktuelle_position_gefunden" gleich -1.
       */
      while ( aktuelle_position_gefunden >= 0 )
      {
        /*
         * Ersetzung machen
         * Ab Stringanfang bis zur Fundstelle, danach der Ersatztext und der 
         * Reststring ab Fundstelle zuzueglich der Laenge des Suchtextes.  
         */
        pString = pString.substring( 0, Math.max( 0, aktuelle_position_gefunden ) ) + pErsatzString + pString.substring( aktuelle_position_gefunden + laenge_such_string );

        /*
         * Neue Startposition
         */
        aktuelle_start_position = aktuelle_position_gefunden + laenge_ersatz_string;

        /*
         * Neue Fundstelle des Suchtextes ermitteln.
         */
        aktuelle_position_gefunden = pString.indexOf( pSuchString, aktuelle_start_position );
      }
    }

    /*
     * Rueckgabe
     * Am Funktionsende bekommt der Aufrufer "pString" zurueck.
     */
    return pString;
  }

  /**
   * Konvertiert die in der Eingabe enthaltenen deutschen Umlaute in OE usw.
   * @param pEingabe die Eingabe
   * @return der konvertierte String 
   */
  static String replaceSpecialCharacters( String pEingabe )
  {
    if ( pEingabe == null )
    {
      return null;
    }

    StringBuffer str_ergebnis = new StringBuffer();

    int akt_index = 0;

    while ( akt_index < pEingabe.length() )
    {
      switch ( pEingabe.charAt( akt_index ) )
      {
        case '@' :
          str_ergebnis.append( "At" );
          break;

        case 0x00f6 :
          str_ergebnis.append( "oe" );
          break;

        case 0x00e4 :
          str_ergebnis.append( "ae" );
          break;

        case 0x00fc :
          str_ergebnis.append( "ue" );
          break;

        case 0x00d6 :
          str_ergebnis.append( "Oe" );
          break;

        case 0x00c4 :
          str_ergebnis.append( "Ae" );
          break;

        case 0x00dc :
          str_ergebnis.append( "Ue" );
          break;

        case 0x00df :
          str_ergebnis.append( "ss" );
          break;

        default :
          str_ergebnis.append( pEingabe.charAt( akt_index ) );
      }

      akt_index++;
    }
    return str_ergebnis.toString();
  }

  /** Konstante fuer ein Leerzeichen */
  private static final String LEERZEICHEN = " ";

  /** Konstante fuer einen Leerstring */
  private static final String LEERSTRING  = "";

  static String getFeldRechtsMin( String pFeldWert, int pMindestLaenge )
  {
    String feld_wert = "" + pFeldWert;

    if ( feld_wert.length() >= pMindestLaenge )
    {
      return feld_wert;
    }

    return LEERZEICHEN.repeat( pMindestLaenge - feld_wert.length() ) + feld_wert;
  }

  /**
   * @param nr der Inhalt des Feldes
   * @param pMindestLaenge die vorgegebene Mindestlaenge des Rueckgabestrings
   * @return einen String, welcher mindestens pMindesLaenge breit ist (evtl. aufgefuellt mit Leerzeichen)
   */
  static String getFeldLinksMin( String nr, int pMindestLaenge )
  {
    if ( nr == null )
    {
      nr = "";
    }

    if ( nr.length() >= pMindestLaenge )
    {
      return nr;
    }

    return nr + LEERZEICHEN.repeat( pMindestLaenge - nr.length() );
  }

}
