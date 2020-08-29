pipeline {
    agent any
    options { timestamps () }

    tools {
        maven 'maven-3'
        jdk 'open-jdk11'
    }

    stages {
        stage('Pull from scm') {
            steps {
                git url: 'https://github.com/robsonbittencourt/gafanhoto'                
            }
        }

        stage('Build JAR') {
            steps {                
                withMaven(maven: 'maven-3') {
                    sh 'mvn clean verify'
                }
            }
        }
    }
}