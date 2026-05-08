pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo "Building branch: ${env.BRANCH_NAME}"
            }
        }
        stage('Deploy') {
            steps {
                script {
                    if (env.BRANCH_NAME == 'main') {
                        echo "Deploying to PRODUCTION server..."
                    } else if (env.BRANCH_NAME == 'dev') {
                        echo "Deploying to STAGING server..."
                    } 
                     else if (env.BRANCH_NAME == 'qa') {
                        echo "Deploying to QA server..."
                    } else {
                        echo "Feature branch detected. Skipping deployment, only running tests."
                    }
                }
            }
        }

