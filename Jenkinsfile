pipeline {
    agent any
    environment {
        AWS_REGION = 'us-east-1' 
        ECR_REPO_URI = '851725174311.dkr.ecr.us-east-1.amazonaws.com/clique-app'
        TARGET_EC2 = 'ec2-3-82-150-19.compute-1.amazonaws.com'
        MAVEN_HOME = '/opt/maven'
        PATH = "$MAVEN_HOME/bin:$PATH"
    }
    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }
        stage('Build Frontend') {
            steps {
                dir('clique-frontend') {
                    sh '''
                        yarn install
                        npm run build
                    '''
                }
            }
        }
        stage('Deploy Frontend on S3') {
            steps {
                dir('clique-frontend') {
                    sh 'aws s3 sync build/ s3://clique-app-bucket'
                }
            }
        }
        stage('Build Backend') {
            steps {
                dir('Clique/Clique') {
                    sh 'mvn clean package'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                dir('Clique/Clique') {
                    sh 'docker build -t clique-app .'
                }
            }
        }
        stage('Tag and Push to ECR') {
            steps {
                withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-credentials']]) {
                    sh '''
                    # Authenticate Docker to ECR
                    aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $ECR_REPO_URI
                    
                    # Tag and push the image
                    docker tag clique-app:latest $ECR_REPO_URI:latest
                    docker push $ECR_REPO_URI:latest
                    '''
                }
            }
        }
        stage('Deploy Backend on EC2') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'ssh-key-credentials', keyFileVariable: 'SSH_KEY')]) {
                    sh """
                        chmod 600 \$SSH_KEY
                        ssh -o StrictHostKeyChecking=no -i \$SSH_KEY ec2-user@${TARGET_EC2} 'bash deploy.sh'
                    """
                }
            }
        }
    }
}
