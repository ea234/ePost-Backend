package de.ea234.epost.util.druck;

import de.ea234.epost.model.dokument.Dokument;
import de.ea234.epost.model.kunden.Kunde;
import de.ea234.epost.util.FkDatum;
import de.ea234.epost.util.FkDatumLong;
import java.awt.Color;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Properties;
import de.ea234.epost.model.kunden.Adresse;
import de.ea234.epost.model.vorgang.Vorgang;

public class TestDokumentKunde {

  private String CSS_DOKUMENT_TITEL = "H1";

  private String CSS_BAUSTEINUEBERSCHRIFT = "H2";

  private String CSS_BAUSTEINUEBERSCHRIFT_H2a = "H5";

  private String CSS_ABSCHNITTSUEBERSCHRIFT = "H3";

  private String CSS_TEXT_STANDARD = "LINE";

  private String CSS_TEXT_HERVORGEHOBEN = "LINE_BOLD";

  private String CSS_TEXT_KLEIN = "HINT";

  private String CSS_TABELLEN_TITEL = "TABLE_TITLE";

  private String CSS_TABELLEN_ZEILE = "TABLE_ROW";

  private Properties m_prop_texte = null;

  private DecimalFormat m_zahlen_formatierer = null;

  private NewLineStringBuffer m_debug_string_buffer = null;

  private ElementVector m_element_vector = null;

  private ElementStyles m_element_styles = null;

  public static void main( String[] args )
  {
    TestDokumentKunde x = new TestDokumentKunde();

    String stamm_nummer = "17000" + "11";

    String kunde_vorname = "Alfons";
    String kunde_nachname = "Buntold";
    String kunde_email = kunde_vorname + "." + kunde_nachname + "@web.de";
    String kunde_telefon_handy = "123456789";
    String kunde_geb_datum_string = FkDatum.getString();
    Date kunde_geb_datum_date = FkDatumLong.parseToDate( kunde_geb_datum_string );

    Date date_today = FkDatum.getDate();

    int kunde_alter_in_jahren = FkDatum.getAnzahlJahre( kunde_geb_datum_date, date_today );

    Kunde new_kunde = new Kunde();

    new_kunde.setStammNummer( stamm_nummer );
    new_kunde.setAnrede( "Herr" );
    new_kunde.setVorName( kunde_vorname );
    new_kunde.setNachName( kunde_nachname );
    new_kunde.setEmail( kunde_email );
    new_kunde.setTelefon( kunde_telefon_handy );
    new_kunde.setGeburtsDatum( kunde_geb_datum_date );

    String adresse_strasse = "Gross Altmarkt 83";
    String adresse_ort = "95402";
    String adresse_plz = "Gurgmah";
    String adresse_land = "DE";

    Adresse kunde_adresse = new Adresse();

    kunde_adresse.setStrasse( adresse_strasse );
    kunde_adresse.setOrt( adresse_ort );
    kunde_adresse.setPostleitzahl( adresse_plz );
    kunde_adresse.setLand( adresse_land );

    new_kunde.setAddresse( kunde_adresse );

    Vorgang new_vorgang = new Vorgang();

    new_vorgang.setPaginierNr( "123456780" );

    Dokument new_dokument = new Dokument();

    new_dokument.setDatumEingang( FkDatum.getDate() );

    new_dokument.setPaginierNr( new_vorgang.getPaginierNr() );

    new_dokument.setDateiTyp( "PDF" );

    new_vorgang.setStammNr( stamm_nummer );

    new_vorgang.setVertragNummer( stamm_nummer + "01" );

    new_vorgang.setVorgangTypNr( "12345" );

    byte[] pdf_bytes = x.getTestDokument( new_kunde, new_vorgang );

    Path verzeichnis_name_ausgabe = Path.of( "/mnt/hd4tbb/daten" );

    PDF.schreibeDatei( verzeichnis_name_ausgabe.resolve( "test_" + FkDatum.getLong() + ".pdf" ).toString(), pdf_bytes );
  }

  public static byte[] getTestDokument( Kunde pKunde, Vorgang new_vorgang )
  {
    TestDokumentKunde test_erst_instance = new TestDokumentKunde();

    return test_erst_instance.erstelleDokument( pKunde, new_vorgang );
  }

