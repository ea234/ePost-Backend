package de.ea234.epost.util.druck;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PDF
{
  public static byte[] druck( ElementDokument pElementDokument ) throws Exception
  {
    return new ITextElementPrinter().createPDF( pElementDokument );
  }

  /**
   * <pre>
   * Erstellt die Datei und schreibt den uebergebenen Inhalt rein.
   * Ist der uebergebene Inhalt null wird eine leere Datei erstellt.
   * </pre>
   * 
   * @param pDateiName der Dateiname 
   * @param pInhalt der (optionale) zu schreibende Inhalt
   * @return TRUE wenn die Datei geschrieben werden konnte, sonst False
   */
  public static boolean schreibeDatei( String pDateiName, byte[] pInhalt )
  {
    boolean knz_ergebnis = false;

    OutputStream output_stream = null;

    try
    {
      /*
       * File-Output-Stream mit dem Dateinamen erstellen
       */
      output_stream = new FileOutputStream( pDateiName );

      /*
       * Wurde ein Inhalt uebergeben, wird dieser in die Datei geschrieben.
       */
      if ( pInhalt != null )
      {
        output_stream.write( pInhalt );
      }

      /*
       * Aufruf von "stream.flush()"
       */
      output_stream.flush();
    }
    catch ( Exception err_inst )
    {
      System.out.println( "Fehler: errSchreibeDatei 1 " + err_inst.getMessage() );
    }

    try
    {
      if ( output_stream != null )
      {
        /*
         * Datei schliessen
         */
        output_stream.close();

        /*
         * Funktionsergebnis auf TRUE stellen.
         * TRUE wird nur zurueckgegeben, wenn der Stream am Ende auch 
         * geschlossen werden konnte.
         */
        knz_ergebnis = true;
      }
    }
    catch ( IOException err_inst )
    {
      knz_ergebnis = false;

      System.out.println( "Fehler: errSchreibeDatei 2 " + err_inst.getMessage() );
    }

    /*
     * Output-Stream auf null stellen.
     */
    output_stream = null;

    /*
     * Dem Aufrufer das Funktionsergebnis zurueckgeben.
     */
    return knz_ergebnis;
  }
}
