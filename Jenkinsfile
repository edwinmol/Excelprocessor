pipeline {
  agent any
  tools {
    maven 'M3'
    jdk 'J8'
  }
  environment {
      version = readMavenPom().getVersion()
  }
  stages {
    stage('Build') {
      steps {
          sh 'mvn -B clean install'
      }
    }
    stage('Echo') {
      steps {
        echo "$version"
      }
    }
  }
}
