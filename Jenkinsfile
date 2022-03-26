pipeline {
    agent any
    environment {
        DOCKER_PASSWORD = credentials("dockerhub_pass")
    }

    stages {
        stage('Build & Test') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Tag image') {
              steps {
                script {
                    GIT_TAG = sh([script: 'git fetch --tag && git tag', returnStdout: true]).trim()
                    MAJOR_VERSION = sh([script: 'git tag | cut -d . -f 1', returnStdout: true]).trim()
                    MINOR_VERSION = sh([script: 'git tag | cut -d . -f 2', returnStdout: true]).trim()
                    PATCH_VERSION = sh([script: 'git tag | cut -d . -f 3', returnStdout: true]).trim()
                }
                sh "docker build -t micu01/hello-img:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION} ."
              }
        }
        stage('Publish image') {
            steps {
               sh "docker login docker.io -u micu01 -p ${DOCKER_PASSWORD}"
               sh "docker push <username>/hello-img:$IMAGE_VERSION"
            }
        }
    }
}
