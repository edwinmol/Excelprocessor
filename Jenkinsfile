pipeline {
  agent any
  tools {
    maven 'M3'
    jdk 'J8'
  }
  environment {
     version = '0.0.0'
  }
  stages {
    stage('Build') {
      steps {
          script {
             def pom = readMavenPom file: 'pom.xml'
             version = pom.version
          }
          sh 'mvn -B clean install'
      }
    }
    stage('Dummy') {
      steps {
        echo 'version from pom = ${version}'
      }
    }
  }
}
