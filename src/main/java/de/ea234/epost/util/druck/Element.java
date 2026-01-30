package de.ea234.epost.util.druck;

public class Element {

  public static final int ART_ELEMENT_TEXT_WERT = 1;

  public static final int ART_ELEMENT_TEXT = 2;

  public static final int ART_ELEMENT_NEUE_SEITE = 3;

  public static final int ART_ELEMENT_CSV_ZEILE = 4;

  public static final int ART_ELEMENT_TEXT_BOXED = 5;

  public static final int ART_ELEMENT_SEITEN_UEBERSCHRIFT = 6;

  public static final int ART_ELEMENT_SEITEN_FUSSLEISTE = 7;

  public static final int ART_ELEMENT_SEITE_1_ABSENDER = 8;

  /**
   * <pre>
   * Fuehrt einen Class-Cast auf die Klasse "Element" auf das uebergebene Objekt aus.
   *
   * Im Falle einer ClassCastException wird "null" zurueckgegeben.
   * Ist das Objekt selber "null" wird "null" zurueckgegeben.
   *
   * Element instanz = Element.getClassCast( java_object );
   *
   * if ( instanz == null )
   * {
   *   str_fehler = "Die Instanz \"java_object\" ist keine Instanz der Klasse \"Element\" ";
   *   return;
   * }
   *
   * </pre>
   *
   * @param pObjekt das zu castende Objekt
   * @return das Objekt als Instanz von "Element", oder "null" im Fehlerfall
   */
  public static Element getClassCast( Object pObjekt )
  {
    /*
     * Pruefung: pObjekt ungleich null ?
     */
    if ( pObjekt != null )
    {
      try
      {
        return (Element) pObjekt;
      }
      catch (ClassCastException abgf_fehler)
      {
        // keine Aktion im Fehlerfall, da Rueckgabe von null
      }
    }

    return null;
  }

  /**
   * <pre>
   * Art des Elements
   * </pre>
   */
  private int m_art;

  private String m_id_style = null;

  public boolean hasIdStyle()
  {
    return ((m_id_style != null) && (m_id_style.trim().length() > 0));
  }

  public String getIdStyle()
  {
    return m_id_style;
  }

  public void setIdStyle( String pIdStyle )
  {
    m_id_style = pIdStyle;
  }

  public String getStrArt()
  {
    if ( m_art == ART_ELEMENT_TEXT_WERT )
    {
      return "ART_ELEMENT_TEXT_WERT";
    }

    if ( m_art == ART_ELEMENT_TEXT )
    {
      return "ART_ELEMENT_TEXT";
    }

    if ( m_art == ART_ELEMENT_NEUE_SEITE )
    {
      return "ART_ELEMENT_NEUE_SEITE";
    }

    if ( m_art == ART_ELEMENT_CSV_ZEILE )
    {
      return "ART_ELEMENT_CSV_ZEILE";
    }

    if ( m_art == ART_ELEMENT_TEXT_BOXED )
    {
      return "ART_ELEMENT_TEXT_BOXED";
    }

    if ( m_art == ART_ELEMENT_SEITEN_UEBERSCHRIFT )
    {
      return "ART_ELEMENT_SEITEN_UEBERSCHRIFT";
    }

    if ( m_art == ART_ELEMENT_SEITEN_FUSSLEISTE )
    {
      return "ART_ELEMENT_SEITEN_FUSSLEISTE";
    }

    if ( m_art == ART_ELEMENT_SEITE_1_ABSENDER )
    {
      return "ART_ELEMENT_SEITE_1_ABSENDER";
    }

    return "ART_ELEMENT_UNBEKANNT_" + m_art;
  }

  /**
   * <pre>
   * ID des Elementes
   * </pre>
   */
  private String m_id_element = null;

  private Object m_element_objekt = null;

  public boolean istArtTextWert()
  {
    return m_art == ART_ELEMENT_TEXT_WERT;
  }

