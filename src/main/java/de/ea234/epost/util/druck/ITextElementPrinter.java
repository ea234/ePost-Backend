package de.ea234.epost.util.druck;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.regex.Pattern;

import org.openpdf.text.Document;
import org.openpdf.text.DocumentException;
import org.openpdf.text.Font;
import org.openpdf.text.Image;
import org.openpdf.text.PageSize;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.pdf.ColumnText;
import org.openpdf.text.pdf.PdfContentByte;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfReader;
import org.openpdf.text.pdf.PdfStamper;
import org.openpdf.text.pdf.PdfWriter;

class ITextElementPrinter
{
  private static final Color                 FARBE_TEXT_FEST_DEFINIERTER_FONT = Color.BLACK;

  private static final Color                 FARBE_TEXT_FEST_CSS_VORGABE_FONT = Color.BLACK;

  private static final Color                 FARBE_LINIE_GRAU_35_PROZENT      = new Color( 166, 166, 166 );

  private static final Color                 FARBE_LINIE_FUSSZEILE            = new Color( 0.35f, 0.35f, 0.35f );

  private static final org.openpdf.text.Font FONT_TEXT_FUSSZEILE              = new Font( Font.HELVETICA, 8, Font.NORMAL, FARBE_TEXT_FEST_DEFINIERTER_FONT );

  private static final Font                  FONT_TEXT_CSV_TABELLE_H2         = new Font( Font.HELVETICA, 12, Font.BOLD, FARBE_TEXT_FEST_DEFINIERTER_FONT );

  private static final Font                  FONT_TEXT_CSV_TABELLE_FETT       = new Font( Font.HELVETICA, 9, Font.BOLD, FARBE_TEXT_FEST_DEFINIERTER_FONT );

  private static String                      EURO_SIGN_CHAR                   = "" + 0x20ac;

  private float                              m_page_margin_left               = 2.0f;

  private float                              m_page_margin_right              = 1.5f;

  private float                              m_abschnitts_tabelle_x           = -1;

  private float                              m_abschnitts_tabelle_y           = -1;

  private PdfPTable                          m_abschnitts_tabelle             = null;

  private PdfPTable                          m_text_wert_tabelle              = null;

  private PdfPTable                          m_csv_table                      = null;

  private String                             m_table_name                     = "Tabellenname";

  private boolean                            m_knz_has_table_bezeichnung      = false;

  /** Hash-Speicher fuer die zu speichernden CSS-Objekte */
  private ITextStyleCache                    m_itext_style_cache              = null;

  private float cmToPoint( float pCM )
  {
    return pCM * 28.35f;
  }

