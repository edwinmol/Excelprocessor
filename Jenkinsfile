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
    stage('Fetching from GIT'){
      steps {
		checkout scm
      }
    }
	stage('Compile') {
		steps {
			echo 'Compiling...'
			sh 'mvn -B clean compile'
		}
	}
	stage('Test') {
		steps {
			echo 'Testing...'
			sh 'mvn -B test'
		}
	}
    stage('Package') {
      steps {
          sh 'mvn -B -DskipTests=true package'
      }
    }
    stage('Echo') {
      steps {
        echo "$version"
      }
    }
  }
}
