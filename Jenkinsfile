pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        def mvn_version = 'M3'
        def java_version = 'J8'
        withEnv( ["PATH+MAVEN=${tool mvn_version}/bin","JAVA_HOME=${tool java_version}] ) {          
          sh 'mvn clean install'
        }
      }
    }
  }
}