  public byte[] createPDF( ElementDokument pElementDokument )
  {
    wl( "ITextElementPrinter.createPDF()" );

    m_page_margin_left = pElementDokument.getPageMarginLeft();

    m_page_margin_right = pElementDokument.getPageMarginRight();

    ElementVector element_vector = pElementDokument.getElementVector();

    ElementStyles element_styles = pElementDokument.getElementStyles();

    ByteArrayOutputStream byte_stream_2 = new ByteArrayOutputStream();

    ElementTextBoxed boxed_text_element = null;

    Document pdf_document = new Document( PageSize.A4, cmToPoint( m_page_margin_left ), cmToPoint( m_page_margin_right ), cmToPoint( pElementDokument.getPageMarginTop() ), cmToPoint( pElementDokument.getPageMarginBottom() ) );

    if ( pElementDokument.getDokumentTitel() != null )
    {
      pdf_document.addTitle( pElementDokument.getDokumentTitel() );
    }

    if ( pElementDokument.getDokumentAuthor() != null )
    {
      pdf_document.addAuthor( pElementDokument.getDokumentAuthor() );

      pdf_document.addCreator( pElementDokument.getDokumentAuthor() );
    }

    pdf_document.addCreationDate();

    try
    {
      ByteArrayOutputStream byte_stream_1 = new ByteArrayOutputStream();

      PdfWriter pdf_writer = PdfWriter.getInstance( pdf_document, byte_stream_1 );

      pdf_document.open();

      ElementSeitenFussleiste e_seiten_fussleiste = null;

      ITextStyle style_fussleiste = null;

      ITextStyle style_standard_brief = getITextStyle( "CSS_BRIEF_TEXT_STANDARD", element_styles );

      if ( pElementDokument.getKnzDruckeFalzmarken() )
      {
        printFalzmarken( pdf_writer, ITextStyle.PAGE_ZISE_A4_HEIGHT );
      }

      ITextStyle i_text_style = null;

      int element_anzahl = element_vector.getAnzahl();

      int element_index = 0;

      while ( element_index < element_anzahl )
      {
        Element element = element_vector.getIndexElement( element_index );

        element_index++;

        i_text_style = getITextStyle( element.getIdStyle(), element_styles );

        switch ( element.getArt() )
        {
          case Element.ART_ELEMENT_SEITEN_FUSSLEISTE :

            e_seiten_fussleiste = element.getElementSeitenFussleiste();

            if ( e_seiten_fussleiste != null )
            {
              if ( e_seiten_fussleiste.hatText() )
              {
                style_fussleiste = i_text_style;
              }
              else
              {
                /*
                 * Ist kein Fussleistentext gesetzt, wird der Standardfussleistentext genommen. 
                 */
                style_fussleiste = null;

                e_seiten_fussleiste = null;
              }
            }

            break;

          case Element.ART_ELEMENT_TEXT_WERT :

            endCsvTable( pdf_document );

            /*
             * Text nach einer Tabelle
             * Sicherstellen dass ein SectionTable vorhanden ist
             */
            if ( m_abschnitts_tabelle == null )
            {
              startSection( pdf_document, pdf_writer );
            }

            boxed_text_element = element.getElementTextBoxed();

            if ( boxed_text_element != null )
            {
              m_abschnitts_tabelle_x = boxed_text_element.getPositionCmX();
              m_abschnitts_tabelle_y = boxed_text_element.getPositionCmY();
            }

            printTextValueRow( pdf_document, pdf_writer, element, i_text_style );

            break;

          case Element.ART_ELEMENT_TEXT_BOXED :

            endCsvTable( pdf_document );

            endSection( pdf_document, pdf_writer );

            /*
             *  EA 
             * Text nach einer Tabelle
             * Sicherstellen dass ein SectionTable vorhanden ist
             */
            if ( m_abschnitts_tabelle == null )
            {
              startSection( pdf_document, pdf_writer );
            }

            boxed_text_element = element.getElementTextBoxed();

            if ( boxed_text_element != null )
            {
              m_abschnitts_tabelle_x = boxed_text_element.getPositionCmX();
              m_abschnitts_tabelle_y = boxed_text_element.getPositionCmY();
            }

            printTextValueRow( pdf_document, pdf_writer, element, i_text_style );

            break;

          case Element.ART_ELEMENT_SEITEN_UEBERSCHRIFT :

            printSeitenUeberschrift( pdf_writer, i_text_style, element.getElementSeitenUeberschrift().getSeitenUeberschrift() );

            break;

          case Element.ART_ELEMENT_NEUE_SEITE :

            endSection( pdf_document, pdf_writer );

            pdf_document.newPage();

            if ( element.getElementNeueSeite().getKnzDebugGitternetzDrucken() )
            {
              printGitterNetz( pdf_writer, ITextStyle.PAGE_ZISE_A4_HEIGHT );
            }

            break;

          case Element.ART_ELEMENT_CSV_ZEILE : // CSV-Werte

            endSection( pdf_document, pdf_writer );

            printCsvTableRow( pdf_document, element, i_text_style, pdf_writer.getVerticalPosition( true ) );

            break;
        }
      }

      endCsvTable( pdf_document );

      endSection( pdf_document, pdf_writer );

      pdf_document.close();

      pdf_writer.close();

      /*
       * Abschlussarbeiten
       * Logo und Fusszeile hinzufuegen
       */
      PdfReader pdf_reader = new PdfReader( byte_stream_1.toByteArray() );

      PdfStamper pdf_stamper = new PdfStamper( pdf_reader, byte_stream_2 );

      Calendar akt_datum = Calendar.getInstance();

      int monat = akt_datum.get( Calendar.MONTH ) + 1;

      int tag = akt_datum.get( Calendar.DATE );

      String druck_datum = ( tag < 10 ? "0" : "" ) + tag + "." + ( monat < 10 ? "0" : "" ) + monat + "." + akt_datum.get( Calendar.YEAR );

      int anzahl_seiten = pdf_reader.getNumberOfPages();

      int aktuelle_seite = 1;

      while ( aktuelle_seite <= anzahl_seiten )
      {
        PdfContentByte pdf_canvas = pdf_stamper.getUnderContent( aktuelle_seite );

        /*
         * Logo
         */
        Image image_logo = Image.getInstance( Logo.getImage(), null );

        image_logo.scalePercent( 14.0f );

        image_logo.setAbsolutePosition( ITextStyle.PAGE_ZISE_A4_WIDTH - image_logo.getScaledWidth() - cmToPoint( m_page_margin_right ), ITextStyle.PAGE_ZISE_A4_HEIGHT - image_logo.getScaledHeight() - cmToPoint( 1.5f ) );

        pdf_canvas.addImage( image_logo );

        if ( e_seiten_fussleiste != null )
        {
          Font pdf_font = ( style_fussleiste != null ? style_fussleiste.getFont() : FONT_TEXT_FUSSZEILE );

          String text_fuss_zeile = e_seiten_fussleiste.getText() + " ";

          String values[] = text_fuss_zeile.split( Pattern.quote( "|" ) );

          /*
           * Fusszeile
           */
          PdfPTable pdf_table_fusszeile = new PdfPTable( values.length );

          pdf_table_fusszeile.setWidthPercentage( 100 );

          pdf_table_fusszeile.setTotalWidth( ITextStyle.PAGE_ZISE_A4_WIDTH - cmToPoint( m_page_margin_left ) - cmToPoint( m_page_margin_right ) );

          for ( int i = 0; i < values.length; i++ )
          {
            PdfPCell pdf_cell = new PdfPCell( new Phrase( cmToPoint( 1.0f ), values[ i ], pdf_font ) );

            pdf_cell.setHorizontalAlignment( ITextStyle.ALIGN_LEFT );
            pdf_cell.setBorder( ITextStyle.RECTANGLE_BORDER_TOP );
            pdf_cell.setBorderWidthTop( 0 );
            pdf_cell.setBorderWidthLeft( 0 );
            pdf_cell.setBorderWidthRight( 0 );

            pdf_table_fusszeile.addCell( pdf_cell );
          }

          pdf_table_fusszeile.writeSelectedRows( 0, -1, cmToPoint( m_page_margin_left ), cmToPoint( 2.0f ), pdf_canvas );
        }
        else
        {
          /*
           * Fusszeile
           */
          PdfPTable pdf_table_fusszeile = new PdfPTable( 3 );

          pdf_table_fusszeile.setWidthPercentage( 100 );
          pdf_table_fusszeile.setTotalWidth( ITextStyle.PAGE_ZISE_A4_WIDTH - cmToPoint( m_page_margin_left ) - cmToPoint( m_page_margin_right ) );

          PdfPCell pdf_cell_name_druckstueck = new PdfPCell( new Paragraph( 0, pElementDokument.getNameDruckstueck(), FONT_TEXT_FUSSZEILE ) );

          pdf_cell_name_druckstueck.setBorder( ITextStyle.RECTANGLE_BORDER_TOP );
          pdf_cell_name_druckstueck.setBorderColor( FARBE_LINIE_FUSSZEILE );
          pdf_cell_name_druckstueck.setBorderWidthTop( 0.75f );

          pdf_table_fusszeile.addCell( pdf_cell_name_druckstueck );

          PdfPCell pdf_cell_seite_nr = new PdfPCell( new Paragraph( 0, "Seite " + aktuelle_seite + " von " + anzahl_seiten, FONT_TEXT_FUSSZEILE ) );

          pdf_cell_seite_nr.setHorizontalAlignment( ITextStyle.ALIGN_CENTER );
          pdf_cell_seite_nr.setBorder( ITextStyle.RECTANGLE_BORDER_TOP );
          pdf_cell_seite_nr.setBorderColor( FARBE_LINIE_FUSSZEILE );
          pdf_cell_seite_nr.setBorderWidthTop( 0.75f );

          pdf_table_fusszeile.addCell( pdf_cell_seite_nr );

          PdfPCell pdf_cell_druck_datum = new PdfPCell( new Paragraph( 0, druck_datum, FONT_TEXT_FUSSZEILE ) );

          pdf_cell_druck_datum.setHorizontalAlignment( ITextStyle.ALIGN_RIGHT );
          pdf_cell_druck_datum.setBorder( ITextStyle.RECTANGLE_BORDER_TOP );
          pdf_cell_druck_datum.setBorderColor( FARBE_LINIE_FUSSZEILE );
          pdf_cell_druck_datum.setBorderWidthTop( 0.75f );
          pdf_table_fusszeile.addCell( pdf_cell_druck_datum );

          pdf_table_fusszeile.writeSelectedRows( 0, -1, cmToPoint( m_page_margin_left ), cmToPoint( 2.0f ), pdf_canvas );
        }

        aktuelle_seite++;
      }

      pdf_stamper.close();
      pdf_reader.close();
    }
    catch ( Exception err_inst )
    {
      err_inst.printStackTrace( System.out );
    }

    clearItextStyleCache();

    return byte_stream_2.toByteArray();
  }

