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
            junit '**/target/surefire-reports/*.xml'
		}
	}
    stage('Archive') {
      steps {
          sh 'mvn -B -DskipTests=true install'
      }
    }
    stage('Deploy') {
      steps {
          if (currentBuild.result == null || currentBuild.result == 'SUCCESS') {
              echo "deploying $version to ..."
          }
      }
    }
  }
}
