#!/bin/bash

MY_PWD=$PWD

APP_VERSION=0.1.1
DEPLOY_DIR=/var/play/multicast-sensor
SOURCE_DIR=~/dev/multicast-sensor
DIST_URL="https://github.com/pereirazc/multicast-sensor/archive/v${APP_VERSION}.zip"
#old "https://dl.dropboxusercontent.com/s/574f0euooypidr4/multicast-sensor.zip?dl=1&token_hash=AAGMImky-zALXUmxgLrcpoO9zCw59fBXZH8g6jG6HFSreQ"
TMPFILE=multicast-sensor.zip

service nginx stop
service multicast-sensor stop

rm -rf $DEPLOY_DIR/bin/*
rm -rf $DEPLOY_DIR/conf/*
rm -rf $DEPLOY_DIR/lib/*
rm -rf $DEPLOY_DIR/logs/*
rm -rf $DEPLOY_DIR/share/*

if [ -d "$SOURCE_DIR" ]; then
        rm -rf $SOURCE_DIR/*
else
        mkdir $SOURCE_DIR
fi

wget $DIST_URL -O $TMPFILE
unzip -d $SOURCE_DIR $TMPFILE

cd $SOURCE_DIR
play clean compile dist
cd $MY_PWD

unzip -d $SOURCE_DIR/target/universal $SOURCE_DIR/target/universal/multicast-sensor-${APP_VERSION}.zip

mv $SOURCE_DIR/target/universal/multicast-sensor-${APP_VERSION}/bin/* $DEPLOY_DIR/bin
mv $SOURCE_DIR/target/universal/multicast-sensor-${APP_VERSION}/conf/* $DEPLOY_DIR/conf
mv $SOURCE_DIR/target/universal/multicast-sensor-${APP_VERSION}/lib/* $DEPLOY_DIR/lib
mv $SOURCE_DIR/target/universal/multicast-sensor-${APP_VERSION}/logs/* $DEPLOY_DIR/logs
mv $SOURCE_DIR/target/universal/multicast-sensor-${APP_VERSION}/share/* $DEPLOY_DIR/share

chmod +x $DEPLOY_DIR/bin/multicast-sensor

service multicast-sensor start
service nginx start