  private void printSeitenUeberschrift( PdfWriter pPdfWriter, ITextStyle pITextStyle, String pTextSeitenueberschrift )
  {
    String array_temp_titel_zeilen[] = null;

    /*
     * Titelzeilen
     * Die Titelzeilen kommen als "\n" getrennte Zeilen in einem String.
     * Die einzelnen Zeilen werden in einem Array gespeichert. 
     * Das Zeilenumbruchszeichen wird geloescht.
     * Es sind maximal 2 Zeilen moeglich.
     */

    if ( pTextSeitenueberschrift != null )
    {
      array_temp_titel_zeilen = pTextSeitenueberschrift.split( "\n" );
    }

    if ( array_temp_titel_zeilen != null )
    {
      PdfContentByte pdf_content_byte = pPdfWriter.getDirectContent();

      float position_x = cmToPoint( m_page_margin_left );

      float position_y = ITextStyle.PAGE_ZISE_A4_HEIGHT - ( array_temp_titel_zeilen.length > 1 ? cmToPoint( 2.1f ) : cmToPoint( 2.3f ) );

      int akt_index = 0;

      int anzahl_zeilen_gedruckt = 0;

      while ( ( akt_index < array_temp_titel_zeilen.length ) && ( anzahl_zeilen_gedruckt < 2 ) )
      {
        String text_zeile = array_temp_titel_zeilen[ akt_index ].trim();

        if ( text_zeile.length() > 0 )
        {
          ColumnText.showTextAligned( pdf_content_byte, pITextStyle.getTextAlign(), new Paragraph( text_zeile, pITextStyle.getFont() ), position_x, position_y, 0 );

          position_y -= cmToPoint( 0.6f );

          anzahl_zeilen_gedruckt++;
        }

        akt_index++;
      }
    }
  }

  private void startSection( Document document, PdfWriter pdf_writer ) throws DocumentException
  {
    endSection( document, pdf_writer );

    m_abschnitts_tabelle = new PdfPTable( new float[] { 100 } );

    m_abschnitts_tabelle.setWidthPercentage( 100 );

    m_abschnitts_tabelle.setSpacingAfter( cmToPoint( 0.7f ) );

    m_text_wert_tabelle = new PdfPTable( new float[] { 70, 30 } );

    m_text_wert_tabelle.setWidthPercentage( 100 );
  }

  private void endSection( Document document, PdfWriter pdf_writer ) throws DocumentException
  {
    if ( m_abschnitts_tabelle != null )
    {
      PdfPCell pdf_cell_mit_text_wert_tabelle = new PdfPCell();

      pdf_cell_mit_text_wert_tabelle.setBorder( 0 );

      pdf_cell_mit_text_wert_tabelle.setPadding( 0 );

      pdf_cell_mit_text_wert_tabelle.addElement( m_text_wert_tabelle );

      m_abschnitts_tabelle.addCell( pdf_cell_mit_text_wert_tabelle );

      if ( ( m_abschnitts_tabelle_x == -1 ) && ( m_abschnitts_tabelle_y == -1 ) )
      {
        document.add( m_abschnitts_tabelle );
      }
      else
      {
        m_abschnitts_tabelle.setTotalWidth( cmToPoint( 10 ) );

        m_abschnitts_tabelle.getDefaultCell().setBorder( ITextStyle.RECTANGLE_BORDER_LEFT | ITextStyle.RECTANGLE_BORDER_RIGHT | ITextStyle.RECTANGLE_BORDER_TOP | ITextStyle.RECTANGLE_BORDER_BOTTOM );

        m_abschnitts_tabelle.getDefaultCell().setBorderWidth( 0.2f );

        m_abschnitts_tabelle.writeSelectedRows( 0, -1, cmToPoint( m_abschnitts_tabelle_x ), ITextStyle.PAGE_ZISE_A4_HEIGHT - cmToPoint( m_abschnitts_tabelle_y ), pdf_writer.getDirectContent() );

        m_abschnitts_tabelle_x = -1;

        m_abschnitts_tabelle_y = -1;
      }

      m_abschnitts_tabelle = null;
    }
  }

