package de.ea234.epost.util.druck;

import java.awt.Color;

public class ElementStyle
{
  private static final String TEXT_ALIGN_LEFT    = "left";

  private static final String TEXT_ALIGN_RIGHT   = "right";

  private static final String TEXT_ALIGN_CENTER  = "center";

  private static final String TEXT_ALIGN_JUSTIFY = "justify";

  private static final String FONT_WEIGHT_BOLD   = "bold";

  private static final String FONT_WEIGHT_NORMAL = "normal";

  /**
   * Erstellt eine Instanz der Klasse "ElementStyle" und gibt diese zurueck. 
   *
   * @return eine Instanz der Klasse "ElementStyle"
   */
  public static ElementStyle getNewElementStyle()
  {
    return new ElementStyle();
  }

  /**
   * <pre>
   * Fuehrt einen Class-Cast auf die Klasse "ElementStyle" auf das uebergebene Objekt aus. 
   *
   * Im Falle einer ClassCastException wird "null" zurueckgegeben.
   * Ist das Objekt selber "null" wird "null" zurueckgegeben.
   *
   * ElementStyle instanz = ElementStyle.getClassCast( java_object );
   *
   * if ( instanz == null )
   * {
   *   str_fehler = "Die Instanz \"java_object\" ist keine Instanz der Klasse \"ElementStyle\" ";
   *   return;
   * }
   *
   * </pre>
   *
   * @param  pObjekt        das zu castende Objekt
   * @return das Objekt als Instanz von "ElementStyle", oder "null" im Fehlerfall
   */
  static ElementStyle getClassCast( Object pObjekt )
  {
    /*
     * Pruefung: pObjekt ungleich null ?
     */
    if ( pObjekt != null )
    {
      try
      {
        return (ElementStyle) pObjekt;
      }
      catch ( ClassCastException abgf_fehler )
      {
        // keine Aktion im Fehlerfall, da Rueckgabe von null
      }
    }

    return null;
  }

  private String m_id_name       = null;

  private int    m_margin_left   = 0;

  private int    m_margin_right  = 0;

  private int    m_margin_top    = 0;

  private int    m_margin_bottom = 0;

  private String m_text_align    = "";

  private String m_font_name     = null;

  private String m_font_weight   = null;

  private int    m_font_size     = 10;

  private float  m_space_before  = 0.0f;

  private float  m_space_after   = 0.0f;

  private float  m_indentation   = 0.0f;

  private Color  m_font_color    = null;

  private String m_border        = null;

  public float getSpaceBefore()
  {
    return m_space_before;
  }

  public void setSpaceBefore( float pSpaceBefore )
  {
    m_space_before = pSpaceBefore;
  }

  public float getSpaceAfter()
  {
    return m_space_after;
  }

  public void setSpaceAfter( float pSpaceAfter )
  {
    m_space_after = pSpaceAfter;
  }

  public float getIndentation()
  {
    return m_indentation;
  }

  public void setIndentation( float pIndentation )
  {
    m_indentation = pIndentation;
  }

  public String getTextAlign()
  {
    return m_text_align;
  }

  public boolean isTextAlignLeft()
  {
    if ( m_text_align != null )
    {
      return m_text_align.equalsIgnoreCase( TEXT_ALIGN_LEFT );
    }

    return false;
  }

  public boolean isTextAlignRight()
  {
    if ( m_text_align != null )
    {
      return m_text_align.equalsIgnoreCase( TEXT_ALIGN_RIGHT );
    }

    return false;
  }

  public boolean isTextAlignCenter()
  {
    if ( m_text_align != null )
    {
      return m_text_align.equalsIgnoreCase( TEXT_ALIGN_CENTER );
    }

    return false;
  }

  public boolean isTextAlignJustified()
  {
    if ( m_text_align != null )
    {
      return m_text_align.equalsIgnoreCase( TEXT_ALIGN_JUSTIFY );
    }

    return false;
  }

  public void setTextAlignJustified()
  {
    m_text_align = TEXT_ALIGN_JUSTIFY;
  }

