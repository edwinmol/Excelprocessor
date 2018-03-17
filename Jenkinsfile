pipeline {
  agent any
  tools {
    maven 'M3'
    java8 'J8'
  }
  stages {
    stage('Build') {
      steps {
          sh 'mvn -B clean install'
      }
    }
  }
}
