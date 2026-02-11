
# ePost  
## Phase 5 – Test und Integration

**Autor:** Eckbert Andresen  
**Datum:** 23.12.2025  
**Version:** 1.0  

---

## 1. Ziel der Testphase
Ziel der Test- und Integrationsphase ist die systematische Überprüfung der implementierten
Funktionalitäten des *ePost*. Es soll sichergestellt werden, dass alle
Anforderungen aus den vorherigen Phasen korrekt umgesetzt wurden und das System stabil,
zuverlässig und reproduzierbar arbeitet.

---

## 2. Teststrategie
Die Teststrategie basiert auf einer Kombination aus:
- **Unit-Tests** zur Prüfung einzelner Komponenten  
- **Integrationstests** zur Überprüfung des Gesamtablaufs  
- **Smoke-Tests** zur schnellen Verifikation nach dem Deployment  

Automatisierte Tests stehen im Vordergrund, um eine kontinuierliche Qualitätssicherung
zu ermöglichen.

---

## 3. Testumgebung
- Java 21  
- Spring Boot Test Framework  
- JUnit 5  
- Maven  
- Test-PDFs im Projekt (Blacklisted / Non-Blacklisted / No-IBAN)

---

## 4. Unit-Tests

### 4.1 Ziel
Unit-Tests prüfen isoliert die fachliche Logik einzelner Klassen ohne Abhängigkeit von
externen Systemen.

### 4.2 Getestete Komponenten
- **IbanScanner** – Erkennung und Validierung von IBANs  
- **PdfTextExtractor** – Textextraktion aus PDF-Dokumenten  
- **InMemoryBlacklistRepository** – Erkennung von Blacklist-Treffern  

### 4.3 Bewertung
Die Unit-Tests stellen sicher, dass die Kernlogik korrekt funktioniert und fehlerhafte
Eingaben robust behandelt werden.

---

## 5. Integrationstests

### 5.1 Ziel
Integrationstests prüfen das Zusammenspiel aller Komponenten über den REST-Endpunkt.

### 5.2 Vorgehen
- Start des Spring Application Context  
- Aufruf des Endpunkts **POST `/api/v1/invoice-scan`**  
- Verwendung realer Test-PDFs  
- Überprüfung von HTTP-Statuscodes und Response-Daten  

### 5.3 Beispieltestfälle
- PDF mit gesperrter IBAN → HTTP 200, Blacklist-Treffer vorhanden  
- PDF ohne gesperrte IBAN → HTTP 200, keine Blacklist-Treffer  
- PDF ohne IBAN → HTTP 200, leere Ergebnislisten  
- Ungültige Eingabe → HTTP 400  

---

## 6. Smoke-Tests
Nach dem Build und Start des Services wird ein Smoke-Test durchgeführt, um die
grundsätzliche Funktionsfähigkeit zu verifizieren.

**Beispiel:**
- Start mit `java -jar invoicescanner.jar`  
- REST-Aufruf per `curl`  
- Erwartetes Ergebnis: HTTP 200 und gültige JSON-Antwort  

---

## 7. Testdaten
Für die Tests werden drei kontrollierte PDF-Dateien verwendet:

### Testdata_Blacklisted.pdf
- Enthält mehrere gültige IBANs  
- Mindestens eine IBAN ist in der Blacklist  

**Erwartung:**
- `extractedIbans` ≥ 1  
- `blacklistedIbans` ≥ 1  

### Testdata_None_Blacklisted.pdf
- Enthält gültige IBANs  
- Keine davon ist in der Blacklist  

**Erwartung:**
- `extractedIbans` ≥ 1  
- `blacklistedIbans` = []  

### Testdata_No_Ibans.pdf
- Enthält keine IBANs  

**Erwartung:**
- `extractedIbans` = []  
- `blacklistedIbans` = []  

Die Testdaten sind anonymisiert und enthalten keine realen sensiblen Informationen.

---

## 8. Testabdeckung und Qualitätssicherung
Die Kombination aus Unit- und Integrationstests gewährleistet eine hohe Testabdeckung
der zentralen Geschäftslogik und der REST-Schnittstelle.

Fehler werden frühzeitig erkannt und können gezielt behoben werden.

---

## 9. Testergebnisse
Alle implementierten Tests wurden erfolgreich ausgeführt.
Es traten keine kritischen Fehler oder Blocker auf.

Die Testergebnisse bestätigen die funktionale Korrektheit und Stabilität des Systems.

---

## 10. Fazit der Testphase
Der ePost erfüllt die definierten funktionalen und nicht-funktionalen
Anforderungen. Das System ist stabil, testbar und bereit für die Einführung
(**Phase 6 – Einführung und Projektabschluss**).
