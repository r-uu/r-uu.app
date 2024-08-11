# Quick Start

- Simply download a zip file of this project and unzip it somewhere on your computer
- open the pom.xml and change the settings at the start of the document to fit with your project

**Important** The shown commands use a syntax that is used by unix and macos. If you
are using Windows then please replace the ./mvnw with mvnw only.

**Important** All commands shown should be issued in the root directoy of the project (the directory where you find the pom.xml)

## How to use this project

### Start the application from commandline
```./mvnw javafx:run```

### Clean up

To clean up the project, call
```./mvnw clean```

### build the application (Without building the application image)

To build the application, maven / the maven wrapper can be used. Simply do a
```./mvnw package```
to build the application.
(simply call mvnw instead of ./mvnw on windows!)

### Build the Image

To build the image, the profile Image must be used:
```./mvnw -Dimage install```

**Important** You cannot build an image using the javafx plugin. The javafx plugin requires that you build a modular
Java application and all dependencies providing a module description.

### complete build including documentation
To build the complete project from scratch and build all parts possible, you could use the following command:
```./mvnw -Dimage clean install site```

- **-Dimage** activates the profile image that is responsible to build the application image
- **clean** cleans everything of previous builds.
- **install** build the project (compile, build jar file, ...)
- **site** creates the html documentation of the project

## Static code analysis results

The static code analysis is done during the build of the application. The results can be found in
- ./target/pmd.xml
- ./target/spotbugsXml.xml
