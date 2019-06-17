FROM fabric8/java-centos-openjdk11-jre:1.6

ARG version

USER root

ENV JAVA_APP_JAR=gafanhoto-$version.jar \
    AB_OFF=true \
    TZ=America/Sao_Paulo 
    
COPY target/gafanhoto-$version.jar /deployments