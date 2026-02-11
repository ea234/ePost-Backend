
# ePost Vorgangsbearbeitung 
## Phase 1 – Voruntersuchung / Durchführbarkeitsuntersuchung

**Autor:** Eckbert Andresen  
**Datum:** 23.12.2025  
**Version:** 1.0  

---

## 1. Ziel der Voruntersuchung
Ziel dieser Phase ist es, die fachliche, technische und organisatorische Durchführbarkeit 
des Projekts *ePost* zu bewerten. 

---

## 2. Prüfung der Kompetenz

### 2.1 Fachliche Kompetenz
Ich verfüge über fundierte Kenntnisse in der Java-Entwicklung, insbesondere im Umgang mit Spring Boot, Maven und der Entwicklung REST-basierter Services.

Die fachliche Aufgabenstellung – das Erkennen gesperrter (blacklisted) IBANs in Rechnungs-PDFs – ist klar definiert, praxisnah und entspricht realen Anforderungen aus dem Finanz- und Compliance-Umfeld.

### 2.2 Technische Kompetenz
Die geplante Systemumgebung basiert auf Java 21 und Spring Boot 3.5.7.

Die eingesetzten Bibliotheken **Apache PDFBox** zur Textextraktion sowie **iban4j** zur IBAN-Validierung sind bewährte und stabile Open-Source-Komponenten.

Die Leistungsfähigkeit des Systems hängt von der Größe der PDF-Dateien und der Anzahl gleichzeitiger Anfragen ab. Diese Aspekte können bei Bedarf durch geeignete Maßnahmen wie Timeouts, Größenlimits und Parallelisierung optimiert werden.

### 2.3 Personelle Kompetenz
Das Projekt kann von mir als Entwicklerin eigenständig umgesetzt werden.

Die notwendigen fachlichen und technischen Kenntnisse sind vorhanden, zusätzliche personelle Ressourcen sind nicht notwendig.

---

## 3. Auswahl des Produktes
Entwickelt wird eine Vorgangssteuerung mit den Namen *ePost*.
  
Das System gliedert sich in 2 Teilbereiche auf:
- Dokumentenspeicherung
- Dokumentenbearbeitung

In dieser Anwendung können durch Benutzer, Vorgänge angenommen und abgeschlossen werden.  

Die Vorgänge sollen in einer Liste für den Sachbearbeiter auswählbar sein.

Die Vorgänge werden über einen internen Workflow durch Aktivitäten geschleust.  

Die Anwendung dient als Prototyp um eine alte Lotus-Notes-Anwendung abzulösen.

---

## 4. Voruntersuchung

### 4.1 Ist-Zustand
Das zugrunde liegende Altsystem basiert auf einer Lotus-Notes-Workflow Anwendung.  

Die Vorgänge entstanden im Altsystem auch aus PDF und XML-Dateien.  
Die PDF-Dateien wurden in einem Archiv der Firma SER gespeichert.  

Dort konnten Benutzer Vorgänge bearbeiten.

Das Altsystem bestand aus einer Lotus-Notes-Workflow Anwendung. 
Dort konnten Benutzer Vorgänge bearbeiten.

Die Dokumente wurden in einem SER-Archiv gespeichert. 

#### Vorstellung Workflowsystem ALT
Grob bestand der Lotus-Notes-Workflow aus folgenden Teilen

START
- Ein Vorgang wurde aufgrund einer XML-Datei initiiert.
- In der XML-Datei standen die notwendigen Daten drin
	- Paginiernummer – Identifizierte das Dokument
	- Vertragsnummer – Identifizierte den Kunden
   	- UID – Identifizierte die Dokumentenart  

  Diese Daten wurden dem Vorgang für die weitere Verarbeitung mitgegeben.
  Die Speicherung der Daten erfolgte durch ein Lotus-Notes Dokument.

RECHERCHE
- Der Vorgang wurde um die Kundendaten ergänzt.

STATISTIK – Eingang
- In der Statistiktabelle wurde für den Vorgang ein Datensatz erstellt (Dokument eingegangen)

WORKFLOW
- Bei bestimmten Dokumententypen wurden automatische Workflows gestartet.
- Es gab Dokumententypen, bei welchem eine manuelle Bearbeitung nicht vorgesehen war.
  Bei diesen Dokumententypen wurde die manuelle Vorgangsbearbeitung übersprungen. 
  Ziel war es hier, dass nicht jeder Dokumententyp eine manuelle Bearbeitung auslöst, oder dass 
  die manuelle Bearbeitung durch die Ausführung des hier gemachten Workflows nicht mehr notwendig war.

