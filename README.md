WildFly silly deployment detector(SDD) extension
================================================

This repository contains undertow subsystem and integration module that builds WildFly 10 with extra subsystem

To create wildfly distro with sdd subsystem run:
> mvn clean install

after install is done you can run WildFly with SDD subsystem by going to build/target/wildfly-sdd-${project.version}/bin
and run server with
> ./standalone.sh|.bat|.ps1

Feature pack
------------

As part of this build "wildfly-sdd" feature pack is created which is later on used to create wildfly distribution.
WildFly servlet is used as "base" feature pack so that build / test workflow is faster.
With small changes to "build" module it could be based off full WildFly.
