package de.ea234.epost.util.druck;

public class ElementSeitenUeberschrift
{
  /**
   * <pre>
   * Fuehrt einen Class-Cast auf die Klasse "ElementSeitenUeberschrift" auf das uebergebene Objekt aus. 
   *
   * Im Falle einer ClassCastException wird "null" zurueckgegeben.
   * Ist das Objekt selber "null" wird "null" zurueckgegeben.
   *
   * ElementSeitenUeberschrift instanz = ElementSeitenUeberschrift.getClassCast( java_object );
   *
   * if ( instanz == null )
   * {
   *   str_fehler = "Die Instanz \"java_object\" ist keine Instanz der Klasse \"ElementSeitenUeberschrift\" ";
   *   return;
   * }
   *
   * </pre>
   *
   * @param  pObjekt        das zu castende Objekt
   * @return das Objekt als Instanz von "ElementSeitenUeberschrift", oder "null" im Fehlerfall
   */
  public static ElementSeitenUeberschrift getClassCast( Object pObjekt )
  {
    /*
     * Pruefung: pObjekt ungleich null ?
     */
    if ( pObjekt != null )
    {
      try
      {
        return (ElementSeitenUeberschrift) pObjekt;
      }
      catch ( ClassCastException abgf_fehler )
      {
        // keine Aktion im Fehlerfall, da Rueckgabe von null
      }
    }

    return null;
  }

  private String m_seiten_bezeichnung  = "";

  private String m_seiten_ueberschrift = "";

  private String m_id_element          = "";

  public String getIdElement()
  {
    return m_id_element;
  }

  public void setIdElement( String pIdElement )
  {
    m_id_element = pIdElement;
  }

  public String getSeitenBezeichnung()
  {
    return m_seiten_bezeichnung;
  }

  public void setSeitenBezeichnung( String pSeitenBeizeichnung )
  {
    m_seiten_bezeichnung = pSeitenBeizeichnung;
  }

  public String getSeitenUeberschrift()
  {
    return m_seiten_ueberschrift;
  }

  public boolean hasSeitenUeberschrift()
  {
    return ( m_seiten_ueberschrift != null ) && ( m_seiten_ueberschrift.trim().length() > 0 );
  }

  public void setSeitenUeberschrift( String pSeitenUeberschrift )
  {
    m_seiten_ueberschrift = pSeitenUeberschrift;
  }
}
