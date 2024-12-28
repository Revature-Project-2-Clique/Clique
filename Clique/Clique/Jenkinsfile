pipeline {
    agent any
    environment {
        AWS_REGION = 'us-east-1' 
        ECR_REPO_URI = '851725174311.dkr.ecr.us-east-1.amazonaws.com/clique-app'
        ECS_CLUSTER = 'Project2Cluster'
        ECS_SERVICE = 'clique-app'
    }
    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t clique-app .'
            }
        }
        stage('Tag and Push to ECR') {
            steps {
                sh '''
                # Authenticate Docker to ECR
                aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $ECR_REPO_URI
                
                # Tag and push the image
                docker tag clique-app:latest $ECR_REPO_URI:latest
                docker push $ECR_REPO_URI:latest
                '''
            }
        }
        stage('Deploy to ECS') {
            steps {
                sh '''
                aws ecs update-service --cluster $ECS_CLUSTER --service $ECS_SERVICE --force-new-deployment
                '''
            }
        }
    }
}