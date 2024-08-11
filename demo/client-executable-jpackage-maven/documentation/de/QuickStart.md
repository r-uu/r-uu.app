# Schnellstart

- Lade einfach das Projekt als ZIP Datei von GitHub und entpacke es an einer 
Stelle Deiner Wahl auf dem Computer.
- Öffne die pom.xl und ändere die Einstellungen am Anfang des Dokumentes.

**Hinweis**: Im folgenden werden die Befehle für Unix artige Systeme / macos
angegeben. Unter Windows kannst Du am Anfang des Befehles das ./mvnw durch mvnw
ersetzen.

**Hinweis**: Die Befehle müssen im Hauptverzeichnis des Projekts, in dem auch
die pom.xml ist, ausgeführt werden.

## Wie kannst Du das Projekt nutzen

### Start der Anwendung von der Kommandozeile
```./mvnw javafx:run```

### Projekt bereinigen

Um das Projekt zu bereinigen, kannst Du
```./mvnw clean```
aufrufen.

### Übersetzen der Anwendung (Ohne ein Image zur Weitergabe zu bauen)

Um die Anwendung zu übersetzen kannst Du aufrufen:
```./mvnw package```

### Bau des Images zur Weitergabe

Um das Image zu bauen, rufst du einfach Maven mit dem Profil Image und dem
Ziel install auf:
```./mvnw -Dimage install```

**Wichtig** Du kannst nicht das JavaFX Plugin mit dem Ziel javafx:jlink verwenden,
da dieses Plugin zwingend eine Modulbeschreibung für das Projekt und alle
Abhängigkeiten erfordert.

### Komplette Übersetzung des Projekts incl. Dokumentation
Mit dem folgenden Befehl lässt sich das ganze Projekt von grundauf neu übersetzen incl.
der Erstellung der HTML Dokumentation des Projektes:
```./mvnw -Dimage clean install site```

- **-Dimage** aktiviert das Profil image, das für den Bau des Application Images zuständig ist
- **clean** Mit dem Ziel clean wird am Anfang alles, was bereits ggf. schon an Übersetzungen vorhanden ist, entfernt wird.
- **install** Durch install wird das ganze Projekt gebaut incl. Unit Tests, statischer Codeanalyse, ...
- **site** Es wird die Dokumentation des Projektes gebaut.

## Ergebnisse der statischen Codeanalyse

Die Codeanalyse läuft automatisch beim Bau des Projektes und die Ergebnisse
finden sich in:
- ./target/pmd.xml
- ./target/spotbugsXml.xml

Wenn die Site gebau wird, dann gibt es html Seiten mit den Ergebnissen von PMD und Spotbugs in der Site.