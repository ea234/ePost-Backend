package de.ea234.epost.util.druck;

import java.util.HashMap;
import java.util.Iterator;

public class ElementStyles
{
  /**
   * Die Map, welche die Objekte zu Schluesselnamen abspeichert
   */
  private HashMap< String, ElementStyle > m_hash_map = new HashMap< String, ElementStyle >();

  /**
   * <pre>
   * Speichert das Objekt in der Hashmap unter dem uebergebenen Namen ab.
   * </pre>
   * 
   * @param pName der Name unter dem das Objekt wiedergefunden werden soll
   * @param pObjekt das zu speichernde Objekt
   */
  public void add( ElementStyle pElementStyle )
  {
    m_hash_map.put( pElementStyle.getIdName(), pElementStyle );
  }

  /**
   * <pre>
   * Liefert das unter dem uebergebenen Namen gespeicherte Objekt zurueck. 
   * Ist unter diesem Namen kein Objekt gespeichert, wird <code>null</code> zurueckgeliefert.
   * </pre>
   * 
   * @param pName der Name unter dem das Objekt gespeichert wurde
   * @return ein Objekt oder null im Fehlerfall
   */
  ElementStyle get( String pName )
  {
    return ElementStyle.getClassCast( m_hash_map.get( pName ) );
  }

  /**
   * Loescht alle Mappings in der Hashmap und setzt die Hash-Map-Instanz auf null. 
   */
  public void clear()
  {
    if ( m_hash_map != null )
    {
      m_hash_map.clear();

      m_hash_map = null;
    }

    m_hash_map = new HashMap< String, ElementStyle >();
  }

  public String toString()
  {
    String ergebnis = "";

    try
    {
      int zaehler = 0;

      Iterator iterator_keys = m_hash_map.keySet().iterator();

      while ( iterator_keys.hasNext() )
      {
        ergebnis = ergebnis + "[" + zaehler + "] " + iterator_keys.next() + "\n";

        zaehler++;
      }
    }
    catch ( Exception err_inst )
    {
      ergebnis = ergebnis + "[Fehler: ElementStyles] " + err_inst.getMessage();
    }

    return ergebnis;
  }
}
