pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        IMAGE_NAME = "java-app"
        KUBECONFIG = 'C:\\Users\\Aditya\\.kube\\config'
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
                    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                        bat """
                        mvn sonar:sonar ^
                        -Dsonar.projectKey=java-app ^
                        -Dsonar.host.url=http://localhost:9000 ^
                        -Dsonar.login=%SONAR_TOKEN%
                        """
            }
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
              bat """
                kubectl set image deployment/java-app java-app=%IMAGE_NAME%
                kubectl rollout status deployment/java-app
                """
            }
        }

        
        // stage('Update Deployments') {
        //     steps {
        //         bat """
        //         kubectl set image deployment/java-app myapp=adimane0801/myapp:%BUILD_NUMBER%
        //         kubectl rollout restart deployment  myapp-deployment
        //         kubectl rollout status deployment  myapp-deployment
        //         """
        //     }
        // }
    }
    }
}