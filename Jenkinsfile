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
          echo "deploying $version to ..."
      }
    }
  }
}
