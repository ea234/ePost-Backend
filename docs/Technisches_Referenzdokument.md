
# ePost  
## Technisches Referenzdokument

**Autor:** Eckbert Andresen  
**Datum:** 23.12.2025  
**Version:** 1.0  

---

## 1. Zweck des Dokuments
Dieses technische Referenzdokument dient als zentrale technische Nachschlagequelle für
Entwickler, Tester und technische Prüfer.

Es beschreibt Architektur, Schnittstellen, Komponenten, Konfiguration, Build- und
Laufzeitverhalten sowie Fehlerbehandlung und Erweiterungsmöglichkeiten des
**ePost**.

---

## 2. Systemüberblick
Der ePost ist ein **REST-basierter Microservice**, der PDF-Rechnungen
automatisiert analysiert und darin enthaltene IBANs erkennt, validiert und mit einer
Blacklist abgleicht.

**Hauptfunktionen:**
- Download von PDF-Rechnungen über HTTP/HTTPS  
- Textextraktion aus PDF-Dokumenten  
- Erkennung und Validierung von IBANs  
- Abgleich erkannter IBANs mit einer Blacklist  
- Rückgabe strukturierter JSON-Ergebnisse  

---

## 3. Technologiestack
- **Programmiersprache:** Java 21  
- **Framework:** Spring Boot 3.5.7  
- **Build-Tool:** Maven  
- **Webserver:** Embedded Apache Tomcat  
- **PDF-Verarbeitung:** Apache PDFBox 3.0.2  
- **IBAN-Validierung:** iban4j 3.2.7  
- **Tests:** JUnit 5, Spring Boot Test  
- **Betriebssystem:** Plattformunabhängig  

---

## 4. Architekturübersicht
Der Service folgt einer mehrschichtigen Architektur:

- Controller-Schicht – REST-Schnittstelle  
- DTO-Schicht – Request- und Response-Objekte  
- Service-Schicht – Geschäftslogik und Orchestrierung  
- Domain-Schicht – Fachliche Logik (IBAN-Erkennung)  
- Infra-Schicht – Technische Infrastruktur (HTTP, PDF)  
- Repository-Schicht – Blacklist-Zugriff  

Diese Trennung gewährleistet Wartbarkeit, Testbarkeit und Erweiterbarkeit.

---

## 5. Paketstruktur
```text
sim.refpro.invoicescanner
 ├── InvoiceScanApplication.java
 ├── controller
 │   └── InvoiceScanController.java
 ├── dto
 │   ├── InvoiceScanRequest.java
 │   └── InvoiceScanResponse.java
 ├── repository
 │   ├── BlacklistRepository.java
 │   └── InMemoryBlacklistRepository.java
 └── service
     ├── InvoiceScanService.java
     ├── InvoiceScanServiceImpl.java
     ├── domain
     │   └── IbanScanner.java
     └── infra
         ├── HttpDownloader.java
         └── PdfTextExtractor.java
```

---

## 6. REST-API-Referenz

### Endpoint
`POST /api/v1/invoice-scan`

### Request
```json
{
  "invoiceUrl": "http://example.com/invoice.pdf"
}
```

### Response (Beispiel)
```json
{
  "extractedIbans": ["DE89370400440532013000"],
  "blacklistedIbans": ["DE89370400440532013000"],
  "success": true,
  "message": "Blacklist IBAN detected"
}
```

---

## 7. HTTP-Statuscodes
- **200 OK** – Erfolgreicher Scan  
- **400 Bad Request** – Ungültiger Request  
- **404 Not Found** – PDF nicht erreichbar  
- **415 Unsupported Media Type** – Kein PDF  
- **500 Internal Server Error** – Unerwarteter Fehler  

---

## 8. Zentrale Komponenten

**InvoiceScanController**
- REST-Endpunkt
- Request-Validierung
- Delegation an Service-Layer

**InvoiceScanServiceImpl**
- Orchestrierung des Gesamtprozesses

**HttpDownloader**
- Download von PDFs
- Prüfung von Statuscodes und Content-Type

**PdfTextExtractor**
- Textextraktion aus PDFs
- Verarbeitung mehrseitiger Dokumente

**IbanScanner**
- Erkennung von IBAN-Kandidaten per Regex
- Validierung mit iban4j

**InMemoryBlacklistRepository**
- Verwaltung gesperrter IBANs
- Blacklist-Abgleich

---

## 9. Konfiguration
Die aktuelle Version verwendet überwiegend Default-Werte.
Vorbereitet sind Konfigurationsparameter für:
- HTTP-Timeouts  
- Maximale PDF-Größe  
- Blacklist-Quelle  

Die Konfiguration erfolgt über `application.properties`.

---

## 10. Build & Start

**Build**
```bash
mvn clean package
```

**Start**
```bash
java -jar invoicescanner.jar
```

**Standardport:** 8080

---

## 11. Teststrategie (Kurzreferenz)

**Manuelle Tests**
- Postman  
- Kommandozeile (`curl`)  

**Automatisierte Tests**
- JUnit 5  
- Spring Boot Test  

**Getestete Szenarien**
- Blacklist-Treffer  
- Keine Blacklist-Treffer  
- Keine IBANs  

---

## 12. Fehlerbehandlung & Logging

**Fehlerbehandlung**
- Strukturierte Exceptions  
- Klare HTTP-Statuscodes  
- Keine Weitergabe interner Details  

**Logging**
- INFO – regulärer Ablauf  
- WARN – behandelbare Sonderfälle  
- ERROR – unerwartete Fehler  

---

## 13. Sicherheitsaspekte
- Keine persistente Speicherung sensibler Daten  
- Validierung aller Eingaben  
- Keine lokalen Dateizugriffe  
- Vorbereitung für HTTPS-Betrieb  

---

## 14. Erweiterungsmöglichkeiten
- Externe Blacklist (Datenbank oder REST-API)  
- Unterstützung weiterer Dokumenttypen  
- KI-gestützte Analyse  
- Dashboard und Monitoring  

---

## 15. Fazit
Der ePost ist ein modularer, wartbarer und erweiterbarer Microservice,
der alle definierten Anforderungen erfüllt.

Dieses technische Referenzdokument ermöglicht es Dritten, das System zu verstehen,
zu betreiben und weiterzuentwickeln, ohne zusätzliche Projektunterlagen lesen zu müssen.
