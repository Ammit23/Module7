pipeline {
  agent any
  options{
  buildDiscarder(logRotator(numToKeepStr: '5'))
  }
  stages {
    stage('Scan') {

      steps {
        withSonarQubeEnv(installationName: 'sq1'){
        bat './gradlew clean sonar'}
      }
    }
  }
}

