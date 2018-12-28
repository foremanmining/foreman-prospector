#!/bin/bash

PROSPECTOR_HOME=$(cd `dirname $0`/..; pwd)

JAVA=$(command -v java)
if [ -z "$JAVA" ]; then
    if [ -x "$JAVA_HOME/bin/java" ]; then
        JAVA="$JAVA_HOME/bin/java"
    fi

    if [ ! -x "$JAVA" ]; then
        echo "Couldn't find java - set JAVA_HOME or add java to the path"
        exit 1
    fi
fi

# JVM classpath
JVM_CLASSPATH="$PROSPECTOR_HOME"/lib/*

# JVM parameters
JVM_PARAMS="-Dlogback.configurationFile=$PROSPECTOR_HOME/etc/logback.xml"
JVM_PARAMS+=" -Dio.netty.tryReflectionSetAccessible=false"

exec $JAVA \
    $JVM_PARAMS \
    -cp "$JVM_CLASSPATH" \
    mn.foreman.prospector.app.Main