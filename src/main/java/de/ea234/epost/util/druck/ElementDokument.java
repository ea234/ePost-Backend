package de.ea234.epost.util.druck;

import java.awt.Color;

public class ElementDokument
{
  public static String       CSS_DOKUMENT_TITEL           = "H1";

  public static String       CSS_BAUSTEINUEBERSCHRIFT     = "H2";

  public static String       CSS_BAUSTEINUEBERSCHRIFT_H2a = "H5";

  public static String       CSS_ABSCHNITTSUEBERSCHRIFT   = "H3";

  public static String       CSS_TEXT_STANDARD            = "LINE";

  public static String       CSS_TEXT_HERVORGEHOBEN       = "LINE_BOLD";

  public static String       CSS_TEXT_KLEIN               = "HINT";

  public static String       CSS_TABELLEN_TITEL           = "TABLE_TITLE";

  public static String       CSS_TABELLEN_ZEILE           = "TABLE_ROW";

  public static String       CSS_BRIEF_ABSENDER_KLEIN     = "CSS_BRIEF_ABSENDER_KLEIN";

  public static String       CSS_BRIEF_ABSENDER_NORMAL    = "CSS_BRIEF_ABSENDER_NORMAL";

  public static String       CSS_BRIEF_BETREFFZEILE       = "CSS_BRIEF_BETREFFZEILE";

  public static String       CSS_BRIEF_ANREDE             = "CSS_BRIEF_ANREDE";

  public static String       CSS_BRIEF_TEXT_STANDARD      = "CSS_BRIEF_TEXT_STANDARD";

  public static String       CSS_BRIEF_TEXT_FETT          = "CSS_BRIEF_TEXT_FETT";

  public static String       CSS_BRIEF_FUSSZEILE          = "CSS_BRIEF_FUSSZEILE";

  private static final float PAGE_MARGIN_LEFT             = 2.0f;

  private static final float PAGE_MARGIN_RIGHT            = 1.5f;

  private static final float PAGE_MARGIN_TOP              = 3.5f;

  private static final float PAGE_MARGIN_BOTTOM           = 3.0f;

  private ElementVector      m_element_vector             = null;

  private ElementStyles      m_element_styles             = null;

  private float              m_page_margin_left           = PAGE_MARGIN_LEFT;

  private float              m_page_margin_right          = PAGE_MARGIN_RIGHT;

  private float              m_page_margin_top            = PAGE_MARGIN_TOP;

  private float              m_page_margin_bottom         = PAGE_MARGIN_BOTTOM;

  private boolean            m_knz_drucke_falzmarken      = false;

  private boolean            m_knz_drucke_lochmarke       = false;

  private String             m_name_druckstueck           = "RBR";

  private String             m_dokument_titel             = null;

  private String             m_dokument_author            = null;

  public String getDokumentTitel()
  {
    return m_dokument_titel;
  }

  public void setDokumentTitel( String pDokumentTitel )
  {
    m_dokument_titel = pDokumentTitel;
  }

  public String getDokumentAuthor()
  {
    return m_dokument_author;
  }

  public void setDokumentAuthor( String pDokumentAuthor )
  {
    m_dokument_author = pDokumentAuthor;
  }

  public String getNameDruckstueck()
  {
    return m_name_druckstueck;
  }

  public void setNameDruckstueck( String pNameDruckstueck )
  {
    m_name_druckstueck = pNameDruckstueck;
  }

  public ElementVector getElementVector()
  {
    if ( m_element_vector == null )
    {
      m_element_vector = new ElementVector();
    }

    return m_element_vector;
  }

  public void setElementVector( ElementVector pPelementVector )
  {
    m_element_vector = pPelementVector;
  }