  private void printTextValueRow( Document pDocument, PdfWriter pdf_writer, Element pElement, ITextStyle pITextStyle ) throws DocumentException
  {
    System.out.println( "de.ea234.epost.util.druck.Element.getElementTextWert() " + pElement.getIdElement() );
    String element_style = pElement.getIdStyle();

    String element_text = null;

    String element_wert = null;

    if ( pElement.istArtTextBoxed() )
    {
      element_text = pElement.getElementTextBoxed().getText();
      element_wert = pElement.getElementTextBoxed().getWert();
    }
    else
    {
      element_text = pElement.getElementTextWert().getText();
      element_wert = pElement.getElementTextWert().getWert();
    }

//    if ( "h2".equalsIgnoreCase( element_style ) )
//    {
//      m_table_name = pElement.getElementTextWert().getText();
//
//      return;
//    }

    if ( "h2".equalsIgnoreCase( element_style ) )
    {
      startSection( pDocument, pdf_writer );
    }

    float space_before = cmToPoint( pITextStyle.getSpaceBefore() );

    float space_after = cmToPoint( pITextStyle.getSpaceAfter() );

    boolean knz_mit_umrandung = "h2".equalsIgnoreCase( pITextStyle.getStyleName() );

    float space_leading_before = pITextStyle.getFont().getCalculatedSize() * ( knz_mit_umrandung ? 1.2f : 1.3f ) + ( space_before > 0 ? space_before : 0 );

    Paragraph pdf_paragraph = new Paragraph( element_text, pITextStyle.getFont() );

    pdf_paragraph.setLeading( space_leading_before );
    pdf_paragraph.setSpacingAfter( space_after );
    pdf_paragraph.setIndentationLeft( cmToPoint( pITextStyle.getIndentation() ) );
    pdf_paragraph.setAlignment( pITextStyle.getTextAlign() );

    PdfPCell pdf_cell = new PdfPCell();

    pdf_cell.setPadding( 0 );

    pdf_cell.setColspan( element_wert == null ? 2 : 1 );

    if ( knz_mit_umrandung )
    {
      pdf_cell.setBorder( ITextStyle.RECTANGLE_BORDER_TOP | ITextStyle.RECTANGLE_BORDER_BOTTOM );
      pdf_cell.setBorderColor( FARBE_LINIE_GRAU_35_PROZENT );
      pdf_cell.setBorderWidthTop( 1.5f );
      pdf_cell.setBorderWidthBottom( 0.75f );
    }
    else
    {
      pdf_cell.setBorder( 0 );
    }

    pdf_cell.addElement( pdf_paragraph );

    m_text_wert_tabelle.addCell( pdf_cell );

    if ( element_wert != null )
    {
      String value = element_wert.replaceAll( " EUR", " €" );

      pdf_paragraph = new Paragraph( space_leading_before, value, pITextStyle.getFont() );

      pdf_paragraph.setSpacingAfter( space_after );
      pdf_paragraph.setAlignment( ITextStyle.ALIGN_RIGHT );

      pdf_cell = new PdfPCell();

      pdf_cell.setBorder( 0 );
      pdf_cell.setPadding( 0 );

      if ( knz_mit_umrandung )
      {
        pdf_cell.setBorder( ITextStyle.RECTANGLE_BORDER_TOP | ITextStyle.RECTANGLE_BORDER_BOTTOM );
        pdf_cell.setBorderColor( FARBE_LINIE_GRAU_35_PROZENT );
        pdf_cell.setBorderWidthTop( 1.5f );
        pdf_cell.setBorderWidthBottom( 0.75f );
      }
      else
      {
        pdf_cell.setBorder( 0 );
      }

      /*
       * Die Breite des Eurozeichens wird von iText falsch berechnet
       * Deshalb wird der Wert hier selber berechnet.
       */
      pdf_cell.setPaddingRight( value.indexOf( "€" ) != -1 ? 8.0f : 0 );

      pdf_cell.addElement( pdf_paragraph );

      m_text_wert_tabelle.addCell( pdf_cell );
    }
  }

  private void endCsvTable( Document document ) throws DocumentException
  {
    if ( m_csv_table != null )
    {
      document.add( m_csv_table );

      m_table_name = null;

      m_csv_table = null;

      m_knz_has_table_bezeichnung = false;
    }
  }

  private void printCsvTableRow( Document pPdfDokument, Element pElement, ITextStyle pItextStyle, float pAktuelleCursorPosition ) throws DocumentException
  {
    String idElement = pElement.getIdElement();

    boolean knz_ist_tabellen_kopf = idElement.indexOf( "TAB_KOPF" ) != -1;

    ElementCsvZeile element_csv_zeile = pElement.getElementCsvZeile();

    if ( element_csv_zeile == null )
    {
      throw new DocumentException( "Fehler: PDF.startCsvTable: ElementCsvZeile is null" );
    }

    if ( knz_ist_tabellen_kopf )
    {
      startCsvTable( pPdfDokument, element_csv_zeile, pAktuelleCursorPosition );
    }
    else
    {
      String string = element_csv_zeile.getCsvStringWerte() + " ";

      String array_csv_strings[] = string.split( Pattern.quote( "|" ) );

      int akt_spalte = 0;

      while ( akt_spalte < array_csv_strings.length )
      {
        String cell_value = array_csv_strings[ akt_spalte ].replaceAll( " EUR", "" ).trim();

        PdfPCell pdf_cell = null;

        if ( istNegativ( cell_value ) )
        {
          pdf_cell = new PdfPCell( new Phrase( cmToPoint( 1.0f ), cell_value, pItextStyle.getFontRed() ) );
        }
        else
        {
          pdf_cell = new PdfPCell( new Phrase( cmToPoint( 1.0f ), cell_value, pItextStyle.getFont() ) );
        }

        pdf_cell.setHorizontalAlignment( ( akt_spalte == 0 || ( akt_spalte == 1 && m_knz_has_table_bezeichnung ) ) ? ITextStyle.ALIGN_LEFT : ITextStyle.ALIGN_RIGHT );

        pdf_cell.setBorderWidthTop( 0 );

        pdf_cell.setBorderWidthLeft( 0 );

        pdf_cell.setBorderWidthRight( 0 );

        pdf_cell.setBorderColorBottom( FARBE_LINIE_GRAU_35_PROZENT );

        pdf_cell.setPaddingBottom( cmToPoint( 0.15f ) );

        m_csv_table.addCell( pdf_cell );

        akt_spalte++;
      }
    }
  }