  /**
   * <pre>
   * Erstellt eine Elementstruktur und erstellt daraus das PDF-Dokument.
   * </pre>
   */
  private byte[] erstelleDokument( Kunde pKunde, Vorgang pVorgang )
  {
    addElementSeitenUeberschrift( "D3_SEITEN_UEBERSCHRIFT", CSS_DOKUMENT_TITEL, "Ueberschrift Seite allgemeine Vertragsdaten", "Testdokument " + pVorgang.getPaginierNr() );

    /*
     * Struktur erstellen
     * Es wird die Funktion "getBausteinKunde" gerufen, welche die Daten in 
     * die Elementstruktur uebertraegt.
     */
    getBausteinKunde( pKunde );

    if ( pVorgang != null )
    {
      getBausteinVorgang( pVorgang );
    }

    wl( m_debug_string_buffer.toString() + "\n\n" + getDebugStringVektor() );

//    FkDatei.schreibeDebugDatei( "test_werte_vorschlagsdokument.txt", m_debug_string_buffer.toString() );
    byte[] pdf_bytes = null;

    try
    {
      ElementDokument element_dokument = new ElementDokument();

      element_dokument.setElementStyles( getElementStyles() );

      element_dokument.setElementVector( getElement() );

      element_dokument.setNameDruckstueck( "Testdruck " );

      element_dokument.setDokumentTitel( "Testdokument " + pKunde.getVorName() );

      element_dokument.setDokumentAuthor( "Author" );

      pdf_bytes = PDF.druck( element_dokument );

      element_dokument = null;
    }
    catch (Exception err_inst)
    {
      System.out.println( "Fehler1 errPdfDokumentVorschlag" + err_inst.getMessage() );

      err_inst.printStackTrace( System.out );
    }

    /*
     * Resourcen freigeben
     */
    clear();

    return pdf_bytes;
  }

  private Element addElementSeitenUeberschrift( String pIdDruckzeile, String pIdStyle, String pSeitenBezeichnung, String pSeitenUeberschrift )
  {
    Element inst_element = getElement().addElement();

    if ( inst_element != null )
    {
      ElementSeitenUeberschrift inst_element_seiten_ueberschrift = new ElementSeitenUeberschrift();

      inst_element_seiten_ueberschrift.setIdElement( pIdDruckzeile );
      inst_element_seiten_ueberschrift.setSeitenBezeichnung( pSeitenBezeichnung );
      inst_element_seiten_ueberschrift.setSeitenUeberschrift( pSeitenUeberschrift );

      inst_element.setIdElement( pIdDruckzeile );
      inst_element.setIdStyle( pIdStyle );
      inst_element.setElementSeitenUeberschrift( inst_element_seiten_ueberschrift );
    }

    return inst_element;
  }

  private void addStyle( String pIdDruckzeile, String pIdText, String pIdStyle, String pWert )
  {
    if ( m_debug_string_buffer == null )
    {
      m_debug_string_buffer = new NewLineStringBuffer();
    }

    String druck_text = getPropTexte().getProperty( pIdText, "" );

    m_debug_string_buffer.appendI( FkStringFeld.getFeldLinksMin( pIdDruckzeile, 35 ) + " | " );
    m_debug_string_buffer.appendI( FkStringFeld.getFeldLinksMin( pIdText, 35 ) + " | " );
    m_debug_string_buffer.appendI( FkStringFeld.getFeldLinksMin( druck_text, 60 ) );

    if ( (pWert != null) && (pWert.trim().length() > 0) )
    {
      m_debug_string_buffer.append( " | " + FkStringFeld.getFeldRechtsMin( pWert, 25 ) );
    }
    else
    {
      m_debug_string_buffer.append( " " ); // neue zeile
    }

    addElementTextWert( pIdDruckzeile, pIdText, pIdStyle, druck_text, pWert );
  }

  /**
   * <pre>
   * Gibt alle benutzten Resourcen der Instanz frei.
   * </pre>
   */
  private void clear()
  {
    /*
     * Aufruf der "clear"-Funktion im Elementstruktur-Vektor
     */
    if ( m_element_vector != null )
    {
      m_element_vector.clear();
    }

    /*
     * Aufruf der "clear"-Funktion im Elementstyle-Vektor
     */
    if ( m_element_styles != null )
    {
      m_element_styles.clear();
    }

    /*
     * Aufruf der "clear"-Funktion im Debug-Stringbuffer
     */
    if ( m_debug_string_buffer != null )
    {
      m_debug_string_buffer.clear();
    }

    /*
     * Aufruf der "clear"-Funktion in den Properties fuer die Texte
     */
    if ( m_prop_texte != null )
    {
      m_prop_texte.clear();
    }

    /*
     * Alle Instanzen auf "null" setzen
     */
    m_element_styles = null;
    m_element_vector = null;
    m_debug_string_buffer = null;
    m_prop_texte = null;
    m_zahlen_formatierer = null;
  }

