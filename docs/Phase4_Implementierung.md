
# ePost  
## Phase 4 – Implementierung

**Autor:** Eckbert Andresen  
**Datum:** 23.12.2025  
**Version:** 1.0  

---

## 1. Ziel und Umfang
Diese Phase beschreibt die konkrete technische Implementierung des *ePost*.
Sie dient als technische Referenz für Entwicklung, Tests und Betrieb.

Der Fokus liegt auf:
- Aufbau der Code- und Paketstruktur
- Implementierung der REST-Schnittstelle
- Umsetzung des Geschäftsprozesses „Rechnung scannen“
- IBAN-Erkennung und Blacklist-Prüfung
- Fehlerbehandlung, Logging sowie Performance-Aspekten

Die Implementierung erfolgt in **Java 21** mit **Spring Boot 3.5.7**.

---

## 2. Projekt- und Paketstruktur
:

```
├── docs
│   ├── img
│   │   ├── epost_html_anzeige_vorgaenge_01.jpg
│   │   ├── epost_html_vorgangsdetail_01.jpg
│   │   ├── epost_login_1.jpg
│   │   └── epost_login.jpg
│   ├── Management_Summary.md
│   ├── Phase1_Voruntersuchung.md
│   ├── Phase2_Initiierung_Lastenheft.md
│   ├── Phase3_System_und_Softwareentwurf.md
│   ├── Phase4_Implementierung.md
│   ├── Phase5_Test_und_Integration.md
│   ├── Phase6_Einfuehrung_und_Projektabschluss.md
│   ├── Projektstatusuebersicht.md
│   └── Technisches_Referenzdokument.md
├── HELP.md
├── nbactions.xml
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── de
│   │   │       └── ea234
│   │   │           └── epost
│   │   │               ├── config
│   │   │               │   └── EPostConfig.java
│   │   │               ├── controller
│   │   │               │   ├── RestControllerDokument.java
│   │   │               │   ├── RestControllerWorkflow.java
│   │   │               │   ├── ScheduledTasks.java
│   │   │               │   ├── ViewControllerKunde.java
│   │   │               │   ├── ViewControllerLogin.java
│   │   │               │   ├── ViewControllerVorgang.java
│   │   │               │   ├── ViewControllerVorgangstypen.java
│   │   │               │   └── ViewErrorController.java
│   │   │               ├── EpostApplication.java
│   │   │               ├── model
│   │   │               │   ├── AppTestInit.java
│   │   │               │   ├── benutzer
│   │   │               │   │   ├── Benutzer.java
│   │   │               │   │   └── BenutzerList.java
│   │   │               │   ├── dokument
│   │   │               │   │   ├── Dokument.java
│   │   │               │   │   └── DokumentList.java
│   │   │               │   ├── kunden
│   │   │               │   │   ├── Adresse.java
│   │   │               │   │   ├── Kunde.java
│   │   │               │   │   ├── KundeList.java
│   │   │               │   │   ├── UhEintrag.java
│   │   │               │   │   └── Vertrag.java
│   │   │               │   ├── RequestContext.java
│   │   │               │   ├── vorgang
│   │   │               │   │   ├── TagesEingang.java
│   │   │               │   │   ├── TagesEingangList.java
│   │   │               │   │   ├── Vorgang.java
│   │   │               │   │   ├── VorgangsList.java
│   │   │               │   │   ├── Vorgangstyp.java
│   │   │               │   │   ├── VorgangstypList.java
│   │   │               │   │   └── VorgangXml.java
│   │   │               │   └── workflow
│   │   │               │       ├── activities
│   │   │               │       │   ├── ActivityStatistikAbschluss.java
│   │   │               │       │   ├── ActivityVorgangBearbeiten.java
│   │   │               │       │   ├── ActivityVorgangStart.java
│   │   │               │       │   └── ActivityWorkflow.java
│   │   │               │       ├── VerzeichnisUeberwacher.java
│   │   │               │       ├── WfAktivitaet.java
│   │   │               │       ├── WorkflowAktivitaet.java
│   │   │               │       ├── WorkflowBackgroundAgent.java
│   │   │               │       └── WorkflowStatus.java
│   │   │               ├── repository
│   │   │               │   ├── AdresseRepository.java
│   │   │               │   ├── BenutzerRepository.java
│   │   │               │   ├── DokumentRepository.java
│   │   │               │   ├── KundeRepository.java
│   │   │               │   ├── TageseingangRepository.java
│   │   │               │   ├── UnterlagenHistorieRepository.java
│   │   │               │   ├── VertragstypRepository.java
│   │   │               │   ├── VorgangRepository.java
│   │   │               │   └── VorgangstypRepository.java
│   │   │               ├── services
│   │   │               │   ├── ServiceListBenutzer.java
│   │   │               │   ├── ServiceListDokument.java
│   │   │               │   ├── ServiceListKunde.java
│   │   │               │   ├── ServiceListVorgaenge.java
│   │   │               │   ├── ServiceListVorgangstypen.java
│   │   │               │   ├── ServiceTagesEingang.java
│   │   │               │   └── TestDokumentErsteller.java
│   │   │               └── util
│   │   │                   ├── druck
│   │   │                   │   ├── ElementCsvZeile.java
│   │   │                   │   ├── ElementDokument.java
│   │   │                   │   ├── ElementImage.java
│   │   │                   │   ├── Element.java
│   │   │                   │   ├── ElementNeueSeite.java
│   │   │                   │   ├── ElementSeitenFussleiste.java
│   │   │                   │   ├── ElementSeitenUeberschrift.java
│   │   │                   │   ├── ElementStyle.java
│   │   │                   │   ├── ElementStyles.java
│   │   │                   │   ├── ElementTextBoxed.java
│   │   │                   │   ├── ElementText.java
│   │   │                   │   ├── ElementTextWert.java
│   │   │                   │   ├── ElementVector.java
│   │   │                   │   ├── FkStringFeld.java
│   │   │                   │   ├── ITextElementPrinter.java
│   │   │                   │   ├── ITextStyleCache.java
│   │   │                   │   ├── ITextStyle.java
│   │   │                   │   ├── Logo.java
│   │   │                   │   ├── NewLineStringBuffer.java
│   │   │                   │   ├── PDF.java
│   │   │                   │   └── TestDokumentKunde.java
│   │   │                   ├── FkDatei.java
│   │   │                   ├── FkDatum.java
│   │   │                   ├── FkDatumLong.java
│   │   │                   ├── FkHtml.java
│   │   │                   ├── FkPaginiernummer.java
│   │   │                   ├── FkPdf.java
│   │   │                   └── FkZahl.java
│   │   └── resources
│   │       ├── application-h2.properties
│   │       ├── application-mysql.properties
│   │       ├── application.properties
│   │       ├── static
│   │       │   ├── 2026012100002.pdf
│   │       │   ├── 2026012100004.pdf
│   │       │   ├── css
│   │       │   │   └── style.css
│   │       │   ├── favicon-16x16.png
│   │       │   ├── favicon-32x32.png
│   │       │   └── TestPdfNachladen.html
│   │       └── templates
│   │           ├── epost-login.html
│   │           ├── error.html
│   │           ├── kunde-details.html
│   │           ├── kunde-list.html
│   │           ├── typen-list.html
│   │           ├── vorgang-details.html
│   │           └── vorgangs-list.html
│   └── test
│       └── java
│           └── de
│               └── ea234
│                   └── epost
│                       ├── controller
│                       │   ├── RestControllerDokumentTest.java
│                       │   ├── ViewControllerKundeTest.java
│                       │   ├── ViewControllerLoginTest.java
│                       │   └── ViewControllerVorgangTest.java
│                       ├── EpostApplicationTests.java
│                       ├── model
│                       │   ├── benutzer
│                       │   │   └── BenutzerListTest.java
│                       │   └── kunden
│                       │       ├── KundeTest.java
│                       │       └── UhEintragTest.java
│                       ├── repository
│                       │   ├── DokumentRepositoryTest.java
│                       │   └── VorgangRepositoryTest.java
│                       └── services
│                           ├── ServiceListBenutzerTest.java
│                           └── ServiceListVorgaengeTest.java
└── temp_doc
    ├── alt_vorgang-details.html
    ├── db_tabellen.txt
    └── dokument_nachladen_1.txt


```

