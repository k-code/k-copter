#!/bin/sh

mvn install:install-file -Dfile=gluegen.jar -DgroupId=org.jogamp -DartifactId=gluegen -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=gluegen-rt.jar -DgroupId=org.jogamp -DartifactId=gluegen-rt -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=jogl.all.jar -DgroupId=org.jogamp -DartifactId=jogl -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=Joystick.jar -DgroupId=com.centralnexus -DartifactId=Joystick -Dversion=0.7 -Dpackaging=jar
mvn install:install-file -Dfile=nrjavaserial-3.8.4.jar -DgroupId=com.neuronrobotics -DartifactId=nrjavaserial -Dversion=3.8.4 -Dpackaging=jar

exit 0

