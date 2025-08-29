pipeline {
    agent any

    tools {
        maven 'Maven_3.9.11'  
        jdk 'jdk17'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/SarveshMorey/TaskManagmentSystem.git'
            }
        }

        stage('Build Backend') {
            steps {
                dir('springboot_tms') {
                    bat 'mvn clean install -DskipTests'
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir('tsm') {
                    bat 'npm install'
                    bat 'npm run build'
                }
            }
        }
    }
}
