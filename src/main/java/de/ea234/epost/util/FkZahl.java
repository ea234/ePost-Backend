package de.ea234.epost.util;

import java.util.UUID;

public class FkZahl {

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
  public static int parseInt( String pString, int pVorgabeWert )
  {
    try
    {
      if ( pString != null )
      {
        return Integer.parseInt( pString.trim() );
      }
    }
    catch (NumberFormatException err_inst)
    {
      // keine Fehlerbehandlung, da im Fehlerfall der Vorgabewert zurueckgegeben wird
    }

    return pVorgabeWert;
  }

  public static String getUUID()
  {
    UUID uuid_number = UUID.randomUUID();

    return uuid_number.toString().replaceAll( "-", "" );
  }
}
