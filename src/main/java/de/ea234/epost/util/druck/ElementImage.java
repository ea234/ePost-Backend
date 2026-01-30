package de.ea234.epost.util.druck;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ElementImage {

  /**
   * <pre>
   * Fuehrt einen Class-Cast auf die Klasse "ElementNeueSeite" auf das uebergebene Objekt aus.
   *
   * Im Falle einer ClassCastException wird "null" zurueckgegeben.
   * Ist das Objekt selber "null" wird "null" zurueckgegeben.
   *
   * ElementNeueSeite instanz = ElementNeueSeite.getClassCast( java_object );
   *
   * if ( instanz == null )
   * {
   *   str_fehler = "Die Instanz \"java_object\" ist keine Instanz der Klasse \"ElementNeueSeite\" ";
   *   return;
   * }
   *
   * </pre>
   *
   * @param pObjekt das zu castende Objekt
   * @return das Objekt als Instanz von "ElementNeueSeite", oder "null" im
   * Fehlerfall
   */
  public static ElementNeueSeite getClassCast( Object pObjekt )
  {
    /*
     * Pruefung: pObjekt ungleich null ?
     */
    if ( pObjekt != null )
    {
      try
      {
        return (ElementNeueSeite) pObjekt;
      }
      catch (ClassCastException abgf_fehler)
      {
        // keine Aktion im Fehlerfall, da Rueckgabe von null
      }
    }

    return null;
  }

  private String m_seiten_beizeichnung = "";

  private String m_id_element = "";

  private static byte[] m_datei_bytes = null;

  private static Image m_image = null;

  public void clear()
  {
    m_seiten_beizeichnung = null;
    m_id_element = null;
    m_datei_bytes = null;
    m_image = null;
  }

  public String getIdElement()
  {
    return m_id_element;
  }

  public void setIdElement( String pIdElement )
  {
    m_id_element = pIdElement;
  }

  public String getSeitenBeizeichnung()
  {
    return m_seiten_beizeichnung;
  }

  public void setSeitenBeizeichnung( String pSeitenBeizeichnung )
  {
    m_seiten_beizeichnung = pSeitenBeizeichnung;
  }

  public static Image getImage()
  {
    try
    {
      return new ImageIcon( m_datei_bytes ).getImage();
    }
    catch (Exception err_inst)
    {
      System.out.println( "Fehler ElementImage: errGetImage" + err_inst.getMessage() );

      err_inst.printStackTrace( System.out );
    }

    return null;
  }

  /**
   * @return Byte-Array mit den Daten der Datei
   */
  public static byte[] getFktNameBytes()
  {
    return m_datei_bytes;
  }

  public void setDateiBytes( byte[] pBytes )
  {
    m_datei_bytes = pBytes;
  }
}
