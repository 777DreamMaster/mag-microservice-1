def mainParams = [
        gitUrl  : "https://github.com/777DreamMaster/mag-microservice-1",
        branch: "master",
        username: "777DreamMaster",
        docker_hub_password: "123"
]

pipeline {
    agent any

    parameters {
        string(name: 'IMAGE_NAME', description: 'Docker образ для сборки и деплоя')
        string(name: 'IMAGE_VERSION', description: 'Версия Docker-образа')
        string(name: 'DEPLOYMENT_FILE', description: 'Файл деплоймента Kubernetes')
        string(name: 'PARAMS_FILE', description: 'Файл переменных окружения')
    }

    stages {
        stage('Load Environment Variables') {
            steps {
                script {
                    if (fileExists(params.PARAMS_FILE)) {
                        def envVars = readProperties file: params.PARAMS_FILE
                        envVars.each { key, value ->
                            env[key] = value
                            echo "Loaded env: ${key}=${value}"
                        }
                    } else {
                        echo "Environment file ${params.PARAMS_FILE} not found."
                    }
                }
            }
        }

        stage('Clone Repository') {
            steps {
                git branch: ${mainParams.branch}, credentialsId: 'github-credentials', url: ${mainParams.gitUrl}
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def image = "${mainParams.username}/${params.DOCKER_IMAGE}:${params.IMAGE_VERSION}"
                    echo "Building Docker image: ${image}"
                    sh "docker build -t ${image} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    def image = "${mainParams.username}/${params.DOCKER_IMAGE}:${params.IMAGE_VERSION}"
                    echo "Pushing Docker image: ${image}"
                    sh "docker login -u ${mainParams.username} -p ${mainParams.docker_hub_password}"
                    sh "docker push ${image}"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    echo "Deploying using file: ${params.DEPLOYMENT_FILE}"
                    sh "kubectl apply -f ${params.DEPLOYMENT_FILE}"
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    sh "kubectl rollout status -f ${params.DEPLOYMENT_FILE}"
                }
            }
        }
    }
}
