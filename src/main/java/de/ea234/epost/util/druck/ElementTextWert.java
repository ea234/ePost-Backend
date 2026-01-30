package de.ea234.epost.util.druck;

public class ElementTextWert
{
  /**
   * <pre>
   * Fuehrt einen Class-Cast auf die Klasse "ElementTextWert" auf das uebergebene Objekt aus. 
   *
   * Im Falle einer ClassCastException wird "null" zurueckgegeben.
   * Ist das Objekt selber "null" wird "null" zurueckgegeben.
   *
   * ElementTextWert instanz = ElementTextWert.getClassCast( java_object );
   *
   * if ( instanz == null )
   * {
   *   str_fehler = "Die Instanz \"java_object\" ist keine Instanz der Klasse \"ElementTextWert\" ";
   *   return;
   * }
   *
   * </pre>
   *
   * @param  pObjekt        das zu castende Objekt
   * @return das Objekt als Instanz von "ElementTextWert", oder "null" im Fehlerfall
   */
  public static ElementTextWert getClassCast( Object pObjekt )
  {
    /*
     * Pruefung: pObjekt ungleich null ?
     */
    if ( pObjekt != null )
    {
      try
      {
        return (ElementTextWert) pObjekt;
      }
      catch ( ClassCastException abgf_fehler )
      {
        // keine Aktion im Fehlerfall, da Rueckgabe von null
      }
    }

    return null;
  }

  private String m_art        = null;

  private String m_id_element = null;

  private String m_id_text    = null;

  private String m_text       = null;

  private String m_wert       = null;

  public void clear()
  {
    m_art = null;
    m_id_element = null;
    m_id_text = null;
    m_text = null;
    m_wert = null;
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_art.
   * </pre>
   * 
   * @param  pArt           der zu setzende Wert
   */
  public void setArt( String pArt )
  {
    m_art = pArt;
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_id_element.
   * </pre>
   * 
   * @param  pIdElement     der zu setzende Wert
   */
  public void setIdElement( String pIdElement )
  {
    m_id_element = pIdElement;
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_id_text.
   * </pre>
   * 
   * @param  pIdText        der zu setzende Wert
   */
  public void setIdText( String pIdText )
  {
    m_id_text = pIdText;
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_text.
   * </pre>
   * 
   * @param  pText          der zu setzende Wert
   */
  public void setText( String pText )
  {
    m_text = pText;
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_wert.
   * </pre>
   * 
   * @param  pWert          der zu setzende Wert
   */
  public void setWert( String pWert )
  {
    m_wert = pWert;
  }

  /**
   * <pre>
   * Liefert den Wert der Variablen m_art.
   * </pre>
   *  
   * @return den Wert der Variablen "m_art"
   */
  public String getArt()
  {
    return m_art;
  }

  /**
   * <pre>
   * Liefert den Wert der Variablen m_id_element.
   * </pre>
   *  
   * @return den Wert der Variablen "m_id_element"
   */
  public String getIdElement()
  {
    return m_id_element;
  }

  /**
   * <pre>
   * Liefert den Wert der Variablen m_id_text.
   * </pre>
   *  
   * @return den Wert der Variablen "m_id_text"
   */
  public String getIdText()
  {
    return m_id_text;
  }

  /**
   * <pre>
   * Liefert den Wert der Variablen m_text.
   * </pre>
   *  
   * @return den Wert der Variablen "m_text"
   */
  public String getText()
  {
    return m_text;
  }

  public boolean hatWert()
  {
    return ( m_wert != null ) && ( m_wert.length() > 0 );
  }

  /**
   * <pre>
   * Liefert den Wert der Variablen m_wert.
   * </pre>
   *  
   * @return den Wert der Variablen "m_wert"
   */
  public String getWert()
  {
    return m_wert;
  }

  /**
   * Erstellt die Stringrepraesentation dieser Beanklasse
   *  
   * @return einen String mit den Variablenwerten der Klasse "ElementTextWert"
   */
  public String toString()
  {
    StringBuffer ergebnis_string_buffer = new StringBuffer();

    ergebnis_string_buffer.append( "\nBean-Klasse ElementTextWert" );

    ergebnis_string_buffer.append( "\n  + m_art        >" + m_art + "<" );
    ergebnis_string_buffer.append( "\n  + m_id_element >" + m_id_element + "<" );
    ergebnis_string_buffer.append( "\n  + m_id_text    >" + m_id_text + "<" );
    ergebnis_string_buffer.append( "\n  + m_text       >" + m_text + "<" );
    ergebnis_string_buffer.append( "\n  + m_wert       >" + m_wert + "<" );

    return ergebnis_string_buffer.toString();
  }

  /**
   * Erstellt eine Stringrepraesentation in einer Zeile, wobei die Werte durch ein Trennzeichen getrennt werden
   *  
   * @return einen String mit den Variablenwerten der Klasse "ElementTextWert" in einer Zeile
   */
  public String toStringZeile()
  {
    StringBuffer ergebnis_string_buffer = new StringBuffer();

    ergebnis_string_buffer.append( m_art + "|" );
    ergebnis_string_buffer.append( m_id_element + "|" );
    ergebnis_string_buffer.append( m_id_text + "|" );
    ergebnis_string_buffer.append( m_text + "|" );
    ergebnis_string_buffer.append( m_wert + "|" );

    return ergebnis_string_buffer.toString();
  }

  public String getDebugString()
  {
    return "#funktionsname( \"" + getArt() + "\",\"" + getIdElement() + "\",\"" + getIdText() + "\",\"" + getText() + "\",\"" + getWert() + "\", \"\" );";
  }

  /**
   * Liefert eine Kopie dieser Klasse wieder
   *
   * @return eine Instanz von "ElementTextWert" mit den Daten dieser Instanz
   */
  public ElementTextWert getCopy()
  {
    ElementTextWert instanz_kopie = new ElementTextWert();

    instanz_kopie.setArt( getArt() );
    instanz_kopie.setIdElement( getIdElement() );
    instanz_kopie.setIdText( getIdText() );
    instanz_kopie.setText( getText() );
    instanz_kopie.setWert( getWert() );

    return instanz_kopie;
  }
}