  public ElementStyles getElementStyles()
  {
    if ( m_element_styles == null )
    {
      m_element_styles = new ElementStyles();

      ElementStyle akt_element_style = new ElementStyle();

      /*
       *######################################################
       */
      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( CSS_DOKUMENT_TITEL );
      akt_element_style.setMarginTop( 5 );
      akt_element_style.setMarginLeft( 10 );

      akt_element_style.setFontName( "HELVETICA" );
      akt_element_style.setFontSize( 14 );
      akt_element_style.setFontWeight( "bold" );
      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setSpaceBefore( 0.0f );
      akt_element_style.setSpaceAfter( 0.0f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );

      /*
       *######################################################
       */
      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( CSS_BAUSTEINUEBERSCHRIFT );
      akt_element_style.setMarginTop( 5 );
      akt_element_style.setMarginLeft( 10 );

      akt_element_style.setFontName( "HELVETICA" );
      akt_element_style.setFontSize( 12 );
      akt_element_style.setFontWeight( "bold" );
      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setSpaceBefore( 0.0f );
      akt_element_style.setSpaceAfter( 0.2f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );

      /*
       *######################################################
       */
      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( CSS_BAUSTEINUEBERSCHRIFT_H2a );
      akt_element_style.setMarginTop( 5 );
      akt_element_style.setMarginLeft( 10 );
      akt_element_style.setFontName( "HELVETICA" );
      akt_element_style.setFontSize( 10 );
      akt_element_style.setFontWeight( "normal" );
      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setSpaceBefore( 0.0f );
      akt_element_style.setSpaceAfter( 0.2f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );

      /*
       *######################################################
       */
      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( CSS_ABSCHNITTSUEBERSCHRIFT );
      akt_element_style.setMarginTop( 0 );
      akt_element_style.setMarginLeft( 0 );
      akt_element_style.setFontName( "HELVETICA" );
      akt_element_style.setFontSize( 10 );
      akt_element_style.setFontWeight( "Bold" );
      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setSpaceBefore( 0.1f );
      akt_element_style.setSpaceAfter( 0.0f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );

      /*
       *######################################################
       */
      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( CSS_TEXT_HERVORGEHOBEN );
      akt_element_style.setMarginTop( 0 );
      akt_element_style.setMarginLeft( 0 );
      akt_element_style.setFontName( "HELVETICA" );
      akt_element_style.setFontSize( 10 );
      akt_element_style.setFontWeight( "bold" );
      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setSpaceBefore( 0.0f );
      akt_element_style.setSpaceAfter( 0.0f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );

      /*
       *######################################################
       */
      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( CSS_TEXT_KLEIN );
      akt_element_style.setMarginTop( 0 );
      akt_element_style.setMarginLeft( 0 );
      akt_element_style.setFontName( "HELVETICA" );
      akt_element_style.setFontSize( 8 );
      akt_element_style.setFontWeight( "normal" );
      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setTextAlignJustified();

      akt_element_style.setSpaceBefore( 0.0f );
      akt_element_style.setSpaceAfter( 0.1f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );

      /*
       *######################################################
       */
      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( CSS_TEXT_STANDARD );
      akt_element_style.setMarginTop( 0 );
      akt_element_style.setMarginLeft( 0 );
      akt_element_style.setFontName( "HELVETICA" );
      akt_element_style.setFontSize( 10 );
      akt_element_style.setFontWeight( "normal" );
      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setSpaceBefore( 0.0f );
      akt_element_style.setSpaceAfter( 0.0f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );

      /*
       *######################################################
       */
      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( CSS_TABELLEN_TITEL );
      akt_element_style.setMarginTop( 0 );
      akt_element_style.setMarginLeft( 0 );
      akt_element_style.setFontName( "HELVETICA" );
      akt_element_style.setFontSize( 9 );
      akt_element_style.setFontWeight( "bold" );
      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setSpaceBefore( 0.0f );
      akt_element_style.setSpaceAfter( 0.0f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );

      /*
       *######################################################
       */
      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( CSS_TABELLEN_ZEILE );
      akt_element_style.setMarginTop( 0 );
      akt_element_style.setMarginLeft( 0 );
      akt_element_style.setFontName( "HELVETICA" );
      akt_element_style.setFontSize( 9 );
      akt_element_style.setFontWeight( "normal" );
      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setSpaceBefore( 0.0f );
      akt_element_style.setSpaceAfter( 0.0f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );

      /*
       *######################################################
       */
      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( CSS_BRIEF_ABSENDER_KLEIN );
      akt_element_style.setMarginTop( 1 );
      akt_element_style.setMarginLeft( 8 );
      akt_element_style.setFontName( "Courier" );
      akt_element_style.setFontName( "TIMES_ROMAN" );
      akt_element_style.setFontSize( 8 );
      akt_element_style.setFontWeight( "normal" );

      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setSpaceBefore( 0.0f );
      akt_element_style.setSpaceAfter( 0.0f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );

      /*
       *######################################################
       */
      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( CSS_BRIEF_FUSSZEILE );
      akt_element_style.setMarginTop( 5 );
      akt_element_style.setMarginLeft( 8 );
      akt_element_style.setFontName( "Courier" );
      akt_element_style.setFontSize( 6 );
      akt_element_style.setFontWeight( "normal" );
      akt_element_style.setTextAlign( "Center" );
      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setSpaceBefore( 0.0f );
      akt_element_style.setSpaceAfter( 0.0f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );

      /*
       *######################################################
       */
      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( CSS_BRIEF_ABSENDER_NORMAL );
      akt_element_style.setMarginTop( 5 );
      akt_element_style.setMarginLeft( 8 );
      akt_element_style.setFontName( "Courier" );
      akt_element_style.setFontSize( 11 );
      akt_element_style.setFontWeight( "normal" );

      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setSpaceBefore( 0.0f );
      akt_element_style.setSpaceAfter( 0.0f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );
      /*
       *######################################################
       */
      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( CSS_BRIEF_BETREFFZEILE );
      akt_element_style.setMarginTop( 5 );
      akt_element_style.setMarginLeft( 8 );
      akt_element_style.setFontName( "Courier" );
      akt_element_style.setFontSize( 11 );
      akt_element_style.setFontWeight( "bold" );

      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setSpaceBefore( 1.0f );
      akt_element_style.setSpaceAfter( 0.5f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );
      /*
       *######################################################
       */
      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( CSS_BRIEF_ANREDE );
      akt_element_style.setMarginTop( 5 );
      akt_element_style.setMarginLeft( 8 );
      akt_element_style.setFontName( "Courier" );
      akt_element_style.setFontSize( 11 );
      akt_element_style.setFontWeight( "normal" );

      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setSpaceBefore( 1.0f );
      akt_element_style.setSpaceAfter( 0.5f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );

      /*
       *######################################################
       */
      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( CSS_BRIEF_TEXT_STANDARD );
      akt_element_style.setMarginTop( 5 );
      akt_element_style.setMarginLeft( 8 );
      akt_element_style.setFontName( "Courier" );
      akt_element_style.setFontSize( 11 );
      akt_element_style.setFontWeight( "nomal" );

      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setSpaceBefore( 0.0f );
      akt_element_style.setSpaceAfter( 0.0f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );

      /*
       *######################################################
       */

      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( CSS_BRIEF_TEXT_FETT );
      akt_element_style.setMarginTop( 5 );
      akt_element_style.setMarginLeft( 8 );
      akt_element_style.setFontName( "Courier" );
      akt_element_style.setFontSize( 11 );
      akt_element_style.setFontWeight( "BOLD" );

      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setSpaceBefore( 0.0f );
      akt_element_style.setSpaceAfter( 0.0f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );

      /*
       *######################################################
       */
    }

    return m_element_styles;
  }

