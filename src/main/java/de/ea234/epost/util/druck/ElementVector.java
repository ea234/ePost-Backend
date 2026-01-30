package de.ea234.epost.util.druck;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Vektorklasse um Instanzen vom Typ "Element" in einem Vektor speichern zu koennen.
 * </pre>
 */
public class ElementVector
{
  /** 
   * Instanz fuer die Speicherung der Objekte
   */
  private List< Element > m_vector = null;

  /**
   * <pre>
   * Ruft bei jedem Element des Vektors die To-String funktion auf.
   * </pre>
   *
   * @return Auflistung der Vektorelemente
   */
  public String toString()
  {
    StringBuffer ergebnis_string_buffer = new StringBuffer();

    ergebnis_string_buffer.append( "\nBean-Klasse Element" );

    if ( hasElement() )
    {
      for ( Element inst_element : m_vector )
      {
        ergebnis_string_buffer.append( inst_element.toStringZeile() );
      }
    }

    return ergebnis_string_buffer.toString();
  }

  /**
   * <pre>
   * Erstellt eine neue Instanz der Klasse "Element", fuegt 
   * diese dem Speichervektor hinzu und gibt die erstellte Instanz zurueck.
   * </pre>
   *
   * @return eine Instanz vom Typ "Element"
   */
  public Element addElement()
  {
    Element inst_element = new Element();

    getElementVector().add( inst_element );

    return inst_element;
  }

  /**
   * <pre>
   * Fuegt die uebergebene Instanz dem Vektor m_element_vector hinzu, sofern der Parameter ungleich null ist.
   * Der Parameter pElement wird dem Aufrufer wieder zurueckgegeben.
   * </pre>
   *
   * @param  pElement       eine Klasse vom Typ "Element"
   *
   * @return eine Instanz vom Typ Element (gleich dem Eingabeparameter)
   */
  public Element addElement( Element pElement )
  {
    if ( pElement != null )
    {
      getElementVector().add( pElement );
    }

    return pElement;
  }

  /**
   * <pre>
   * Liefert die Information zurueck, ob der Vektor "m_element_vector" instanziiert
   * und Werte vom Typ "Element" gesetzt wurden.
   * </pre>
   *
   * @return TRUE wenn Werte im Vektor vorhanden sind, sonst FALSE
   */
  private boolean hasElement()
  {
    return ( m_vector != null ) && ( m_vector.size() > 0 );
  }

  /**
   * <pre>
   * Gibt die Vektorinstanz "m_element_vector" zurueck.
   * Ist die Instanz noch nicht vorhanden, wird diese erzeugt.
   * </pre>
   *
   * @return eine Instanz vom Typ "MyVectorClass"
   */
  public List< Element > getElementVector()
  {
    if ( m_vector == null )
    {
      m_vector = new ArrayList< Element >();
    }

    return m_vector;
  }

  /**
   * <pre>
   * Liefert das Objekt an dem uebergebenen Index zurueck. 
   * Ist der Parameterindex nicht vorhanden, wird "null" zurueckgegeben.
   * </pre>
   * 
   * @param  pIndex         der gewuenschte Index im Speichervektor
   * 
   * @return eine Instanz vom Typ "Element", oder "null", wenn der gewuenschte Index nicht existiert
   */
  public Element getIndexElement( int pIndex )
  {
    try
    {
      return (Element) getElementVector().get( pIndex );
    }
    catch ( Exception err_inst )
    {
      // Keine Aktion notwendig, da im Fehlerfall null zurueckgegeben wird
    }

    return null;
  }

  /**
   * <pre>
   * Liefert die Anzahl der gespeicherten Elemente der Beanklasse im Vektor zurueck.
   * Ist der Vektor noch nicht gesetzt, wird 0 zurueckgegeben.
   * </pre>
   * 
   * @return die Anzahl der gespeicherten Elemente der Beanklasse Element
   */
  public int getAnzahl()
  {
    if ( m_vector == null )
    {
      return 0;
    }

    return m_vector.size();
  }

  /**
   * <pre>
   * Sofern eine Vektorinstanz vorhanden ist, wird dort die Funktion "clear()" aufgerufen.
   * Anschliessend wird die Vektorinstanz auf null gesetzt.
   * </pre>
   */
  public void clear()
  {
    if ( m_vector != null )
    {
      for ( Element inst_element : m_vector )
      {
        inst_element.clear();
      }

      m_vector.clear();
    }

    m_vector = null;
  }
}