VORGANGSBEARBEITUNG 
- Die Vorgangsbearbeitung war eine manuelle Workflow-Aktivität
- Ein Vorgang konnte diese Workflow-Punkt nur durch manuelle Bearbeitung verlasssen.
	- Vorgang bearbeiten und abschliessend beenden (Weiter nach Statisik-Abschluss)
	- Vorgang an ein anderes Team weiterleiten (= Weiterleitung nach Recherche)
	- Vertragsnummer konnte sich ändern (= Weiterleitung nach Recherche)
	- Vorgangstyp konnte sich ändern (= Weiterleitung nach Recherche)
- Die Vorgangsbearbeitung konnte bei automatischen Dokumententypen übersprungen werden.

STATISTIK - Abschluss
- In der Statistiktabelle für den Vorgang wurde der Vorgang auf bearbeitet gesetzt
- In der Unterlagenhistorie für den Kunden, wurde ein Datensatz für das Dokument gespeichert.
  (=Kunde hat am Datum, das Dokument geschickt, das Dokument wurde mit der Paginiernummer identifiziert.)

ENDE
- Hier wurden Vorgänge noch für 60 Tage gespeichert.
- Danach wurden die Vorgänge aus der Notes-Datenbank gelöscht.

#### Nachteile Workflowsystem ALT
- Jeder Vorgang bestand aus 2 Notes-Dokumenten (Cover- und Main-Dokument).
Das war für die Vorgangsbearbeitung unnötig kompliziert.
- Es kam häufiger vor, dass die Workflow-Engine die Vorgänge nicht weiterleiten konnte, 
da irgendwo ein Notes-Feld nicht korrekt gefuellt war, wie der Workflow es haben wollte.
Das waren unnötige Recherche und Reperaturaufwände.
- Die potentiellen Bearbeiter eines Vorganges wurden im Vorgang selber vorgehalten.
Bei neuen Mitarbeitern war dieses hinderlich. 
In solchen Fällen mussten alle Vorgänge einmal neu "geroutet" werden, damit eine 
neue Bearbeiterliste berechnet wurde. Das führte zu Last auf dem Server.
- Der Workflow-Backgrounder durfte nicht manuell gestartet werden. Dieses 
hätte Racing-Conditions zur folge gehabt. (2 Backgrounder Läufe verarbeiten den gleichen Vorgang)

### 4.2 Soll-Zustand
Es soll eine neuere und verbesserte Version dieses Systems erstellt werden.  
Das neue System soll die Nachteile des Altsystems nicht mehr beinhalten:  
- keine Speicherung der Benutzerliste im Vorgang selber. 
Das System soll zur Laufzeit die Berechtigung des Benutzers für den Vorgang prüfen.
- Die Verzeichnisüberwachung und der Backgrounder können jederzeit manuell gestartet werden.
Sofern der Backgrounder schon läuft, wird dieser eben kein zweites mal paralel gestartet. 
Gleiches mit der Verzeichnisüberwachung.

---

## 5. Festlegen der Hauptanforderungen

### 5.1 Hauptfunktionen
- Speicherung von Posteingangsdokumenten   
- Erstellung von Vorgängen für die Posteingänge 
- Weiterleitung der Vorgänge durch Aktivitäten  
- Bearbeitbarkeit der Vorgänge durch Benutzer
- Speicherung von Unterlagenhistoriensätzen bei abgeschlossenenen Vorgängen


### 5.2 Hauptdaten
  

**Eingabedaten:**  
- PDF-Datei des eingescanten Posteinganges
- XML-Datei mit den Metadaten des Posteinganges
- RDY-Datei welche die Verarbeitbarkeit von PDF und XML-Datei anzeigt.  


**Ausgabedaten:**  
- Unterlagenhistorie für die Vorgägne in der der Kundendatenbank für abgeschlossene Vorgänge.  
- Speicherung der Dokumente in der Archivdatenbank  


### 5.5 Haupt-Qualitätsmerkmale
- **Zuverlässigkeit:** korrekte Verarbeitung der Vorgänge un schreiben der Unterlagenhistorie
- **Wartbarkeit:** modularer Aufbau (Controller, Services, DTOs)  
- **Erweiterbarkeit:** einfache Integration zusätzlicher Workflow-Aktivitätenn  
- **Testbarkeit:** Unittests  

