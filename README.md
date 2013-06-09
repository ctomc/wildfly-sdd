WildFly silly deployment detector(SDD) extension
=========================================

This repository contains undertow subsystem and integration module that enables to install extension into existing WildFly instalation.

To install to existing WildFly run
> mvn clean install -Pupdate-as -Dwildfly.home=/path/to/as8

The -Dwildfly.home is not necessary if $JBOSS_HOME is already pointing at your WildFly installation.

after install is done you can run WildFly with SDD subsystem by running

> ./standalone.sh -c standalone-sdd.xml