  public void setElementStyles( ElementStyles pPelementStyles )
  {
    m_element_styles = pPelementStyles;
  }

  public float getPageMarginLeft()
  {
    return m_page_margin_left;
  }

  public void setPageMarginLeft( float pPageMarginLeft )
  {
    m_page_margin_left = pPageMarginLeft;
  }

  public float getPageMarginRight()
  {
    return m_page_margin_right;
  }

  public void setPageMarginRight( float pPageMarginRight )
  {
    m_page_margin_right = pPageMarginRight;
  }

  public float getPageMarginTop()
  {
    return m_page_margin_top;
  }

  public void setPageMarginTop( float pPageMarginTop )
  {
    m_page_margin_top = pPageMarginTop;
  }

  public float getPageMarginBottom()
  {
    return m_page_margin_bottom;
  }

  public void setPageMarginBottom( float pPageMarginBottom )
  {
    m_page_margin_bottom = pPageMarginBottom;
  }

  public boolean getKnzDruckeFalzmarken()
  {
    return m_knz_drucke_falzmarken;
  }

  public void setKnzDruckeFalzmarken( boolean pKnzDruckeFalzmarken )
  {
    m_knz_drucke_falzmarken = pKnzDruckeFalzmarken;
  }

  public boolean getKnzDruckeLochmarke()
  {
    return m_knz_drucke_lochmarke;
  }

