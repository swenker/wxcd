#!/bin/bash
mvn clean install && rm -r ~/tomcat/webapps/ycweb* && cp target/ycweb-1.0.war ~/tomcat/webapps/
