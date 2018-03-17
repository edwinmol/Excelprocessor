pipeline {
  agent any
  tools {
    maven 'M3'
    jdk 'J8'
  }
  stages {
    stage('Build') {
      steps {
          sh 'mvn -B clean install'
      }
    }
  }
}