  private static boolean istNegativ( String pString )
  {
    try
    {
      if ( pString != null )
      {
        return Double.parseDouble( getZahl( pString.replaceAll( EURO_SIGN_CHAR, "" ), 4 ) ) < 0.0;
      }
    }
    catch ( Exception err_inst )
    {
      //
    }
    return false;
  }

  private void startCsvTable( Document pPdfDokument, ElementCsvZeile pElementCsvZeile, float pAktuelleCursorPosition ) throws DocumentException
  {
    endCsvTable( pPdfDokument );

    String array_csv_strings[] = pElementCsvZeile.getCsvStringWerte().split( Pattern.quote( "|" ) );

    int anzahl_spalten = array_csv_strings.length;

    float array_spalten[] = new float[ anzahl_spalten ];

    /*
     * Kennzeichen setzen, ob eine Bezeichnung der Tabelle vorhanden ist
     */
    m_knz_has_table_bezeichnung = "bezeichnung".equalsIgnoreCase( array_csv_strings[ 1 ] );

    /*
     * Spaltenbreite ermitteln
     */
    float spalten_breite = 100.0f / ( m_knz_has_table_bezeichnung ? anzahl_spalten + 1.2f : anzahl_spalten );

    for ( int i = 0; i < anzahl_spalten; i++ )
    {
      array_spalten[ i ] = ( i == 1 && m_knz_has_table_bezeichnung ) ? 2.2f * spalten_breite : spalten_breite;
    }

    m_csv_table = new PdfPTable( array_spalten );

    m_csv_table.setWidthPercentage( 100 );

    m_csv_table.setSpacingAfter( cmToPoint( 0.7f ) );

    m_csv_table.setHeaderRows( 2 );

    /*
     * Verbleiben weniger als 10 cm Platz bis zum Seitenende, wird ein Seitenumbruch erzwungen
     */
    float minimumHeight = 3.0f;

    float remainingHeight = pAktuelleCursorPosition / 28.35f; // 1 cm == 28.35pt

    boolean keepTogether = remainingHeight < minimumHeight;

    m_csv_table.setKeepTogether( keepTogether );

    /*
     * Tabellentitel
     */
    if ( m_table_name != null && m_table_name.length() > 0 )
    {
      PdfPCell pdf_cell = new PdfPCell( new Phrase( m_table_name, FONT_TEXT_CSV_TABELLE_H2 ) );

      pdf_cell.setColspan( anzahl_spalten );

      pdf_cell.setHorizontalAlignment( ITextStyle.ALIGN_LEFT );

      pdf_cell.setPaddingBottom( cmToPoint( 0.2f ) );

      pdf_cell.setBorder( ITextStyle.RECTANGLE_BORDER_TOP | ITextStyle.RECTANGLE_BORDER_BOTTOM );

      pdf_cell.setBorderColor( FARBE_LINIE_GRAU_35_PROZENT );

      pdf_cell.setBorderWidthTop( 1.5f );

      pdf_cell.setBorderWidthBottom( 0.75f );

      m_csv_table.addCell( pdf_cell );
    }

    int akt_spalte = 0;

    while ( akt_spalte < anzahl_spalten )
    {
      PdfPCell pdf_cell = new PdfPCell( new Phrase( array_csv_strings[ akt_spalte ].trim(), FONT_TEXT_CSV_TABELLE_FETT ) );

      pdf_cell.setHorizontalAlignment( ( akt_spalte == 0 || ( akt_spalte == 1 && m_knz_has_table_bezeichnung ) ) ? ITextStyle.ALIGN_LEFT : ITextStyle.ALIGN_RIGHT );

      pdf_cell.setVerticalAlignment( ITextStyle.ALIGN_BOTTOM );

      pdf_cell.setBorderWidthTop( 0 );

      pdf_cell.setBorderWidthLeft( 0 );

      pdf_cell.setBorderWidthRight( 0 );

      pdf_cell.setBorderColor( FARBE_LINIE_GRAU_35_PROZENT );

      pdf_cell.setPaddingTop( cmToPoint( 0.15f ) );

      pdf_cell.setPaddingBottom( cmToPoint( 0.15f ) );

      m_csv_table.addCell( pdf_cell );

      akt_spalte++;
    }
  }

