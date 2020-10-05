pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }

        stage('Unit tests') {
            steps {
                sh 'mvn test'
            }
        }
    }
}