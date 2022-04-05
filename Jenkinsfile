pipeline {
    agent any

    environment {
        GIT_TAG = sh([script: 'git fetch --tag && git tag', returnStdout: true]).trim()
        MAJOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1| cut -d . -f 1', returnStdout: true]).trim()
        MINOR_VERSION = sh([script: 'git tag | sort --version-sort | tail -1| cut -d . -f 2', returnStdout: true]).trim()
        PATCH_VERSION = sh([script: 'git tag | sort --version-sort | tail -1| cut -d . -f 3', returnStdout: true]).trim()
        IMAGE_TAG = "${env.MAJOR_VERSION}.\$((${env.MINOR_VERSION} + 1)).${env.PATCH_VERSION}"
        GITHUB_TOKEN = credentials("github_token")
    }

    stages {
        stage('Build & Test') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Tag image') {
              steps {
                    sh "docker build -t fmininjava/hello-img:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION} ."

                    withCredentials([string(credentialsId: 'dockerhub-pass', variable: 'DOCKER_PASSWORD')]) {
                        sh '''
                            docker login docker.io -u fmininjava -p ${DOCKER_PASSWORD}
                            docker push fmininjava/hello-img:${MAJOR_VERSION}.\$((${MINOR_VERSION} + 1)).${PATCH_VERSION}
                        '''
                    }

                    sh "git tag ${env.IMAGE_TAG}"
                    sh "git push https://$env.GITHUB_TOKEN@github.com/fmi-ninjava/service.git ${env.IMAGE_TAG}"
              }
        }
        stage('Deploy') {
              steps {
                    sh "IMAGE_TAG=${env.IMAGE_TAG} docker-compose up -d hello"
              }
        }
//         stage('Test') {
//               steps {
//                     sh "./gradlew testE2E"
//               }
//         }
    }
}