  private void printGitterNetz( PdfWriter pPdfWriter, float pPageHeight )
  {
    boolean knzGitternetzDrucken = true;

    try
    {
      if ( knzGitternetzDrucken )
      {
        PdfContentByte itext_content_byte = pPdfWriter.getDirectContent();

        itext_content_byte.setLineWidth( 0f );
        itext_content_byte.setColorStroke( Color.BLACK );

        float cursor_y1 = 0;
        float cursor_y2 = pPageHeight;
        float cursor_x1 = 0;
        float cursor_x2 = pPageHeight;

        float lineal_wert = 0.0f;
        float lineal_cm_to_point = 0.0f;
        float akt_cm = 0.0f;
        float cm_schrittweite = 1.0f;

        while ( cursor_y1 <= cursor_y2 )
        {
          itext_content_byte.moveTo( cursor_x1, pPageHeight - cursor_y1 );
          itext_content_byte.lineTo( cursor_x2, pPageHeight - cursor_y1 );

          if ( akt_cm > 0 )
          {
            lineal_wert = 0.1f;

            while ( lineal_wert < 1.0f )
            {
              lineal_cm_to_point = cmToPoint( akt_cm - lineal_wert );

              itext_content_byte.moveTo( cursor_x1, pPageHeight - lineal_cm_to_point );

              if ( lineal_wert == 0.5f )
              {
                itext_content_byte.lineTo( cursor_x1 + cmToPoint( 0.16f ), pPageHeight - lineal_cm_to_point );
              }
              else
              {
                itext_content_byte.lineTo( cursor_x1 + cmToPoint( 0.10f ), pPageHeight - lineal_cm_to_point );
              }

              lineal_wert += 0.1;
            }
          }

          akt_cm += cm_schrittweite;

          cursor_y1 = cmToPoint( akt_cm );
        }

        cursor_y1 = 0;

        akt_cm = 0;

        while ( cursor_x1 <= cursor_x2 )
        {
          itext_content_byte.moveTo( cursor_x1, cursor_y1 );
          itext_content_byte.lineTo( cursor_x1, cursor_y2 );

          if ( akt_cm > 0 )
          {
            lineal_wert = 0.1f;

            while ( lineal_wert < 1.0f )
            {
              lineal_cm_to_point = cmToPoint( akt_cm - lineal_wert );

              itext_content_byte.moveTo( lineal_cm_to_point, pPageHeight );

              if ( lineal_wert == 0.5f )
              {
                itext_content_byte.lineTo( lineal_cm_to_point, pPageHeight - cmToPoint( 0.16f ) );
              }
              else
              {
                itext_content_byte.lineTo( lineal_cm_to_point, pPageHeight - cmToPoint( 0.10f ) );
              }

              lineal_wert += 0.1;
            }
          }

          akt_cm += cm_schrittweite;

          cursor_x1 = cmToPoint( akt_cm );
        }

        itext_content_byte.stroke();
      }

    }
    catch ( Exception err_inst )
    {
    }
  }

  /**
   * <pre>
   * Gibt die Instanz von ITextStyleCache zurueck. 
   * Sofern diese noch nicht instantiiert ist, wird dieses gemacht.
   * </pre>
   * 
   * @return m_itext_style_cache = eine Instanz von ITextStyleCache
   */
  private ITextStyleCache getITextStyleCache()
  {
    /*
     * Pruefung: Objekt-Cache vorhanden?
     *   
     * Sofern die Variable "m_itext_style_cache" null ist, wird dieser Variablen 
     * eine neue Instanz der Klasse "DrObjectCache" zugewiesen.
     */
    if ( m_itext_style_cache == null )
    {
      m_itext_style_cache = new ITextStyleCache();
    }

    /*
     * Rueckgabe der Instanz "m_itext_style_cache". 
     */
    return m_itext_style_cache;
  }

  private ITextStyle getITextStyle( String pStyleName, ElementStyles pElementStyles )
  {
    if ( pStyleName == null )
    {
      pStyleName = "element_printer_null_style";
    }

    ITextStyle i_text_style = null;

    try
    {
      i_text_style = ITextStyle.getClassCast( getITextStyleCache().get( pStyleName.toLowerCase() ) );
    }
    catch ( Exception err_inst )
    {
      //
    }

    if ( i_text_style == null )
    {
      i_text_style = new ITextStyle();

      if ( pElementStyles == null )
      {
        i_text_style.setFont( new Font( Font.HELVETICA, 10, Font.NORMAL, FARBE_TEXT_FEST_CSS_VORGABE_FONT ) );
      }
      else
      {
        ElementStyle element_style = pElementStyles.get( pStyleName );

        if ( element_style != null )
        {
          i_text_style.setStyleName( pStyleName );

          i_text_style.setFont( element_style.getFontName(), element_style.getFontSize(), element_style.getFontWeight(), element_style.getFontColor() );

          /*
           * Align Left ist Vorgabewert
           */
          if ( element_style.isTextAlignRight() )
          {
            i_text_style.setTextAlign( ITextStyle.ALIGN_RIGHT );
          }
          else if ( element_style.isTextAlignCenter() )
          {
            i_text_style.setTextAlign( ITextStyle.ALIGN_CENTER );
          }
          else if ( element_style.isTextAlignJustified() )
          {
            i_text_style.setTextAlign( ITextStyle.ALIGN_JUSTIFIED );
          }

          i_text_style.setSpaceBefore( element_style.getSpaceBefore() );

          i_text_style.setSpaceAfter( element_style.getSpaceAfter() );

          i_text_style.setIndentation( element_style.getIndentation() );
        }
        else
        {
          i_text_style.setFont( new Font( Font.HELVETICA, 8, Font.NORMAL, FARBE_TEXT_FEST_CSS_VORGABE_FONT ) );
        }
      }

      getITextStyleCache().add( pStyleName.toLowerCase(), i_text_style );
    }

    return i_text_style;
  }

  /**
   * <pre>
   * Loescht den Objektspeicher und setzt die Membervariable auf null. 
   * </pre>
   */
  public void clearItextStyleCache()
  {
    /*
     * Pruefung: Objekt-Cache vorhanden?
     *   
     * Ist eine Instanz von "ITextStyleCache" vorhanden, wird  
     * dort die Funktion "clear()" aufgerufen.   
     */
    if ( m_itext_style_cache != null )
    {
      m_itext_style_cache.clear();
    }

    /*
     * Variable "m_itext_style_cache" auf "null" stellen. 
     */
    m_itext_style_cache = null;
  }

