#!/bin/sh

BASE="$(dirname $(readlink -f $0))"
LIB_PATH="$BASE/lib"
if [ ! -d $LIB_PATH ]
then
        echo "ERROR:$LIB_PATH not exists"
        exit -1
fi

export PATH=$JAVA_HOME/bin:$PATH

CLASSPATH=.
for i in $LIB_PATH/*.jar;
  do
     CLASSPATH="$CLASSPATH":$i;
  done

echo $CLASSPATH

java -server -Xmn1g -Xms4g -Xmx4g -Dfile.encoding=UTF-8 -Dzutils=apache -XX:PermSize=128m -XX:MaxPermSize=256m -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70 -XX:OnOutOfMemoryError="kill -9 %p" -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:logs/gc.log -cp $CLASSPATH com.asiainfo.billing.drescaping.util.TripleDes -e $1

