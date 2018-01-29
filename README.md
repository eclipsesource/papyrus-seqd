# Papyrus Sequence Diagrams [![Build Status](https://travis-ci.org/eclipsesource/papyrus-seqd.svg?branch=master)](https://travis-ci.org/eclipsesource/papyrus-seqd)

## Developer information

This section provides information for developers, such as the project structure, how to set up tests, and how to build.

### Project structure

The project is structured in four folders.

* [`features`](features) contains the p2 features as well as the p2 repository configuraitons.
* [`plugins`](plugins) hosts all plug-ins. Find out more about the [ARCHITECTURE.MD](plug-in architecture).
* [`releng`](releng) encompasses all artifacts related to release engineering, such as the target platform and the maven parent.
* [`tests`](tests) contains the test plug-ins. Please see below to find out more about the test configuration.

### Workspace set up

It should be sufficient to import this git repository into EGit and set the [releng/org.eclipse.papyrus.uml.diagram.sequence.targetplatform/org.eclipse.papyrus.uml.diagram.sequence.targetplatform.target](target platform).

### Testing

Test plug-ins must be located in the folder [`tests`](tests). During the build, every Java class named `*Test.java` or `*UITest.java` will be compiled and verified. If no running workbench is required, please call your test class `*Test.java`. If a workbench is required, name your test class `*UITest.java`. The maven configuration in folder [`tests`](tests) will run your test accordingly, i.e., with or without running the workbench and enabling the UI harness.

### Building

Run the following command from the repository root:

`mvn -f releng/org.eclipse.papyrus.uml.diagram.sequence.releng/pom.xml clean verify`
