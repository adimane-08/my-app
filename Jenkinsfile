pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        IMAGE_NAME = "java-app"
    }

    stages {

        stage('Checkout') {
            steps {
              git branch: 'main', url: 'https://github.com/adimane-08/my-app.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
              withSonarQubeEnv('SonarQube') {
                bat 'mvn sonar:sonar'
              }
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t java-app:latest .'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                bat 'kubectl apply -f k8s.yaml'
            }
        }
    }
}