  public void setKnzDruckeLochmarke( boolean pKnzDruckeLochmarke )
  {
    m_knz_drucke_lochmarke = pKnzDruckeLochmarke;
  }

  public Element addElementFusszeile( String pIdDruckzeile, String pIdStyle, String pText )
  {
    Element inst_element = getElementVector().addElement();

    if ( inst_element != null )
    {
      ElementSeitenFussleiste inst_element_text_wert = null;

      inst_element_text_wert = new ElementSeitenFussleiste();

      inst_element_text_wert.setIdElement( pIdDruckzeile );
      inst_element_text_wert.setIdText( null );

      inst_element_text_wert.setText( ( pText ) );

      inst_element_text_wert.setWert( null );

      inst_element.setIdElement( pIdDruckzeile );
      inst_element.setIdStyle( pIdStyle );
      inst_element.setElementSeitenFussleiste( inst_element_text_wert );
    }

    return inst_element;
  }

  public Element addElementText( String pIdDruckzeile, String pIdStyle, String pText )
  {
    Element inst_element = getElementVector().addElement();

    if ( inst_element != null )
    {
      ElementTextWert inst_element_text_wert = null;

      inst_element_text_wert = new ElementTextWert();

      inst_element_text_wert.setIdElement( pIdDruckzeile );
      inst_element_text_wert.setIdText( null );

      inst_element_text_wert.setText( ( pText ) );

      inst_element_text_wert.setWert( null );

      inst_element.setIdElement( pIdDruckzeile );
      inst_element.setIdStyle( pIdStyle );
      inst_element.setElementTextWert( inst_element_text_wert );
    }

    return inst_element;
  }

  public Element addElementTextWert( String pIdDruckzeile, String pIdText, String pIdStyle, String pText, String pWert )
  {
    Element inst_element = getElementVector().addElement();

    if ( inst_element != null )
    {
      ElementTextWert inst_element_text_wert = null;

      inst_element_text_wert = new ElementTextWert();

      inst_element_text_wert.setIdElement( pIdDruckzeile );
      inst_element_text_wert.setIdText( pIdText );

      inst_element_text_wert.setText( ( pText ) );

      inst_element_text_wert.setWert( pWert );

      inst_element.setIdElement( pIdDruckzeile );
      inst_element.setIdStyle( pIdStyle );
      inst_element.setElementTextWert( inst_element_text_wert );
    }

    return inst_element;
  }

  public Element addElementCsvZeile( String pIdDruckzeile, String pIdText, String pIdStyle, String pText, String pCsvAusrichtung )
  {
    Element inst_element = getElementVector().addElement();

    if ( inst_element != null )
    {
      ElementCsvZeile inst_element_csv_zeile = null;

      inst_element_csv_zeile = new ElementCsvZeile();

      inst_element_csv_zeile.setIdElement( pIdDruckzeile );
      inst_element_csv_zeile.setIdText( pIdText );

      inst_element_csv_zeile.setCsvStringWerte( ( pText ) );
      inst_element_csv_zeile.setCsvAusrichtung( pCsvAusrichtung );

      inst_element.setIdElement( pIdDruckzeile );
      inst_element.setIdStyle( pIdStyle );
      inst_element.setElementCsvZeile( inst_element_csv_zeile );
    }

    return inst_element;
  }

  public Element addElementNeueSeite( String pIdDruckzeile, String pIdStyle, String pSeitenBezeichnung )
  {
    Element inst_element = getElementVector().addElement();

    if ( inst_element != null )
    {
      ElementNeueSeite inst_element_neue_seite = null;

      inst_element_neue_seite = new ElementNeueSeite();

      inst_element_neue_seite.setIdElement( pIdDruckzeile );

      inst_element.setIdElement( pIdDruckzeile );
      inst_element.setIdStyle( pIdStyle );
      inst_element.setElementNeueSeite( inst_element_neue_seite );
    }

    return inst_element;
  }
}