  private ElementStyles getElementStyles()
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
      akt_element_style.setIdName( "css_ergebnis_detail_einzug" );
      akt_element_style.setMarginLeft( 10 );

      m_element_styles.add( akt_element_style );

      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( "css_ergebnis_detail_einzug_h2" );
      akt_element_style.setMarginTop( 5 );
      akt_element_style.setMarginLeft( 10 );
      akt_element_style.setFontSize( 11 );
      akt_element_style.setFontWeight( "bold" );

      m_element_styles.add( akt_element_style );

      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( "css_ergebnis_detail_einzug_h3" );
      akt_element_style.setMarginTop( 0 );
      akt_element_style.setMarginLeft( 10 );
      akt_element_style.setFontSize( 11 );
      akt_element_style.setFontWeight( "bold" );

      m_element_styles.add( akt_element_style );

      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( "css_ergebnis_praesentation_einzug" );
      akt_element_style.setMarginLeft( 20 );

      m_element_styles.add( akt_element_style );

      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( "css_ergebnis_praesentation_einzug_h2" );
      akt_element_style.setMarginTop( 5 );
      akt_element_style.setMarginLeft( 20 );
      akt_element_style.setFontSize( 11 );
      akt_element_style.setFontWeight( "bold" );

      m_element_styles.add( akt_element_style );

      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( "css_ergebnis_praesentation_einzug_h3" );
      akt_element_style.setMarginTop( 0 );
      akt_element_style.setMarginLeft( 20 );
      akt_element_style.setFontSize( 11 );
      akt_element_style.setFontWeight( "bold" );

      m_element_styles.add( akt_element_style );

      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( "h1" );
      akt_element_style.setFontSize( 14 );
      akt_element_style.setFontWeight( "bold" );

      m_element_styles.add( akt_element_style );

      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( "css_h1_margin_top" );
      akt_element_style.setMarginTop( 5 );
      akt_element_style.setFontSize( 14 );
      akt_element_style.setFontWeight( "bold" );

      m_element_styles.add( akt_element_style );

      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( "css_h1_normal" );
      akt_element_style.setFontSize( 14 );
      akt_element_style.setFontWeight( "bold" );

      m_element_styles.add( akt_element_style );

      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( "css_h1_border" );
      akt_element_style.setMarginTop( 5 );
      akt_element_style.setMarginBottom( 5 );
      akt_element_style.setFontSize( 14 );
      akt_element_style.setBorder( "1" );

      akt_element_style.setFontWeight( "bold" );
      akt_element_style.setTextAlign( "center" );

      m_element_styles.add( akt_element_style );

      akt_element_style = new ElementStyle();
      akt_element_style.setIdName( "css_h1_center" );
      akt_element_style.setMarginTop( 5 );
      akt_element_style.setMarginBottom( 5 );
      akt_element_style.setFontSize( 14 );

      akt_element_style.setFontWeight( "bold" );
      akt_element_style.setTextAlign( "center" );

      m_element_styles.add( akt_element_style );

      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( "css_normal" );
      akt_element_style.setMarginTop( 0 );
      akt_element_style.setMarginLeft( 0 );
      akt_element_style.setFontName( "HELVETICA" );
      akt_element_style.setFontSize( 12 );
      akt_element_style.setFontWeight( "normal" );
      akt_element_style.setFontColor( Color.BLACK );

      akt_element_style.setSpaceBefore( 0.0f );
      akt_element_style.setSpaceAfter( 0.0f );
      akt_element_style.setIndentation( 0.0f );

      m_element_styles.add( akt_element_style );

      akt_element_style = new ElementStyle();

      akt_element_style.setIdName( "css_hinweis" );
      akt_element_style.setMarginTop( 10 );
      akt_element_style.setFontSize( 9 );

      m_element_styles.add( akt_element_style );

