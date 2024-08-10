# Überprüfung auf Updates

Eine regelmäßige Überprüfung auf Updates von Plugins und Dependencies ist aus mehreren Gründen essenziell für die 
Softwareentwicklung. Erstens garantiert sie Sicherheit, indem sie sicherstellt, dass alle verwendeten Komponenten auf 
dem neuesten Stand sind und bekannte Sicherheitslücken geschlossen werden. Zweitens verbessert sie die Leistung und 
Stabilität der Software, da Updates oft Optimierungen und Fehlerbehebungen enthalten. Drittens ermöglicht die 
Aktualisierung den Zugang zu neuen Funktionen und Technologien, was die Entwicklung effizienter und zukunftssicher 
macht. Durch das regelmäßige Aktualisieren wird zudem die Kompatibilität mit anderen Tools und Systemen sichergestellt,
was die Integration und Wartung vereinfacht.

## Codehaus Version Plugin

Das Codehaus Versions Maven Plugin ist ein nützliches Werkzeug in der Softwareentwicklung, da es überprüft, ob neuere
Versionen von Plugins oder Dependencies verfügbar sind. Es hilft Entwicklern, ihre Projekte auf dem neuesten Stand zu
halten, indem es automatisch nach Updates sucht und Vorschläge für mögliche Upgrades macht. Diese Funktionalität ist
besonders wichtig, um sicherzustellen, dass die verwendeten Komponenten aktuell sind und um von den neuesten
Sicherheitspatches, Fehlerbehebungen und Leistungsverbesserungen zu profitieren. Das Plugin bietet eine einfache und
effiziente Möglichkeit, die Softwarewartung zu optimieren und die Softwarequalität zu verbessern.

## Einbindung in das Projekt

Das Plugin ist sehr einfach in ein Projekt einbindbar:
```xml
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>versions-maven-plugin</artifactId>
                <version>${codehaus.version.plugin}</version>
                <executions>
                    <execution>
                        <phase>validate</phase>
                        <goals>
                            <goal>display-dependency-updates</goal>
                            <goal>display-plugin-updates</goal>
                            <goal>display-property-updates</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

Wichtig ist, dass die Ziele sowohl für Abhängigkeiten, Plugins als auch Properties ausgeführt wird, damit kein Update
übersehen wird.

## Probleme

Die Verfügbarkeit von Updates für Plugins und Dependencies in einem Maven-Projekt kann teilweise von der verwendeten
Version von Maven selbst abhängen. Neuere Versionen von Maven unterstützen oft aktuellere Plugins und Dependencies,
die verbesserte Funktionen und Sicherheitsupdates bieten können. Daher ist es wichtig, sicherzustellen, dass ein
Projekt mit einer spezifischen, minimal erforderlichen Maven-Version betrieben wird.

Um dies zu gewährleisten, kann das Maven Enforcer Plugin eingesetzt werden. Dieses Plugin erlaubt es, bestimmte Regeln
innerhalb der Build-Umgebung durchzusetzen, darunter auch die Anforderung einer minimalen Maven-Version. Durch die
Konfiguration des Maven Enforcer Plugins im Build-Prozess wird sichergestellt, dass der Build nur dann erfolgreich
durchgeführt wird, wenn die verwendete Maven-Version der definierten Mindestanforderung entspricht. Dies hilft,
Inkonsistenzen und potenzielle Fehler aufgrund von Versionskonflikten zu vermeiden und fördert die Stabilität und
Sicherheit des Projekts.

Einbindung des Maven Enforcer Plugins:
```xml
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-enforcer-plugin</artifactId>
                <version>${maven.enforcer.plugin}</version>
                <executions>
                    <execution>
                        <id>enforce-versions</id>
                        <goals>
                            <goal>enforce</goal>
                        </goals>
                        <configuration>
                            <rules>
                                <requireMavenVersion>
                                    <version>${required.maven.version}</version>
                                </requireMavenVersion>
                            </rules>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
```

## Ergebnis

Während des Build-Vorgangens bekommt man eine Ausgabe ähnlich:
```text
[INFO] The following version properties are referencing the newest available version:
[INFO]   ${codehaus.version.plugin} ................................... 2.16.2
[INFO]   ${javafx.maven.plugin} ........................................ 0.0.8
[INFO] The following version property updates are available:
[INFO]   ${javafx.version} ................................. 21.0.3 -> 23-ea+3
[INFO]   ${junit.version} ................................ 5.10.2 -> 5.11.0-M1
[INFO]   ${maven.site.plugin} ......................... 4.0.0-M13 -> 4.0.0-M14
```

**Hinweis** Das Tool führt keine Bewertung der Updates durch. So zeigt die kopierte Ausgabe drei mögliche
Aktualisierungen:

- javafx.version könnte auf eine early access Version aktualisiert werden. Das ist etwas, das man sich natürlich gut
überlegen sollte (und bei JavaFX kann es sinnvoll sein, die Main-Version gleich zur Java Version zu halten).
- Für JUnit gibt es eine neue Milestone Version. Auch hier kann es Sinn machen, keine Milestone Versionen zu verwenden.
- Für das Site Version wird bereits auf eine Milestone Version gesetzt. Hier macht das Update dann durchaus Sinn.

**Es wird also deutlich, dass hier eine bewusste Bewertung stattfindet, die vom Plugin nicht geleistet werden kann.**