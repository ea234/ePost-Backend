
# ePost  
## Phase 3 – System- und Softwareentwurf

**Autor:** Eckbert Andresen  
**Datum:** 23.12.2025  
**Version:** 1.0  

---

## 1. Ziel und Abgrenzung
Dieses Dokument beschreibt den technischen System- und Softwareentwurf der Anwendugn *ePost*.
Es konkretisiert die Anforderungen aus dem Lastenheft (Phase 2) und legt fest, wie die funktionalen
und nicht-funktionalen Anforderungen technisch umgesetzt werden.

---

## 2. Systemarchitektur
Die Anwendung ePost ist als Java Anwendung umgesetzt.
Die technische Basis bilden **Java 21** und **Spring Boot 3.5.7**.

Die Anwendung folgt einer **mehrschichtigen Architektur**, um eine klare Trennung von
Verantwortlichkeiten sicherzustellen sowie Wartbarkeit und Erweiterbarkeit zu fördern.

### 2.1 Architekturdiagramm
Das Architekturdiagramm zeigt die Schichten **Controller**, **Service**, **Domain** und
**Infrastruktur** sowie deren Interaktion. Externe Bibliotheken sind klar von der
Geschäftslogik getrennt.

### 2.2 Schichten und Verantwortlichkeiten

**Controller-Schicht**
- Oberflächensteuerung durch View-Controller
- Verfügungstellung von REST-Controllern (Anzeige PDF)
- Delegation an die Service-Schicht
- Erstellung von Fehlern im Thymeleaf Modell


**Service-Schicht**
- Transportobjekte für Request und Response
- Bean Validation zur Eingabeprüfung
- Zentrale Geschäftslogik
- Orchestrierung des Scan-Ablaufs  
  PDF-Download → Textextraktion → IBAN-Erkennung → Blacklist-Abgleich

### 2.3 Externe Bibliotheken
- **openpdf 3.0.0** – Erstellung von PDF-Testdaten (ehemals iText 4)
- **datafaker** – Erstellung von Testdaten

---

## 3. Komponentenmodell
Zentrale Komponenten des Systems sind:

- **ViewControllerVorgang** – View-Controller für die Vorgangsbearbeitung
- **VerzeichnisUeberwacher** – Ablaufsteuerung für den Posteingang und Vorgangserstellung
- **WorkflowBackgroundAgent** – Ablaufsteuerung für die Vorgänge in die einzelnen Aktivitäten

---

## 4. Schnittstellen (REST-API)

### GET `/api/dokument/paginr/{paginr}`

**Request:**  
Beispiel: http://localhost:8080/api/dokument/paginr/2026012000003

Aufruf mit einer Paginiernummer.


**Response:**  
- Byte-Daten des angeforderten Objektes als Application/PDF
<br/>  

**HTTP-Statuscodes**
- **200 OK** – Dokument zur Paginiernummer gefunden  
- **400 Bad Request** – ungültige Paginiernummer  
- **404 Not Found** – PDF nicht erreichbar  
<br/>  
<br/>  
<br/>  

### GET `api/wf/startTestdokumente`

**Request:**  
Beispiel: http://localhost:8080/api/wf/startTestdokumente

Start fuer die Erstellung von Testdokumenten im Eingangsverzeichnis.


**Response:**  
- Json-Struktur

````
    {
      "datum" : "03.02.2026",
      "message" : "Testdokumente wurden erstellt - Freitag 03.02.2026 09:51:40",
      "zeit" : "09:51:40",
      "status" : "OK"
    }
````
<br/>  

**HTTP-Statuscodes**
- **200 OK** – Testdokumenterstellung wurde gestartet. 
<br/>  
<br/>  
<br/>  

### GET `api/wf/startVzUeberwachung`

**Request:**  
Beispiel: http://localhost:8080/api/wf/startVzUeberwachung

Manueller Start der Eingangs-Verzeichnisüberwachung 


**Response:**  
- Json-Struktur

````
    {
      "datum" : "03.02.2026",
      "message" : "Verzeichnisueberwachung manuell gestartet - Freitag 03.02.2026 10:02:45",
      "zeit" : "10:02:45",
      "status" : "OK"
    }
````
<br/>  

### HTTP-Statuscodes
- **200 OK** – Testdokumenterstellung wurde gestartet. 
<br/>  
<br/>  
<br/>  


### GET `api/wf/startWfBackgrounder`

**Request:**  
Beispiel: http://localhost:8080/api/wf/startWfBackgrounder

Manueller Start des Workflow-Backgrounders 


**Response:**  
- Json-Struktur

````
    {
      "datum" : "06.02.2026",
      "message" : "Background-Agent manuell gestartet -Freitag 06.02.2026 10:05:37",
      "zeit" : "10:05:37",
      "status" : "OK"
    }
````

### HTTP-Statuscodes
- **200 OK** – Testdokumenterstellung wurde gestartet. 
<br/>  
<br/>  
<br/>  




---

## 5. Datenmodell und DTOs

### Benutzer

**Benutzer**
- Speichert einen Benutzer der Anwendung

**BenutzerList**
- Stellt die Benutzerliste aus der Datenbank bereit
- Hinzufügen von Benutzern

### Archiv

**Dokument**
-Stellt ein Dokument in der Archiv-Datenbank dar

**DokumentList**
-Stellt die gespeicherten Dokumente der Archivdatenbank zur Verfügung

### Kunde

**Kunde**
- Stellt die Basisdaten für einen Kunden bereit
- Der Kunde hat eine Adresse
- Der Kunde kann mehrere Verträge haben
- Der Kunde verfügt über eine Unterlagenhistorie der eingegangenen Poststücke

**Adresse**
- Speichert eine Adresse

**UhEintrag**
- Speichert einen Unterlagenhistoriendatensatz

**Vertrag**
- Speichert die Daten für einen Vertrag des Kunden


### Vorgang

**Vorgang**




---

## 6. Ablaufbeschreibung 

---

## 7. Nicht-funktionale Anforderungen (Design-Umsetzung)


**Wartbarkeit**
- Modulare Struktur
- Erweiterbarkeit um neue Workflow

**Testbarkeit**
- Unit-Tests für Kernkomponenten
- Integrationstests für den Gesamtablauf

---

## 8. Fehlerbehandlung und Logging
Fehler werden eindeutig klassifiziert und auf HTTP-Statuscodes abgebildet.
Logging erfolgt in den Stufen **INFO**, **WARN** und **ERROR** ohne Speicherung sensibler Daten.

---

## 9. Konfiguration
Die Konfiguration erfolgt zentral über `application.properties`, u. a.:
- HTTP-Timeouts

---

## 10. Qualitätssicherung und Tests
Die Architektur unterstützt automatisierte Unittests.

---


## 11. Build, Laufzeit und Abhängigkeiten

**Build**
- Maven (`mvn clean package`)
- Ausführbares Spring-Boot-JAR

**Laufzeit**
- Java 21
- Embedded Tomcat
- Standard-Port 8080

**Abhängigkeiten**
- spring-boot-starter-web
- spring-boot-starter-validation
- spring-boot-starter-test
- maria db
