WildFly silly deployment detector(SDD) extension
================================================

This repository contains undertow subsystem and integration module that builds WildFly 9 with extra subsystem

To create wildfly distro with sdd subsystem run:
> mvn clean install

The -Dwildfly.home is not necessary if $JBOSS_HOME is already pointing at your WildFly installation.

after install is done you can run WildFly with SDD subsystem by running

> ./standalone.sh -c standalone.xml