  public boolean istArtCsvZeile()
  {
    return m_art == ART_ELEMENT_CSV_ZEILE;
  }

  public boolean istArtNeueSeite()
  {
    return m_art == ART_ELEMENT_NEUE_SEITE;
  }

  public boolean istArtTextBoxed()
  {
    return m_art == ART_ELEMENT_TEXT_BOXED;
  }

  public boolean istArtText()
  {
    return m_art == ART_ELEMENT_TEXT;
  }

  public boolean istArtSeitenUeberschrift()
  {
    return m_art == ART_ELEMENT_SEITEN_UEBERSCHRIFT;
  }

  public boolean istArtSeitenFussleiste()
  {
    return m_art == ART_ELEMENT_SEITEN_FUSSLEISTE;
  }

  public Object getElementObjekt()
  {
    return m_element_objekt;
  }

  public void setElementObjekt( Object pElementObjekt )
  {
    m_element_objekt = pElementObjekt;
  }

  public void setElementTextBoxed( ElementTextBoxed pElementTextBoxed )
  {
    m_art = ART_ELEMENT_TEXT_BOXED;
    m_element_objekt = pElementTextBoxed;
  }

  public void setElementText( ElementText pElementText )
  {
    m_art = ART_ELEMENT_TEXT_BOXED;
    m_element_objekt = pElementText;
  }

  public void setElementTextWert( ElementTextWert pElementTextWert )
  {
    m_art = ART_ELEMENT_TEXT_WERT;
    m_element_objekt = pElementTextWert;
  }

  public void setElementCsvZeile( ElementCsvZeile pElementCsvZeile )
  {
    m_art = ART_ELEMENT_CSV_ZEILE;
    m_element_objekt = pElementCsvZeile;
  }

  public void setElementNeueSeite( ElementNeueSeite pElementNeueSeite )
  {
    m_art = ART_ELEMENT_NEUE_SEITE;
    m_element_objekt = pElementNeueSeite;
  }

  public void setElementSeitenUeberschrift( ElementSeitenUeberschrift pElementSeitenUeberschrift )
  {
    m_art = ART_ELEMENT_SEITEN_UEBERSCHRIFT;
    m_element_objekt = pElementSeitenUeberschrift;
  }

  public void setElementSeitenFussleiste( ElementSeitenFussleiste pElementSeitenFussleiste )
  {
    m_art = ART_ELEMENT_SEITEN_FUSSLEISTE;
    m_element_objekt = pElementSeitenFussleiste;
  }

  public ElementSeitenFussleiste getNewElementSeitenFussleiste()
  {
    m_element_objekt = new ElementSeitenFussleiste();

    m_art = ART_ELEMENT_SEITEN_FUSSLEISTE;

    return ElementSeitenFussleiste.getClassCast( m_element_objekt );
  }

  public ElementSeitenFussleiste getElementSeitenFussleiste()
  {
    return ElementSeitenFussleiste.getClassCast( m_element_objekt );
  }

  public ElementSeitenUeberschrift getNewElementSeitenUeberschrift()
  {
    m_element_objekt = new ElementSeitenUeberschrift();

    m_art = ART_ELEMENT_SEITEN_UEBERSCHRIFT;

    return ElementSeitenUeberschrift.getClassCast( m_element_objekt );
  }

  public ElementSeitenUeberschrift getElementSeitenUeberschrift()
  {
    return ElementSeitenUeberschrift.getClassCast( m_element_objekt );
  }

  public ElementNeueSeite getNewElementNeueSeite()
  {
    m_element_objekt = new ElementNeueSeite();

    m_art = ART_ELEMENT_NEUE_SEITE;

    return ElementNeueSeite.getClassCast( m_element_objekt );
  }

  public ElementNeueSeite getElementNeueSeite()
  {
    return ElementNeueSeite.getClassCast( m_element_objekt );
  }

  public ElementTextWert getNewElementTextWert()
  {
    m_element_objekt = new ElementTextWert();

    m_art = ART_ELEMENT_TEXT_WERT;

    return ElementTextWert.getClassCast( m_element_objekt );
  }