  public void setTextAlign( String pTextAlign )
  {
    m_text_align = pTextAlign;
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_id_name.
   * </pre>
   * 
   * @param  pIdName        der zu setzende Wert
   */
  public void setIdName( String pIdName )
  {
    m_id_name = pIdName;
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_margin_left.
   * </pre>
   * 
   * @param  pMarginLeft    der zu setzende Wert als String (wird zu int gewandelt)
   */
  public void setMarginLeft( String pMarginLeft )
  {
    m_margin_left = getInteger( pMarginLeft, 0 );
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_margin_left.
   * </pre>
   * 
   * @param  pMarginLeft    der zu setzende Wert
   */
  public void setMarginLeft( int pMarginLeft )
  {
    m_margin_left = pMarginLeft;
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_margin_right.
   * </pre>
   * 
   * @param  pMarginRight   der zu setzende Wert als String (wird zu int gewandelt)
   */
  public void setMarginRight( String pMarginRight )
  {
    m_margin_right = getInteger( pMarginRight, 0 );
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_margin_right.
   * </pre>
   * 
   * @param  pMarginRight   der zu setzende Wert
   */
  public void setMarginRight( int pMarginRight )
  {
    m_margin_right = pMarginRight;
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_margin_top.
   * </pre>
   * 
   * @param  pMarginTop     der zu setzende Wert als String (wird zu int gewandelt)
   */
  public void setMarginTop( String pMarginTop )
  {
    m_margin_top = getInteger( pMarginTop, 0 );
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_margin_top.
   * </pre>
   * 
   * @param  pMarginTop     der zu setzende Wert
   */
  public void setMarginTop( int pMarginTop )
  {
    m_margin_top = pMarginTop;
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_margin_bottom.
   * </pre>
   * 
   * @param  pMarginBottom  der zu setzende Wert als String (wird zu int gewandelt)
   */
  public void setMarginBottom( String pMarginBottom )
  {
    m_margin_bottom = getInteger( pMarginBottom, 0 );
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_margin_bottom.
   * </pre>
   * 
   * @param  pMarginBottom  der zu setzende Wert
   */
  public void setMarginBottom( int pMarginBottom )
  {
    m_margin_bottom = pMarginBottom;
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_font.
   * </pre>
   * 
   * @param  pFontName          der zu setzende Wert
   */
  public void setFontName( String pFontName )
  {
    m_font_name = pFontName;
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_font_size.
   * </pre>
   * 
   * @param  pFontSize      der zu setzende Wert als String (wird zu int gewandelt)
   */
  public void setFontSize( String pFontSize )
  {
    m_font_size = getInteger( pFontSize, 0 );
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_font_size.
   * </pre>
   * 
   * @param  pFontSize      der zu setzende Wert
   */
  public void setFontSize( int pFontSize )
  {
    m_font_size = pFontSize;
  }

  /**
   * <pre>
   * Setzt den Wert der Variablen m_font_weight.
   * </pre>
   * 
   * @param  pFontWeight    der zu setzende Wert
   */
  public void setFontWeight( String pFontWeight )
  {
    m_font_weight = pFontWeight;
  }

  /**
   * <pre>
   * Liefert den Wert der Variablen m_id_name.
   * </pre>
   *  
   * @return den Wert der Variablen "m_id_name"
   */
  public String getIdName()
  {
    return m_id_name;
  }

  /**
   * <pre>
   * Liefert den Wert der Variablen m_margin_left.
   * </pre>
   *  
   * @return den Wert der Variablen "m_margin_left"
   */
  public int getMarginLeft()
  {
    return m_margin_left;
  }

  /**
   * <pre>
   * Liefert den Wert der Variablen m_margin_right.
   * </pre>
   *  
   * @return den Wert der Variablen "m_margin_right"
   */
  public int getMarginRight()
  {
    return m_margin_right;
  }

  public String getBorder()
  {
    return m_border;
  }

  public void setBorder( String pBorder )
  {
    m_border = pBorder;
  }

  public boolean isBorder()
  {
    return m_border != null;
  }

  /**
   * <pre>
   * Liefert den Wert der Variablen m_margin_top.
   * </pre>
   *  
   * @return den Wert der Variablen "m_margin_top"
   */
  public int getMarginTop()
  {
    return m_margin_top;
  }

  /**
   * <pre>
   * Liefert den Wert der Variablen m_margin_bottom.
   * </pre>
   *  
   * @return den Wert der Variablen "m_margin_bottom"
   */
  public int getMarginBottom()
  {
    return m_margin_bottom;
  }

  /**
   * <pre>
   * Liefert den Wert der Variablen m_font.
   *  
   * @return den Wert der Variablen "m_font"
   */
  public String getFontName()
  {
    return m_font_name;
  }

  /**
   * <pre>
   * Liefert den Wert der Variablen m_font_size.
   * </pre>
   *  
   * @return den Wert der Variablen "m_font_size"
   */
  public int getFontSize()
  {
    return m_font_size;
  }

  /**
   * <pre>
   * Liefert den Wert der Variablen m_font_weight.
   * </pre>
   *  
   * @return den Wert der Variablen "m_font_weight"
   */
  public String getFontWeight()
  {
    return m_font_weight;
  }

  public boolean hasFontWeight()
  {
    return ( m_font_weight != null ) && ( m_font_weight.trim().length() > 0 );
  }

  public void setFontColor( Color pFontColor )
  {
    m_font_color = pFontColor;
  }

  public Color getFontColor()
  {
    if ( m_font_color == null )
    {
      m_font_color = Color.RED;
    }
    return m_font_color;
  }

  public boolean isFontWeightBold()
  {
    if ( m_font_weight != null )
    {
      return m_font_weight.equalsIgnoreCase( FONT_WEIGHT_BOLD );
    }

    return false;
  }

  public boolean isFontWeightNormal()
  {
    if ( m_font_weight != null )
    {
      return m_font_weight.equalsIgnoreCase( FONT_WEIGHT_NORMAL );
    }

    return false;
  }

  /**
   * Erstellt die Stringrepraesentation dieser Beanklasse
   *  
   * @return einen String mit den Variablenwerten der Klasse "ElementStyle"
   */
  public String toString()
  {
    StringBuffer ergebnis_string_buffer = new StringBuffer();

    ergebnis_string_buffer.append( "\nBean-Klasse ElementStyle" );

    ergebnis_string_buffer.append( "\n  + m_id_name       >" + m_id_name + "<" );
    ergebnis_string_buffer.append( "\n  + m_margin_left   >" + m_margin_left + "<" );
    ergebnis_string_buffer.append( "\n  + m_margin_right  >" + m_margin_right + "<" );
    ergebnis_string_buffer.append( "\n  + m_margin_top    >" + m_margin_top + "<" );
    ergebnis_string_buffer.append( "\n  + m_margin_bottom >" + m_margin_bottom + "<" );
    ergebnis_string_buffer.append( "\n  + m_font          >" + m_font_name + "<" );
    ergebnis_string_buffer.append( "\n  + m_font_size     >" + m_font_size + "<" );
    ergebnis_string_buffer.append( "\n  + m_font_weight   >" + m_font_weight + "<" );

    return ergebnis_string_buffer.toString();
  }

  /**
   * Erstellt eine Stringrepraesentation in einer Zeile, wobei die Werte durch ein Trennzeichen getrennt werden
   *  
   * @return einen String mit den Variablenwerten der Klasse "ElementStyle" in einer Zeile
   */
  public String toStringZeile()
  {
    StringBuffer ergebnis_string_buffer = new StringBuffer();

    ergebnis_string_buffer.append( m_id_name + "|" );
    ergebnis_string_buffer.append( m_margin_left + "|" );
    ergebnis_string_buffer.append( m_margin_right + "|" );
    ergebnis_string_buffer.append( m_margin_top + "|" );
    ergebnis_string_buffer.append( m_margin_bottom + "|" );
    ergebnis_string_buffer.append( m_font_name + "|" );
    ergebnis_string_buffer.append( m_font_size + "|" );
    ergebnis_string_buffer.append( m_font_weight + "|" );

    return ergebnis_string_buffer.toString();
  }

  /**
   * Liefert eine Kopie dieser Klasse wieder
   *
   * @return eine Instanz von "ElementStyle" mit den Daten dieser Instanz
   */
  public ElementStyle getCopy()
  {
    ElementStyle instanz_kopie = new ElementStyle();

    instanz_kopie.setIdName( getIdName() );
    instanz_kopie.setMarginLeft( getMarginLeft() );
    instanz_kopie.setMarginRight( getMarginRight() );
    instanz_kopie.setMarginTop( getMarginTop() );
    instanz_kopie.setMarginBottom( getMarginBottom() );
    instanz_kopie.setFontName( getFontName() );
    instanz_kopie.setFontSize( getFontSize() );
    instanz_kopie.setFontWeight( getFontWeight() );

    return instanz_kopie;
  }

  /**
   * <pre>
   * Ermittelt aus dem Parameter "pString" den Integerwert.
   * 
   * Kommt es bei der Umwandlung zu einer NumberFormatException,
   * wird der Vorgabewert zurueckgegeben. 
   * 
   * Auf pString wird ein TRIM ausgefuehrt.
   * </pre>
   * 
   * @param pString zu parsende Zeichenkette
   * @param pVorgabeWert Vorgabewert im Fehlerfall
   * @return der Wert als int oder der Vorgabewert
   */
  private static int getInteger( String pString, int pVorgabeWert )
  {
    try
    {
      if ( pString != null )
      {
        return Integer.parseInt( pString.trim() );
      }
    }
    catch ( NumberFormatException err_inst )
    {
      // Keine Fehlermeldung, da im Fehlerfall der Vorgabewert zurueckgegeben wird
    }

    return pVorgabeWert;
  }
}
