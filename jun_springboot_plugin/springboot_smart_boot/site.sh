#/bin/bash
curDir=$(cd `dirname $0`; pwd)
mvn clean
mvn site:site -f ${curDir}/site_pom.xml