  private void wl( String pString )
  {
    System.out.println( pString );
  }

  private void printFalzmarken( PdfWriter pPdfWriter, float pPageHeight )
  {
    try
    {
      PdfContentByte itext_content_byte = pPdfWriter.getDirectContent();

      itext_content_byte.setLineWidth( 0f );
      itext_content_byte.setColorStroke( Color.BLACK );

      float cmBreiteFalzMarken = 0.5f;

      float cursor_y_pos = pPageHeight - cmToPoint( 8.7f );
      float width = cmToPoint( cmBreiteFalzMarken );

      itext_content_byte.moveTo( 0, cursor_y_pos );
      itext_content_byte.lineTo( width, cursor_y_pos );

      cursor_y_pos = pPageHeight - cmToPoint( 19.09f );

      itext_content_byte.moveTo( 0, cursor_y_pos );
      itext_content_byte.lineTo( width, cursor_y_pos );

      itext_content_byte.stroke();
    }
    catch ( Exception err_inst )
    {
    }
  }

  /**
   * @param pString die Eingabe 
   * @param pAnzahlNachkommaStellen die gewuenschten Anzahl von Nachkommastellen
   * @return eine formatierte Zahl 
   */
  private static String getZahl( String pString, int pAnzahlNachkommaStellen )
  {
    return getZahl( pString, pAnzahlNachkommaStellen, false );
  }

  private static String getZahl( String pString, int pAnzahlNachkommaStellen, boolean pKnzFallbackTrennzeichenEin )
  {
    return getZahl( pString, ".", pAnzahlNachkommaStellen, pKnzFallbackTrennzeichenEin );
  }

