package de.ea234.epost.util.druck;

public class ElementText
{
  /**
   * <pre>
   * Fuehrt einen Class-Cast auf die Klasse "ElementText" auf das uebergebene Objekt aus. 
   *
   * Im Falle einer ClassCastException wird "null" zurueckgegeben.
   * Ist das Objekt selber "null" wird "null" zurueckgegeben.
   *
   * ElementText instanz = ElementText.getClassCast( java_object );
   *
   * if ( instanz == null )
   * {
   *   str_fehler = "Die Instanz \"java_object\" ist keine Instanz der Klasse \"ElementText\" ";
   *   return;
   * }
   *
   * </pre>
   *
   * @param  pObjekt        das zu castende Objekt
   * @return das Objekt als Instanz von "ElementText", oder "null" im Fehlerfall
   */
  public static ElementText getClassCast( Object pObjekt )
  {
    /*
     * Pruefung: pObjekt ungleich null ?
     */
    if ( pObjekt != null )
    {
      try
      {
        return (ElementText) pObjekt;
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

  public void setArt( String pArt )
  {
    m_art = pArt;
  }

  public void setIdElement( String pIdElement )
  {
    m_id_element = pIdElement;
  }

  public void setIdText( String pIdText )
  {
    m_id_text = pIdText;
  }

  public void setText( String pText )
  {
    m_text = pText;
  }

  public void setWert( String pWert )
  {
    m_wert = pWert;
  }

  public String getArt()
  {
    return m_art;
  }

  public String getIdElement()
  {
    return m_id_element;
  }

  public String getIdText()
  {
    return m_id_text;
  }

  public String getText()
  {
    return m_text;
  }

  public boolean hatWert()
  {
    return ( m_wert != null ) && ( m_wert.length() > 0 );
  }

  public String getWert()
  {
    return m_wert;
  }

  public String toString()
  {
    StringBuffer ergebnis_string_buffer = new StringBuffer();

    ergebnis_string_buffer.append( "\nBean-Klasse ElementText" );

    ergebnis_string_buffer.append( "\n  + m_art        >" + m_art + "<" );
    ergebnis_string_buffer.append( "\n  + m_id_element >" + m_id_element + "<" );
    ergebnis_string_buffer.append( "\n  + m_id_text    >" + m_id_text + "<" );
    ergebnis_string_buffer.append( "\n  + m_text       >" + m_text + "<" );
    ergebnis_string_buffer.append( "\n  + m_wert       >" + m_wert + "<" );

    return ergebnis_string_buffer.toString();
  }

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

  public ElementText getCopy()
  {
    ElementText instanz_kopie = new ElementText();

    instanz_kopie.setArt( getArt() );
    instanz_kopie.setIdElement( getIdElement() );
    instanz_kopie.setIdText( getIdText() );
    instanz_kopie.setText( getText() );
    instanz_kopie.setWert( getWert() );

    return instanz_kopie;
  }
}
