#!/usr/bin/env bash

version=3.6.3
while getopts ":v:" optname
do
   case "$optname" in
     "v")
       version=$OPTARG
       echo "The maven version is $OPTARG";;
     ":")
       echo "No argument value for option $OPTARG"
       exit 1;;
     "?")
       echo "Unknown option $OPTARG"
       exit 1;;
     *)
       echo "Unknown error while processing options"
       exit 1;;
   esac
done
cd ~
mkdir maven
cd maven
tar xzvf apache-maven-${version}-bin.tar.gz
cd apache-maven-${version}/bin
MAVEN_HOME=$(pwd)
if [[ ! -x ~/.bash_profile ]];
then
touch ~/.bash_profile
fi
echo "export PATH=$PATH:${MAVEN_HOME}" >> ~/.bash_profile
source ~/.bash_profile
mvn -v
echo "maven install success"
echo "start set maven config"
cd ..
cd conf
cat > settings.xml << EOF
<?xml version="1.0" encoding="UTF-8"?>

<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <localRepository>/root/maven/.m2/repository</localRepository>
  <offline>true</offline>
  <pluginGroups>
  </pluginGroups>
  <proxies>
  </proxies>
  <servers>
  </servers>
  <mirrors>
  </mirrors>
  <profiles>
  </profiles>
</settings>
EOF
echo "set maven config end"
