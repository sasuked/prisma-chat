pipeline {
    agent any

    environment {
        GRADLE_HOME = tool name: 'Gradle', type: 'hudson.plugins.gradle.GradleTool'
        PATH = "${GRADLE_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                // Verifica o código-fonte do repositório Git
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Executa o comando 'shadowJar' para criar o JAR sombreado
                sh './gradlew shadowJar'
            }
        }
    }

    post {
        always {
            // Sempre limpe após a compilação
            cleanWs()
        }
    }
}