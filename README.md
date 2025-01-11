# Clique
Clique is our implementation of project 2 of Revature's Java Fullstack Training course. \
This is a group project in which teams are required to extend the functionality of their Social Networking Application by integrating DevOps practices and tools like Docker and Jenkins. \
The goal is to deploy a fully functional, containerized application with CI/CD pipelines and cloud-hosted infrastructure. 

## Project Requirements:
1. Implement Cloud Infrastructure
    - Host the application on AWS using EC2 and RDS
    - Use IAM roles to manage access and permissions securely
2. Containerization: 
    - Dockerize the backend application. Frontend is optional.
3. Continuous Integration/Continuous Deployment (CI?CD):
    - Create a Jenkins pipeline to automate testing, building, and deploying the application
4. Scalability and Monitoring (Optional)
    - Implement auto-scaling using AWS features.
    - Set up monitoring and logging using AWS CloudWatch or similar tools. 

### Project 1 Requirements: 
- User registration and authentication
- User login
- User profile management
- Connect with other users (follow/friend)
- Create posts
- Comment on posts
- Like/react to posts
- Search for users, posts, and content

## Instructions for Setting Up
If you want to fork this repository and deploy your own verison of our application in the same way we did you can follow these steps:
### Required Software and AWS Services 
- [Docker](https://docs.docker.com/), to dockerize your backend and to run the container.
- [Jenkins](https://www.jenkins.io/doc/), either locally running or running on a server. 
    - This project runs Jenkins on an EC2 instance and uses a second EC2 instance as an agent node.
- An [EC2](https://docs.aws.amazon.com/ec2/) instance to run the backend container.
- An [S3](https://docs.aws.amazon.com/s3/) bucket to host the frontend.
- [RDS](https://docs.aws.amazon.com/rds/) or any database administration/management tool of your choice. 

### Configuring the Backend:
1. Update ```application.properties``` to configure the connection to your database.
    - Properties to be updated:
        - spring.datasource.url
        - spring.datasource.username
        - spring.datasource.password

### Configuring Your Jenkins
1. You'll have to create a pipeline in Jenkins that is configured as "Pipeline script from SCM" and have the SCM set to your GitHub repository.
2. Enable the setting "GitHub hook trigger for GITScm polling."
3. Setup [GitHub Webhooks](https://docs.github.com/en/webhooks/about-webhooks) in your repo.

### Editing the Jenkinsfile
1. You'll need to reconfigure several lines in the Jenkinsfile.
    - Lines 4-8 are environment variables:
        - ```AWS_REGION = 'your-aws-region'```
        - ```ECR_REPO_URI = <link-to-your-ecr-repo>```
        - ```TARGET_EC2 = <link-to-your-server-EC2```
        - ```MAVEN_HOME = 'path/to/maven```
        - ```PATH = "$MAVEN_HOME/bin:$PATH"```
    - Line 29, you need to update the shell command:
        - ```sh 'aws s3 sync build/ s3://your-s3-arn'```
    - Lines 49 and 63, you'll have to update the credentialsId to match the ids of the credentials you've configured in Jenkins. 

### Once you've made these configurations, you can manually trigger your first build in Jenkins or commit your changes to your repo to trigger the build process automatically. 