      /*
       *######################################################
       */
    }

    return m_element_styles;
  }

  /**
   * <pre>
   * Gibt den Vektor fuer die Speicherung von "Element" zurueck
   * Ist der Vektor noch nicht vorhanden, wird dieser erstellt.
   * </pre>
   *
   * @return "m_element_vector"
   */
  private ElementVector getElement()
  {
    if ( m_element_vector == null )
    {
      m_element_vector = new ElementVector();
    }

    return m_element_vector;
  }

  private Element addElementTextWert( String pIdDruckzeile, String pIdText, String pIdStyle, String pText, String pWert )
  {
    // http://stackoverflow.com/questions/4016671/how-to-parse-a-string-that-is-in-a-different-encoding-from-java
    // http://stackoverflow.com/questions/4022240/java-convert-utf8-string-to-byte-array-in-another-encoding

    Element inst_element = getElement().addElement();

    if ( inst_element != null )
    {
      ElementTextWert inst_element_text_wert = null;

      inst_element_text_wert = new ElementTextWert();

      inst_element_text_wert.setIdElement( pIdDruckzeile );
      inst_element_text_wert.setIdText( pIdText );

      inst_element_text_wert.setText( FkStringFeld.replaceSpecialCharacters( pText ) );

      inst_element_text_wert.setWert( pWert );

      inst_element.setIdElement( pIdDruckzeile );
      inst_element.setIdStyle( pIdStyle );
      inst_element.setElementTextWert( inst_element_text_wert );
    }

    return inst_element;
  }

  private Element addElementCsvZeile( String pIdDruckzeile, String pIdText, String pIdStyle, String pText, String pCsvAusrichtung )
  {
    Element inst_element = getElement().addElement();

    if ( inst_element != null )
    {
      ElementCsvZeile inst_element_csv_zeile = null;

      inst_element_csv_zeile = new ElementCsvZeile();

      inst_element_csv_zeile.setIdElement( pIdDruckzeile );
      inst_element_csv_zeile.setIdText( pIdText );

      inst_element_csv_zeile.setCsvStringWerte( FkStringFeld.replaceSpecialCharacters( pText ) );
      inst_element_csv_zeile.setCsvAusrichtung( pCsvAusrichtung );

      inst_element.setIdElement( pIdDruckzeile );
      inst_element.setIdStyle( pIdStyle );
      inst_element.setElementCsvZeile( inst_element_csv_zeile );

    }

    return inst_element;
  }

  private Element addElementNeueSeite( String pIdDruckzeile, String pIdStyle, String pSeitenBezeichnung )
  {
    Element inst_element = getElement().addElement();

    if ( inst_element != null )
    {
      ElementNeueSeite inst_element_neue_seite = null;

      inst_element_neue_seite = new ElementNeueSeite();

      inst_element_neue_seite.setIdElement( pIdDruckzeile );
      inst_element_neue_seite.setSeitenBezeichnung( FkStringFeld.replaceSpecialCharacters( pSeitenBezeichnung ) );

      inst_element.setIdElement( pIdDruckzeile );
      inst_element.setIdStyle( pIdStyle );
      inst_element.setElementNeueSeite( inst_element_neue_seite );
    }

    return inst_element;
  }

  /**
   * @return einen Debugstring mit den Inhalten aus dem Vektor
   */
  private String getDebugStringVektor()
  {
    StringBuffer ergebnis_str = new StringBuffer();

    if ( m_element_vector != null )
    {
      ElementTextWert inst_element_text_wert = null;
      Element inst_element = null;
      int akt_index = 0;

      inst_element = Element.getClassCast( m_element_vector.getIndexElement( akt_index ) );

      while ( inst_element != null )
      {
        ergebnis_str.append( "\n" );
        ergebnis_str.append( "( " + akt_index + " ) " );
        ergebnis_str.append( inst_element.toStringZeile() );

        if ( inst_element.istArtTextWert() )
        {
          inst_element_text_wert = inst_element.getElementTextWert();

          if ( inst_element_text_wert != null )
          {
            ergebnis_str.append( "\n" );
            ergebnis_str.append( "inst_element_text_wert " );
            ergebnis_str.append( inst_element_text_wert.toStringZeile() );
            ergebnis_str.append( "\n" );
          }
        }

        akt_index ++;
        inst_element = Element.getClassCast( m_element_vector.getIndexElement( akt_index ) );
      }
    }
    else
    {
      ergebnis_str.append( "\n---- Keine Elemente im Vektor vorhanden ----" );
    }

    return ergebnis_str.toString();
  }

  private void addElementStyle( String pIdDruckzeile, String pIdText, String pIdStyle, String pWert )
  {
    if ( m_debug_string_buffer == null )
    {
      m_debug_string_buffer = new NewLineStringBuffer();
    }

    String druck_text = getPropTexte().getProperty( pIdText, "" );

    m_debug_string_buffer.appendI( FkStringFeld.getFeldLinksMin( pIdDruckzeile, 35 ) + " | " );
    m_debug_string_buffer.appendI( FkStringFeld.getFeldLinksMin( pIdText, 35 ) + " | " );
    m_debug_string_buffer.appendI( FkStringFeld.getFeldLinksMin( druck_text, 60 ) );

    if ( (pWert != null) && (pWert.trim().length() > 0) )
    {
      m_debug_string_buffer.append( " | " + FkStringFeld.getFeldRechtsMin( pWert, 25 ) );
    }
    else
    {
      m_debug_string_buffer.append( " " ); // neue zeile
    }

    addElementTextWert( pIdDruckzeile, pIdText, pIdStyle, druck_text, pWert );
  }

  private void addElementStyleText( String pIdDruckzeile, String pText, String pIdStyle, String pWert )
  {
    if ( m_debug_string_buffer == null )
    {
      m_debug_string_buffer = new NewLineStringBuffer();
    }

    String druck_text = pText;

    m_debug_string_buffer.appendI( FkStringFeld.getFeldLinksMin( pIdDruckzeile, 35 ) + " | " );
    m_debug_string_buffer.appendI( FkStringFeld.getFeldLinksMin( "--", 35 ) + " | " );
    m_debug_string_buffer.appendI( FkStringFeld.getFeldLinksMin( druck_text, 60 ) );

    if ( (pWert != null) && (pWert.trim().length() > 0) )
    {
      m_debug_string_buffer.append( " | " + FkStringFeld.getFeldRechtsMin( pWert, 25 ) );
    }
    else
    {
      m_debug_string_buffer.append( " " ); // neue zeile
    }

    addElementTextWert( pIdDruckzeile, pText, pIdStyle, druck_text, pWert );
  }

  private void addStyleText( String pIdDruckzeile, String pIdText, String pIdStyle, String pText, String pWert )
  {
    if ( m_debug_string_buffer == null )
    {
      m_debug_string_buffer = new NewLineStringBuffer();
    }

    m_debug_string_buffer.appendI( FkStringFeld.getFeldLinksMin( pIdDruckzeile, 35 ) + " | " );
    m_debug_string_buffer.appendI( FkStringFeld.getFeldLinksMin( pIdText, 35 ) + " | " );
    m_debug_string_buffer.appendI( FkStringFeld.getFeldLinksMin( pText, 60 ) );

    if ( (pWert != null) && (pWert.trim().length() > 0) )
    {
      m_debug_string_buffer.append( " | " + FkStringFeld.getFeldRechtsMin( pWert, 25 ) );
    }
    else
    {
      m_debug_string_buffer.append( " " ); // neue zeile
    }

    addElementTextWert( pIdDruckzeile, pIdText, pIdStyle, pText, pWert );
  }

  /**
   *
   */
  private void addElementStyle( String pIdDruckzeile, String pIdText, String pIdStyle, String pTextParameter1, String pTextParameter2, String pTextParameter3, String pWert )
  {
    if ( m_debug_string_buffer == null )
    {
      m_debug_string_buffer = new NewLineStringBuffer();
    }

    String druck_text = getPropTexte().getProperty( pIdText, "" );

    if ( pTextParameter1 != null )
    {
      druck_text = FkStringFeld.replaceSubstring( druck_text, "{0}", pTextParameter1 );
    }

    if ( pTextParameter2 != null )
    {
      druck_text = FkStringFeld.replaceSubstring( druck_text, "{1}", pTextParameter2 );
    }

    if ( pTextParameter3 != null )
    {
      druck_text = FkStringFeld.replaceSubstring( druck_text, "{2}", pTextParameter3 );
    }

    m_debug_string_buffer.appendI( FkStringFeld.getFeldLinksMin( pIdDruckzeile, 35 ) + " | " );
    m_debug_string_buffer.appendI( FkStringFeld.getFeldLinksMin( pIdText, 35 ) + " | " );
    m_debug_string_buffer.appendI( FkStringFeld.getFeldLinksMin( druck_text, 60 ) );

    if ( (pWert != null) && (pWert.trim().length() > 0) )
    {
      m_debug_string_buffer.append( " | " + FkStringFeld.getFeldRechtsMin( pWert, 25 ) );
    }
    else
    {
      m_debug_string_buffer.append( " " ); // neue zeile
    }

    addElementTextWert( pIdDruckzeile, pIdText, pIdStyle, druck_text, pWert );
  }

  /**
   *
   */
  private void add( String pString1 )
  {
    if ( m_debug_string_buffer == null )
    {
      m_debug_string_buffer = new NewLineStringBuffer();
    }

    m_debug_string_buffer.append( "\n\n" + pString1 + "\n" );
  }

// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
  private static String TEXT_USER_ID_EXTERN = "User Id Extern";

  private static String TEXT_USER_ID_NR = "User Id Nr";

  private static String TEXT_USER_ID = "User Id";

  private static String TEXT_ANREDE = "Anrede";

  private static String TEXT_VORNAME = "Vorname";

  private static String TEXT_NACHNAME = "Nachname";

  private static String TEXT_STRASSE = "Strasse";

  private static String TEXT_PLZ = "Plz";

  private static String TEXT_ORT = "Ort";

  private static String TEXT_EMAIL = "Email";

  private static String TEXT_TELEFONNUMMER = "Telefonnummer";

  private static String TEXT_FAXNUMMER = "Faxnummer";

  private static String TEXT_PAGINIERNR = "Paginiernr";
  private static String TEXT_DATUMEINGANG = "Datumeingang";
  private static String TEXT_STAMMNR = "Stammnr";
  private static String TEXT_VERTRAGNUMMER = "Vertragnummer";
  private static String TEXT_VORGANGTYPNR = "Vorgangtypnr";

  private static String TEXT_DATUM_ERSTELLLT = "Datum erstellt";
  
  private static String TEXT_UEBERSCHRIFT_KUNDENDATEN = "Kundendaten";

  private static String TEXT_UEBERSCHRIFT_VORGANGSDATEN = "Vorgangsdaten";

  /**
   * Eine Konstante von BigDecimal mit "0" als Wert ( ohne definierte
   * Nachkommastellen )
   */
  private static BigDecimal BIG_DECIMAL_0 = new BigDecimal( "0" );

  private Properties getPropTexte()
  {
    if ( m_prop_texte == null )
    {
      m_prop_texte = new Properties();

      m_prop_texte.setProperty( "TEXT_USER_ID_EXTERN", TEXT_USER_ID_EXTERN );
      m_prop_texte.setProperty( "TEXT_USER_ID_NR", TEXT_USER_ID_NR );
      m_prop_texte.setProperty( "TEXT_USER_ID", TEXT_USER_ID );
      m_prop_texte.setProperty( "TEXT_ANREDE", TEXT_ANREDE );
      m_prop_texte.setProperty( "TEXT_VORNAME", TEXT_VORNAME );
      m_prop_texte.setProperty( "TEXT_NACHNAME", TEXT_NACHNAME );
      m_prop_texte.setProperty( "TEXT_STRASSE", TEXT_STRASSE );
      m_prop_texte.setProperty( "TEXT_PLZ", TEXT_PLZ );
      m_prop_texte.setProperty( "TEXT_ORT", TEXT_ORT );
      m_prop_texte.setProperty( "TEXT_EMAIL", TEXT_EMAIL );
      m_prop_texte.setProperty( "TEXT_TELEFONNUMMER", TEXT_TELEFONNUMMER );
      m_prop_texte.setProperty( "TEXT_FAXNUMMER", TEXT_FAXNUMMER );

      m_prop_texte.setProperty( "TEXT_PAGINIERNR", TEXT_PAGINIERNR );
      m_prop_texte.setProperty( "TEXT_DATUMEINGANG", TEXT_DATUMEINGANG );
      m_prop_texte.setProperty( "TEXT_STAMMNR", TEXT_STAMMNR );
      m_prop_texte.setProperty( "TEXT_VERTRAGNUMMER", TEXT_VERTRAGNUMMER );
      m_prop_texte.setProperty( "TEXT_VORGANGTYPNR", TEXT_VORGANGTYPNR );
      m_prop_texte.setProperty( "TEXT_DATUM_ERSTELLLT", TEXT_DATUM_ERSTELLLT );

      m_prop_texte.setProperty( "TEXT_UEBERSCHRIFT_VORGANGSDATEN", TEXT_UEBERSCHRIFT_VORGANGSDATEN );
      m_prop_texte.setProperty( "TEXT_UEBERSCHRIFT_KUNDENDATEN", TEXT_UEBERSCHRIFT_KUNDENDATEN );
    }

    return m_prop_texte;
  }


  private void getBausteinKunde( Kunde pKunde )
  {
    /*
     * Deklaration der benoeigten lokalen Variablen
     * 
     * WICHTIG:
     * Die Druckbausteines-ID ist nur fuer Debugzwecke vorhanden.
     * 
     * Die Druckbaustein-ID ist keine Steuervariable fuer Aktionen
     * innerhalb des Dokumenterstellungsprozesses.
     */
    String id_druck_zeile = null; // ID des Bausteines fuer Debug-Zwecke
    String id_text_baustein = null; // ID des zu verwendenden Textes aus den Properties
    String id_style = null; // ID des zu verwendenden Styles 
    String text_spalte_2 = null; // speichert den Ausgabestring der 2ten Spalte

    id_druck_zeile = "D1_DATEN_KUNDE";
    id_text_baustein = "TEXT_UEBERSCHRIFT_KUNDENDATEN";
    text_spalte_2 = null;

    addStyle( id_druck_zeile, id_text_baustein, CSS_BAUSTEINUEBERSCHRIFT, text_spalte_2 );

    //--------------------------------------------------------------------------------
    id_druck_zeile = "D1_DATEN_USER_ID";
    id_text_baustein = "TEXT_USER_ID";
    id_style = "css_normal";
    text_spalte_2 = "" + pKunde.getId();

    addElementStyle( id_druck_zeile, id_text_baustein, id_style, text_spalte_2 );

    //--------------------------------------------------------------------------------
    id_druck_zeile = "D1_DATEN_STAMM_NR";
    id_text_baustein = "TEXT_STAMMNR";
    id_style = "css_normal";
    text_spalte_2 = "" + pKunde.getStammNummer();

    addElementStyle( id_druck_zeile, id_text_baustein, id_style, text_spalte_2 );

    //--------------------------------------------------------------------------------
    id_druck_zeile = "D1_DATEN_ANREDE";
    id_text_baustein = "TEXT_ANREDE";
    id_style = "css_normal";
    text_spalte_2 = pKunde.getAnrede();

    addElementStyle( id_druck_zeile, id_text_baustein, id_style, text_spalte_2 );

    //--------------------------------------------------------------------------------
    id_druck_zeile = "D1_DATEN_VORNAME";
    id_text_baustein = "TEXT_VORNAME";
    id_style = "css_normal";
    text_spalte_2 = pKunde.getVorName();

    addElementStyle( id_druck_zeile, id_text_baustein, id_style, text_spalte_2 );

    //--------------------------------------------------------------------------------
    id_druck_zeile = "D1_DATEN_NACHNAME";
    id_text_baustein = "TEXT_NACHNAME";
    id_style = "css_normal";
    text_spalte_2 = pKunde.getNachName();

    addElementStyle( id_druck_zeile, id_text_baustein, id_style, text_spalte_2 );

    //--------------------------------------------------------------------------------
    id_druck_zeile = "D1_DATEN_EMAIL";
    id_text_baustein = "TEXT_EMAIL";
    id_style = "css_normal";
    text_spalte_2 = pKunde.getEmail();

    addElementStyle( id_druck_zeile, id_text_baustein, id_style, text_spalte_2 );

    //--------------------------------------------------------------------------------
    id_druck_zeile = "D1_DATEN_STRASSE";
    id_text_baustein = "TEXT_STRASSE";
    id_style = "css_normal";
    text_spalte_2 = pKunde.getAddresse().getStrasse();

    addElementStyle( id_druck_zeile, id_text_baustein, id_style, text_spalte_2 );

    //--------------------------------------------------------------------------------
    id_druck_zeile = "D1_DATEN_PLZ";
    id_text_baustein = "TEXT_PLZ";
    id_style = "css_normal";
    text_spalte_2 = pKunde.getAddresse().getPlzOrt();

    addElementStyle( id_druck_zeile, id_text_baustein, id_style, text_spalte_2 );

    //--------------------------------------------------------------------------------
    id_druck_zeile = "D1_DATEN_FREITEXT";

    String text = "\n\n" + ("Test Freitext für Kunde " + pKunde.getVorUndNachname() + ".\n").repeat( 5 );

    addElementStyleText( id_druck_zeile, text, id_style, null );
  }

  private void getBausteinVorgang( Vorgang pVorgang )
  {
    /*
     * Deklaration der benoeigten lokalen Variablen
     * 
     * WICHTIG:
     * Die Druckbausteines-ID ist nur fuer Debugzwecke vorhanden.
     * 
     * Die Druckbaustein-ID ist keine Steuervariable fuer Aktionen
     * innerhalb des Dokumenterstellungsprozesses.
     */
    String id_druck_zeile = null; // ID des Bausteines fuer Debug-Zwecke
    String id_text_baustein = null; // ID des zu verwendenden Textes aus den Properties
    String id_style = null; // ID des zu verwendenden Styles 
    String text_spalte_2 = null; // speichert den Ausgabestring der 2ten Spalte

    id_druck_zeile = "D2_DATEN_VORGANG";
    id_text_baustein = "TEXT_UEBERSCHRIFT_VORGANGSDATEN";
    text_spalte_2 = null;

    addStyle( id_druck_zeile, id_text_baustein, CSS_BAUSTEINUEBERSCHRIFT, text_spalte_2 );

    //--------------------------------------------------------------------------------
    id_druck_zeile = "D2_DATEN_PAGINIERNR";
    id_text_baustein = "TEXT_PAGINIERNR";
    id_style = "css_normal";
    text_spalte_2 = pVorgang.getPaginierNr();

    addElementStyle( id_druck_zeile, id_text_baustein, id_style, text_spalte_2 );

    //--------------------------------------------------------------------------------
    id_druck_zeile = "D2_DATEN_DATUMEINGANG";
    id_text_baustein = "TEXT_DATUMEINGANG";
    id_style = "css_normal";
    text_spalte_2 = FkDatum.getString( pVorgang.getDatumEingang() );

    addElementStyle( id_druck_zeile, id_text_baustein, id_style, text_spalte_2 );

    //--------------------------------------------------------------------------------
    id_druck_zeile = "D2_DATEN_STAMMNR";
    id_text_baustein = "TEXT_STAMMNR";
    id_style = "css_normal";
    text_spalte_2 = pVorgang.getStammNr();

    addElementStyle( id_druck_zeile, id_text_baustein, id_style, text_spalte_2 );

    //--------------------------------------------------------------------------------
    id_druck_zeile = "D2_DATEN_VERTRAGNUMMER";
    id_text_baustein = "TEXT_VERTRAGNUMMER";
    id_style = "css_normal";
    text_spalte_2 = pVorgang.getVertragNummer();

    addElementStyle( id_druck_zeile, id_text_baustein, id_style, text_spalte_2 );

    //--------------------------------------------------------------------------------
    id_druck_zeile = "D2_DATEN_VORGANGTYPNR";
    id_text_baustein = "TEXT_VORGANGTYPNR";
    id_style = "css_normal";
    text_spalte_2 = pVorgang.getVorgangTypNr();

    addElementStyle( id_druck_zeile, id_text_baustein, id_style, text_spalte_2 );
    
    
    id_druck_zeile = "D2_LEERZEILE";
    
    String text = "\n";

    addElementStyleText( id_druck_zeile, text, id_style, null );

    //--------------------------------------------------------------------------------
    id_druck_zeile = "D2_DATUM_ERSTELLLT";
    id_text_baustein = "TEXT_DATUM_ERSTELLLT";
    id_style = "css_normal";
    text_spalte_2 = FkDatum.getWochentagDatumUndZeit();

    addElementStyle( id_druck_zeile, id_text_baustein, id_style, text_spalte_2 );
  }

  /**
   * Ausgabe auf System.out
   *
   * @param pString der auszugebende String
   */
  private static void wl( String pString )
  {
    //System.out.println( pString );
  }

}
