
# ePost  
## Phase 2 – Initiierung / Lastenheft

**Autor:** Eckbert Andresen  
**Datum:** 23.12.2025  
**Version:** 1.0  

---

## 1. Ziel der Phase
Ziel dieser Phase ist die vollständige Beschreibung der fachlichen und technischen Anforderungen
an den *ePost*. Das Lastenheft bildet die verbindliche Grundlage für den
Systementwurf (Phase 3), die Implementierung (Phase 4) sowie die Testphase (Phase 5).

---

## 2. Projektbeschreibung
Die Anwendung ePost ist eine HTML-Anwendung für die Vorgangsbearbeitung.
Die Vorgänge werden dabei aus den Meta-Daten der Posteingangs-XML-Datei erstellt.

Die Posteingänge werden als PDF-Dokument bereitgestellt und in der Datenbank gespeichert.
  
Die Metadaten zu einem Posteingang werden in einer XML-Datei zur Verfüugng gestellt.
   
Die Vorgangsdaten werden in einer Tabelle gespeichert. 

Diese Tabelle dient dem Workflowsystem für die Bereitstellung der Vorgänge für den Anwender.

---

## 3. Zielsetzung
Entwicklung einer Anwendug zur Bearbeitung von Posteingängen.
---

## 4. Funktionale Anforderungen
- Entgegennahme der Posteingänge in einem Eingangsverzeichnis.
- Automatische Verarbeitung der Posteingänge (Verzeichnisscan)
- Erstellung von Vorgängen aus den Meta-Daten.
- Bereitstellung einer Oberfläche für die Anwender

---

## 5. Nicht-funktionale Anforderungen
- **Sicherheit:** Sicherheit durch Log-In 
- **Wartbarkeit:** Klare Schichtenarchitektur (Controller, Service, DTO)  
- **Testbarkeit:** Unterstützung von Unit- und Integrationstests  

---

## 6. Liefergegenstände
- Vollständiger Quellcode der Anwendung ePost  
- Maven-Projekt (Java 21, Spring Boot)  
- Projektdokumentation  

---

## 7. Abnahmekriterien
- Dokumente können gespeichert werden
- Vorgänge werden durch den Workflow geroutet
- Anwendung ist lokal stabil ausführbar  

---

## 8. Systemumgebung
- Java 21  
- Spring Boot  
- Maven  
- openpdf (Erstellung Testdokumente)
- Maria-Datenbank
