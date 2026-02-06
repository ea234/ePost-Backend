package de.ea234.epost.model;

import de.ea234.epost.model.benutzer.Benutzer;
import de.ea234.epost.model.benutzer.BenutzerList;
import de.ea234.epost.model.kunden.Adresse;
import de.ea234.epost.model.kunden.Kunde;
import de.ea234.epost.model.kunden.KundeList;
import de.ea234.epost.model.kunden.Vertrag;
import de.ea234.epost.model.vorgang.VorgangstypList;
import de.ea234.epost.model.workflow.WorkflowBackgroundAgent;
import de.ea234.epost.services.TestDokumentErsteller;
import de.ea234.epost.util.FkDatum;
import de.ea234.epost.util.FkDatumLong;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AppTestInit {

  private static final Logger log = LoggerFactory.getLogger( AppTestInit.class );

  private final BenutzerList benutzerList;

  private final VorgangstypList vorgangstypList;

  private final KundeList kundeList;

  private final TestDokumentErsteller testDokumentErsteller;

  private final WorkflowBackgroundAgent workflowBackgroundAgent;

  @EventListener(ApplicationReadyEvent.class)
  public void initTestdaten()
  {
    doTestDatenBenutzer();

    vorgangstypList.init();

    doTestDatenKunden();

    testDokumentErsteller.doErstelleTestEingangJeKunde();

    workflowBackgroundAgent.doRun( "Test" );
  }

  private void doTestDatenBenutzer()
  {
    System.out.println( "🔴 doTestdaten Kunde" );

    addIniBenutzer( "abrad", "Alons1", "Brader1" );
    addIniBenutzer( "ehart", "Egon", "Hartling" );
    addIniBenutzer( "sniss", "Sabine", "Nissen" );
    addIniBenutzer( "mhuan", "Maria", "Huana" );
  }

  private void addIniBenutzer( String pUserName, String pVorname, String pNachname )
  {
    Benutzer bestehender_benutzer = benutzerList.getBenutzerByUserName( pUserName );

    if ( bestehender_benutzer == null )
    {
      Benutzer neuer_benutzer = new Benutzer();

      neuer_benutzer.setUserName( pUserName );
      neuer_benutzer.setVorName( pVorname );
      neuer_benutzer.setNachName( pNachname );
      neuer_benutzer.setEmail( pVorname + "." + pNachname + "@firma.de" );

      neuer_benutzer.setUserPassword( pUserName );

      benutzerList.add( neuer_benutzer );
    }
  }

  /*
   * ****************************************************************************
   * Erstellung von Testkunden
   * ****************************************************************************
   */
  private void doTestDatenKunden()
  {
    Adresse adresse_01 = getNewAdresse( "Hahnenblecher 60a", "Bad Friederike", "78614", "DE" );
    Adresse adresse_02 = getNewAdresse( "Bogenstr. 526", "Schön Alanberg", "09143", "DE" );
    Adresse adresse_03 = getNewAdresse( "Brandenburger Str. 86b", "Leipoldscheid", "96731", "DE" );
    Adresse adresse_04 = getNewAdresse( "Feldsiefer Wiesen 39", "Vogelgsangscheid", "21139", "DE" );

    Adresse adresse_05 = getNewAdresse( "Ginsterweg 42c", "Ost Enya", "63226", "DE" );
    Adresse adresse_06 = getNewAdresse( "Elisabeth-von-Thadden-Str. 40b", "Schimastadt", "31852", "DE" );
    Adresse adresse_07 = getNewAdresse( "Friedrich-Ebert-Platz 38b", "Neu Riekegrün", "05156", "DE" );
    Adresse adresse_08 = getNewAdresse( "Hans-von-Dohnanyi-Str. 10b", "Cheyenneberg", "92800", "DE" );
    Adresse adresse_09 = getNewAdresse( "Walter-Hochapfel-Str. 888", "Luisabrunn", "27719", "DE" );

    Adresse adresse_10 = getNewAdresse( "Am Märchen 54b", "Jonnaberg", "07872", "DE" );
    Adresse adresse_11 = getNewAdresse( "Faßbacher Hof 3", "Mallmannburg", "25730", "DE" );
    Adresse adresse_12 = getNewAdresse( "Jahnstr. 68b", "Graufeld", "64245", "DE" );
    Adresse adresse_13 = getNewAdresse( "Carl-Duisberg-Str. 0", "West Steve", "86165", "DE" );
    Adresse adresse_14 = getNewAdresse( "Mathildenweg 813", "Köhreheim", "72928", "DE" );
    Adresse adresse_15 = getNewAdresse( "Kinderhausen 54c", "Merlinheim", "16585", "DE" );
    Adresse adresse_16 = getNewAdresse( "Brandenburger Str. 86b", "Leipoldscheid", "96731", "DE" );
    Adresse adresse_17 = getNewAdresse( "Feldsiefer Wiesen 39", "Vogelgsangscheid", "21139", "DE" );
    Adresse adresse_18 = getNewAdresse( "Ginsterweg 42c", "Ost Enya", "63226", "DE" );
    Adresse adresse_19 = getNewAdresse( "Elisabeth-von-Thadden-Str. 40b", "Schimastadt", "31852", "DE" );

    Adresse adresse_20 = getNewAdresse( "Friedrich-Ebert-Platz 38b", "Neu Riekegrün", "05156", "DE" );
    Adresse adresse_21 = getNewAdresse( "Hans-von-Dohnanyi-Str. 10b", "Cheyenneberg", "92800", "DE" );
    Adresse adresse_22 = getNewAdresse( "Walter-Hochapfel-Str. 888", "Luisabrunn", "27719", "DE" );
    Adresse adresse_23 = getNewAdresse( "Am Märchen 54b", "Jonnaberg", "07872", "DE" );
    Adresse adresse_24 = getNewAdresse( "Faßbacher Hof 3", "Mallmannburg", "25730", "DE" );
    Adresse adresse_25 = getNewAdresse( "Jahnstr. 68b", "Graufeld", "64245", "DE" );
    Adresse adresse_26 = getNewAdresse( "Carl-Duisberg-Str. 0", "West Steve", "86165", "DE" );
    Adresse adresse_27 = getNewAdresse( "Mathildenweg 813", "Köhreheim", "72928", "DE" );
    Adresse adresse_28 = getNewAdresse( "Kinderhausen 54c", "Merlinheim", "16585", "DE" );
    Adresse adresse_29 = getNewAdresse( "Friedrich-Bergius-Platz 74c", "Klein Natalia", "63945", "DE" );
    Adresse adresse_30 = getNewAdresse( "Gronenborner Weg 6", "Reitzeburg", "62258", "DE" );

    addIniKunde( "115591", "Herr", "Alexander", "Meisch", "1959-05-19", "+49-172-7120873", adresse_01 );
    addIniKunde( "133773", "Frau", "Annemarie", "Werpas", "1986-01-10", "+49-174-5737792", adresse_02 );

    addIniKunde( "170009", "Frau", "Henriette", "Neuendorf", "1981-11-02", "+49-166-5753224", adresse_03 );
    addIniKunde( "151955", "Herr", "Willi", "Bertenbreiter", "1944-06-09", "+49-168-8577760", adresse_04 );

//    addIniKunde( "142864", "Frau", "Chayenne", "Sinnhuber", ("1973-06-29"), "+49-175-9803840", adresse_05 );
//    addIniKunde( "160046", "Frau", "Bianca", "Kampf", ("1959-06-30"), "+49-161-2833174", adresse_06 );
//    addIniKunde( "179137", "Frau", "Noemi", "Hanenberger", ("1986-04-25"), "+49-1509-6450986", adresse_07 );
//    addIniKunde( "188208", "Frau", "Pia", "Büngener", ("1997-09-19"), "+49-1518-9709669", adresse_08 );
//    addIniKunde( "197329", "Herr", "Nino", "Hertel", ("2000-09-24"), "+49-1553-5220938", adresse_09 );
//    addIniKunde( "106440", "Herr", "Alexander", "Hentschel", ("1970-06-07"), "+49-1550-0444974", adresse_10 );
//    addIniKunde( "115551", "Herr", "Josef", "Hartlieb", ("1998-12-19"), "+49-168-4853147", adresse_11 );
//    addIniKunde( "124672", "Frau", "Ida", "Moser", ("1977-07-24"), "+49-172-4532828", adresse_12 );
//    addIniKunde( "133713", "Herr", "Jay", "Dittmar", ("1987-03-28"), "+49-161-5854588", adresse_13 );
//    addIniKunde( "142774", "Herr", "Eduart", "Poeschl", ("1995-02-06"), "+49-178-0597505", adresse_14 );
//    addIniKunde( "151895", "Herr", "Pepe", "Hesse", ("1976-07-09"), "+49-170-3003488", adresse_15 );
//    addIniKunde( "124682", "Frau", "Kimberley", "Bienias", ("1981-02-03"), "+49-176-9317664", adresse_16 );
//    addIniKunde( "170001", "Herr", "Till", "Felke", "1975-04-13", "+49-1579-2422598", adresse_17 );
//    addIniKunde( "170002", "Herr", "Aliyah", "Lenzen", "1999-07-14", "+49-1546-9823332", adresse_18 );
//    addIniKunde( "170003", "Frau", "Miriam", "Battke", "1970-11-04", "+49-171-6315295", adresse_19 );
//    addIniKunde( "170004", "Frau", "Ayleen", "Wollmann", "1974-09-13", "+49-1533-1810586", adresse_20 );
//    addIniKunde( "170005", "Herr", "Leopold", "Jerschabek", "1983-02-18", "+49-178-4650548", adresse_21 );
//    addIniKunde( "170006", "Herr", "Asya", "Lepthin", "1973-06-27", "+49-166-8109497", adresse_22 );
//    addIniKunde( "170007", "Herr", "Hennes", "Urbansky", "1969-12-10", "+49-167-3348512", adresse_23 );
//    addIniKunde( "170008", "Herr", "Yves", "Lipus", "1988-08-26", "+49-1506-3433799", adresse_24 );
//    addIniKunde( "170000", "Frau", "Anna", "Schellenbeck", "1993-04-20", "+49-172-7945942", adresse_25 );  
//    addIniKunde( "1700010", "Herr", "Luisa", "Wölpert", "2000-01-31", "+49-1574-3426375", adresse_26 );
//    addIniKunde( "1700011", "Herr", "Leni", "Schwarzer", "1981-04-20", "+49-160-7285866", adresse_27 );
//    addIniKunde( "1700012", "Herr", "Jannis", "Matthes", "1998-01-13", "+49-163-1161467", adresse_28 );
//    addIniKunde( "1700013", "Herr", "Laurin", "Koszewski", "1981-07-05", "+49-163-4684086", adresse_29 );
//    addIniKunde( "1700014", "Herr", "Maira", "Sagonas", "1964-05-22", "+49-169-1480657", adresse_30 );
//    addIniKunde( "1700015", "Herr", "Semih", "Rink", "1969-04-18", "+49-1564-6287853", adresse_31 );
//    addIniKunde( "1700016", "Herr", "Hugo", "Hingsen", "1985-08-12", "+49-166-2217617", adresse_12 );
//    addIniKunde( "1700017", "Herr", "Mareike", "Merkel", "1968-11-10", "+49-1574-3272622", adresse_12 );
//    addIniKunde( "1700018", "Herr", "Richard", "Reinberg", "1956-12-14", "+49-163-7293751", adresse_12 );
//    addIniKunde( "1700019", "Herr", "Emely", "Schuri", "1971-10-10", "+49-173-5268648", adresse_12 );
//    addIniKunde( "1700020", "Herr", "Tjark", "Lenfers", "1976-06-14", "+49-174-9304773", adresse_12 );
  }

  private void addIniKunde( String stammNr, String anrede, String vorName, String nachName, String geburtsDatum, String telefon, Adresse adresse )
  {
    log.info( "addIniKunde " + stammNr + ", " + vorName );

    Kunde bestands_kunde = kundeList.getKundeByStammnummer( stammNr );

    if ( bestands_kunde == null )
    {
      log.info( "Kunde noch nicht vorhanden. " + stammNr + ", " + vorName );

      Kunde new_kunde = getNewKunde( stammNr, anrede, vorName, nachName, geburtsDatum, telefon, adresse );

      addVertrag( new_kunde );

      kundeList.add( new_kunde );
    }
  }

  private Kunde getNewKunde( String stammNr, String anrede, String vorName, String nachName, String geburtsDatum, String telefon, Adresse adresse )
  {
    Kunde new_kunde = new Kunde();

    new_kunde.setStammNummer( stammNr );
    new_kunde.setAnrede( anrede );
    new_kunde.setVorName( vorName );
    new_kunde.setNachName( nachName );

    String kunde_email = vorName + "." + nachName + "@web.de";

    new_kunde.setEmail( kunde_email );
    new_kunde.setGeburtsDatum( FkDatumLong.parseToDate( geburtsDatum ) );
    new_kunde.setTelefon( telefon );
    new_kunde.setAddresse( adresse );

    return new_kunde;
  }

  private Adresse getNewAdresse( String strasse, String ort, String postleitzahl, String land )
  {
    Adresse new_adresse = new Adresse();

    new_adresse.setStrasse( strasse );
    new_adresse.setOrt( ort );
    new_adresse.setPostleitzahl( postleitzahl );
    new_adresse.setLand( land );

    return new_adresse;
  }

  private void addVertrag( Kunde pKunde )
  {
    String pStammNummer = pKunde.getStammNummer();

    Date geb_datum = pKunde.getGeburtsDatum();

    Date datum_abschluss_vertrag_1 = FkDatum.addJahre( geb_datum, 18, -1 );

    Vertrag vertrag_1 = getNewVertrag( pStammNummer, "01", FkDatum.getString( datum_abschluss_vertrag_1 ) );

    vertrag_1.setKunde( pKunde );

    pKunde.addVertrag( vertrag_1 );
  }

  private Vertrag getNewVertrag( String pStammNummer, String pVertragNr, String pDatumAbschluss )
  {
    Vertrag v2 = new Vertrag();

    v2.setBezeichnung( "Vertrag " + pVertragNr );
    v2.setDatumAbschluss( FkDatumLong.parseToDate( pDatumAbschluss ) );
    v2.setDatumEnde( FkDatum.addJahre( FkDatumLong.parseToDate( pDatumAbschluss ), 10, 1 ) );
    v2.setStammNummer( pStammNummer );
    v2.setVertragsNummer( pStammNummer + pVertragNr );

    return v2;
  }

}
