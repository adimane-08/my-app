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
              withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                bat """
                  echo %PASS% | docker login -u %USER% --password-stdin
                  docker build -t java-app:latest .
                  docker tag java-app:latest adimane0801/java-app:%BUILD_NUMBER%
                  docker push adimane0801/java-app:%BUILD_NUMBER%
                  """
              }
            }
        }


        stage('Deploy to Kubernetes') {
            steps {
              bat """
                kubectl set image deployment/java-app java-app=adimane0801/java-app:%BUILD_NUMBER%
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