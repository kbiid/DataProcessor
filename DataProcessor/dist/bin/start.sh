#!/bin/sh
export WORK_HOME=/home/kbiid/Dataprocessor/dist
export CLASSPATH=.
export CLASSPATH=$CLASSPATH:"$WORK_HOME"/lib/slf4j-api-1.7.26.jar
export CLASSPATH=$CLASSPATH:"$WORK_HOME"/lib/slf4j-log4j12-1.7.26.jar
export CLASSPATH=$CLASSPATH:"$WORK_HOME"/lib/slf4j-simple-1.7.26.jar
export CLASSPATH=$CLASSPATH:"$WORK_HOME"/lib/commons-io-2.6.jar
export CLASSPATH=$CLASSPATH:"$WORK_HOME"/lib/log4j-1.2.17.jar
export CLASSPATH=$CLASSPATH:"$WORK_HOME"/lib/logback-classic-1.2.3.jar
export CLASSPATH=$CLASSPATH:"$WORK_HOME"/lib/logback-core-1.2.3.jar
export CLASSPATH=$CLASSPATH:"$WORK_HOME"/lib/gson-2.8.5.jar
export CLASSPATH=$CLASSPATH:"$WORK_HOME"/lib/mariadb-java-client-2.3.0.jar
export CLASSPATH=$CLASSPATH:"$WORK_HOME"/lib/mybatis-3.4.6.jar
export CLASSPATH=$CLASSPATH:"$WORK_HOME"/lib/c3p0-0.9.5.4.jar
export CLASSPATH=$CLASSPATH:"$WORK_HOME"/lib/hibernate-commons-annotations-5.1.0.Final.jar
export CLASSPATH=$CLASSPATH:"$WORK_HOME"/lib/hibernate-core-5.4.2.Final.jar
export CLASSPATH=$CLASSPATH:"$WORK_HOME"/lib/DataProcessor-1.0.0.jar

java -Dconfig.properties=/home/kbiid/Dataprocessor/dist/conf/config.properties -classpath "$CLASSPATH" kr.co.torpedo.dataprocessor.Main
