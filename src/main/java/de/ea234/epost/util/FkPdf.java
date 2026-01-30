package de.ea234.epost.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FkPdf {

  public static boolean istPdfDatei( String pDateiName )
  {
    /*
     * Pruefung: Parameter "pDateiName" gleich null ? 
     * 
     * Wird kein Dateiname uebergeben, ist es keine PDF-Datei. 
     * Der Aufrufer bekommt FALSE zurueck.
     */
    if ( pDateiName == null )
    {
      return false;
    }

    boolean knz_ist_pdf_datei = false;

    try
    {
      /*
       * Instanz von "java.io.File" erstellen
       */
      File instanz_file = new File( pDateiName );

      /*
       * Festlegung der zu lesenden Bytes am Start und am Ende der Datei.
       */
      int anzahl_bytes_pdf_startsequenz = 10;
      int anzahl_bytes_pdf_endsequenz = 10;

      byte[] datei_bytes = new byte[anzahl_bytes_pdf_startsequenz + anzahl_bytes_pdf_endsequenz];

      InputStream input_stream = new FileInputStream( instanz_file );

      try
      {
        /*
         * Die Bytes der Startsequenz lesen. 
         * Die anzahl der gelesenen Bytes werden in einer Variablen gespeichert. 
         */
        long anzahl_bytes_file_gelesen = (long) input_stream.read( datei_bytes, 0, anzahl_bytes_pdf_startsequenz ); // %PDF-

        /*
         * Pruefung: Wurde eine Anzahl von Bytes gelesen, welche groesser gleich der Anzahl Bytes der Startsequenz ist?
         */
        if ( anzahl_bytes_file_gelesen >= anzahl_bytes_pdf_startsequenz )
        {
          /*
           * Startsequenz "%PDF-" pruefen
           */
          if ( datei_bytes[0] == 0x25 && datei_bytes[1] == 0x50 && datei_bytes[2] == 0x44 && datei_bytes[3] == 0x46 && datei_bytes[4] == 0x2D )
          {
            /*
             * Ueberspringen des Dateiinhaltes bis zur PDF-Endsequenz.
             */
            anzahl_bytes_file_gelesen = input_stream.skip( instanz_file.length() - datei_bytes.length ); // Skip bytes

            /*
             * Lesen der Endsequenz von PDF-Dateien.
             */
            anzahl_bytes_file_gelesen = input_stream.read( datei_bytes, anzahl_bytes_pdf_startsequenz, anzahl_bytes_pdf_endsequenz ); // %%EOF,SP?,CR?,LF?

            /*
             * Es wird im Byte-Array die Zeichenfolge %%EOF gesucht. 
             * Wird diese Zeichenfolge gefunden, ist es wohl eine korrekte PDF-Datei.
             */
            int offset_start = anzahl_bytes_pdf_startsequenz + 1;
            int offset_end = (anzahl_bytes_pdf_startsequenz + anzahl_bytes_pdf_endsequenz) - 5;

            while ( (offset_start < offset_end) && (knz_ist_pdf_datei == false) )
            {
              if ( (datei_bytes[offset_start] == 0x25) && (datei_bytes[offset_start + 1] == 0x25) && (datei_bytes[offset_start + 2] == 0x45) && (datei_bytes[offset_start + 3] == 0x4F) && (datei_bytes[offset_start + 4] == 0x46) )
              {
                knz_ist_pdf_datei = true;
              }
              else
              {
                offset_start ++;
              }
            }
          }
        }
      }
      catch (Exception err_inst)
      {
        // Keine Fehlerausgabe
      }
      finally
      {
        input_stream.close();
      }

      input_stream = null;

      datei_bytes = null;
    }
    catch (Exception err_inst)
    {
      // Keine Fehlerausgabe
    }

    return knz_ist_pdf_datei; // isPdf( bytes );
  }
}
