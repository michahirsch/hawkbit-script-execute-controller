pipeline {
  agent any
  stages {
    stage('compile') {
      steps {
        parallel(
          "compile": {
            sh 'mvn compile'
            
          },
          "": {
            sleep 10
            
          }
        )
      }
    }
  }
}