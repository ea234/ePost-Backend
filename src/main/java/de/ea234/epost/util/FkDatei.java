package de.ea234.epost.util;

import java.nio.file.Path;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicLong;

import java.io.FileFilter;

public class FkDatei {

  /**
   * <pre>
   * Aendert die Erweiterung fuer den uebergebenen Dateinamen.
   *
   * Es wird null zurueckgegeben wenn der Parameter:
   *   - selber null ist
   *   - ein Leerstring ist
   *   - der Parameter "." oder ".." ist
   *
   * FkDatei.testGetDateiNameOhneErweiterung( "/dir1/dir2/datei.pdf" ) = /dir1/dir2/datei
   * FkDatei.testGetDateiNameOhneErweiterung( "datei.pdf"            ) = datei
   * FkDatei.testGetDateiNameOhneErweiterung( "datei.pdf.txt"        ) = datei.pdf
   * FkDatei.testGetDateiNameOhneErweiterung( "datei"                ) = datei
   * FkDatei.testGetDateiNameOhneErweiterung( "datei."               ) = datei
   * FkDatei.testGetDateiNameOhneErweiterung( ".datei"               ) = .datei
   * FkDatei.testGetDateiNameOhneErweiterung( ""                     ) = null
   * FkDatei.testGetDateiNameOhneErweiterung( "      "               ) = null
   * FkDatei.testGetDateiNameOhneErweiterung( "."                    ) = null
   * FkDatei.testGetDateiNameOhneErweiterung( ".."                   ) = null
   * FkDatei.testGetDateiNameOhneErweiterung( null                   ) = null
   * </pre>
   *
   * @param pDateiName der Dateiname
   * @return den Dateinamen ohne Erweiterung oder null
   */
  public static String getDateiNameOhneErweiterung( String pDateiName )
  {
    String datei_name_ergebnis = null;

    if ( (pDateiName != null) && (pDateiName.isBlank() == false) )
    {
      if ( (pDateiName.compareTo( "." ) != 0) && (pDateiName.compareTo( ".." ) != 0) )
      {
        if ( pDateiName.startsWith( "." ) )
        {
          datei_name_ergebnis = pDateiName;
        }
        else
        {
          int index_letzter_punkt = pDateiName.lastIndexOf( '.' );

          if ( index_letzter_punkt >= 0 )
          {
            datei_name_ergebnis = pDateiName.substring( 0, index_letzter_punkt );
          }
          else
          {
            datei_name_ergebnis = pDateiName;
          }
        }
      }
    }

    return datei_name_ergebnis;
  }

  public static String getDateiNameOhneErweiterung( Path pPathDateiName )
  {
    if ( pPathDateiName == null )
    {
      return null;
    }

    return getDateiNameOhneErweiterung( pPathDateiName.getFileName().toString() );
  }

  public static long getAnzahlDateien( String pVerzeichnis )
  {
    return new File( pVerzeichnis ).listFiles( new MyFileFilter() ).length;
  }

  private static class MyFileFilter implements FileFilter {

    public boolean accept( File pathname )
    {
      return  ! pathname.isDirectory();
    }
  }

}
