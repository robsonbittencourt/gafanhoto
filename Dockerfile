FROM fabric8/java-centos-openjdk8-jre:1.3.1

ARG version

ENV JAVA_APP_JAR=gafanhoto-$version.jar \
    AB_OFF=true \
    TZ=America/Sao_Paulo 
    
COPY target/gafanhoto-$version.jar /deployments