  public ElementTextWert getElementTextWert()
  {

    return ElementTextWert.getClassCast( m_element_objekt );
  }

  public ElementCsvZeile getNewElementCsvZeile()
  {
    m_element_objekt = new ElementCsvZeile();

    m_art = ART_ELEMENT_CSV_ZEILE;

    return ElementCsvZeile.getClassCast( m_element_objekt );
  }

  public ElementCsvZeile getElementCsvZeile()
  {
    return ElementCsvZeile.getClassCast( m_element_objekt );
  }

  public ElementTextBoxed getElementTextBoxed()
  {
    return ElementTextBoxed.getClassCast( m_element_objekt );
  }

  public ElementText getNewElementText()
  {
    m_element_objekt = new ElementText();

    m_art = ART_ELEMENT_TEXT;

    return ElementText.getClassCast( m_element_objekt );
  }

  public ElementText getElementText()
  {
    return ElementText.getClassCast( m_element_objekt );
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_art.
   * </pre>
   *
   * @param pArt der zu setzende Wert
   */
  public void setArt( int pArt )
  {
    m_art = pArt;
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_id_element.
   * </pre>
   *
   * @param pIdElement der zu setzende Wert
   */
  public void setIdElement( String pIdElement )
  {
    m_id_element = pIdElement;
  }

  /**
   * <pre>
   * Liefert den Wert der Variablen m_art.
   * </pre>
   *
   * @return den Wert der Variablen "m_art"
   */
  public int getArt()
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
   * Erstellt die Stringrepraesentation dieser Beanklasse
   *
   * @return einen String mit den Variablenwerten der Klasse "Element"
   */
  public String toString()
  {
    StringBuffer ergebnis_string_buffer = new StringBuffer();

    ergebnis_string_buffer.append( "\nBean-Klasse Element" );

    ergebnis_string_buffer.append( "\n  + m_art        >" + m_art + "<" );
    ergebnis_string_buffer.append( "\n  + m_id_element >" + m_id_element + "<" );

    return ergebnis_string_buffer.toString();
  }

  /**
   * Erstellt eine Stringrepraesentation in einer Zeile, wobei die Werte durch
   * ein Trennzeichen getrennt werden
   *
   * @return einen String mit den Variablenwerten der Klasse "Element" in einer
   * Zeile
   */
  public String toStringZeile()
  {
    StringBuffer ergebnis_string_buffer = new StringBuffer();

    ergebnis_string_buffer.append( m_art + "|" );
    ergebnis_string_buffer.append( m_id_element + "|" );

    return ergebnis_string_buffer.toString();
  }

  /**
   * Liefert eine Kopie dieser Klasse wieder
   *
   * @return eine Instanz von "Element" mit den Daten dieser Instanz
   */
  public Element getCopy()
  {
    Element instanz_kopie = new Element();

    instanz_kopie.setArt( getArt() );
    instanz_kopie.setIdElement( getIdElement() );

    return instanz_kopie;
  }

  public void clear()
  {
    m_art = 0;
    m_id_element = null;

    ElementTextWert a = getElementTextWert();

    if ( a != null )
    {
      a.clear();
    }
    else
    {
      ElementCsvZeile b = getElementCsvZeile();

      if ( b != null )
      {
        b.clear();
      }
      else
      {
        ElementTextBoxed tb = getElementTextBoxed();

        if ( tb != null )
        {
          tb.clear();
        }
        else
        {
          ElementText text = getElementText();

          if ( text != null )
          {
            text.clear();
          }
          else
          {
            ElementSeitenFussleiste tss = getElementSeitenFussleiste();

            if ( tss != null )
            {
              tss.clear();
            }
          }
        }
      }

    }

    m_element_objekt = null;
    m_id_style = null;
  }

  public boolean istArtElementSeite1Absender()
  {
    return m_art == ART_ELEMENT_SEITE_1_ABSENDER;
  }
}