  /**
   *<pre>
   *Beispiele:
   *
   * FkZahl.getZahl("+150.000,123456 Euro"   , 2    ) =  "150000.12"
   * FkZahl.getZahl("+150.000,123456 Euro"   , 0    ) =  "0"
   * FkZahl.getZahl("150.000,123456 DM"      , -1   ) =  "150000.123456"
   * FkZahl.getZahl("DM 150.000,123456-"     , 2    ) =  "-150000.12"
   * FkZahl.getZahl("null"                   , 2    ) =  "0.00"
   * FkZahl.getZahl("DM,Euro,Reichsmark"     , 2    ) =  "0.00"
   * FkZahl.getZahl("DM 15,0.0,00,12,34,56-" , 2    ) =  "-15.00"
   * FkZahl.getZahl("100.12"                 , 2    ) =  "100.12"
   * FkZahl.getZahl("100.1234-"              , 3    ) =  "-100.123"
   * FkZahl.getZahl("100,-"                  , 3    ) =  "-100.000"
   *</pre>
   *
   * @param pString die Eingabe 
   * @param pTrennzeichenFuerNK das Trennzeichen fuer Nachkommastellen im Ergebnis
   * @param pAnzahlNachkommaStellen die Anzahl der Nachkommastellen im Ergebnis
   * @param pKnzFallbackTrennzeichenEin Kennzeichen, ob das Trennzeichen auch ein Punkt anstelle eines Kommas sein darf
   * @return eine formatierte Zahl 
   */
  private static String getZahl( String pString, String pTrennzeichenFuerNK, int pAnzahlNachkommaStellen, boolean pKnzFallbackTrennzeichenEin )
  {
    StringBuffer str_ergebnis = new StringBuffer();

    /*
     * Kennzeichen Zahl Negativ
     * 
     * Solange in der Eingabe noch kein "-" gefunden wurde, ist diese Variable
     * ein Leerstring. Wird ein "-" gefunden, enthaelt ist diese Variable "-". 
     */
    String knz_negativ = "";

    char aktuelles_zeichen;
    char trennzeichen_nk = ',';

    if ( pTrennzeichenFuerNK == null )
    {
      pTrennzeichenFuerNK = ".";
    }
    else if ( ( pTrennzeichenFuerNK != null ) && ( pTrennzeichenFuerNK.length() > 0 ) )
    {
      trennzeichen_nk = pTrennzeichenFuerNK.charAt( 0 );
    }

    /*
     * 
     */
    int knz_nk_aktiv = 0;

    int akt_index = 0;
    int zaehler_nk = 0;
    int ziffern_zaehler = 0;

    if ( ( pString != null ) && ( pString.length() > 0 ) )
    {
      /*
       * Es wird ermittelt, ob das Nachkommatrennzeichen auf einen Punkt geaendert werden muss. 
       * Per Vorgabe wird das Komma als Trennzeichen genommen.
       * Wird in der Eingabe kein Komma gefunden, wird der Punkt als Trennzeichen genommen. 
       * 
       * Die Notwendigkeit ergab sich, da die Eingabe ja auch schon korrekt 
       * formatiert uebergeben werden kannm, z.B. aus den Werten einer DB.
       * 
       * Da dieses Vorgehen aber auch unerwartete Seiteneffekte haben kann,
       * kann dieses Vorgehen von aussen mit einer boolschen Variable gesteuert
       * werden.
       */
      if ( ( pKnzFallbackTrennzeichenEin ) && ( pString.indexOf( "," ) == -1 ) )
      {
        trennzeichen_nk = '.';
      }

      while ( akt_index < pString.length() )
      {
        aktuelles_zeichen = pString.charAt( akt_index );

        if ( aktuelles_zeichen >= '0' && aktuelles_zeichen <= '9' )
        {
          /*
           * Ist der Zaehler fuer die Nachkommastellen kleiner als die gewuenschte
           * Anzahl der Nachkommastellen, wird die aktuelle Zahl dem Ergebnis
           * hinzugefuegt. 
           * 
           * Dieses wird auch dann gemacht, wenn die Anzahl der gewuenschten 
           * Nachkommastellen 0 ist. In einem solchen Fall wird dem Aufrufer 
           * die Eingabe nur in eine Zahl konvertiert.
           */
          if ( ( zaehler_nk < pAnzahlNachkommaStellen ) || ( pAnzahlNachkommaStellen < 0 ) )
          {
            str_ergebnis.append( aktuelles_zeichen );

            zaehler_nk = zaehler_nk + knz_nk_aktiv; // knz_nk_aktiv = 1 wenn Leseprozess in Nachkommastellen

            ziffern_zaehler = ziffern_zaehler + 1;
          }
          /*
           * Soll die Anzahl der Nachkommastellen 0 sein, duerfen nur 
           * solange Zahlen hinzugefuegt werden, solange das Kennzeichen fuer 
           * "leseprozeß innerhalb Nachkommastellen" nicht gesetzt ist.
           * 
           * ... oder anders, wenn sich der Leseprozeß in den Nachkommastellen
           * befindet und die Anzahl der NK-Stellen soll 0 sein, werden alle
           * Zahlen ignoriert. 
           */
          else if ( ( pAnzahlNachkommaStellen == 0 ) && ( knz_nk_aktiv == 0 ) )
          {
            str_ergebnis.append( aktuelles_zeichen );

            ziffern_zaehler = ziffern_zaehler + 1;
          }
        }
        else if ( aktuelles_zeichen == trennzeichen_nk )
        {
          /*
           * Wurden bisher nur 0en gelesen, muss verhindert werden, das ein 
           * Ergebnis der Form "0000,xyz" zurueckgegeben wird. Der bisherige 
           * Stringbuffer wird ausgenullt und nur eine fuehrende 0 hinzugefuegt.
           */
          if ( ( ziffern_zaehler > 0 ) && ( parseInt( str_ergebnis.toString(), 0 ) == 0 ) )
          {
            str_ergebnis = new StringBuffer();

            str_ergebnis.append( '0' );
          }

          /*
           * Ist das aktuelle Zeichen ein Komma, wird dieses beim 
           * ersten Auftretetn in einen Punkt gewandelt. 
           * 
           * Das Umwandeln darf nicht doppelt gemacht werden und nur 
           * dann, wenn die gewuenschte Anzahl der NKStellen groeßer 0 ist.
           */
          if ( knz_nk_aktiv == 0 )
          {
            if ( pAnzahlNachkommaStellen > 0 )
            {
              str_ergebnis.append( pTrennzeichenFuerNK );
            }
          }

          /*
           * Kennzeichen wird auf 1 gesetzt
           */
          knz_nk_aktiv = 1;

          /*
           * Das Trennzeichen fuer die Nachkommastellen wird mit dem Zeichen 1 
           * versehen um zu verhindern, das mehrere Trennstellen gefunden werden.
           */
          trennzeichen_nk = '1';
        }
        else if ( aktuelles_zeichen == '-' )
        {
          knz_negativ = "-";
        }

        akt_index++;
      }
      /*
       * In der Eingabe waren keine Zahlen vorhanden z.B. " Test,Test".
       * In diesem Fall wuerde das Komma durch einen Punkt ersetzt werden. 
       * Der StringBuffer muss neu initialisiert werden, damit z.B. 0.00 
       * aus dem Rest der Routine hergestellt werden kann. 
       */
      if ( ziffern_zaehler == 0 )
      {
        str_ergebnis = new StringBuffer();
      }
    }

    /*
     * Ist die Eingabe null, ein Leerstring, oder durch vorangegangene 
     * Abfragen wieder ausgenullt worden ist, ist die Laenge des String-
     * Buffers 0. Damit jetzt eine korrekte Zahl erstellt werden 
     * kann, wird eine fuehrende 0 hinzugefuegt.
     * 
     * Abaenderung: Eine Pruefung des Intwertes aus "ziffern_zaehler" kann 
     * schneller gemacht werden.
     */
    //if ( ergebnis_string.length() == 0 )
    if ( ziffern_zaehler == 0 )
    {
      str_ergebnis.append( '0' );
    }

    /*
     * Hier werden die gewuenschten Anzahl der Nachkommastellen hinzugefuegt. 
     * 
     * Ist der Nachkommastellenzaehler noch 0, muss noch ein Punkt hinzugefuegt werden.
     * 
     * Das Hinzufuegen darf nur gemacht werden, wenn die Anzahl der Nachkommastellen 
     * groesser als 0 ist.
     */
    if ( pAnzahlNachkommaStellen > 0 )
    {
      while ( zaehler_nk < pAnzahlNachkommaStellen )
      {
        if ( ( zaehler_nk == 0 ) && ( knz_nk_aktiv == 0 ) )
        {
          str_ergebnis.append( pTrennzeichenFuerNK );
        }
        str_ergebnis.append( '0' );

        zaehler_nk = zaehler_nk + 1;
      }
    }

    /*
     * Die Rueckgabe der Funktion ist das Zeichen aus knz_negativ und dem 
     * Inhalt aus dem Stringbuffer. 
     */
    return knz_negativ + str_ergebnis.toString();
  }

  /**
   * <pre>
   * Ermittelt aus dem Parameter "pString" den Integerwert.
   * Kommt es bei der Umwandlung zu einer NumberFormatException,
   * wird der Vorgabewert zurueckgegeben. 
   * 
   * Auf pString wird ein TRIM ausgefuehrt.
   * </pre>
   * 
   * @param pString zu parsende Zeichenkette
   * @param pVorgabeWert der im Fehlerfall zurueckzugebende Wert
   * @return der Wert als int oder der Vorgabewert
   */
  private static int parseInt( String pString, int pVorgabeWert )
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
      // keine Fehlerbehandlung, da im Fehlerfall der Vorgabewert zurueckgegeben wird
    }

    return pVorgabeWert;
  }
}