**Begründung der Schichtung:**
- *config*: Speicherung der Konfigurationsdaten aus der application.context klasse  
- *controller*: REST-Schnittstellen und View-Controller
- *model*: Anwendungsklassen 
- *repository*: Definition der Datenbankklassen  
- *service*: Interfaces für die Serviceimplementierungen innerhalb des Modells  
- *util*: Generelle Util-Klassen (Der Präfix "Fk" steht dabei für Funktionskomponente)
- *test*: Testklassen

---

## 3. Build- und Laufzeitumgebung
- Programmiersprache: Java 21  
- Framework: Spring Boot 3.5.7  
- Build-Tool: Maven (inkl. Wrapper)  
- Laufzeit: Embedded Apache Tomcat  
- Standard-Port: 8080  

---

## 4. Abhängigkeiten
- spring-boot-starter-web – REST-API & Tomcat  
- spring-boot-starter-validation – Bean Validation  
- openpdf 3.0.0 – Erstellung der PDF-Testdokumente 
- iban4j 3.2.7 – IBAN-Validierung  
- spring-boot-starter-test – Testframework  

---

## 5. Konfiguration
In der aktuellen MVP-Version werden überwiegend Default-Werte verwendet.
Eine spätere Externalisierung in `application.properties` ist vorgesehen, z. B. für:
- HTTP-Timeouts  
- maximale PDF-Größe  
- Blacklist-Quelle  

