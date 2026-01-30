package de.ea234.epost.util.druck;

import java.awt.Color;

import org.openpdf.text.Element;
import org.openpdf.text.Font;

class ITextStyle
{
  public static final int   ALIGN_LEFT                 = Element.ALIGN_LEFT;

  public static final int   ALIGN_CENTER               = Element.ALIGN_CENTER;

  public static final int   ALIGN_RIGHT                = Element.ALIGN_RIGHT;

  public static final int   ALIGN_JUSTIFIED            = Element.ALIGN_JUSTIFIED;

  public static final int   ALIGN_BOTTOM               = Element.ALIGN_BOTTOM;

  public static final float PAGE_ZISE_A4_WIDTH         = 595.0f;

  public static final float PAGE_ZISE_A4_HEIGHT        = 842.0f;

  public static final int   RECTANGLE_BORDER_TOP       = 1;

  public static final int   RECTANGLE_BORDER_BOTTOM    = 2;

  public static final int   RECTANGLE_BORDER_LEFT      = 4;

  public static final int   RECTANGLE_BORDER_RIGHT     = 8;

  public static final int   RECTANGLE_BORDER_NO_BORDER = 0;

  /**
   * <pre>
   * Fuehrt einen Class-Cast auf die Klasse "ITextStyle" auf das uebergebene Objekt aus. 
   *
   * Im Falle einer ClassCastException wird "null" zurueckgegeben.
   * Ist das Objekt selber "null" wird "null" zurueckgegeben.
   *
   * ITextStyle var_instanz = ITextStyle.getClassCast( java_object );
   *
   * if ( var_instanz == null )
   * {
   *   str_fehler = "Die Instanz \"java_object\" ist keine Instanz der Klasse \"ITextStyle\" ";
   *   return;
   * }
   *
   * </pre>
   *
   * @param  pObjekt        das zu castende Objekt
   * @return das Objekt als Instanz von "ITextStyle", oder "null" im Fehlerfall
   */
  public static ITextStyle getClassCast( Object pObjekt )
  {
    /*
     * Pruefung: pObjekt ungleich null ?
     */
    if ( pObjekt != null )
    {
      try
      {
        return (ITextStyle) pObjekt;
      }
      catch ( ClassCastException abgf_fehler )
      {
        // keine Aktion im Fehlerfall, da Rueckgabe von null
      }
    }

    return null;
  }

  public void clear()
  {
    m_style_name = null;
    m_itext_font = null;
    m_itext_font_red = null;
    m_text_align = 0;
    m_font_basename = 1;
    m_font_weight = 0;
    m_font_size = 10;
    m_space_before = 0.0f;
    m_space_after = 0.0f;
    m_indentation = 0.0f;
  }

  private Font   m_itext_font     = null;

  private Font   m_itext_font_red = null;

  private int    m_text_align     = ALIGN_LEFT;

  private float  m_space_before   = 0.0f;

  private float  m_space_after    = 0.0f;

  private float  m_indentation    = 0.0f;

  private int    m_font_basename  = 1;

  private int    m_font_weight    = 0;

  private int    m_font_size      = 10;

  private String m_style_name     = "unbekannt";

  public String getStyleName()
  {
    return m_style_name;
  }

  public void setStyleName( String pStyleName )
  {
    m_style_name = pStyleName;
  }

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

  public int getTextAlign()
  {
    return m_text_align;
  }

  public void setTextAlign( int pTextAlign )
  {
    m_text_align = pTextAlign;
  }

  public Font getFont()
  {
    return m_itext_font;
  }

  public void setFont( Font pItextFont )
  {
    m_itext_font = pItextFont;
  }

  public Font getFontRed()
  {
    if ( m_itext_font_red == null )
    {
      m_itext_font_red = new Font( m_font_basename, Float.parseFloat( "" + m_font_size ), m_font_weight, Color.RED );
    }

    return m_itext_font_red;
  }

  public void setFont( String pFontName, int pFontSize, String pFontWeight, Color pColor )
  {
    m_font_size = pFontSize;
    m_font_basename = 0;
    m_font_weight = 0;

    if ( pFontName.equalsIgnoreCase( "COURIER" ) )
    {
      m_font_basename = 0;
    }
    else if ( pFontName.equalsIgnoreCase( "HELVETICA" ) )
    {
      m_font_basename = 1;
    }
    else if ( pFontName.equalsIgnoreCase( "TIMES_ROMAN" ) )
    {
      m_font_basename = 2;
    }
    else if ( pFontName.equalsIgnoreCase( "SYMBOL" ) )
    {
      m_font_basename = 3;
    }
    else if ( pFontName.equalsIgnoreCase( "ZAPFDINGBATS" ) )
    {
      m_font_basename = 4;
    }

    if ( pFontWeight.equalsIgnoreCase( "NORMAL" ) )
    {
      m_font_weight = 0;
    }
    else if ( pFontWeight.equalsIgnoreCase( "BOLD" ) )
    {
      m_font_weight = 1;
    }
    else if ( pFontWeight.equalsIgnoreCase( "ITALIC" ) )
    {
      m_font_weight = 2;
    }
    else if ( pFontWeight.equalsIgnoreCase( "UNDERLINE" ) )
    {
      m_font_weight = 4;
    }
    else if ( pFontWeight.equalsIgnoreCase( "STRIKETHRU" ) )
    {
      m_font_weight = 8;
    }
    else if ( pFontWeight.equalsIgnoreCase( "BOLDITALIC" ) )
    {
      m_font_weight = 3;
    }

    if ( pColor == null )
    {
      m_itext_font = new Font( m_font_basename, Float.parseFloat( "" + m_font_size ), m_font_weight, Color.BLACK );
    }
    else
    {
      m_itext_font = new Font( m_font_basename, Float.parseFloat( "" + m_font_size ), m_font_weight, pColor );
    }
  }
}
